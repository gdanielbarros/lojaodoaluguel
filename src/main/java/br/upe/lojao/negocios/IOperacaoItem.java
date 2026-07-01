package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.entidades.Produtos;
import java.math.BigDecimal;
import java.util.List;

public interface IOperacaoItem {
    boolean cadastrarItem(Produtos item);
    boolean editarItem(int id, String novoNome, BigDecimal novaTaxa, int novaCategoria,int novoFornecedor, String novaConservacao,BigDecimal novoValorRepo);
    boolean deletarItem(int id);
    Produtos buscarPorId(int id);
    List<Produtos> buscarItem(String nome);
    List<Produtos> listarTodosItens();
    List<Produtos> listarItemDisponivel();
    boolean verificarExclusao(int id);
    boolean salvarItensDisponiveisPorCategoria(List<String[]> dados);
    List<String[]> listarItensDisponiveisPorCategoria();
    List<String[]> listarProdutosAlugados();
    boolean salvarProdutosAlugados(List<String[]> dados);
    int gerarId();
}