package br.upe.lojao.ui;

import java.util.Scanner;
import br.upe.lojao.facade.Facade;
import br.upe.lojao.negocios.IOperacaoUsuario;
import br.upe.lojao.negocios.OperacaoUsuario;

public class TelaLogin {
    protected String login;
    protected String senha;
    protected String tipo;
    protected int id;
    protected Facade facade = new Facade();
    protected Scanner scanner = new Scanner(System.in);
    protected IOperacaoUsuario operacaoUsuario = new OperacaoUsuario();

    public static void main (String[] args) {
        TelaLogin iniciar = new TelaLogin();
        iniciar.imprimirTela();
    }
    
    private void imprimirTela() {
        while (true) {
            System.out.printf("=================Tela para Login=================%n Login: ");
            receberValidarEntradas("login");
            System.out.printf("%n Senha: ");
            receberValidarEntradas("senha");
            System.out.printf("%n Tipo(Cliente | Funcionario | Administrador):");
            boolean tipoValido = receberValidarEntradas("tipo");
            System.out.printf("%n");
            if (!tipoValido) {
                System.out.println("Tipo invalido. Pressione ENTER para tentar novamente.");
                scanner.nextLine();
            }
        }
    }

    private boolean receberValidarEntradas(String opcao){
        try{
            String resposta=scanner.next();
            if(opcao.equals("login")){
                this.login=resposta;
                return true;
            }
            else if(opcao.equals("senha")){
                this.senha=resposta;
                return true;
            }
            else if(opcao.equals("tipo")){
                if(resposta.equalsIgnoreCase("cliente") || resposta.equalsIgnoreCase("funcionario") || resposta.equalsIgnoreCase("administrador")){
                    this.tipo=resposta;
                    int validacao = operacaoUsuario.autenticarUsuario(this.login, this.senha, this.tipo);
                    if(validacao > -1){
                        this.id = validacao;
                        facade.verificarAtrasos(); 
                        if(tipo.equalsIgnoreCase("cliente")) {
                            MenuCliente menuCliente = new MenuCliente();
                            menuCliente.receberValidarEntradas();
                        } else if(tipo.equalsIgnoreCase("funcionario")) {
                            MenuFuncionario menuFuncionario = new MenuFuncionario();
                            menuFuncionario.receberValidarEntradas();
                        } else if(tipo.equalsIgnoreCase("administrador")) {
                            MenuAdministrador menuAdministrador = new MenuAdministrador();
                            menuAdministrador.receberValidarEntradas();
                        }
                        return true;
                    }
                    else{
                        System.out.println("Login ou senha incorretos | Aperte ENTER para voltar a tela de login");
                        scanner.next();
                        return true;
                    }
                }
                else{
                    System.out.printf("%n Erro, tipo invalido!%n");
                    return false;
                }
            }
            else{
                System.out.printf("%n Erro, opcao invalida!%n");
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
}