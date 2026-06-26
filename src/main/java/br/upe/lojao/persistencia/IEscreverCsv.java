package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;

import java.util.ArrayList;

public interface IEscreverCsv {

    boolean escreverCliente(ArrayList<Cliente> clientes);
    boolean escreverFuncionario(ArrayList<Funcionario> funcionarios);
}
