package br.upe.lojao.negocios;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import br.upe.lojao.persistencia.IPersistenciaContrato;
import br.upe.lojao.persistencia.PersistenciaContratos;

public class OperacaoMultas implements IOperacaoMultas {

    private IPersistenciaContrato persistencia = new PersistenciaContratos();

    public int gerarId() {
        int novoId = persistencia.maiorIdMulta() + 1;
        return novoId;
    }

    public boolean verificarExclusão(int idMulta) {
        boolean resultado = false;
        Ocorrencias multa = persistencia.buscarMulta(idMulta);
        if (multa.id() != -1) {
            resultado = multa.status().equals("PAGO");
        }
        return resultado;
    }

    public BigDecimal calcularMulta(int idContrato) {
        BigDecimal resultado = new BigDecimal("0");
        Contrato contrato = persistencia.buscarContrato(idContrato);

        if (contrato.id() != -1 && contrato.diasAlugados() > 0) {
            BigDecimal taxaDiaria = contrato.valorTotal().divide(new BigDecimal(contrato.diasAlugados()), 2, java.math.RoundingMode.HALF_UP);
            long diasAtraso = Duration.between(contrato.dataFinal(), LocalDateTime.now()).toDays();

            if (diasAtraso > 0) {
                BigDecimal percentualDiario = taxaDiaria.multiply(new BigDecimal("0.10"));
                BigDecimal totalPercentual = percentualDiario.multiply(new BigDecimal(diasAtraso));
                resultado = totalPercentual.add(new BigDecimal("20"));
            }
        }

        return resultado;
    }

    public boolean aplicarMulta(int idContrato) {
        boolean resultado = false;
        Contrato contrato = persistencia.buscarContrato(idContrato);

        if (contrato.id() != -1) {
            BigDecimal valorMulta = calcularMulta(idContrato);
            BigDecimal zero = new BigDecimal("0");

            if (valorMulta.compareTo(zero) > 0) {
                int idMulta = contrato.idMulta();
                if (idMulta == 0) {
                    idMulta = gerarId();
                }

                Contrato contratoAtualizado = new Contrato(
                    contrato.id(), contrato.idCliente(), contrato.dataInicio(), contrato.dataFinal(),
                    contrato.diasAlugados(), contrato.valorTotal(), contrato.idItem(),
                    contrato.status(), idMulta, valorMulta
                );

                Ocorrencias multaExistente = persistencia.buscarMulta(idMulta);

                if (multaExistente.id() == -1) {
                    Ocorrencias novaMulta = new Ocorrencias(
                        idMulta, idContrato, contrato.idCliente(),
                        contrato.valorTotal(), contrato.dataInicio(), contrato.dataFinal(),
                        valorMulta, new BigDecimal("0.10"), "", "PENDENTE"
                    );
                    boolean contratoSalvo = persistencia.atualizarContrato(contratoAtualizado);
                    boolean multaSalva = persistencia.adicionarMulta(novaMulta);
                    resultado = contratoSalvo && multaSalva;
                } else {
                    Ocorrencias multaAtualizada = new Ocorrencias(
                        multaExistente.id(), multaExistente.idContrato(), multaExistente.idCliente(),
                        multaExistente.valorBase(), multaExistente.dataInicio(), multaExistente.dataFinal(),
                        valorMulta, multaExistente.valorPorcentagem(), multaExistente.avarias(), multaExistente.status()
                    );
                    boolean contratoSalvo = persistencia.atualizarContrato(contratoAtualizado);
                    boolean multaSalva = persistencia.atualizarMulta(multaAtualizada);
                    resultado = contratoSalvo && multaSalva;
                }
            }
        }

        return resultado;
    }

    public boolean marcarPago(int idMulta) {
        boolean resultado = false;
        Ocorrencias multa = persistencia.buscarMulta(idMulta);

        if (multa.id() != -1) {
            Ocorrencias multaPaga = new Ocorrencias(
                multa.id(), multa.idContrato(), multa.idCliente(),
                multa.valorBase(), multa.dataInicio(), multa.dataFinal(),
                multa.valorFinal(), multa.valorPorcentagem(), multa.avarias(), "PAGO"
            );
            resultado = persistencia.atualizarMulta(multaPaga);
        }

        return resultado;
    }

    public boolean deletarMulta(int idMulta) {
        boolean resultado = false;

        if (verificarExclusão(idMulta)) {
            Ocorrencias multa = persistencia.buscarMulta(idMulta);
            Contrato contrato = persistencia.buscarContrato(multa.idContrato());

            if (contrato.id() != -1) {
                Contrato contratoSemMulta = new Contrato(
                    contrato.id(), contrato.idCliente(), contrato.dataInicio(), contrato.dataFinal(),
                    contrato.diasAlugados(), contrato.valorTotal(), contrato.idItem(),
                    contrato.status(), 0, new BigDecimal("0")
                );
                boolean contratoSalvo = persistencia.atualizarContrato(contratoSemMulta);
                boolean multaDeletada = persistencia.deletarMulta(idMulta);
                resultado = contratoSalvo && multaDeletada;
            }
        }

        return resultado;
    }

    public boolean registrarAvaria(int idMulta, String avaria) {
        boolean resultado = false;
        Ocorrencias multa = persistencia.buscarMulta(idMulta);

        if (multa.id() != -1) {
            Ocorrencias multaComAvaria = new Ocorrencias(
                multa.id(), multa.idContrato(), multa.idCliente(),
                multa.valorBase(), multa.dataInicio(), multa.dataFinal(),
                multa.valorFinal(), multa.valorPorcentagem(), avaria, multa.status()
            );
            resultado = persistencia.atualizarMulta(multaComAvaria);
        }

        return resultado;
    }

    public List<Ocorrencias> multasPendentes() {
        ArrayList<Ocorrencias> multasPendentes = new ArrayList<>();
        ArrayList<Ocorrencias> todasMultas = persistencia.lerMultas();

        if (todasMultas.isEmpty() || todasMultas.get(0).id() == -1) {
            Ocorrencias erro = new Ocorrencias(-1, -1, -1, BigDecimal.ZERO, LocalDateTime.MIN, LocalDateTime.MIN, BigDecimal.ZERO, BigDecimal.ZERO, "ERRO", "ERRO");
            multasPendentes.add(erro);
        } else {
            for (int x = 0; x < todasMultas.size(); x++) {
                if (todasMultas.get(x).status().equals("PENDENTE")) {
                    multasPendentes.add(todasMultas.get(x));
                }
            }
        }

        return multasPendentes;
    }

    public List<Ocorrencias> buscarMultaCliente(int idCliente) {
        ArrayList<Ocorrencias> multasDoCliente = new ArrayList<>();
        ArrayList<Ocorrencias> todasMultas = persistencia.lerMultas();

        if (todasMultas.isEmpty() || todasMultas.get(0).id() == -1) {
            Ocorrencias erro = new Ocorrencias(-1, -1, -1, BigDecimal.ZERO, LocalDateTime.MIN, LocalDateTime.MIN, BigDecimal.ZERO, BigDecimal.ZERO, "ERRO", "ERRO");
            multasDoCliente.add(erro);
        } else {
            for (int x = 0; x < todasMultas.size(); x++) {
                if (todasMultas.get(x).idCliente() == idCliente) {
                    multasDoCliente.add(todasMultas.get(x));
                }
            }
        }

        return multasDoCliente;
    }
}