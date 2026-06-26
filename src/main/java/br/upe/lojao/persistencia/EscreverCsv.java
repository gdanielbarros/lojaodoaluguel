package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import br.upe.lojao.persistencia.entidades.IEscreverCsv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class EscreverCsv implements IEscreverCsv {

    private String caminhoCliente = "caminho aqui";
    private String caminhoFuncionario = "caminho aqui";

    public boolean escreverCliente(ArrayList<Cliente> clientes){
        boolean resposta = false;
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoCliente))){
            escritor.write("id,nome,login,senha,email,telefone,cpf,statusMulta,statusContrato");
            escritor.newLine();
            for(Cliente c : clientes){
                escritor.write(c.id() + "," + c.nome() + "," + c.login() + "," + c.senha() + "," + c.email() + "," + c.telefone() + "," + c.cpf() + "," + c.statusMulta() + "," + c.statusContrato());
                escritor.newLine();
            }
            resposta = true;
        }catch(Exception e){
            resposta = false;
        }finally {
            return resposta;
        }
    }

    public boolean escreverFuncionario(ArrayList<Funcionario> funcionarios){
        boolean resposta = false;
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoFuncionario))){
            escritor.write("id,nome,login,senha,email,telefone,cpf,statusMulta,statusContrato");
            escritor.newLine();
            for(Funcionario f : funcionarios){
                escritor.write(f.id() + "," + f.nome() + "," + f.login() + "," + f.senha() + "," + f.email() + "," + f.telefone() + "," + f.cpf() + "," + f.statusContrato());
                escritor.newLine();
            }
            resposta = true;
        }catch(Exception e){
            resposta = false;
        }finally {
            return resposta;
        }
    }

}
