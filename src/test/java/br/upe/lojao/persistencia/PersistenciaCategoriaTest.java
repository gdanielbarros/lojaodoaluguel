package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Categoria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaCategoriaTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private PersistenciaCategoria persistencia;

    @BeforeEach
    void setUp() {
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());
        persistencia = new PersistenciaCategoria();
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", userDirOriginal);
    }

    @Test
    void testaCriarEListar() {
        List<Categoria> listaInicial = persistencia.lerCategoria();
        assertTrue(listaInicial.isEmpty(), "A lista não esta vazia cara");
 
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Ferramentas", 0));
        persistencia.atualizarCategoria(categorias);

        List<Categoria> resultado = persistencia.lerCategoria();
        assertEquals(1, resultado.size());
        assertEquals("Ferramentas", resultado.get(0).getNome());
        assertEquals(1, resultado.get(0).getId());
    }

    @Test
    void testarAtualizarNome() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Ferramentas", 0));
        persistencia.atualizarCategoria(categorias);

        List<Categoria> lidas = persistencia.lerCategoria();
        lidas.get(0).setNome("Eletrodomesticos");
        persistencia.atualizarCategoria(lidas);

        List<Categoria> resultado = persistencia.lerCategoria();
        assertEquals(1, resultado.size());
        assertEquals("Eletrodomesticos", resultado.get(0).getNome());
    }

    @Test
    void testeRemoverCategoria() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Ferramentas", 0));
        categorias.add(new Categoria(2, "Roupas", 0));
        persistencia.atualizarCategoria(categorias);

        List<Categoria> lidas = persistencia.lerCategoria();
        lidas.removeIf(c -> c.getId() == 1);
        persistencia.atualizarCategoria(lidas);

        List<Categoria> resultado = persistencia.lerCategoria();
        assertEquals(1, resultado.size());
        assertEquals("Roupas", resultado.get(0).getNome());
    }

    @Test
    void testeArquivoNaoExiste() {
        List<Categoria> resultado = persistencia.lerCategoria();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}