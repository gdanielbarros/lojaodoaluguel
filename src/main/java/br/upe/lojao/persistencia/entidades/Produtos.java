package br.upe.lojao.persistencia.entidades;

import java.math.BigDecimal;

public class Produtos {
    private int id;
    private String nome;
    private BigDecimal taxaDiaria;
    private int idCategoria;
    private int idFornecedor;
    private String disponibilidade;
    private String conservacao;
    private BigDecimal valorRepo;

    public Produtos(int id, String nome, BigDecimal taxaDiaria, int idCategoria, int idFornecedor,
                    String disponibilidade, String conservacao, BigDecimal valorRepo) {
        this.id = id;
        this.nome = nome;
        this.taxaDiaria = taxaDiaria;
        this.idCategoria = idCategoria;
        this.idFornecedor = idFornecedor;
        this.disponibilidade = disponibilidade;
        this.conservacao = conservacao;
        this.valorRepo = valorRepo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTaxaDiaria() {
        return taxaDiaria;
    }

    public void setTaxaDiaria(BigDecimal taxaDiaria) {
        this.taxaDiaria = taxaDiaria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getConservacao() {
        return conservacao;
    }

    public void setConservacao(String conservacao) {
        this.conservacao = conservacao;
    }

    public BigDecimal getValorRepo() {
        return valorRepo;
    }

    public void setValorRepo(BigDecimal valorRepo) {
        this.valorRepo = valorRepo;
    }

}