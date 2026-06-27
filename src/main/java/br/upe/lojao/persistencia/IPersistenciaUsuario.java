package br.upe.lojao.persistencia;

import java.util.ArrayList;

public interface IPersistenciaUsuario {

    int autenticarUsuario(String login, String senha, String tipo);
    String cadastrarCliente(Cliente cliente);
    boolean atualizarCliente(int id, int opcao, String dadoModificado);
    String deletarCliente(int id);
    ArrayList<Cliente> buscarCliente(String nome);
    String cadastrarFuncionario(Funcionario contratado);
    boolean atualizarFuncionario(int id, int opcao, String dadoModificado);
    String deletarFuncionario(int id);
    ArrayList<Funcionario> buscarFuncionario(String nome);

}
