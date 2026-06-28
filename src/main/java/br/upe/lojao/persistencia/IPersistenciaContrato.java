package br.upe.lojao.persistencia;

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
    boolean atualizarContratos(List<Contrato> contratos);
    boolean atualizarMultas(List<Ocorrencias> multas);
    boolean deletarContrato(int id);
    boolean itemContratoAtivo(int idItem);
    List<Ocorrencias> clienteMultaPendente(int idCliente);
    List<Contrato> clienteHistorico(int idCliente);
    List<Contrato> contratosAtrasados();
    List<Contrato> contratosClienteAtivos(int idCliente);
    Ocorrencias buscarMulta(int id);
    boolean adicionarMulta(Ocorrencias multa);
    boolean atualizarMulta(Ocorrencias multa);
    boolean deletarMulta(int id);
}