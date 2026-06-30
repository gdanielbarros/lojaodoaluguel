package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Fornecedor;
import java.util.List;

public interface IPersistenciaFornecedor {
    List<Fornecedor> lerFornecedor();
    void atualizarFornecedor(List<Fornecedor> lista);
}