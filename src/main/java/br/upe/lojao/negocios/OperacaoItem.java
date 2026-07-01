package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.PersistenciaProdutos;
import br.upe.lojao.persistencia.IPersistenciaProduto;
import br.upe.lojao.persistencia.PersistenciaCategoria;
import br.upe.lojao.persistencia.IPersistenciaCategoria;
import br.upe.lojao.persistencia.PersistenciaFornecedor;
import br.upe.lojao.persistencia.IPersistenciaFornecedor;
import br.upe.lojao.persistencia.PersistenciaContratos;
import br.upe.lojao.persistencia.IPersistenciaContrato;

import br.upe.lojao.persistencia.entidades.Produtos;
import br.upe.lojao.persistencia.entidades.Categoria;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Fornecedor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OperacaoItem implements IOperacaoItem {
    private IPersistenciaProduto persistenciaProduto = new PersistenciaProdutos();
    private IPersistenciaCategoria persistenciaCategoria = new PersistenciaCategoria();
    private IPersistenciaFornecedor persistenciaFornecedor = new PersistenciaFornecedor();
    private IPersistenciaContrato persistenciaContrato = new PersistenciaContratos();

    public int gerarId() {
        int novoId = persistenciaProduto.maiorIdProduto() + 1;
        return novoId;
    }

    public boolean verificarExclusao(int id) {
        boolean permissao = true;
        if (persistenciaContrato.itemContratoAtivo(id)) {
            permissao = false;
        }
        return permissao;
    }

    public boolean cadastrarItem(Produtos item) {
        boolean sucesso = false;
        List<Categoria> categorias = persistenciaCategoria.lerCategoria();
        List<Fornecedor> fornecedores = persistenciaFornecedor.lerFornecedor();
        boolean categoriaValida = false;
        boolean fornecedorValido = false;
        Categoria categoriaEncontrada = null;

        for (int i = 0; i < categorias.size(); i++) {
            Categoria categoriaAtual = categorias.get(i);
            if (categoriaAtual.getId() == item.getIdCategoria()) {
                categoriaValida = true;
                categoriaEncontrada = categoriaAtual;
                break;
            }
        }

        for (int i = 0; i < fornecedores.size(); i++) {
            Fornecedor fornecedorAtual = fornecedores.get(i);
            if (fornecedorAtual.getId() == item.getIdFornecedor()) {
                fornecedorValido = true;
                break;
            }
        }

        if (categoriaValida && fornecedorValido) {
            List<Produtos> produtos = persistenciaProduto.lerProdutos();
            int novoId = gerarId();
            item.setId(novoId);
            produtos.add(item);
            sucesso = persistenciaProduto.escreverProdutos(produtos);
            
            if (sucesso && categoriaEncontrada != null) {
                categoriaEncontrada.setQuantidade(categoriaEncontrada.getQuantidade() + 1);
                persistenciaCategoria.atualizarCategoria(categorias);
            }
        }
        return sucesso;
    }

    public boolean editarItem(int id, String novoNome, BigDecimal novaTaxa, int novaCategoria,
        int novoFornecedor, String novaConservacao,
        BigDecimal novoValorRepo) {
    	boolean sucesso = false;
    	if (persistenciaContrato.itemContratoAtivo(id)) {
    		return false;
    		}
        List<Produtos> produtos = persistenciaProduto.lerProdutos();
        List<Categoria> categorias = persistenciaCategoria.lerCategoria();
        List<Fornecedor> fornecedores = persistenciaFornecedor.lerFornecedor();
        boolean categoriaValida = false;
        boolean fornecedorValido = false;

        for (int i = 0; i < categorias.size(); i++) {
            Categoria categoriaAtual = categorias.get(i);
            if (categoriaAtual.getId() == novaCategoria) {
                categoriaValida = true;
                break;
            }
        }

        for (int i = 0; i < fornecedores.size(); i++) {
            Fornecedor fornecedorAtual = fornecedores.get(i);
            if (fornecedorAtual.getId() == novoFornecedor) {
                fornecedorValido = true;
                break;
            }
        }

        if (categoriaValida && fornecedorValido) {
            for (int i = 0; i < produtos.size(); i++) {
                Produtos produtoAtual = produtos.get(i);
                if (produtoAtual.getId() == id) {
                    produtoAtual.setNome(novoNome);
                    produtoAtual.setTaxaDiaria(novaTaxa);
                    produtoAtual.setIdCategoria(novaCategoria);
                    produtoAtual.setIdFornecedor(novoFornecedor);
                    produtoAtual.setConservacao(novaConservacao);
                    produtoAtual.setValorRepo(novoValorRepo);
                    sucesso = persistenciaProduto.escreverProdutos(produtos);
                    break;
                }
            }
        }
        return sucesso;
    }

    public boolean deletarItem(int id) {
        boolean sucesso = false;
        if (verificarExclusao(id)) {
            List<Produtos> produtos = persistenciaProduto.lerProdutos();
            Produtos produtoRemovido = null;
            for (int i = 0; i < produtos.size(); i++) {
                Produtos produtoAtual = produtos.get(i);
                if (produtoAtual.getId() == id) {
                    produtoRemovido = produtoAtual;
                    produtoAtual.setDisponibilidade("INDISPONIVEL");
                    sucesso = persistenciaProduto.escreverProdutos(produtos);
                    break;
                }
            }
            
            if (sucesso && produtoRemovido != null) {
                List<Categoria> categorias = persistenciaCategoria.lerCategoria();
                for (Categoria c : categorias) {
                    if (c.getId() == produtoRemovido.getIdCategoria()) {
                        int novaQtd = c.getQuantidade() - 1;
                        if (novaQtd < 0) novaQtd = 0;
                        c.setQuantidade(novaQtd);
                        persistenciaCategoria.atualizarCategoria(categorias);
                        break;
                    }
                }
            }
        }
        return sucesso;
    }

    public Produtos buscarPorId(int id) {
        return persistenciaProduto.buscarPorId(id);
    }

    public List<Produtos> buscarItem(String nome) {
        List<Produtos> resultado = new ArrayList<>();
        List<Produtos> todos = persistenciaProduto.lerProdutos();
        for (int i = 0; i < todos.size(); i++) {
            Produtos produtoAtual = todos.get(i);
            if (produtoAtual.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(produtoAtual);
            }
        }
        return resultado;
    }

    public List<Produtos> listarTodosItens() {
        return persistenciaProduto.lerProdutos();
    }

    public List<Produtos> listarItemDisponivel() {
        List<Produtos> disponiveis = new ArrayList<>();
        List<Produtos> todos = persistenciaProduto.lerProdutos();
        for (int i = 0; i < todos.size(); i++) {
            Produtos produtoAtual = todos.get(i);
            if (produtoAtual.getDisponibilidade().equalsIgnoreCase("DISPONIVEL")) {
                disponiveis.add(produtoAtual);
            }
        }
        return disponiveis;
    }
    public List<String[]> listarItensDisponiveisPorCategoria() {
        PersistenciaCategoria persistenciaCategoria = new PersistenciaCategoria();
        List<Categoria> categorias = persistenciaCategoria.lerCategoria();
        List<Produtos> disponiveis = this.listarItemDisponivel();
        List<String[]> resultado = new ArrayList<>();

        for (int i = 0; i < categorias.size(); i++) {
            Categoria categoria = categorias.get(i);
            for (int j = 0; j < disponiveis.size(); j++) {
                Produtos produto = disponiveis.get(j);
                if (produto.getIdCategoria() == categoria.getId()) {
                    String[] linha = {
                        categoria.getNome(),
                        String.valueOf(produto.getId()),
                        produto.getNome(),
                        produto.getTaxaDiaria().toString(),
                        produto.getConservacao()
                    };
                    resultado.add(linha);
                }
            }
        }
        return resultado;
    }

    public boolean salvarItensDisponiveisPorCategoria(List<String[]> dados) {
        return persistenciaProduto.escreverRelatorioItensPorCategoria(dados);
    }
    
    public List<String[]> listarProdutosAlugados() {
        List<Contrato> ativos = persistenciaContrato.listarContratosAtivos();
        return persistenciaProduto.listarDadosProdutosAlugados(ativos);
    }

    public boolean salvarProdutosAlugados(List<String[]> dados) {
        return persistenciaProduto.escreverProdutosAlugados(dados);
    }
    
}