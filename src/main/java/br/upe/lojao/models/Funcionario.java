package br.upe.lojao.models;

public record Funcionario (int id,String nome,String login,String senha,String tipo,String email, boolean statusContrato, String cpf, String numero){
}
