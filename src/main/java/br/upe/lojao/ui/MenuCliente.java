package br.upe.lojao.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuCliente extends Menu{

    private static final Log log = LogFactory.getLog(MenuCliente.class);

    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada){
        if(entrada == 6){
            System.out.println(operacaoUsuario.deletarCliente(this.id));
        }
    }

    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaContrato(int entrada){}

    @Override
    protected void imprimirRespostaFacadeString(int entrada, int escolha, String dado){
        if (entrada == 5){
            System.out.println(operacaoUsuario.editarCliente(this.id,escolha,dado));
        }
    }


    @Override
    public void receberValidarEntradas(){
        System.out.printf("=====================Cliente=====================%n 1 - Itens disponiveis%n 2 - Alugueis ativos%n 3 - Multas%n 4 - Historico%n 5 - Editar informações%n 6 - Excluir conta%n 0 - Sair%n");
        try{
            int opcao = scanner.nextInt();
            limparBuffer();

            if(opcao == 1){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 2){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 3){imprimirRespostaFacadeListaContrato(opcao);}

            else if(opcao == 4){imprimirRespostaFacadeListaContrato(opcao);}

            else if (opcao == 5) {
                System.out.printf("===========Qual dado deseja modificar?===========%n 1 - Nome% 2 - Login%n 3 - Senha%n 4 - Email%n 5 - Telefone%n 0 - Sair%n");
                int escolha = scanner.nextInt();
                limparBuffer();
                if (escolha >= 1 && escolha <= 5){
                    System.out.printf("Dado atualizado: ");
                    String dado = scanner.nextLine();
                    imprimirRespostaFacadeString(opcao,escolha,dado);
                }
            }

            else if (opcao == 6) {
                System.out.printf("Voce tem certeza que deseja continuar?%n (ESSA AÇÃO NAO PODERA SER DESFEITA!!!)%n 1 - Sim%n 2 - Não%n");
                int escolha = scanner.nextInt();
                limparBuffer();

                if (escolha == 1){
                    System.out.printf("Digite sua senha para confirmar: ");
                    String dado = scanner.next();
                    limparBuffer();
                    System.out.printf("%n");

                    if(dado.equals(this.senha)) {
                        imprimirRespostaFacadeBoolean(opcao);
                        System.out.println("Aperte ENTER para continuar");
                        scanner.nextLine();
                        opcao = 0;
                    }
                    else{System.out.println("Senha incorreta. Operação cancelada!");}
                }

                else if(escolha == 2){System.out.println("Operação cancelada!");}

                else{System.out.println("Opção invalida! operação cancelada");}
            }

            if(opcao == 0){return;}

            else{System.out.println("Opção invalida");}

        } catch (Exception e){System.out.println("ERRO! Opção invalida, digite um numero");}
        finally {
            receberValidarEntradas();
        }
    }
}