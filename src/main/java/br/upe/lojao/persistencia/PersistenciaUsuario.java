package br.upe.lojao.persistencia;

import java.io.File;
import java.util.ArrayList;

import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;

public class PersistenciaUsuario implements IPersistenciaUsuario{

    private String caminhoCliente = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "cliente.csv";
    private String caminhoFuncionario = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "funcionario.csv";
    private String caminhoAdministrador = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "administrador.csv";

    ILerCSV lerCsv = new LerCSV();
    IEscreverCSV escreverCsv = new EscreverCSV();

    public ArrayList<Cliente> lerClientes() {
        return new ArrayList<>(lerCsv.ler(caminhoCliente, linha ->
            new Cliente(Integer.parseInt(linha[0]), linha[1], linha[2], linha[3], "Cliente",
                linha[4], linha[5], linha[6], Boolean.parseBoolean(linha[7]), Boolean.parseBoolean(linha[8]))
        ));
    }

    public ArrayList<Funcionario> lerFuncionarios() {
        return new ArrayList<>(lerCsv.ler(caminhoFuncionario, linha ->
            new Funcionario(Integer.parseInt(linha[0]), linha[1], linha[2], linha[3], "Funcionario",
                linha[4], linha[5], linha[6], Boolean.parseBoolean(linha[7]))
        ));
    }

    public ArrayList<Administrador> lerAdministradores() {
        return new ArrayList<>(lerCsv.ler(caminhoAdministrador, linha ->
            new Administrador(Integer.parseInt(linha[0]), linha[1], linha[2], linha[3], "Administrador", linha[4])
        ));
    }

    private boolean escreverClientes(ArrayList<Cliente> lista) {
        return escreverCsv.escrever(caminhoCliente,
            new String[]{"id","nome","login","senha","email","telefone","cpf","statusMulta","statusContrato"},
            lista,
            c -> new String[]{ String.valueOf(c.id()), c.nome(), c.login(), c.senha(),
                c.email(), c.telefone(), c.cpf(), String.valueOf(c.statusMulta()), String.valueOf(c.statusContrato()) }
        );
    }

    private boolean escreverFuncionarios(ArrayList<Funcionario> lista) {
        return escreverCsv.escrever(caminhoFuncionario,
            new String[]{"id","nome","login","senha","email","telefone","cpf","statusContrato"},
            lista,
            f -> new String[]{ String.valueOf(f.id()), f.nome(), f.login(), f.senha(),
                f.email(), f.telefone(), f.cpf(), String.valueOf(f.statusContrato()) }
        );
    }

    public int autenticarUsuario(String login, String senha, String tipo) {
        int resposta = -1;
        if (tipo.equalsIgnoreCase("cliente")) {
            ArrayList<Cliente> clientes = lerClientes();
            for (Cliente c : clientes) {
                if (login.equals(c.login()) && senha.equals(c.senha())) {
                    resposta = c.id();
                }
            }
        } else if (tipo.equalsIgnoreCase("funcionario")) {
            ArrayList<Funcionario> funcionarios = lerFuncionarios();
            for (Funcionario f : funcionarios) {
                if (login.equals(f.login()) && senha.equals(f.senha())) {
                    resposta = f.id();
                }
            }
        } else if (tipo.equalsIgnoreCase("administrador")) {
            ArrayList<Administrador> administradores = lerAdministradores();
            for (Administrador a : administradores) {
                if (login.equals(a.login()) && senha.equals(a.senha())) {
                    resposta = a.id();
                }
            }
        }
        return resposta;
    }

    public String cadastrarCliente(Cliente cliente) {
        String resposta = "Erro ao salvar os dados!";
        ArrayList<Cliente> lista = lerClientes();
        if (lista.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        lista.add(cliente);
        if(escreverClientes(lista)){
            resposta = "Salvo com sucesso";
        }
        return resposta;
    }

    public boolean atualizarCliente(int id, int opcao, String dadoModificado) {
        boolean resposta = false;
        ArrayList<Cliente> clientes = lerClientes();
        for(int i=0 ; i < clientes.size() ; i++){
            Cliente c = clientes.get(i);

            if(c.id() == id){
                Cliente atualizado = switch (opcao){
                    case 1 -> new Cliente(c.id(),dadoModificado,c.login(),c.senha(),c.tipo(),c.email(),c.telefone(),c.cpf(),c.statusMulta(),c.statusContrato());
                    case 2 -> new Cliente(c.id(),c.nome(),dadoModificado,c.senha(),c.tipo(),c.email(),c.telefone(),c.cpf(),c.statusMulta(),c.statusContrato());
                    case 3 -> new Cliente(c.id(),c.nome(),c.login(),dadoModificado,c.tipo(),c.email(),c.telefone(),c.cpf(),c.statusMulta(),c.statusContrato());
                    case 4 -> new Cliente(c.id(),c.nome(),c.login(),c.senha(),c.tipo(),dadoModificado,c.telefone(),c.cpf(),c.statusMulta(),c.statusContrato());
                    case 5 -> new Cliente(c.id(),c.nome(),c.login(),c.senha(),c.tipo(),c.email(),dadoModificado,c.cpf(),c.statusMulta(),c.statusContrato());
                    case 6 -> new Cliente(c.id(),c.nome(),c.login(),c.senha(),c.tipo(),c.email(),c.telefone(),dadoModificado,c.statusMulta(),c.statusContrato());
                    case 7 -> new Cliente(c.id(),c.nome(),c.login(),c.senha(),c.tipo(),c.email(),c.telefone(),c.cpf(),Boolean.parseBoolean(dadoModificado),c.statusContrato());
                    case 8 -> new Cliente(c.id(),c.nome(),c.login(),c.senha(),c.tipo(),c.email(),c.telefone(),c.cpf(),c.statusMulta(),Boolean.parseBoolean(dadoModificado));
                    default -> c;
                };
                clientes.set(i, atualizado);
                if(escreverClientes(clientes)){
                    resposta = true;
                }
            }
        }
        return resposta;
    }

    public String deletarCliente(int id) {
        String resposta = "Id nao encontrado";
        ArrayList<Cliente> clientes = lerClientes();
        if (clientes.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.id() == id) {
                clientes.remove(i);
                if (escreverClientes(clientes)) {
                    resposta = "Deletado com sucesso";
                }
            }
        }
        return resposta;
    }

