package br.upe.lojao.facade;

import br.upe.lojao.ui.MenuAdministrador;
import br.upe.lojao.ui.MenuCliente;
import br.upe.lojao.ui.MenuFuncionario;
import br.upe.lojao.negocios.OperacaoUsuario;
import br.upe.lojao.negocios.OperacaoItem; 
import br.upe.lojao.persistencia.PersistenciaUsuario;
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
    private OperacaoUsuario operacaoUsuario = new OperacaoUsuario();
    private IOperacaoMultas operacaoMultas = new OperacaoMultas();
    private OperacaoItem operacaoItem = new OperacaoItem();
    private IOperacaoContrato operacaoContrato = new OperacaoContrato();
    private IOperacaoCategoria operacaoCategoria = new OperacaoCategoria();
    private IOperacaoFornecedor operacaoFornecedor = new OperacaoFornecedor();
    private PersistenciaUsuario percistenciaUsuario = new PersistenciaUsuario();
    private MenuCliente menucliente = new MenuCliente();
    private MenuFuncionario menufuncionario = new MenuFuncionario();
    private MenuAdministrador menuadministrador = new MenuAdministrador();


    public boolean autenticarUsuario(String login, String senha, String tipo){
        return operacaoUsuario.autenticarUsuario(login,senha,tipo);
    }

    public String cadastrarCliente(Cliente cliente){
        return operacaoUsuario.cadastararCliente(cliente);
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
        return percistenciaUsuario.lerCliente();
    }

    public boolean atualizarCliente(List<Cliente> cliente){
        return percistenciaUsuario.atualizarCliente(cliente);
    }

    public ArrayList<Funcionario> lerFuncionario(){
        return percistenciaUsuario.lerFuncionario();
    }

    public boolean atualizarFuncionario(List<Funcionario> funcionarios){
        return percistenciaUsuario.atualizarFuncionario(funcionarios);
    }

    public ArrayList<Administrador> lerAdministrador(){
        return percistenciaUsuario.lerAdministrador();
    }

    public void receberValidarEntradaCliente(){
        menucliente.receberValidarEntradas();
    }
    
    public List<Contrato> listarAtivos(int idCliente) {
        return operacaoContrato.listarAtivos(idCliente);
    }

    public List<Ocorrencias> multasPendentes(int idCliente) {
        return operacaoContrato.multasPendentes(idCliente);
    }

    public List<Contrato> historicoCliente(int idCliente) {
        return operacaoContrato.historicoCliente(idCliente);
    }
    
    public boolean registrarAluguel(int idProduto, LocalDateTime dataInicio, LocalDateTime dataFinal, int idCliente) {
        return operacaoContrato.registrar(idProduto, dataInicio, dataFinal, idCliente);
    }

    public boolean processarDevolucao(int idContrato) {
        return operacaoContrato.concluir(idContrato);
    }

    public List<Contrato> listarTodosContratos() {
        return operacaoContrato.listarTodos();
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
		return OperacaoItem.buscarItem(nome);
	}

	public List<Produtos> listarTodosItens() {
		return OperacaoItem.listarTodosItens();
	}

	public List<Produtos> listarItemDisponivel() {
		return OperacaoItem.listarItemDisponivel();
	}

	public Produtos buscarProdutoPorId(int id) {
		// TODO Gerar essa função dentro de OperacaoItem se nescessario.
		return null;
	}

    // --- Categoria ---

    public boolean cadastrarCategoria(String nome, int quantidade) {
        return operacaoCategoria.cadastrarCategoria(nome, quantidade);
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

    // --- Fornecedor ---

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

}
