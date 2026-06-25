package br.upe.lojao.models;

public record Funcionario (int id,String nome,String login,String senha,String tipo,String email, String cpf, String telefone, boolean statusContrato){
}
