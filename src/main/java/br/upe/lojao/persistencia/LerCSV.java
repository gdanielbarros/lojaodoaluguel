package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LerCSV {

    private String caminhoCliente = "caminho aqui";
    private String caminhoFuncionario = "caminho aqui";
    private String caminhoAdministrador = "caminho aqui";

    public List<Cliente> lerCliente(){
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
        } catch (Exception e){
            clientes.clear();
        }
        finally{
            return clientes;
        }
    }

    public List<Funcionario> lerFuncionario(){
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
        } catch (Exception e){
            funcionarios.clear();
        } finally {
            return funcionarios;
        }
    }


    public List<Administrador> lerAdministrador(){
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
        } finally {
            return administradores;
        }
    }

}
