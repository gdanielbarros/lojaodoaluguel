package br.upe.lojao.persistencia;

import java.util.ArrayList;

public class PersistenciaUsuario implements IPersistenciaUsuario{

    ILeituraUsuario leituraUsuario = new LeituraUsuario();
    IEscreverUsuario escreverUsuario = new EscreverUsuario();

    public int autenticarUsuario(String login, String senha, String tipo) {
        int resposta = -1;
        if (tipo.equalsIgnoreCase("cliente")) {
            ArrayList<Cliente> clientes = leituraUsuario.lerCliente();
            for (Cliente c : clientes) {
                if (login.equals(c.login()) && senha.equals(c.senha())) {
                    resposta = c.id();
                }
            }
        } else if (tipo.equalsIgnoreCase("funcionario")) {
            ArrayList<Funcionario> funcionarios = leituraUsuario.lerFuncionario();
            for (Funcionario f : funcionarios) {
                if (login.equals(f.login()) && senha.equals(f.senha())) {
                    resposta = f.id();
                }
            }
        } else if (tipo.equalsIgnoreCase("administrador")) {
            ArrayList<Administrador> administradores = leituraUsuario.lerAdministrador();
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
        ArrayList<Cliente> lista = leituraUsuario.lerCliente();
        if (lista.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        lista.add(cliente);
        if(escreverUsuario.escreverCliente(lista)){
            resposta = "Salvo com sucesso";
        }
        return resposta;
    }

    public boolean atualizarCliente(int id, int opcao, String dadoModificado) {
        boolean resposta = false;
        ArrayList<Cliente> clientes = leituraUsuario.lerCliente();
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
                if(escreverUsuario.escreverCliente(clientes)){
                    resposta = true;
                }

            }
        }
        return resposta;
    }

    public String deletarCliente(int id) {
        String resposta = "Id nao encontrado";
        ArrayList<Cliente> clientes = leituraUsuario.lerCliente();
        if (clientes.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.id() == id) {
                clientes.remove(i);
                if (escreverUsuario.escreverCliente(clientes)) {
                    resposta = "Deletado com sucesso";
                }
            }
        }
        return resposta;
    }

    public ArrayList<Cliente> buscarCliente(String nome) {
        ArrayList<Cliente> clientes = leituraUsuario.lerCliente();
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
        ArrayList<Funcionario> lista = leituraUsuario.lerFuncionario();
        if (lista.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        lista.add(contratado);
        if (escreverUsuario.escreverFuncionario(lista)) {
            resposta = "Cadastrado com sucesso";
        }
        return resposta;
    }


    public boolean atualizarFuncionario(int id, int opcao, String dadoModificado) {
        boolean resposta = false;
        ArrayList<Funcionario> funcionarios = leituraUsuario.lerFuncionario();
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
                if(escreverUsuario.escreverFuncionario(funcionarios)){
                    resposta = true;
                }
            }
        }
        return resposta;
    }


    public String deletarFuncionario(int id) {
        String resposta = "Id nao encontrado";
        ArrayList<Funcionario> funcionarios = leituraUsuario.lerFuncionario();
        if (funcionarios.size() == 0) {
            resposta = "Erro ao ler o arquivo CSV";
        }
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            if (f.id() == id) {
                funcionarios.remove(i);
                if (escreverUsuario.escreverFuncionario(funcionarios)) {
                    resposta ="Deletado com sucesso";
                }
            }
        }
        return resposta;
    }

    public ArrayList<Funcionario> buscarFuncionario(String nome) {
        ArrayList<Funcionario> funcionarios = leituraUsuario.lerFuncionario();
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

}