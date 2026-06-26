package br.upe.lojao.negocios;
import br.upe.lojao.facade.Facade;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import java.util.ArrayList;

public class OperacaoUsuario {
    private Facade facade;

    public String cadastrarCliente(Cliente cliente, String tipoUsuario ){
        String resposta;
        if(tipoUsuario.equalsIgnoreCase("funcionario") || tipoUsuario.equalsIgnoreCase("administrador")){
            resposta = cadastrarCliente(cliente);
        }
        else{
            resposta = "Acesso negado!";
        }

        return resposta;
    }

    public String editarCliente(int id, int opcao,String dadoModificado){
        String resposta = "Erro ao editar informações";
        if(atualizarCliente()){
            resposta = "Editado com sucesso!";
        }
        return resposta;
    }

    public String deletarCliente(int id){
        return deletarCliente(id);
    }

    public ArrayList<Cliente> buscarCliente(String nome){
        return buscarCliente(nome);
    }

    public String cadastrarFuncionario(Funcionario contratado){
        return cadastrarFuncionario(contratado);
    }

    public String editarFuncionario(int id, int opcao, String dadoModificado){
        String resposta = "Erro ao editar informações";
        if(editarFuncionario(id,opcao,dadoModificado)){
            resposta = "Editado com sucesso";
        }
        return resposta;
    }

    public String deletarFuncionario(int id) {
        return deletarFuncionario(id);
    }

    public ArrayList<Funcionario> buscarFuncionario(String nome){
        return buscarFuncionario(nome);
    }

}
