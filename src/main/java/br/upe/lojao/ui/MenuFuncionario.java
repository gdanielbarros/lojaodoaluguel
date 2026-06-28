package br.upe.lojao.ui;

public class MenuFuncionario extends Menu{
    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada){}

    @Override
    protected void imprimirRespostaFacadeString(int entrada, int escolha, String dado){}

    @Override
    protected void imprimirRespostaFacadeListaContrato(int entrada){}

    @Override
    public void receberValidarEntradas(){
        System.out.printf("===================Funcionario===================%n 1 - Registrar novo aluguel%n 2 - Processar devolução%n %n 3 - Cadastrar cliente%n 4 - Emitir relatorio operacional%  0 - Sair");
        try{
            int opcao = scanner.nextInt();
            if(opcao == 1){}

            else if(opcao == 2){}

            else if(opcao == 3){}

            else if(opcao == 4){}

            else if(opcao == 0){}
        }catch(Exception e){}
        finally {receberValidarEntradas();}
    }

    @Override
    protected void imprimirRespostaFacadeMapLista (int entrada){}

    @Override
    protected void imprimirRespostaFacadeContrato (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaCliente (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaCategoria (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaOcorrencia (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaFornecedor (int entrada){}
}
