package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.PersistenciaCategoria;
import br.upe.lojao.persistencia.IPersistenciaProduto;
import br.upe.lojao.persistencia.PersistenciaProdutos;
import br.upe.lojao.persistencia.entidades.Categoria;
import br.upe.lojao.persistencia.entidades.Produtos;

import java.util.ArrayList;
import java.util.List;

public class OperacaoCategoria implements IOperacaoCategoria {

    private PersistenciaCategoria persistencia = new PersistenciaCategoria();
    private IPersistenciaProduto persistenciaProduto = new PersistenciaProdutos();
    private List<Categoria> categorias;

    private int proximoId = 1;

    public OperacaoCategoria() {
        this.categorias = persistencia.lerCategoria();
        atualizarProximoId();
    }

    private void atualizarProximoId() {
        for (Categoria c : categorias) {
            if (c.getId() >= proximoId) {
                proximoId = c.getId() + 1;
            }
        }
    }

    @Override
    public boolean cadastrarCategoria(String nome) {
        if (existeCategoriaComNome(nome)) {
            return false;
        }

        
        Categoria nova = new Categoria(proximoId, nome, 0);
        categorias.add(nova);
        proximoId++;
        persistencia.atualizarCategoria(categorias);
        return true;
    }

    private boolean existeCategoriaComNome(String nome) {
        for (Categoria c : categorias) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categorias;
    }

    @Override
    public Categoria buscarPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Categoria> buscarCategoria(String nome) {
        List<Categoria> encontradas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontradas.add(c);
            }
        }
        return encontradas;
    }

    @Override
    public boolean editarCategoria(int id, String novoNome, int novaQuantidade) {
        Categoria categoria = buscarPorId(id);
        if (categoria == null) {
            return false;
        }
        categoria.setNome(novoNome);
        categoria.setQuantidade(novaQuantidade);
        persistencia.atualizarCategoria(categorias);
        return true;
    }

    @Override
    public boolean deletarCategoria(int id) {
        Categoria categoria = buscarPorId(id);
        if (categoria == null) {
            return false;
        }
        List<Produtos> produtos = persistenciaProduto.lerProdutos();
        for (Produtos p : produtos) {
            if (p.getIdCategoria() == id) {
                return false; 
            }
        }
        categorias.remove(categoria);
        persistencia.atualizarCategoria(categorias);
        return true;
    }
}