package br.upe.lojao.persistencia.entidades;

public class Categoria {

	private int id;
	private String nome;
	private int quantidade;
	
	public Categoria(int id, String nome, int quantidade) {
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
	}
	
	public int getId() {
	    return this.id;
	}
	
	public String getNome() {
	    return this.nome;
	}
	
	public int getQuantidade() {
	    return this.quantidade;
	}

	public void setId(int id) {
	    this.id = id;
	}
	
	public void setNome(String nome) {
	    this.nome = nome;
	}
	
	public void setQuantidade(int quantidade) {
	    this.quantidade = quantidade;
	}
	
	
	
}
