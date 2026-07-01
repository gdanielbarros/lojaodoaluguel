package br.upe.lojao.ui;

import br.upe.lojao.negocios.IOperacaoUsuario;
import br.upe.lojao.negocios.OperacaoUsuario;
import br.upe.lojao.persistencia.entidades.Produtos;
import java.util.List;

public abstract class Menu extends TelaLogin {

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
    
    protected void imprimirProdutos(List<Produtos> lista, boolean disponibilidade) {}
    
}

