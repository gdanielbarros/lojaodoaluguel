package br.upe.lojao.persistencia.entidades;

import java.util.ArrayList;

public interface IEscreverCsv {

    boolean escreverCliente(ArrayList<Cliente> clientes);
    boolean escreverFuncionario(ArrayList<Funcionario> funcionarios);
}