    public ArrayList<Cliente> buscarCliente(String nome) {
        ArrayList<Cliente> clientes = lerClientes();
        ArrayList<Cliente> lista = new ArrayList<>();
        if (clientes.size() == 0) {
            Cliente c = new Cliente(-1, "ERRO", "ERRO", "ERRO", "ERRO", "ERRO", "ERRO", "ERRO", false, false);
            lista.add(c);
        }
        for (Cliente c : clientes) {
            if ((c.nome()).equalsIgnoreCase(nome)) {
                lista.add(c);
            }
        }
        return lista;
    }

    public String cadastrarFuncionario(Funcionario contratado) {
        String resposta = "Erro ao salvar informações";
        ArrayList<Funcionario> lista = lerFuncionarios();
        if (lista.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        lista.add(contratado);
        if (escreverFuncionarios(lista)) {
            resposta = "Cadastrado com sucesso";
        }
        return resposta;
    }

    public boolean atualizarFuncionario(int id, int opcao, String dadoModificado) {
        boolean resposta = false;
        ArrayList<Funcionario> funcionarios = lerFuncionarios();
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);

            if (f.id() == id) {
                Funcionario atualizado = switch (opcao) {
                    case 1 -> new Funcionario(f.id(), dadoModificado, f.login(), f.senha(), f.tipo(), f.email(), f.cpf(), f.telefone(), f.statusContrato());
                    case 2 -> new Funcionario(f.id(), f.nome(), dadoModificado, f.senha(), f.tipo(), f.email(), f.cpf(), f.telefone(), f.statusContrato());
                    case 3 -> new Funcionario(f.id(), f.nome(), f.login(), dadoModificado, f.tipo(), f.email(), f.cpf(), f.telefone(), f.statusContrato());
                    case 4 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), dadoModificado, f.cpf(), f.telefone(), f.statusContrato());
                    case 5 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), f.email(), f.cpf(), dadoModificado, f.statusContrato());
                    case 6 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), f.email(), f.cpf(), f.telefone(), Boolean.parseBoolean(dadoModificado));
                    default -> f;
                };
                funcionarios.set(i, atualizado);
                if(escreverFuncionarios(funcionarios)){
                    resposta = true;
                }
            }
        }
        return resposta;
    }

    public String deletarFuncionario(int id) {
        String resposta = "Id nao encontrado";
        ArrayList<Funcionario> funcionarios = lerFuncionarios();
        if (funcionarios.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            if (f.id() == id) {
                funcionarios.remove(i);
                if (escreverFuncionarios(funcionarios)) {
                    resposta ="Deletado com sucesso";
                }
            }
        }
        return resposta;
    }

    public ArrayList<Funcionario> buscarFuncionario(String nome) {
        ArrayList<Funcionario> funcionarios = lerFuncionarios();
        ArrayList<Funcionario> lista = new ArrayList<>();
        if (funcionarios.size() == 0) {
            Funcionario f = new Funcionario(-1, "ERRO", "ERRO", "ERRO", "ERRO", "ERRO", "ERRO", "ERRO", false);
            lista.add(f);
        }
        for (Funcionario f : funcionarios) {
            if ((f.nome()).equalsIgnoreCase(nome)) {
                lista.add(f);
            }
        }
        return lista;
    }
    
    public boolean clienteExiste(int id) {
        boolean resultado = false;
        ArrayList<Cliente> clientes = lerClientes();
        for (int x = 0; x < clientes.size(); x++) {
            if (clientes.get(x).id() == id) {
                resultado = true;
            }
        }
        return resultado;
    }
}