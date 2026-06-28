package br.upe.lojao.negocios;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.IPersistenciaContrato;
import br.upe.lojao.persistencia.IPersistenciaUsuario;
import br.upe.lojao.persistencia.PersistenciaContratos;
import br.upe.lojao.persistencia.PersistenciaUsuario;

public class OperacaoContrato implements IOperacaoContrato {

    private IPersistenciaContrato persistencia = new PersistenciaContratos();

    public int gerarId() {
        return persistencia.maiorIdContrato() + 1;
    }

    public boolean verificarExclusao(int idContrato) {
        boolean permissao;
    	Contrato excluido = persistencia.buscarContrato(idContrato);
        if (excluido.id() == -1) {
        	permissao = false;
        }
        else{
        	permissao = excluido.status().equals("CONCLUIDO");
        	}
        return permissao;
    }

    public BigDecimal calcularAluguel(long dias, int idProduto) {
        IPersistenciaProduto persistenciaProduto = new PersistenciaProduto();
        BigDecimal taxaDiaria = persistenciaProduto.getTaxaDiaria(idProduto);
        return taxaDiaria.multiply(new BigDecimal(dias));
    }

    public void verificarMultas() {
        List<Contrato> atrasados = persistencia.contratosAtrasados();
        if (atrasados.isEmpty() != true) {
        OperacaoMultas operacaoMultas = new OperacaoMultas();
        for (int x = 0; x < atrasados.size(); x++) {
            operacaoMultas.aplicarMulta(atrasados.get(x).id());
        }
        }
        persistencia.recarregar();

        for (int x = 0; x < atrasados.size(); x++) {
            Contrato atualizado = new Contrato(
                atrasados.get(x).id(), atrasados.get(x).idCliente(), atrasados.get(x).dataInicio(), atrasados.get(x).dataFinal(), atrasados.get(x).diasAlugados(), atrasados.get(x).valorTotal(), atrasados.get(x).idItem(), "ATRASADO", atrasados.get(x).idMulta(), atrasados.get(x).valorMulta());
            persistencia.atualizarContrato(atualizado);
        }
    }

    public boolean registrar(int idProduto, LocalDateTime dataInicio, LocalDateTime dataFinal, int idCliente) {
    	boolean permissao;
    	if (persistencia.itemContratoAtivo(idProduto)) {
        	permissao = false;
        	}
        if (persistencia.clienteMultaPendente(idCliente)) {
        	permissao = false;
        }
        if (dataInicio.isAfter(dataFinal)) {
        	permissao = false;
        }
        if (dataFinal.isBefore(LocalDateTime.now())) {
        	permissao = false;
        }
        int id = gerarId();
        long diasAlugados = Duration.between(dataInicio, dataFinal).toDays();
        BigDecimal valorTotal = calcularAluguel(diasAlugados, idProduto);
        Contrato novoContrato = new Contrato(id, idCliente, dataInicio, dataFinal, diasAlugados, valorTotal, idProduto, "ATIVO", 0, new BigDecimal("0"));
        permissao = persistencia.adicionarContrato(novoContrato);
        return permissao;
    }

    public boolean atualizar(int idContrato, int valor, int opcao) {
        Contrato atualizar = persistencia.buscarContrato(idContrato);
        boolean permissao = true;
        
        if (atualizar.id() == -1) {
        	permissao = false;
        }

        Contrato atualizado;
        if (opcao == 1) {
            IPersistenciaUsuario persistenciaUsuario = new PersistenciaUsuario();
            if (persistenciaUsuario.clienteExiste(valor) == false) {
            	permissao = false;
            }
            atualizado = new Contrato(atualizar.id(), valor, atualizar.dataInicio(), atualizar.dataFinal(), atualizar.diasAlugados(), atualizar.valorTotal(), atualizar.idItem(), atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else if (opcao == 2) { 
            if (persistencia.itemContratoAtivo(valor)) {
            	permissao = false;
            }
            IPersistenciaProduto persistenciaProduto = new PersistenciaProduto();
            if (persistenciaProduto.produtoExiste(valor) == false) {
            	permissao = false;
            }
            BigDecimal novoValor = calcularAluguel(atualizar.diasAlugados(), valor);
            atualizado = new Contrato(atualizar.id(), atualizar.idCliente(), atualizar.dataInicio(), atualizar.dataFinal(), atualizar.diasAlugados(), novoValor, valor, atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else {
            permissao = false;
        }
        
        if (permissao != false) {
        permissao = persistencia.atualizarContrato(atualizado);
        }
        return permissao;
    }

    public boolean atualizar(int idContrato, LocalDateTime valor, int opcao) {
        Contrato atualizar = persistencia.buscarContrato(idContrato);
        boolean permissao = true;
        
        if (atualizar.id() == -1) {
        	permissao = false;
        }

        Contrato atualizado = null;
        if (opcao == 1) {
            if (valor.isAfter(atualizar.dataFinal())) {
            	permissao = false;
            }
            long diasAlugados = Duration.between(valor, atualizar.dataFinal()).toDays();
            BigDecimal novoValor = calcularAluguel(diasAlugados, atualizar.idItem());
            atualizado = new Contrato(atualizar.id(), atualizar.idCliente(), valor, atualizar.dataFinal(), diasAlugados, novoValor, atualizar.idItem(), atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else if (opcao == 2) { 
            if (atualizar.dataInicio().isAfter(valor)) return false;
            long diasAlugados = Duration.between(atualizar.dataInicio(), valor).toDays();
            BigDecimal novoValor = calcularAluguel(diasAlugados, atualizar.idItem());
            atualizado = new Contrato(atualizar.id(), atualizar.idCliente(), atualizar.dataInicio(), valor, diasAlugados, novoValor, atualizar.idItem(), atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else {
            permissao = false;
        }

        if (permissao != false) {
            permissao = persistencia.atualizarContrato(atualizado);
            }
            return permissao;
    }

    public boolean concluirContrato(int idContrato) {
        Contrato concluir = persistencia.buscarContrato(idContrato);
        if (concluir.id() == -1) return false;

        LocalDateTime dataFinal = concluir.dataFinal();
        long diasAlugados = concluir.diasAlugados();
        BigDecimal valorTotal = concluir.valorTotal();

        if (LocalDateTime.now().isBefore(dataFinal)) {
            dataFinal = LocalDateTime.now();
            diasAlugados = Duration.between(concluir.dataInicio(), dataFinal).toDays();
            valorTotal = calcularAluguel(diasAlugados, concluir.idItem());
        }

        Contrato concluido = new Contrato(concluir.id(), concluir.idCliente(), concluir.dataInicio(), dataFinal, diasAlugados, valorTotal, concluir.idItem(), "CONCLUIDO", concluir.idMulta(), concluir.valorMulta());
        boolean resultado = persistencia.atualizarContrato(concluido);

        if (resultado && concluir.idMulta() != 0) {
            OperacaoMultas operacaoMultas = new OperacaoMultas();
            operacaoMultas.marcarPago(concluir.idMulta());
        }

        return resultado;
    }

    public boolean deletarContrato(int idContrato) {
        if (!verificarExclusao(idContrato)) return false;
        return persistencia.deletarContrato(idContrato);
    }

    public Contrato buscarContrato(int idContrato) {
        return persistencia.buscarContrato(idContrato);
    }

    public List<Contrato> listarTodos() {
        return persistencia.lerContratos();
    }
}