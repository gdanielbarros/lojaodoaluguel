package br.upe.lojao.models;

public class Categoria {

	private int id;
	private String nome;
	private int quantidade;
	
	public Categoria(int id, int quantidade) {
		this.id = id;
		//this.nome = nome;
		this.quantidade = quantidade;
	}
	
	// Getters
	public int id() {
	    return this.id;
	}
	
	public String nome() {
	    return this.nome;
	}
	
	public int quantidade() {
	    return this.quantidade;
	}

	// Setters
	public void id(int id) {
	    this.id = id;
	}
	
	public void nome(String nome) {
	    this.nome = nome;
	}
	
	public void quantidade(int quantidade) {
	    this.quantidade = quantidade;
	}
	
	
	
}
