package br.upe.lojao.persistencia;

public record Cliente(int id,String nome,String login,String senha,String tipo,String email, String telefone,String cpf, boolean statusMulta, boolean statusContrato){}
