package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Produtos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaProdutosTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private PersistenciaProdutos persistencia;

    @BeforeEach
    void setUp() {  
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());
        persistencia = new PersistenciaProdutos();
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", userDirOriginal);
    }

    @Test
    void testeCriar() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));

        boolean salvo = persistencia.escreverProdutos(produtos);
        assertTrue(salvo, "Deveria retornar true ao salvar produtos");

        List<Produtos> resultado = persistencia.lerProdutos();
        assertEquals(1, resultado.size());
        assertEquals("Martelo", resultado.get(0).getNome());
        assertEquals(new BigDecimal("15.50"), resultado.get(0).getTaxaDiaria());
    }

    @Test
    void testeLerVerificado() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        produtos.add(new Produtos(2, "Furadeira", new BigDecimal("30.00"), 1, 2, "INDISPONIVEL", "Usado", new BigDecimal("120.00")));
        persistencia.escreverProdutos(produtos);

        List<Produtos> resultado = persistencia.lerProdutos();
        assertEquals(2, resultado.size());

        Produtos p1 = resultado.get(0);
        assertEquals(1, p1.getId());
        assertEquals("Martelo", p1.getNome());
        assertEquals(new BigDecimal("15.50"), p1.getTaxaDiaria());
        assertEquals(1, p1.getIdCategoria());
        assertEquals(1, p1.getIdFornecedor());
        assertEquals("DISPONIVEL", p1.getDisponibilidade());
        assertEquals("Novo", p1.getConservacao());
        assertEquals(new BigDecimal("50.00"), p1.getValorRepo());

        Produtos p2 = resultado.get(1);
        assertEquals(2, p2.getId());
        assertEquals("Furadeira", p2.getNome());
        assertEquals(new BigDecimal("30.00"), p2.getTaxaDiaria());
    }

    @Test
    void testeAtualizar() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        persistencia.escreverProdutos(produtos);

        List<Produtos> lidos = persistencia.lerProdutos();
        lidos.get(0).setTaxaDiaria(new BigDecimal("20.00"));
        lidos.get(0).setConservacao("Desgastado");
        boolean atualizado = persistencia.escreverProdutos(lidos);
        assertTrue(atualizado, "Deveria retornar true ao atualizar");

        List<Produtos> resultado = persistencia.lerProdutos();
        assertEquals(1, resultado.size());
        assertEquals(new BigDecimal("20.00"), resultado.get(0).getTaxaDiaria());
        assertEquals("Desgastado", resultado.get(0).getConservacao());
    }

    @Test
    void testeDeletar() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        produtos.add(new Produtos(2, "Furadeira", new BigDecimal("30.00"), 1, 2, "INDISPONIVEL", "Usado", new BigDecimal("120.00")));
        persistencia.escreverProdutos(produtos);

        List<Produtos> lidos = persistencia.lerProdutos();
        lidos.removeIf(p -> p.getId() == 1);
        boolean salvo = persistencia.escreverProdutos(lidos);
        assertTrue(salvo, "Deveria retornar true ao salvar apos remocao");

        List<Produtos> resultado = persistencia.lerProdutos();
        assertEquals(1, resultado.size());
        assertEquals("Furadeira", resultado.get(0).getNome());
    }

    @Test
    void testeListaVazia() {
        
        List<Produtos> resultado = persistencia.lerProdutos();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "Era pra retornar lista vazia quando CSV nao existe");
    }

    @Test
    void testeBuscaPorID() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        produtos.add(new Produtos(2, "Furadeira", new BigDecimal("30.00"), 1, 2, "INDISPONIVEL", "Usado", new BigDecimal("120.00")));
        persistencia.escreverProdutos(produtos);

        Produtos encontrado = persistencia.buscarPorId(2);
        assertNotNull(encontrado);
        assertEquals("Furadeira", encontrado.getNome());

        Produtos inexistente = persistencia.buscarPorId(99);
        assertNull(inexistente);
    }

    @Test
    void testeVerificaExistencia() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        persistencia.escreverProdutos(produtos);

        assertTrue(persistencia.produtoExiste(1), "Tinha que confirmar existencia do produto 1");
        assertFalse(persistencia.produtoExiste(99), "Nao devia encontrar produto inexistente");
    }

    @Test
    void testeRetornaTaxa() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        persistencia.escreverProdutos(produtos);

        BigDecimal taxa = persistencia.getTaxaDiaria(1);
        assertEquals(new BigDecimal("15.50"), taxa);

        BigDecimal taxaInexistente = persistencia.getTaxaDiaria(99);
        assertEquals(BigDecimal.ZERO, taxaInexistente);
    }

    @Test
    void testeRetornaID() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(5, "Martelo", new BigDecimal("15.50"), 1, 1, "DISPONIVEL", "Novo", new BigDecimal("50.00")));
        produtos.add(new Produtos(10, "Furadeira", new BigDecimal("30.00"), 1, 2, "INDISPONIVEL", "Usado", new BigDecimal("120.00")));
        persistencia.escreverProdutos(produtos);

        assertEquals(10, persistencia.maiorIdProduto(), "Não retornou o maior ID presente no CSV");
    }

    @Test
    void testeSalvaRelatorio() {
        List<String[]> dados = new ArrayList<>();
        dados.add(new String[]{"Ferramentas", "1", "Martelo", "15.50", "Novo"});

        boolean resultado = persistencia.escreverRelatorioItensPorCategoria(dados);
        assertTrue(resultado, "Deveria retornar true ao salvar relatorio de itens por categoria");

        File arquivo = new File(pastaTemporaria.toString(), "src/resources/java/br/upe/lojao/Relátorios/itensDisponivelCategoria.csv");
        assertTrue(arquivo.exists(), "O arquivo de relatorio não foi criado");
    }

    @Test
    void testeListaAlugados() {
        List<Produtos> produtos = new ArrayList<>();
        produtos.add(new Produtos(1, "Martelo", new BigDecimal("15.50"), 1, 1, "INDISPONIVEL", "Novo", new BigDecimal("50.00")));
        persistencia.escreverProdutos(produtos);

        List<Contrato> contratos = new ArrayList<>();
        contratos.add(new Contrato(1, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 2L, new BigDecimal("31.00"), 1, "ATIVO", 0, BigDecimal.ZERO));

        List<String[]> resultado = persistencia.listarDadosProdutosAlugados(contratos);
        assertEquals(1, resultado.size());
        assertEquals("1", resultado.get(0)[0]);        
        assertEquals("Martelo", resultado.get(0)[1]);   
        assertEquals("15.50", resultado.get(0)[2]);     
        assertEquals("1", resultado.get(0)[3]);         
    }

    @Test
    void testeSalvaAlugados() {
        List<String[]> dados = new ArrayList<>();
        dados.add(new String[]{"1", "Martelo", "15.50", "1", "2024-01-10T10:00", "Nao"});

        boolean resultado = persistencia.escreverProdutosAlugados(dados);
        assertTrue(resultado, "Retornou false tentando salvar o relatorio de produtos alugados");

        File arquivo = new File(pastaTemporaria.toString(), "src/resources/java/br/upe/lojao/Relatorios/itensAlugados.csv");
        assertTrue(arquivo.exists(), "O arquivo de relatorio não foi criado");
    }
}