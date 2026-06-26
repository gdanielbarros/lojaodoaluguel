package br.upe.lojao.ui;

public class MenuCliente extends Menu{

    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaContrato(int entrada){}

    @Override
    public void receberValidarEntradas(){
        System.out.printf("=====================Cliente=====================%n 1 - Itens disponiveis%n 2 - Alugueis ativos%n 3 - Multas%n 4 - Historico%n 0 - Sair");
        try{
            opcao = scanner.nextInt();
            if(opcao == 1){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 2){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 3){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 4){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 0){return;}

            else{System.out.println("Opção invalida");}

        } catch (Exception e){System.out.println("ERRO! Opção invalida, digite o numero");}
        finally { receberValidarEntradas();
        }
    }
}