package br.upe.lojao.camada3_persistencia;

import br.upe.lojao.models.Administrador;
import br.upe.lojao.models.Cliente;
import br.upe.lojao.models.Funcionario;
import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PercistenciaUsuario {

    private String caminhoCliente = "caminho aqui";
    private String caminhoFuncionario = "caminho aqui";
    private String caminhoAdministrador = "caminho aqui";

public ArrayList<Cliente> lerCliente() {
    ArrayList<Cliente> clientes = new ArrayList<>();

    try (CSVReader leitor = new CSVReader(new FileReader(caminhoCliente))) {
        String[] linha;

        while((linha = leitor.readNext()) != null){
            int id = Integer.parseInt(linha[0]);
            String nome = linha [1];
            String login = linha[2];
            String senha = linha[3];
            String tipo = "Cliente";
            String email = linha[4];
            String telefone = linha[5];
            String cpf = linha[6];
            boolean statusMulta = Boolean.parseBoolean(linha[7]);
            boolean status = Boolean.parseBoolean(linha[8]);

            Cliente c = new Cliente(id, nome, login, senha, tipo, email, telefone, cpf, statusMulta, status);
            clientes.add(c);
        }
        return clientes;
    } catch (Exception e){
        clientes.clear();
        return clientes;}

}

    public boolean atualizarCliente(List<Cliente> clientes){
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoCliente))){
            escritor.write("id,nome,login,senha,email,telefone,cpf,statusMulta,statusContrato");
            escritor.newLine();
            for(Cliente c : clientes){
                escritor.write(c.id() + "," + c.nome() + "," + c.login() + "," + c.senha() + "," + c.email() + "," + c.telefone() + "," + c.cpf() + "," + c.statusMulta() + "," + c.statusContrato());
                escritor.newLine();
            }
        }catch(Exception e){
            return false;
        }finally {
            return true;
        }
    }

    public ArrayList<Funcionario> lerFuncionario(){
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try (CSVReader leitor = new CSVReader(new FileReader(caminhoFuncionario))) {
            String[] linha;

            while((linha = leitor.readNext()) != null){
                int id = Integer.parseInt(linha[0]);
                String nome = linha [1];
                String login = linha[2];
                String senha = linha[3];
                String tipo = "Funcionario";
                String email = linha[4];
                String telefone = linha[5];
                String cpf = linha[6];
                boolean statusContrato = Boolean.parseBoolean(linha[7]);

                Funcionario f = new Funcionario(id, nome, login, senha, tipo, email, telefone, cpf, statusContrato);
                funcionarios.add(f);
            }
            return funcionarios;
        } catch (Exception e){
            funcionarios.clear();
            return funcionarios;}
    }

    public boolean atualizarFuncionario(List<Funcionario> funcionarios){
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoFuncionario))){
            escritor.write("id,nome,login,senha,email,telefone,cpf,statusContrato");
            escritor.newLine();
            for(Funcionario f : funcionarios){
                escritor.write(f.id() + "," + f.nome() + "," + f.login() + "," + f.senha() + "," + f.email() + "," + f.telefone() + "," + f.cpf() + "," + f.statusContrato());
                escritor.newLine();
            }
        }catch(Exception e){
            return false;
        }finally {
            return true;
        }
    }

    public ArrayList<Administrador> lerAdministrador(){
        ArrayList<Administrador> administradores = new ArrayList<>();

        try (CSVReader leitor = new CSVReader(new FileReader(caminhoAdministrador))) {
            String[] linha;

            while((linha = leitor.readNext()) != null){
                int id = Integer.parseInt(linha[0]);
                String nome = linha [1];
                String login = linha[2];
                String senha = linha[3];
                String tipo = "Administrador";
                String email = linha[4];

                Administrador a = new Administrador(id, nome, login, senha, tipo, email);
                administradores.add(a);
            }
            return administradores;
        } catch (Exception e){
            administradores.clear();
            return administradores;
        }
    }

}
