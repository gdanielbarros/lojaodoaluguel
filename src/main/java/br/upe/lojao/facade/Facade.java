package br.upe.lojao.facade;

import br.upe.lojao.negocios.OperacaoUsuario;
import br.upe.lojao.negocios.IOperacaoUsuario;
import br.upe.lojao.negocios.OperacaoItem; 
import br.upe.lojao.negocios.IOperacaoItem; 
import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import br.upe.lojao.negocios.OperacaoContrato;
import br.upe.lojao.negocios.IOperacaoContrato;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import br.upe.lojao.persistencia.entidades.Produtos;
import br.upe.lojao.negocios.OperacaoMultas;
import br.upe.lojao.negocios.IOperacaoMultas;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import br.upe.lojao.negocios.IOperacaoCategoria;
import br.upe.lojao.negocios.OperacaoCategoria;
import br.upe.lojao.negocios.IOperacaoFornecedor;
import br.upe.lojao.negocios.OperacaoFornecedor;
import br.upe.lojao.persistencia.entidades.Categoria;
import br.upe.lojao.persistencia.entidades.Fornecedor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    private IOperacaoUsuario operacaoUsuario = new OperacaoUsuario();
    private IOperacaoMultas operacaoMultas = new OperacaoMultas();
    private IOperacaoItem operacaoItem = new OperacaoItem();
    private IOperacaoContrato operacaoContrato = new OperacaoContrato();
    private IOperacaoCategoria operacaoCategoria = new OperacaoCategoria();
    private IOperacaoFornecedor operacaoFornecedor = new OperacaoFornecedor();


    public boolean autenticarUsuario(String login, String senha, String tipo) {
        return operacaoUsuario.autenticarUsuario(login, senha, tipo) != -1;
    }

    
    public void verificarAtrasos() {
        operacaoContrato.verificarMultas();
    }

    
    public String cadastrarCliente(Cliente cliente){
        return operacaoUsuario.cadastrarCliente(cliente);
    }

    public String editarCliente(int id, int opcao, String dadoModificado){
        return operacaoUsuario.editarCliente(id,opcao,dadoModificado);
    }

    public String deletarCliente(int id){
        return operacaoUsuario.deletarCliente(id);
    }

    public ArrayList<Cliente> buscarCliente(String nome){
        return operacaoUsuario.buscarCliente(nome);
    }

    public String cadastreFuncionario(Funcionario contratado){
        return operacaoUsuario.cadastrarFuncionario(contratado);
    }

    public String editarFuncionario(int id, int opcao, String dadoModificado){
        return operacaoUsuario.editarFuncionario(id,opcao,dadoModificado);
    }

    public String deletarFuncionario(int id){
        return operacaoUsuario.deletarFuncionario(id);
    }
    

    public ArrayList<Funcionario> buscarFuncionario(String nome){
        return operacaoUsuario.buscarFuncionario(nome);
    }

    public ArrayList<Cliente> lerCliente(){
        return operacaoUsuario.listarClientes();
    }

   

    public ArrayList<Funcionario> lerFuncionario(){
        return operacaoUsuario.listarFuncionarios();
    }

   
    public ArrayList<Administrador> lerAdministrador(){
        return operacaoUsuario.listarAdministradores();
    }

    
    public List<Contrato> listarAtivos(int idCliente) {
        return operacaoContrato.listarAtivos(idCliente);
    }

    public List<Ocorrencias> multasPendentes(int idCliente) {
        return operacaoContrato.multasPendentes(idCliente);
    }

    public List<Contrato> historicoCliente(int idCliente, int opcao) {
        return operacaoContrato.historicoCliente(idCliente, opcao);
    }
    
    public boolean registrarAluguel(int idProduto, LocalDateTime dataInicio, LocalDateTime dataFinal, int idCliente) {
        return operacaoContrato.registrar(idProduto, dataInicio, dataFinal, idCliente);
    }

    public boolean processarDevolucao(int idContrato) {
        return operacaoContrato.concluirContrato(idContrato);
    }

    public List<Contrato> listarTodosContratos() {
        return operacaoContrato.listarTodos();
    }

    public java.math.BigDecimal faturamento(LocalDateTime dataInicio, LocalDateTime dataFim, int opcao) {
        return operacaoContrato.faturamento(dataInicio, dataFim, opcao);
    }

    public List<Contrato> contratosClienteEspecifico(int idCliente) {
        return operacaoContrato.listarContratosCliente(idCliente);
    }

    public List<Ocorrencias> multasPendentesGeral() {
        return operacaoMultas.multasPendentes();
    }

    public List<Ocorrencias> buscarMultaCliente(int idCliente) {
        return operacaoMultas.buscarMultaCliente(idCliente);
    }

    public boolean registrarAvaria(int idMulta, String avaria) {
        return operacaoMultas.registrarAvaria(idMulta, avaria);
    }

    public boolean marcarPago(int idMulta) {
        return operacaoMultas.marcarPago(idMulta);
    }

    public List<Produtos> listarItemNome(String nome) {
        return operacaoItem.buscarItem(nome);
    }
    public List<Produtos> listarTodosItens() {
        return operacaoItem.listarTodosItens();
    }
    public List<Produtos> listarItemDisponivel() {
        return operacaoItem.listarItemDisponivel();
    }

	public Produtos buscarProdutoPorId(int id) {
		return operacaoItem.buscarPorId(id);
	}

	public boolean cadastrarProduto(String nome, int idCategoria, int idFornecedor, java.math.BigDecimal taxaDiaria,
			String disponibilidade, String conservacao, java.math.BigDecimal valorReposicao) {
		Produtos produto = new Produtos(0, nome, taxaDiaria, idCategoria, idFornecedor, disponibilidade, conservacao, valorReposicao);
		return operacaoItem.cadastrarItem(produto);
	}

	public boolean editarProduto(int id, java.math.BigDecimal novaTaxa, String novaDisponibilidade,
			String novaConservacao, java.math.BigDecimal novoValorReposicao) {
		Produtos atual = operacaoItem.buscarPorId(id);
		if (atual == null) {
			return false;
		}
		return operacaoItem.editarItem(id, atual.getNome(), novaTaxa, atual.getIdCategoria(), atual.getIdFornecedor(),
				novaDisponibilidade, novaConservacao, novoValorReposicao);
	}

	public boolean deletarProduto(int id) {
		return operacaoItem.deletarItem(id);
	}

    

    public boolean cadastrarCategoria(String nome) {
        return operacaoCategoria.cadastrarCategoria(nome);
    }

    public List<Categoria> listarCategorias() {
        return operacaoCategoria.listarCategorias();
    }

    public Categoria buscarCategoriaPorId(int id) {
        return operacaoCategoria.buscarPorId(id);
    }

    public List<Categoria> buscarCategoria(String nome) {
        return operacaoCategoria.buscarCategoria(nome);
    }

    public boolean editarCategoria(int id, String novoNome, int novaQuantidade) {
        return operacaoCategoria.editarCategoria(id, novoNome, novaQuantidade);
    }

    public boolean deletarCategoria(int id) {
        return operacaoCategoria.deletarCategoria(id);
    }

    public boolean cadastrarFornecedor(String email, String telefone, String nome) {
        return operacaoFornecedor.cadastrarFornecedor(email, telefone, nome);
    }

    public List<Fornecedor> listarFornecedores() {
        return operacaoFornecedor.listarFornecedores();
    }

    public Fornecedor buscarFornecedorPorId(int id) {
        return operacaoFornecedor.buscarPorId(id);
    }

    public List<Fornecedor> buscarFornecedor(String nome) {
        return operacaoFornecedor.buscarFornecedor(nome);
    }

    public boolean editarFornecedor(int id, String novoEmail, String novoTelefone, String novoNome) {
        return operacaoFornecedor.editarFornecedor(id, novoEmail, novoTelefone, novoNome);
    }

    public boolean deletarFornecedor(int id) {
        return operacaoFornecedor.deletarFornecedor(id);
    }
    
    public List<String[]> listarItensDisponiveisPorCategoria() {
        return operacaoItem.listarItensDisponiveisPorCategoria();
    }

    public boolean salvarItensDisponiveisPorCategoria(List<String[]> dados) {
        return operacaoItem.salvarItensDisponiveisPorCategoria(dados);
    }

    public List<String[]> listarProdutosAlugados() {
        return operacaoItem.listarProdutosAlugados();
    }

    public boolean salvarProdutosAlugados(List<String[]> dados) {
        return operacaoItem.salvarProdutosAlugados(dados);
    }

    
    public boolean editarContratoInt(int idContrato, int valor, int opcao) {
        return operacaoContrato.atualizar(idContrato, valor, opcao);
    }

    public boolean editarContratoData(int idContrato, LocalDateTime valor, int opcao) {
        return operacaoContrato.atualizar(idContrato, valor, opcao);
    }

    public boolean deletarContrato(int idContrato) {
        return operacaoContrato.deletarContrato(idContrato);
    }

    public boolean deletarMulta(int idMulta) {
        return operacaoMultas.deletarMulta(idMulta);
    }
}