package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Fornecedor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaFornecedorTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private PersistenciaFornecedor persistencia;

    @BeforeEach
    void setUp() { 
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());
        persistencia = new PersistenciaFornecedor();
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", userDirOriginal);
    }

    @Test
    void testaCriarEListar() {
        List<Fornecedor> listaInicial = persistencia.lerFornecedor();
        assertTrue(listaInicial.isEmpty(), "A lista nao esta vazia cara");

        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor(1, "forn1@email.com", "81999999999", "Fornecedor A", "Ativo"));
        persistencia.atualizarFornecedor(fornecedores);

        List<Fornecedor> resultado = persistencia.lerFornecedor();
        assertEquals(1, resultado.size());
        assertEquals("Fornecedor A", resultado.get(0).getNome());
        assertEquals(1, resultado.get(0).getId());
        assertEquals("forn1@email.com", resultado.get(0).getEmail());
        assertEquals("81999999999", resultado.get(0).getTelefone());
        assertEquals("Ativo", resultado.get(0).getStatus());
    }

    @Test
    void testarAtualizarEmail() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor(1, "forn1@email.com", "81999999999", "Fornecedor A", "Ativo"));
        persistencia.atualizarFornecedor(fornecedores);

        List<Fornecedor> lidos = persistencia.lerFornecedor();
        lidos.get(0).setEmail("novo@email.com");
        persistencia.atualizarFornecedor(lidos);

        List<Fornecedor> resultado = persistencia.lerFornecedor();
        assertEquals(1, resultado.size());
        assertEquals("novo@email.com", resultado.get(0).getEmail());
        assertEquals("Fornecedor A", resultado.get(0).getNome());
    }

    @Test
    void testeRemoverFornecedor() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor(1, "forn1@email.com", "81999999999", "Fornecedor A", "Ativo"));
        fornecedores.add(new Fornecedor(2, "forn2@email.com", "81888888888", "Fornecedor B", "Ativo"));
        persistencia.atualizarFornecedor(fornecedores);

        List<Fornecedor> lidos = persistencia.lerFornecedor();
        lidos.removeIf(f -> f.getId() == 1);
        persistencia.atualizarFornecedor(lidos);

        List<Fornecedor> resultado = persistencia.lerFornecedor();
        assertEquals(1, resultado.size());
        assertEquals("Fornecedor B", resultado.get(0).getNome());
    }

    @Test
    void testeArquivoNaoExiste() {
        List<Fornecedor> resultado = persistencia.lerFornecedor();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}