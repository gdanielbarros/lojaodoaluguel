package br.upe.lojao.camada1_ui;

import java.util.Scanner;
import br.upe.lojao.facade.Facade;

public class TelaLogin {
    protected String login;
    protected String senha;
    protected String tipo;
    protected Facade facade;
    protected Scanner scanner = new Scanner(System.in);

    private void imprimirTela() {
        System.out.printf("=================Tela para Login=================%n Login: ");
        receberValidarEntradas("login");
        System.out.printf("%n Senha: ");
        receberValidarEntradas("senha");
        System.out.printf("%n Tipo(Cliente | Funcionario | Administrador):");
        receberValidarEntradas("tipo");
        System.out.printf("%n");
    }

    private void receberValidarEntradas(String opcao){
        try{
            String resposta=scanner.next();
            if(opcao.equals("login")){
                this.login=resposta;
            }
            else if(opcao.equals("senha")){
                this.senha=resposta;
            }
            else if(opcao.equals("tipo")){
                if(resposta.equalsIgnoreCase("cliente") && resposta.equalsIgnoreCase("funcionario") && resposta.equalsIgnoreCase("administrador")){
                    this.tipo=resposta;
                    boolean validacao = facade.autenticarUsuario(this.login, this.senha, this.tipo);
                    if(validacao){
                        if(tipo == "Cliente" || tipo == "cliente"){facade.receberValidarEntradasCliente();}
                        else if (tipo == "Funcionario" || tipo == "funcionario"){facade.receberValidarEntradasFuncionario();}
                        else if (tipo == "Administrador" || tipo =="administrador"){facade.receberValidarEntradasAdministrador();}
                    }
                    else{
                        System.out.println("Login ou senha incorretos | Aperte ENTER para voltar a tela de login");
                        scanner.next();
                    }
                }
            }
            else{
                System.out.printf("%n Erro, tipo invalido!");
            }
        } catch (Exception e){}
    }
}

