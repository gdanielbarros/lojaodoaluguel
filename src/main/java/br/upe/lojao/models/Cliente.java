package br.upe.lojao.models;

public record Cliente(int id,String nome,String login,String senha,String tipo,String email, String numero,String cpf, boolean statusMulta, boolean statusContrato){
}
