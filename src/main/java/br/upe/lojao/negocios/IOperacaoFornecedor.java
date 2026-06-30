package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.entidades.Fornecedor;

import java.util.List;

/**
 * Interface da camada de NEGÓCIOS para Fornecedor.
 * Mesmo papel da IOperacaoCategoria: define o contrato que
 * OperacaoFornecedor deve seguir, permitindo que outras classes
 * (como a Facade) dependam da interface, não da implementação.
 */
public interface IOperacaoFornecedor {

    boolean cadastrarFornecedor(String email, String telefone, String nome);

    List<Fornecedor> listarFornecedores();

    Fornecedor buscarPorId(int id);

    List<Fornecedor> buscarFornecedor(String nome);

    boolean editarFornecedor(int id, String novoEmail, String novoTelefone, String novoNome);

    boolean deletarFornecedor(int id);
}