package br.upe.lojao.persistencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;

public interface IPersistenciaContrato {
    ArrayList<Contrato> lerContratos();
    ArrayList<Ocorrencias> lerMultas();
    void carregarDados();
    int maiorIdContrato();
    int maiorIdMulta();
    Contrato buscarContrato(int id);
    boolean adicionarContrato(Contrato contrato);
    boolean atualizarContrato(Contrato contrato);
    boolean deletarContrato(int id);
    boolean itemContratoAtivo(int idItem);
    List<Ocorrencias> clienteMultaPendente(int idCliente);
    List<Contrato> clienteHistorico(int idCliente, int opcao);
    List<Contrato> contratosAtrasados();
    List<Contrato> contratosClienteAtivos(int idCliente);
    Ocorrencias buscarMulta(int id);
    boolean adicionarMulta(Ocorrencias multa);
    boolean atualizarMulta(Ocorrencias multa);
    boolean deletarMulta(int id);
    boolean escreverRelatorios(LocalDateTime dataInicio, LocalDateTime dataFinal, BigDecimal valor);
    List<Contrato> listarContratosAtivos();
    List<BigDecimal> valorContratosPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim, int opcao);
}