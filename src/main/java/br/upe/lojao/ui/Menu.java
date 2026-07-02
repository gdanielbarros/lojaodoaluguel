package br.upe.lojao.ui;

import br.upe.lojao.facade.Facade;
import br.upe.lojao.persistencia.entidades.Produtos;

import java.util.List;
import java.util.Scanner;

public abstract class Menu extends TelaLogin {

    public Menu(int id, String login, String senha, String tipo, Scanner scanner, Facade facade){
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.scanner = scanner;
        this.facade = facade;
    }

    protected void receberValidarEntradas(){}

    protected void limparBuffer(){
        scanner.nextLine();
    }

    protected void imprimirRespostaFacadeBoolean(int entrada){}

    protected void imprimirRespostaFacadeListaProdutos(int entrada){}

    protected void imprimirRespostaFacadeListaContrato(int entrada){}

    protected void imprimirRespostaFacadeString(int entrada, int escolha, String dado){}

    protected void imprimirRespostaFacadeMapLista (int entrada){}

    protected void imprimirRespostaFacadeContrato (int entrada){}

    protected void imprimirRespostaFacadeListaCliente (int entrada){}

    protected void imprimirRespostaFacadeListaCategoria (int entrada){}

    protected void imprimirRespostaFacadeListaOcorrencia (int entrada){}

    protected void imprimirRespostaFacadeListaFornecedor (int entrada){}
    
    protected void imprimirProdutos(List<Produtos> lista, boolean comDisponibilidade) {
        System.out.println("---------------------------------------");
        for (Produtos p : lista) {
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Categoria: " + p.getIdCategoria());
            System.out.println("Fornecedor: " + p.getIdFornecedor());
            System.out.println("Taxa Diaria: R$ " + p.getTaxaDiaria());
            if (comDisponibilidade) {
                System.out.println("Disponibilidade: " + p.getDisponibilidade());
            }
            System.out.println("Conservacao: " + p.getConservacao());
            System.out.println("Valor Reposicao: R$ " + p.getValorRepo());
            System.out.println("---------------------------------------");
        }
    }
    
}