package br.upe.lojao.camada2_negocios;
import br.upe.lojao.facade.Facade;
import br.upe.lojao.models.Administrador;
import br.upe.lojao.models.Cliente;
import br.upe.lojao.models.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class OperacaoUsuario {
    private Facade facade;

    public boolean autenticarUsuario(String login, String senha, String tipo){
        int i;
        if(tipo.equalsIgnoreCase("cliente")) {
            ArrayList<Cliente> clientes = facade.lerCliente();
            for(Cliente c : clientes){
                if(login.equals(c.login()) && senha.equals(c.senha())){
                    return true;
                }
            }
            return false;
        }


        else if(tipo.equalsIgnoreCase("funcionario")){
            ArrayList<Funcionario> funcionarios = facade.lerFuncionario();
            for(Funcionario f : funcionarios){
                if(login.equals(f.login()) && senha.equals(f.senha())){
                    return true;
                }
            }
            return false;
        }

        else if(tipo == "administrador" || tipo == "Administrador"){
            ArrayList<Administrador> administradores = facade.lerAdministrador();
            for(Administrador a : administradores){
                if(login.equals(a.login()) && senha.equals(a.senha())){
                    return true;
                }
            }
            return false;
        }
    }

    public String cadastararCliente(Cliente cliente){
        ArrayList<Cliente> lista = lerCliente();
        if(clientes.size() == 0){return "Erro ao ler o arquivo CSV";}
        lista.add(cliente);
        if(atualizarCliente(lista)){
            return "Cadastrado com sucesso";
        }
        else{
            return "Erro ao cadastrar";
        }
    }

    public String editarCliente(int id, int opcao, String dadoModificado){
        ArrayList<Cliente> clientes = lerCliente();
        if(clientes.size() == 0){return "Erro ao ler o arquivo CSV";}
        for(int i=0 ; i < clientes.size() ; i++){
            Cliente c = clientes.get(i);

            if(c.id() == id){
                Cliente atualizado = switch (opcao){
                    case 1 -> new Cliente(c.id(), dadoModificado, c.login(), c.senha(),c.tipo(), c.email(), c.telefone(), c.cpf(), c.statusMulta(), c.statusContrato());
                    case 2 -> new Cliente(c.id(), c.nome(), dadoModificado, c.senha(), c.tipo(), c.email(), c.telefone(), c.cpf(), c.statusMulta(), c.statusContrato());
                    case 3 -> new Cliente(c.id(), c.nome(), c.login(), dadoModificado, c.tipo(), c.email(), c.telefone(), c.cpf(), c.statusMulta(), c.statusContrato());
                    case 4 -> new Cliente(c.id(), c.nome(), c.login(), c.senha(), c.tipo(), dadoModificado, c.telefone(), c.cpf(), c.statusMulta(), c.statusContrato());
                    case 5 -> new Cliente(c.id(), c.nome(), c.login(), c.senha(), c.tipo(), c.email(), dadoModificado, c.cpf(), c.statusMulta(), c.statusContrato());
                    case 6 -> new Cliente(c.id(), c.nome(), c.login(), c.senha(), c.tipo(), c.email(), c.telefone(), dadoModificado, c.statusMulta(), c.statusContrato());
                    case 7 -> new Cliente(c.id(), c.nome(), c.login(), c.senha(), c.tipo(), c.email(), c.telefone(), c.cpf(), Boolean.parseBoolean(dadoModificado), c.statusContrato());
                    case 8 -> new Cliente(c.id(), c.nome(), c.login(), c.senha(), c.tipo(), c.email(), c.telefone(), c.cpf(), c.statusMulta(), Boolean.parseBoolean(dadoModificado));
                    default -> c;
                };
                clientes.set(i, atualizado);
                if(atualizarCliente(clientes)){
                    return "Operção realizada com sucesso";
                }
                else{
                    return "Erro ao salvar as alterações";
                }
            }
        }
        return "ID nao encontrado";
    }

    public String deletarCliente(int id){
        ArrayList<Cliente> clientes = lerCliente();
        if(clientes.size() == 0){return "Erro ao ler o arquivo CSV";}
        for(int i=0 ; i < clientes.size() ; i++){
            Cliente c = clientes.get(i);
            if(c.id() == id){
                clientes.remove(i);
                if(atualizarCliente(clientes)){
                    return "Deletado com sucesso";
                }
                else{
                    return "Erro ao salvar alterações";
                }
            }
        }
        return "ID nao encontrado";
    }

    public ArrayList<Cliente> buscarCliente(String nome){
        ArrayList<Cliente> clientes = lerCliente();
        if(clientes.size() == 0){
            Cliente c = new Cliente(0,"ERRO","ERRO","ERRO","ERRO","ERRO","ERRO","ERRO",false,false);
            clientes.add(c);
            return clientes;
        }
        ArrayList<Cliente> lista = new ArrayList<>();
        for(Cliente c : clientes){
            if((c.nome()).equalsIgnoreCase(nome)){
                lista.add(c);
            }
        }
        return lista;
    }

    public String cadastrarFuncionario(Funcionario contratado){
        ArrayList<Funcionario> lista = lerFuncionario();
        if(lista.size() == 0){return "Erro ao ler o arquivo CSV";}
        lista.add(contratado);
        if(atualizarFuncionario(lista)){
            return "Cadastrado com sucesso";
        }
        else{
            return "Erro ao cadastrar";
        }
    }

    public String editarFuncionario(int id, int opcao, String dadoModificado){
        ArrayList<Funcionario> funcioanrios = lerFuncionario();
        if(funcioanrios.size() == 0){return "Erro ao ler o arquivo CSV";}
        for(int i=0 ; i < funcioanrios.size() ; i++){
            Funcionario f = funcioanrios.get(i);

            if(f.id() == id){
                Funcionario atualizado = switch (opcao){
                    case 1 -> new Funcionario(f.id(), dadoModificado, f.login(), f.senha(), f.tipo(), f.email(), f.cpf(), f.telefone(), f.statusContrato());
                    case 2 -> new Funcionario(f.id(), f.nome(), dadoModificado, f.senha(), f.tipo(), f.email(), f.cpf(), f.telefone(), f.statusContrato());
                    case 3 -> new Funcionario(f.id(), f.nome(), f.login(), dadoModificado, f.tipo(), f.email(), f.cpf(), f.telefone(), f.statusContrato());
                    case 4 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), dadoModificado, f.cpf(), f.telefone(), f.statusContrato());
                    case 5 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), f.email(), dadoModificado, f.telefone(), f.statusContrato());
                    case 6 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), f.email(), f.cpf(), dadoModificado, f.statusContrato());
                    case 7 -> new Funcionario(f.id(), f.nome(), f.login(), f.senha(), f.tipo(), f.email(), f.cpf(), f.telefone(), Boolean.parseBoolean(dadoModificado));
                    default -> f;
                };
                funcioanrios.set(i, atualizado);
                if(atualizarFuncionario(funcionarios)){
                    return "Operação realizada coms ucesso";
                }
                else{
                    return "Erro ao slavar alterações";
                }

            }
        }
        return "ID nao encontrado";
    }

    public String deletarFuncionario(int id){
        ArrayList<Funcionario> funcionarios = lerFuncionario();
        if(funcionarios.size() == 0){return "Erro ao ler o arquivo CSV";}
        for(int i=0 ; i < funcionarios.size() ; i++){
            Funcionario f = funcionarios.get(i);
            if(f.id() == id){
                funcionarios.remove(i);
                if(atualizarFuncionario(funcionarios)){
                    return "Deletado com sucesso";
                }
                else{
                    return "Erro ao salvar alterações";
                }
            }
        }
        return "ID nao encontrado";
    }

    public ArrayList<Funcionario> buscarFuncionario(String nome){
        ArrayList<Funcionario> funcionarios = lerFuncionario();
        if(funcionarios.size() == 0){
            Funcionario f = new Funcionario(0,"ERRO","ERRO","ERRO","ERRO","ERRO","ERRO","ERRO",false);
            funcionarios.add(f);
            return funcionarios;
        }
        ArrayList<Funcionario> lista = new ArrayList<>();
        for(Funcionario f : funcionarios){
            if((f.nome()).equalsIgnoreCase(nome)){
                lista.add(f);
            }
        }
        return lista;
    }

}
