package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.Cliente;
import br.upe.lojao.persistencia.Funcionario;

import java.util.ArrayList;

public interface IOperacaoUsuario {

    int autenticarUsuario(String login, String senha, String tipo);
    String cadastrarCliente(Cliente cliente);
    String editarCliente(int id, int opcao,String dadoModificado);
    String deletarCliente(int id);
    ArrayList<Cliente> buscarCliente(String nome);
    String cadastrarFuncionario(Funcionario contratado);
    String editarFuncionario(int id, int opcao, String dadoModificado);
    String deletarFuncionario(int id);
    ArrayList<Funcionario> buscarFuncionario(String nome);

}
