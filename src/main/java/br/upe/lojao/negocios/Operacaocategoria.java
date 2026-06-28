package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.PersistenciaCategoria;
import br.upe.lojao.persistencia.entidades.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe da camada de NEGÓCIOS responsável pelas regras de Categoria.
 * Implementa IOperacaoCategoria, seguindo a orientação do professor.
 *
 * Esta classe usa PersistenciaCategoria (do João) para ler/gravar os
 * dados em arquivo. A lista de categorias é carregada do CSV ao criar
 * o objeto, e salva de volta no CSV sempre que há uma alteração
 * (cadastro, edição ou remoção).
 */
public class OperacaoCategoria implements IOperacaoCategoria {

    private PersistenciaCategoria persistencia = new PersistenciaCategoria();
    private List<Categoria> categorias;

    // Contador simples para gerar IDs únicos, simulando um auto-incremento.
    private int proximoId = 1;

    public OperacaoCategoria() {
        this.categorias = persistencia.lerCategoria();
        atualizarProximoId();
    }

    /**
     * Garante que o próximo ID gerado nunca repita um ID já existente
     * no arquivo (importante ao carregar categorias já salvas antes).
     */
    private void atualizarProximoId() {
        for (Categoria c : categorias) {
            if (c.getId() >= proximoId) {
                proximoId = c.getId() + 1;
            }
        }
    }

    @Override
    public boolean cadastrarCategoria(String nome, int quantidade) {
        if (existeCategoriaComNome(nome)) {
            return false;
        }

        Categoria nova = new Categoria(proximoId, nome, quantidade);
        categorias.add(nova);
        proximoId++;
        persistencia.atualizarCategoria(categorias);
        return true;
    }

    /**
     * Verifica se já existe uma categoria com o nome informado.
     * Comparação ignora maiúsculas/minúsculas, para evitar duplicidade
     * tipo "Ferramentas" e "ferramentas" sendo tratadas como diferentes.
     */
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

    /**
     * Remove uma categoria.
     *
     * ATENÇÃO - RN05 do enunciado (Integridade): antes de remover de
     * verdade, será necessário verificar se existe algum Produto
     * vinculado a esta categoria. Essa verificação depende da camada
     * de Produto (João) e ainda não está implementada aqui - por ora,
     * esta é uma exclusão direta.
     */
    @Override
    public boolean deletarCategoria(int id) {
        Categoria categoria = buscarPorId(id);
        if (categoria == null) {
            return false;
        }
        categorias.remove(categoria);
        persistencia.atualizarCategoria(categorias);
        return true;
    }
}