package br.upe.lojao.persistencia;

import java.util.ArrayList;

public interface ILeituraUsuario{

    ArrayList<Cliente> lerCliente();
    ArrayList<Funcionario> lerFuncionario();
    ArrayList<Administrador> lerAdministrador();

}
