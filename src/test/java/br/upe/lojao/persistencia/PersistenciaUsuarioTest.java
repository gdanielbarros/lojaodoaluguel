package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaUsuarioTest {

    @TempDir
    Path pastaTemporaria;

    private String userDirOriginal;
    private PersistenciaUsuario persistencia;

    @BeforeEach
    void setUp() {
        userDirOriginal = System.getProperty("user.dir");
        System.setProperty("user.dir", pastaTemporaria.toString());
        persistencia = new PersistenciaUsuario();
    }

    @AfterEach
    void tearDown() {
        // Restaura o diretório original para não afetar outros testes nem o projeto
        System.setProperty("user.dir", userDirOriginal);
    }


    @Test
    void testeCadastrarCliente() {
        Cliente novo = new Cliente(0, "Joao Silva", "joao", "123456", "Cliente",
                "joao@email.com", "81999999999", "12345678901", false, false);
        String resposta = persistencia.cadastrarCliente(novo);

        assertEquals("Salvo com sucesso", resposta);

        ArrayList<Cliente> lista = persistencia.lerClientes();
        assertEquals(1, lista.size());
        assertEquals("Joao Silva", lista.get(0).nome());
        assertEquals(1, lista.get(0).id());
    }

    @Test
    void testeLerClientes() {
        Cliente novo = new Cliente(0, "Maria Souza", "maria", "abcdef", "Cliente",
                "maria@email.com", "81888888888", "10987654321", false, false);
        persistencia.cadastrarCliente(novo);

        ArrayList<Cliente> lista = persistencia.lerClientes();
        assertEquals(1, lista.size());
        assertEquals("Maria Souza", lista.get(0).nome());
        assertEquals("maria", lista.get(0).login());
        assertEquals("abcdef", lista.get(0).senha());
        assertEquals("Cliente", lista.get(0).tipo());
        assertEquals("maria@email.com", lista.get(0).email());
        assertEquals("81888888888", lista.get(0).telefone());
        assertEquals("10987654321", lista.get(0).cpf());
        assertFalse(lista.get(0).statusMulta());
        assertFalse(lista.get(0).statusContrato());
    }

    @Test
    void testeAtualizarCliente() {
        Cliente novo = new Cliente(0, "Pedro Costa", "pedro", "senha", "Cliente",
                "pedro@email.com", "81777777777", "11122233344", false, false);
        persistencia.cadastrarCliente(novo);

        boolean atualizado = persistencia.atualizarCliente(1, 1, "Pedro Costa Atualizado");
        assertTrue(atualizado);

        ArrayList<Cliente> lista = persistencia.lerClientes();
        assertEquals(1, lista.size());
        assertEquals("Pedro Costa Atualizado", lista.get(0).nome());
        assertEquals("pedro", lista.get(0).login()); // campo não alterado permanece igual
    }

    @Test
    void testeDeletarCliente() {
        Cliente c1 = new Cliente(0, "Ana Paula", "ana", "111", "Cliente",
                "ana@email.com", "81666666666", "22233344455", false, false);
        Cliente c2 = new Cliente(0, "Bia Lima", "bia", "222", "Cliente",
                "bia@email.com", "81555555555", "33344455566", false, false);
        persistencia.cadastrarCliente(c1);
        persistencia.cadastrarCliente(c2);

        String resposta = persistencia.deletarCliente(1);
        assertEquals("Deletado com sucesso", resposta);

        ArrayList<Cliente> lista = persistencia.lerClientes();
        assertEquals(1, lista.size());
        assertEquals("Bia Lima", lista.get(0).nome());
    }

    @Test
    void testeArquivoClienteNaoExiste() {
        ArrayList<Cliente> resultado = persistencia.lerClientes();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void testeCadastrarFuncionario() {
        Funcionario novo = new Funcionario(0, "Carlos Mendes", "carlos", "789", "Funcionario",
                "carlos@email.com", "44455566677", "81444444444", false);
        String resposta = persistencia.cadastrarFuncionario(novo);

        assertEquals("Cadastrado com sucesso", resposta);

        ArrayList<Funcionario> lista = persistencia.lerFuncionarios();
        assertEquals(1, lista.size());
        assertEquals("Carlos Mendes", lista.get(0).nome());
        assertEquals(1, lista.get(0).id());
    }

    @Test
    void testeLerFuncionarios() {
        Funcionario novo = new Funcionario(0, "Diana Rocha", "diana", "xyz", "Funcionario",
                "diana@email.com", "55566677788", "81333333333", false);
        persistencia.cadastrarFuncionario(novo);

        ArrayList<Funcionario> lista = persistencia.lerFuncionarios();
        assertEquals(1, lista.size());
        assertEquals("Diana Rocha", lista.get(0).nome());
        assertEquals("diana", lista.get(0).login());
        assertEquals("xyz", lista.get(0).senha());
        assertEquals("Funcionario", lista.get(0).tipo());
        assertEquals("diana@email.com", lista.get(0).email());
        assertEquals("55566677788", lista.get(0).cpf());
        assertEquals("81333333333", lista.get(0).telefone());
        assertFalse(lista.get(0).statusContrato());
    }

    @Test
    void testeAtualizarFuncionario() {
        Funcionario novo = new Funcionario(0, "Eduardo Dias", "edu", "pass", "Funcionario",
                "edu@email.com", "66677788899", "81222222222", false);
        persistencia.cadastrarFuncionario(novo);

        boolean atualizado = persistencia.atualizarFuncionario(1, 4, "edu.novo@email.com");
        assertTrue(atualizado);

        ArrayList<Funcionario> lista = persistencia.lerFuncionarios();
        assertEquals(1, lista.size());
        assertEquals("edu.novo@email.com", lista.get(0).email());
        assertEquals("Eduardo Dias", lista.get(0).nome()); // campo não alterado permanece igual
    }

    @Test
    void testeDeletarFuncionario() {
        Funcionario f1 = new Funcionario(0, "Fernanda Lopes", "fer", "333", "Funcionario",
                "fer@email.com", "77788899900", "81111111111", false);
        Funcionario f2 = new Funcionario(0, "Gabriel Nunes", "gab", "444", "Funcionario",
                "gab@email.com", "88899900011", "81000000000", false);
        persistencia.cadastrarFuncionario(f1);
        persistencia.cadastrarFuncionario(f2);

        String resposta = persistencia.deletarFuncionario(1);
        assertEquals("Deletado com sucesso", resposta);

        ArrayList<Funcionario> lista = persistencia.lerFuncionarios();
        assertEquals(1, lista.size());
        assertEquals("Gabriel Nunes", lista.get(0).nome());
    }

    @Test
    void testeArquivoFuncionarioNaoExiste() {
        ArrayList<Funcionario> resultado = persistencia.lerFuncionarios();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void testeLerAdministradores() throws Exception {
        Path caminhoAdmin = pastaTemporaria.resolve("src/resources/java/br/upe/lojao/administrador.csv");
        Files.createDirectories(caminhoAdmin.getParent());
        Files.writeString(caminhoAdmin,
                "id,nome,login,senha,tipo,email\n1,Admin Principal,admin,adm123,Administrador,admin@loja.com\n");

        ArrayList<Administrador> lista = persistencia.lerAdministradores();
        assertEquals(1, lista.size());
        assertEquals(1, lista.get(0).id());
        assertEquals("Admin Principal", lista.get(0).nome());
        assertEquals("admin", lista.get(0).login());
        assertEquals("adm123", lista.get(0).senha());
        assertEquals("Administrador", lista.get(0).tipo());
        assertEquals("admin@loja.com", lista.get(0).email());
    }

    @Test
    void testeArquivoAdministradorNaoExiste() {
        ArrayList<Administrador> resultado = persistencia.lerAdministradores();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void testeAutenticarUsuario() {
        Cliente c = new Cliente(0, "Henrique Alves", "henrique", "minhasenha", "Cliente",
                "henrique@email.com", "81911111111", "99988877766", false, false);
        persistencia.cadastrarCliente(c);

        int idOk = persistencia.autenticarUsuario("henrique", "minhasenha", "cliente");
        assertEquals(1, idOk);

        int idErrado = persistencia.autenticarUsuario("henrique", "senhaerrada", "cliente");
        assertEquals(-1, idErrado);

        int idTipoErrado = persistencia.autenticarUsuario("henrique", "minhasenha", "funcionario");
        assertEquals(-1, idTipoErrado);
    }

    @Test
    void testeBuscarCliente() {
        Cliente c = new Cliente(0, "Isabela Martins", "isa", "555", "Cliente",
                "isa@email.com", "81822222222", "88877766655", false, false);
        persistencia.cadastrarCliente(c);

        ArrayList<Cliente> encontrados = persistencia.buscarCliente("Isabela Martins");
        assertEquals(1, encontrados.size());
        assertEquals("Isabela Martins", encontrados.get(0).nome());

        ArrayList<Cliente> vazio = persistencia.buscarCliente("Nome Inexistente");
        assertTrue(vazio.isEmpty());
    }

    @Test
    void testeBuscarFuncionario() {
        Funcionario f = new Funcionario(0, "Juliana Torres", "ju", "666", "Funcionario",
                "ju@email.com", "77766655544", "81733333333", false);
        persistencia.cadastrarFuncionario(f);

        ArrayList<Funcionario> encontrados = persistencia.buscarFuncionario("Juliana Torres");
        assertEquals(1, encontrados.size());
        assertEquals("Juliana Torres", encontrados.get(0).nome());

        ArrayList<Funcionario> vazio = persistencia.buscarFuncionario("Inexistente");
        assertTrue(vazio.isEmpty());
    }

    @Test
    void testeClienteExiste() {
        Cliente c = new Cliente(0, "Lucas Pereira", "lucas", "777", "Cliente",
                "lucas@email.com", "81644444444", "66655544433", false, false);
        persistencia.cadastrarCliente(c);

        assertTrue(persistencia.clienteExiste(1));
        assertFalse(persistencia.clienteExiste(999));
    }
}