package br.upe.lojao.facade;

import br.upe.lojao.camada1_ui.MenuAdministrador;
import br.upe.lojao.camada1_ui.MenuCliente;
import br.upe.lojao.camada1_ui.MenuFuncionario;
import br.upe.lojao.camada2_negocios.OperacaoUsuario;
import br.upe.lojao.camada3_persistencia.PercistenciaUsuario;
import br.upe.lojao.models.Administrador;
import br.upe.lojao.models.Cliente;
import br.upe.lojao.models.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class Facade {
    private OperacaoUsuario operacaoUsuario = new OperacaoUsuario();
    private PercistenciaUsuario percistenciaUsuario = new PercistenciaUsuario();
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

}
