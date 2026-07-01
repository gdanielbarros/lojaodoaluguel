package br.upe.lojao.persistencia.entidades;

public class Fornecedor {
	
	private int id;
	private String email;
	private String telefone;
	private String nome;
	private String status;
	
	
	public Fornecedor(int id, String email, String telefone, String nome, String status) {
		this.id = id;
		this.email = email;
		this.telefone = telefone;
		this.nome = nome;
		this.status = status;
	}
	
	public int getId() {
	    return this.id;
	}
	
	public String getEmail() {
	    return this.email;
	}
	
	public String getTelefone() {
	    return this.telefone;
	}
	
	public String getNome() {
	    return this.nome;
	}
	
	public String getStatus() {
	    return this.status;
	}
	
	
	public void setId(int id) {
	    this.id = id;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}
	
	public void setTelefone(String telefone) {
	    this.telefone = telefone;
	}
	
	public void setNome(String nome) {
	    this.nome = nome;
	}
	
	public void setStatus(String status) {
	    this.status = status;
	}
	

}
