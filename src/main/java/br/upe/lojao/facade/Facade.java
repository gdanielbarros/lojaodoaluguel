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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    private OperacaoUsuario operacaoUsuario = new OperacaoUsuario();
    private IOperacaoMultas operacaoMultas = new OperacaoMultas();
    private OperacaoItem operacaoItem = new OperacaoItem();
    private IOperacaoContrato operacaoContrato = new OperacaoContrato();
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
        

}
