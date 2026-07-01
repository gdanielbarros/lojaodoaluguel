package br.upe.lojao.negocios;
import br.upe.lojao.persistencia.IPersistenciaUsuario;
import br.upe.lojao.persistencia.PersistenciaUsuario;
import br.upe.lojao.persistencia.IPersistenciaContrato;
import br.upe.lojao.persistencia.PersistenciaContratos;
import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class OperacaoUsuario implements IOperacaoUsuario{

    IPersistenciaUsuario persistenciaUsuario = new PersistenciaUsuario();
    IPersistenciaContrato persistenciaContrato = new PersistenciaContratos();

    public int autenticarUsuario(String login, String senha, String tipo) {
        return persistenciaUsuario.autenticarUsuario(login,senha,tipo);
    }

    public String cadastrarCliente(Cliente cliente){
        String resposta;
        resposta = persistenciaUsuario.cadastrarCliente(cliente);
        return resposta;
    }

    public String editarCliente(int id, int opcao,String dadoModificado){
        String resposta = "Erro ao editar informações";
        if(persistenciaUsuario.atualizarCliente(id,opcao,dadoModificado)){
            resposta = "Editado com sucesso!";
        }
        return resposta;
    }

    public String deletarCliente(int id){
       
        if (!persistenciaContrato.contratosClienteAtivos(id).isEmpty()) {
            return "Nao e possivel excluir: cliente possui aluguel(eis) ativo(s).";
        }
        if (!persistenciaContrato.clienteMultaPendente(id).isEmpty()) {
            return "Nao e possivel excluir: cliente possui multa(s) pendente(s).";
        }
        return persistenciaUsuario.deletarCliente(id);
    }

    public ArrayList<Cliente> buscarCliente(String nome){
        return persistenciaUsuario.buscarCliente(nome);
    }
    
    public ArrayList<Cliente> listarClientes() {
        return persistenciaUsuario.lerClientes();
    }

    public ArrayList<Funcionario> listarFuncionarios() {
        return persistenciaUsuario.lerFuncionarios();
    }

    public ArrayList<Administrador> listarAdministradores() {
        return persistenciaUsuario.lerAdministradores();
    }
    
  

    public String cadastrarFuncionario(Funcionario contratado){
        return persistenciaUsuario.cadastrarFuncionario(contratado);
    }

    public String editarFuncionario(int id, int opcao, String dadoModificado){
        String resposta = "Erro ao editar informações";
        if(persistenciaUsuario.atualizarFuncionario(id,opcao,dadoModificado)){
            resposta = "Editado com sucesso";
        }
        return resposta;
    }

    public String deletarFuncionario(int id) {
        return persistenciaUsuario.deletarFuncionario(id);
    }

    public ArrayList<Funcionario> buscarFuncionario(String nome){
        return persistenciaUsuario.buscarFuncionario(nome);
    }

}