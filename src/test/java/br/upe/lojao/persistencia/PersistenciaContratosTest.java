package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaContratosTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private PersistenciaContratos persistencia;

    @BeforeEach
    void setUp() throws Exception {
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());
        Files.createDirectories(pastaTemporaria.resolve("src/resources/java/br/upe/lojao/Relátorios")); 
        persistencia = new PersistenciaContratos();
    }

    @AfterEach
    void tearDown() {
       
        System.setProperty("user.dir", userDirOriginal);
    }

    @Test
    void testeArquivoNaoExiste() {
        
        List<Contrato> contratos = persistencia.lerContratos();
        List<Ocorrencias> multas = persistencia.lerMultas();
        assertNotNull(contratos);
        assertNotNull(multas);
        assertTrue(contratos.isEmpty(), "A lista de contratos deveria estar vazia quando o arquivo não existe");
        assertTrue(multas.isEmpty(), "A lista de multas deveria estar vazia quando o arquivo não existe");
    }

    @Test
    void testeAdicionarEListarContrato() {
        Contrato c = new Contrato(
                1, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        );

        assertTrue(persistencia.adicionarContrato(c), "adicionarContrato deveria retornar true");
        List<Contrato> resultado = persistencia.lerContratos();
        assertEquals(1, resultado.size());
        assertEquals(c, resultado.get(0));
    }

    @Test
    void testeAdicionarEListarMulta() {
        Ocorrencias o = new Ocorrencias(
                1, 1, 10,
                new BigDecimal("100.00"),
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                new BigDecimal("20.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        );

        assertTrue(persistencia.adicionarMulta(o), "adicionarMulta deveria retornar true");
        List<Ocorrencias> resultado = persistencia.lerMultas();
        assertEquals(1, resultado.size());
        assertEquals(o, resultado.get(0));
    }

    @Test
    void testeBuscarContratoExistenteEInexistente() {
        Contrato c = new Contrato(
                1, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        );
        persistencia.adicionarContrato(c);

        Contrato encontrado = persistencia.buscarContrato(1);
        assertEquals(1, encontrado.id());
        assertEquals("ATIVO", encontrado.status());

        Contrato naoEncontrado = persistencia.buscarContrato(999);
        assertEquals(-1, naoEncontrado.id());
    }

    @Test
    void testeBuscarMultaExistenteEInexistente() {
        Ocorrencias o = new Ocorrencias(
                1, 1, 10,
                new BigDecimal("100.00"),
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                new BigDecimal("20.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        );
        persistencia.adicionarMulta(o);

        Ocorrencias encontrada = persistencia.buscarMulta(1);
        assertEquals(1, encontrada.id());
        assertEquals("PENDENTE", encontrada.status());

        Ocorrencias naoEncontrada = persistencia.buscarMulta(999);
        assertEquals(-1, naoEncontrada.id());
    }

    @Test
    void testeAtualizarContrato() {
        Contrato c = new Contrato(
                1, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        );
        persistencia.adicionarContrato(c);

        Contrato atualizado = new Contrato(
                1, 10,
                c.dataInicio(), c.dataFinal(),
                4L, new BigDecimal("100.00"), 1,
                "CONCLUIDO", 0, BigDecimal.ZERO
        );
        assertTrue(persistencia.atualizarContrato(atualizado), "atualizarContrato deveria retornar true");

        Contrato relido = persistencia.buscarContrato(1);
        assertEquals("CONCLUIDO", relido.status());
    }

    @Test
    void testeAtualizarMulta() {
        Ocorrencias o = new Ocorrencias(
                1, 1, 10,
                new BigDecimal("100.00"),
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                new BigDecimal("20.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        );
        persistencia.adicionarMulta(o);

        Ocorrencias atualizada = new Ocorrencias(
                1, 1, 10,
                o.valorBase(), o.dataInicio(), o.dataFinal(),
                o.valorFinal(), o.valorPorcentagem(),
                "arranhao no painel", "PAGO"
        );
        assertTrue(persistencia.atualizarMulta(atualizada), "atualizarMulta deveria retornar true");

        Ocorrencias relida = persistencia.buscarMulta(1);
        assertEquals("PAGO", relida.status());
        assertEquals("arranhao no painel", relida.avarias());
    }

    @Test
    void testeDeletarContrato() {
        Contrato c = new Contrato(
                1, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        );
        persistencia.adicionarContrato(c);
        assertEquals(1, persistencia.lerContratos().size());

        assertTrue(persistencia.deletarContrato(1), "deletarContrato deveria retornar true");
        assertTrue(persistencia.lerContratos().isEmpty(), "A lista deveria ficar vazia após deleção");
    }

    @Test
    void testeDeletarMulta() {
        Ocorrencias o = new Ocorrencias(
                1, 1, 10,
                new BigDecimal("100.00"),
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                new BigDecimal("20.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        );
        persistencia.adicionarMulta(o);
        assertEquals(1, persistencia.lerMultas().size());

        assertTrue(persistencia.deletarMulta(1), "deletarMulta deveria retornar true");
        assertTrue(persistencia.lerMultas().isEmpty(), "A lista de multas deveria ficar vazia após deleção");
    }

    @Test
    void testeMaiorIdContrato() {
        assertEquals(0, persistencia.maiorIdContrato(), "Sem registros, maior id deve ser 0");

        persistencia.adicionarContrato(new Contrato(
                5, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        ));
        assertEquals(5, persistencia.maiorIdContrato());

        persistencia.adicionarContrato(new Contrato(
                12, 10,
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 5, 0, 0),
                4L, new BigDecimal("100.00"), 2,
                "ATIVO", 0, BigDecimal.ZERO
        ));
        assertEquals(12, persistencia.maiorIdContrato());
    }

    @Test
    void testeMaiorIdMulta() {
        assertEquals(0, persistencia.maiorIdMulta(), "Sem registros, maior id deve ser 0");

        persistencia.adicionarMulta(new Ocorrencias(
                3, 1, 10,
                new BigDecimal("100.00"),
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                new BigDecimal("20.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        ));
        assertEquals(3, persistencia.maiorIdMulta());

        persistencia.adicionarMulta(new Ocorrencias(
                7, 2, 11,
                new BigDecimal("200.00"),
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 5, 0, 0),
                new BigDecimal("30.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        ));
        assertEquals(7, persistencia.maiorIdMulta());
    }

    @Test
    void testeItemContratoAtivo() {
        persistencia.adicionarContrato(new Contrato(
                1, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 50,
                "ATIVO", 0, BigDecimal.ZERO
        ));
        assertTrue(persistencia.itemContratoAtivo(50), "Item 50 tem contrato ATIVO");

        persistencia.adicionarContrato(new Contrato(
                2, 11,
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 5, 0, 0),
                4L, new BigDecimal("100.00"), 60,
                "CONCLUIDO", 0, BigDecimal.ZERO
        ));
        assertFalse(persistencia.itemContratoAtivo(60), "Item 60 tem contrato CONCLUIDO, não está ativo");
        assertFalse(persistencia.itemContratoAtivo(99), "Item 99 não tem contrato algum");
    }

    @Test
    void testeContratosClienteAtivos() {
        persistencia.adicionarContrato(new Contrato(
                1, 5,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        ));
        persistencia.adicionarContrato(new Contrato(
                2, 5,
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 5, 0, 0),
                4L, new BigDecimal("100.00"), 2,
                "CONCLUIDO", 0, BigDecimal.ZERO
        ));

        List<Contrato> ativos = persistencia.contratosClienteAtivos(5);
        assertEquals(1, ativos.size());
        assertEquals(1, ativos.get(0).id());
        assertEquals("ATIVO", ativos.get(0).status());
    }

    @Test
    void testeClienteMultaPendente() {
        Ocorrencias o1 = new Ocorrencias(
                1, 1, 7,
                new BigDecimal("100.00"),
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                new BigDecimal("20.00"),
                new BigDecimal("0.10"),
                "", "PENDENTE"
        );
        Ocorrencias o2 = new Ocorrencias(
                2, 2, 7,
                new BigDecimal("200.00"),
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 5, 0, 0),
                new BigDecimal("30.00"),
                new BigDecimal("0.10"),
                "", "PAGO"
        );
        persistencia.adicionarMulta(o1);
        persistencia.adicionarMulta(o2);

        List<Ocorrencias> pendentes = persistencia.clienteMultaPendente(7);
        assertEquals(1, pendentes.size());
        assertEquals(1, pendentes.get(0).id());
        assertEquals("PENDENTE", pendentes.get(0).status());
    }

    @Test
    void testeContratosAtrasados() {
        LocalDateTime ontem = LocalDateTime.now().minusDays(1);
        LocalDateTime amanha = LocalDateTime.now().plusDays(1);

        persistencia.adicionarContrato(new Contrato(
                1, 10,
                ontem.minusDays(5), ontem,
                5L, new BigDecimal("50.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        ));
        persistencia.adicionarContrato(new Contrato(
                2, 10,
                ontem, amanha,
                1L, new BigDecimal("10.00"), 2,
                "ATIVO", 0, BigDecimal.ZERO
        ));

        List<Contrato> atrasados = persistencia.contratosAtrasados();
        assertEquals(1, atrasados.size());
        assertEquals(1, atrasados.get(0).id());
    }

    @Test
    void testeListarContratosAtivos() {
        persistencia.adicionarContrato(new Contrato(
                1, 10,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "ATIVO", 0, BigDecimal.ZERO
        ));
        persistencia.adicionarContrato(new Contrato(
                2, 10,
                LocalDateTime.of(2024, 2, 1, 0, 0),
                LocalDateTime.of(2024, 2, 5, 0, 0),
                4L, new BigDecimal("100.00"), 2,
                "CONCLUIDO", 0, BigDecimal.ZERO
        ));

        List<Contrato> ativos = persistencia.listarContratosAtivos();
        assertEquals(1, ativos.size());
        assertEquals("ATIVO", ativos.get(0).status());
    }

    @Test
    void testeValorContratosPeriodo() {
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 1, 31, 0, 0);

        Contrato c = new Contrato(
                1, 10,
                inicio.plusDays(2), inicio.plusDays(5),
                3L, new BigDecimal("90.00"), 1,
                "CONCLUIDO", 5, new BigDecimal("15.00")
        );
        persistencia.adicionarContrato(c);

        List<BigDecimal> valores = persistencia.valorContratosPeriodo(inicio, fim, 1);
        assertEquals(2, valores.size());
        assertEquals(0, new BigDecimal("90.00").compareTo(valores.get(0)));
        assertEquals(0, new BigDecimal("15.00").compareTo(valores.get(1)));
    }

    @Test
    void testeClienteHistoricoOpcaoVisualizar() {
        Contrato c = new Contrato(
                1, 8,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 5, 0, 0),
                4L, new BigDecimal("100.00"), 1,
                "CONCLUIDO", 0, BigDecimal.ZERO
        );
        persistencia.adicionarContrato(c);

        List<Contrato> historico = persistencia.clienteHistorico(8, 1);
        assertEquals(1, historico.size());
        assertEquals(1, historico.get(0).id());
        assertEquals("CONCLUIDO", historico.get(0).status());
    }

    @Test
    void testeEscreverRelatorioFaturamento() {
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 1, 31, 0, 0);

        assertTrue(persistencia.escreverRelatorios(inicio, fim, new BigDecimal("1250.50")),
                "Era pra escreverRelatorios de faturamento retornar true");

        Path caminhoRelatorio = pastaTemporaria.resolve("src/resources/java/br/upe/lojao/Relátorios/faturamento.csv");
        assertTrue(caminhoRelatorio.toFile().exists(), "O arquivo de faturamento nao foi criado");
    }

    @Test
    void testeEscreverRelatorioHistoricoViaClienteHistorico() {
        Contrato c = new Contrato(
                1, 9,
                LocalDateTime.of(2024, 3, 1, 0, 0),
                LocalDateTime.of(2024, 3, 5, 0, 0),
                4L, new BigDecimal("80.00"), 1,
                "CONCLUIDO", 0, BigDecimal.ZERO
        );
        persistencia.adicionarContrato(c);

        List<Contrato> historico = persistencia.clienteHistorico(9, 2);
        assertEquals(1, historico.size());

        Path caminhoRelatorio = pastaTemporaria.resolve("src/resources/java/br/upe/lojao/Relátorios/historicoCliente.csv");
        assertTrue(caminhoRelatorio.toFile().exists(), "O arquivo de histórico nao foi criado mesmo com a opção 2");
    }
}