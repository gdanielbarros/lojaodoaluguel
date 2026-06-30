package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.entidades.Categoria;

import java.util.List;

/**
 * Interface da camada de NEGÓCIOS para Categoria.
 *
 * Por que esta interface existe: o professor pediu que cada classe de
 * negócio, persistência e UI tenha sua própria interface. Isso permite
 * que outras classes (ex: a Facade) dependam apenas do "contrato"
 * (o que o serviço faz), e não da implementação (como ele faz por
 * dentro). Assim, se o código de dentro de OperacaoCategoria mudar,
 * quem usa essa interface não precisa mudar nada.
 *
 * Quem for usar este serviço deve declarar pela interface, não pela
 * classe concreta:
 *   IOperacaoCategoria categoria = new OperacaoCategoria();
 */
public interface IOperacaoCategoria {

    boolean cadastrarCategoria(String nome, int quantidade);

    List<Categoria> listarCategorias();

    Categoria buscarPorId(int id);

    List<Categoria> buscarCategoria(String nome);

    boolean editarCategoria(int id, String novoNome, int novaQuantidade);

    boolean deletarCategoria(int id);
}