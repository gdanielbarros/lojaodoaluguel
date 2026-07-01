package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.entidades.Categoria;

import java.util.List;

public interface IOperacaoCategoria {

    boolean cadastrarCategoria(String nome);

    List<Categoria> listarCategorias();

    Categoria buscarPorId(int id);

    List<Categoria> buscarCategoria(String nome);

    boolean editarCategoria(int id, String novoNome);

    boolean deletarCategoria(int id);
}