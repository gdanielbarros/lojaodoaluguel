package br.upe.lojao.negocios;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;

public interface IOperacaoContrato {

    int gerarId();
    boolean verificarExclusao(int idContrato);
    BigDecimal calcularAluguel(long dias, int idProduto);
    void verificarMultas();
    boolean registrar(int idProduto, LocalDateTime dataInicio, LocalDateTime dataFinal, int idCliente);
    boolean atualizar(int idContrato, int valor, int opcao);
    boolean atualizar(int idContrato, LocalDateTime valor, int opcao);
    boolean concluirContrato(int idContrato);
    boolean deletarContrato(int idContrato);
    Contrato buscarContrato(int idContrato);
    List<Contrato> listarTodos();
    List<Contrato> listarAtivos(int idCliente);
    List<Ocorrencias> multasPendentes(int idCliente);
    List<Contrato> historicoCliente(int idCliente);
}