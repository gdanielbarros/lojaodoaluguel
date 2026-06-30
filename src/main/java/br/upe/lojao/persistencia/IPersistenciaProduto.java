package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Produtos;
import java.math.BigDecimal;
import java.util.List;

public interface IPersistenciaProduto {
    List<Produtos> lerProdutos();
    boolean escreverProdutos(List<Produtos> produtos);
    Produtos buscarPorId(int id);
    boolean produtoExiste(int id);
    BigDecimal getTaxaDiaria(int id);
    int maiorIdProduto();
    boolean escreverRelatorioItensPorCategoria(List<String[]> dados);
    List<String[]> listarDadosProdutosAlugados(List<Contrato> contratos);
    boolean escreverProdutosAlugados(List<String[]> dados);
}