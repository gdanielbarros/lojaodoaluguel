package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.entidades.Fornecedor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperacaoFornecedorTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private OperacaoFornecedor operacao;

    @BeforeEach
    void setUp() {
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());

        File dirs = new File(pastaTemporaria.toString()
                + File.separator + "src"
                + File.separator + "resources"
                + File.separator + "java"
                + File.separator + "br"
                + File.separator + "upe"
                + File.separator + "lojao");
        dirs.mkdirs();

        operacao = new OperacaoFornecedor();
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", userDirOriginal);
    }

    @Test
    void testaCadastrarFornecedorValido() {
        boolean resultado = operacao.cadastrarFornecedor("contato@empresa.com", "81999999999", "Empresa A");
        assertTrue(resultado);
        assertEquals(1, operacao.listarFornecedores().size());
        assertEquals("Empresa A", operacao.listarFornecedores().get(0).getNome());
        assertEquals("Ativo", operacao.listarFornecedores().get(0).getStatus());
    }

    @Test
    void testaCadastrarFornecedorNomeDuplicado() {
        operacao.cadastrarFornecedor("contato@empresa.com", "81999999999", "Empresa A");
        boolean resultado = operacao.cadastrarFornecedor("outro@empresa.com", "81888888888", "Empresa A");
        assertFalse(resultado);
        assertEquals(1, operacao.listarFornecedores().size());
    }

    @Test
    void testaCadastrarFornecedorDuplicadoIgnoraCase() {
        operacao.cadastrarFornecedor("contato@empresa.com", "81999999999", "Empresa A");
        boolean resultado = operacao.cadastrarFornecedor("outro@empresa.com", "81888888888", "EMPRESA A");
        assertFalse(resultado, "Nomes iguais com letras diferentes nao deveriam ser aceitos");
        assertEquals(1, operacao.listarFornecedores().size());
    }

    @Test
    void testaBuscarPorIdExistente() {
        operacao.cadastrarFornecedor("contato@empresa.com", "81999999999", "Empresa A");
        Fornecedor resultado = operacao.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Empresa A", resultado.getNome());
        assertEquals("contato@empresa.com", resultado.getEmail());
    }

    @Test
    void testaBuscarPorIdInexistente() {
        Fornecedor resultado = operacao.buscarPorId(99);
        assertNull(resultado);
    }

    @Test
    void testaBuscarFornecedorPorNomeParcial() {
        operacao.cadastrarFornecedor("a@a.com", "81999999999", "Empresa Alpha");
        operacao.cadastrarFornecedor("b@b.com", "81888888888", "Empresa Beta");

        List<Fornecedor> resultado = operacao.buscarFornecedor("alpha");
        assertEquals(1, resultado.size());
        assertEquals("Empresa Alpha", resultado.get(0).getNome());
    }

    @Test
    void testaBuscarFornecedorSemResultado() {
        operacao.cadastrarFornecedor("a@a.com", "81999999999", "Empresa Alpha");
        List<Fornecedor> resultado = operacao.buscarFornecedor("xyz");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testaEditarFornecedorExistente() {
        operacao.cadastrarFornecedor("antigo@email.com", "81999999999", "Empresa A");
        boolean resultado = operacao.editarFornecedor(1, "novo@email.com", "81000000000", "Empresa A Atualizada");
        assertTrue(resultado);
        Fornecedor atualizado = operacao.buscarPorId(1);
        assertEquals("novo@email.com", atualizado.getEmail());
        assertEquals("81000000000", atualizado.getTelefone());
        assertEquals("Empresa A Atualizada", atualizado.getNome());
    }

    @Test
    void testaEditarFornecedorInexistente() {
        boolean resultado = operacao.editarFornecedor(99, "novo@email.com", "81000000000", "Qualquer");
        assertFalse(resultado);
    }

    @Test
    void testaDeletarFornecedorExclusaoLogica() {
        operacao.cadastrarFornecedor("contato@empresa.com", "81999999999", "Empresa A");
        boolean resultado = operacao.deletarFornecedor(1);
        assertTrue(resultado);
        // exclusao logica: fornecedor continua na lista, so muda o status
        assertEquals(1, operacao.listarFornecedores().size());
        assertEquals("Inativo", operacao.buscarPorId(1).getStatus());
    }

    @Test
    void testaDeletarFornecedorInexistente() {
        boolean resultado = operacao.deletarFornecedor(99);
        assertFalse(resultado);
    }

    @Test
    void testaIdAutoIncrementoNaoRepete() {
        operacao.cadastrarFornecedor("a@a.com", "81111111111", "Fornecedor A");
        operacao.cadastrarFornecedor("b@b.com", "81222222222", "Fornecedor B");
        operacao.cadastrarFornecedor("c@c.com", "81333333333", "Fornecedor C");

        List<Fornecedor> lista = operacao.listarFornecedores();
        assertEquals(3, lista.size());
        assertEquals(1, lista.get(0).getId());
        assertEquals(2, lista.get(1).getId());
        assertEquals(3, lista.get(2).getId());
    }

    @Test
    void testaFornecedorCadastradoComStatusAtivoInicial() {
        operacao.cadastrarFornecedor("contato@empresa.com", "81999999999", "Empresa A");
        Fornecedor f = operacao.buscarPorId(1);
        assertEquals("Ativo", f.getStatus());
    }
}
