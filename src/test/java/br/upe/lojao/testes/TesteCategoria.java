package br.upe.lojao.testes;  // para OperacaoCategoria, OperacaoFornecedor
import br.upe.lojao.persistencia.entidades.Categoria; // para Categoria, Fornecedor
import br.upe.lojao.negocios.OperacaoCategoria;
import java.util.List;


/**
 * PROVA DE CONCEITO - módulo Categoria/Fornecedor
 *
 * Esta classe não faz parte do sistema final. Ela serve apenas para
 * validar manualmente que a entidade Categoria e o serviço
 * OperacaoCategoria estão funcionando corretamente, antes de
 * integrarmos com a Persistência, a Facade e a UI.
 *
 * Mais pra frente, estes mesmos cenários de teste serão reescritos
 * como testes JUnit de verdade (conforme pedido no enunciado).
 */
public class TesteCategoria {

    public static void main(String[] args) {

        OperacaoCategoria servico = new OperacaoCategoria();

        System.out.println("=== Teste 1: cadastrar categorias ===");
        boolean r1 = servico.cadastrarCategoria("Ferramentas", 50);
        boolean r2 = servico.cadastrarCategoria("Eletrônicos", 20);
        System.out.println("Cadastro 'Ferramentas': " + r1);
        System.out.println("Cadastro 'Eletrônicos': " + r2);

        System.out.println();
        System.out.println("=== Teste 2: impedir nome duplicado ===");
        boolean r3 = servico.cadastrarCategoria("ferramentas", 10); // mesmo nome, minúsculo
        System.out.println("Cadastro 'ferramentas' (duplicado): " + r3 + " (esperado: false)");

        System.out.println();
        System.out.println("=== Teste 3: listar categorias ===");
        List<Categoria> todas = servico.listarCategorias();
        for (Categoria c : todas) {
            System.out.println(c);
        }

        System.out.println();
        System.out.println("=== Teste 4: buscar por id ===");
        Categoria encontrada = servico.buscarPorId(1);
        System.out.println("Categoria com id=1: " + encontrada);

        System.out.println();
        System.out.println("=== Teste 5: editar categoria ===");
        boolean r4 = servico.editarCategoria(2, "Eletrônicos e Informática", 25);
        System.out.println("Edição do id=2: " + r4);
        System.out.println("Categoria após edição: " + servico.buscarPorId(2));

        System.out.println();
        System.out.println("=== Teste 6: deletar categoria ===");
        boolean r5 = servico.deletarCategoria(1);
        System.out.println("Remoção do id=1: " + r5);
        System.out.println("Lista após remoção:");
        for (Categoria c : servico.listarCategorias()) {
            System.out.println(c);
        }

        System.out.println();
        System.out.println("=== Teste 7: deletar id inexistente ===");
        boolean r6 = servico.deletarCategoria(99);
        System.out.println("Remoção do id=99 (não existe): " + r6 + " (esperado: false)");
    }
}