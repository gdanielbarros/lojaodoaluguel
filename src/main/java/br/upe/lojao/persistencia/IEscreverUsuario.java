package br.upe.lojao.persistencia;

import java.util.ArrayList;

public interface IEscreverUsuario {

    boolean escreverCliente(ArrayList<Cliente> clientes);
    boolean escreverFuncionario(ArrayList<Funcionario> funcionarios);
}
