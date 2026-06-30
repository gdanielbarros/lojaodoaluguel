package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Categoria;
import java.util.List;

public interface IPersistenciaCategoria {
    List<Categoria> lerCategoria();
    void atualizarCategoria(List<Categoria> lista);
}