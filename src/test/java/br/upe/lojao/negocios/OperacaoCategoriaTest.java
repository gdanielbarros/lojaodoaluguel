package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.entidades.Categoria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperacaoCategoriaTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private OperacaoCategoria operacao;

    @BeforeEach
    void setUp() {
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());

        // PersistenciaCategoria monta o caminho a partir de user.dir —
        // os diretorios precisam existir para o escritor nao falhar silenciosamente
        File dirs = new File(pastaTemporaria.toString()
                + File.separator + "src"
                + File.separator + "resources"
                + File.separator + "java"
                + File.separator + "br"
                + File.separator + "upe"
                + File.separator + "lojao");
        dirs.mkdirs();

        operacao = new OperacaoCategoria();
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", userDirOriginal);
    }

    @Test
    void testaCadastrarCategoriaValida() {
        boolean resultado = operacao.cadastrarCategoria("Ferramentas", 10);
        assertTrue(resultado);
        assertEquals(1, operacao.listarCategorias().size());
        assertEquals("Ferramentas", operacao.listarCategorias().get(0).getNome());
    }

    @Test
    void testaCadastrarCategoriaNomeDuplicado() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        boolean resultado = operacao.cadastrarCategoria("Ferramentas", 5);
        assertFalse(resultado);
        assertEquals(1, operacao.listarCategorias().size());
    }

    @Test
    void testaCadastrarCategoriaDuplicadaIgnoraCase() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        boolean resultado = operacao.cadastrarCategoria("FERRAMENTAS", 5);
        assertFalse(resultado, "Nomes iguais com letras diferentes nao deveriam ser aceitos");
        assertEquals(1, operacao.listarCategorias().size());
    }

    @Test
    void testaBuscarPorIdExistente() {
        operacao.cadastrarCategoria("Roupas", 5);
        Categoria resultado = operacao.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Roupas", resultado.getNome());
    }

    @Test
    void testaBuscarPorIdInexistente() {
        Categoria resultado = operacao.buscarPorId(99);
        assertNull(resultado);
    }

    @Test
    void testaBuscarCategoriaPorNomeParcial() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        operacao.cadastrarCategoria("Eletrodomesticos", 5);

        List<Categoria> resultado = operacao.buscarCategoria("ferra");
        assertEquals(1, resultado.size());
        assertEquals("Ferramentas", resultado.get(0).getNome());
    }

    @Test
    void testaBuscarCategoriaSemResultado() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        List<Categoria> resultado = operacao.buscarCategoria("xyz");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testaEditarCategoriaExistente() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        boolean resultado = operacao.editarCategoria(1, "Ferramentas Eletricas", 20);
        assertTrue(resultado);
        assertEquals("Ferramentas Eletricas", operacao.buscarPorId(1).getNome());
        assertEquals(20, operacao.buscarPorId(1).getQuantidade());
    }

    @Test
    void testaEditarCategoriaInexistente() {
        boolean resultado = operacao.editarCategoria(99, "Novo Nome", 5);
        assertFalse(resultado);
    }

    @Test
    void testaDeletarCategoriaExistente() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        boolean resultado = operacao.deletarCategoria(1);
        assertTrue(resultado);
        assertTrue(operacao.listarCategorias().isEmpty());
    }

    @Test
    void testaDeletarCategoriaInexistente() {
        boolean resultado = operacao.deletarCategoria(99);
        assertFalse(resultado);
    }

    @Test
    void testaIdAutoIncrementoNaoRepete() {
        operacao.cadastrarCategoria("Ferramentas", 10);
        operacao.cadastrarCategoria("Roupas", 5);
        operacao.cadastrarCategoria("Eletronicos", 8);

        List<Categoria> lista = operacao.listarCategorias();
        assertEquals(3, lista.size());
        assertEquals(1, lista.get(0).getId());
        assertEquals(2, lista.get(1).getId());
        assertEquals(3, lista.get(2).getId());
    }

    @Test
    void testaListarComVariasCategorias() {
        operacao.cadastrarCategoria("A", 1);
        operacao.cadastrarCategoria("B", 2);
        operacao.cadastrarCategoria("C", 3);
        assertEquals(3, operacao.listarCategorias().size());
    }
}
