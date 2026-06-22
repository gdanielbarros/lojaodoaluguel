package com.loja.model;

import java.math.BigDecimal;

/**
 * Classe MODEL (entidade) que representa um item disponível para aluguel
 * na loja.
 *
 * IMPORTANTE: esta classe pertence à CAMADA DE MODELS (entidades).
 * Ela é "burra" de propósito: só guarda dados sobre um produto, ela NÃO
 * decide nada (não calcula multa, não verifica se pode alugar, etc).
 * Quem decide essas coisas é a camada de SERVIÇO (ex: OperaçãoProduto),
 * que vai usar este objeto, mas a regra de negócio fica fora daqui.
 * Isso segue o princípio de Responsabilidade Única (cada classe faz 1 coisa).
 */
public class Produtos {

    // Identificador único do produto (equivalente a uma "chave primária").
    private int id;

    private String nome;

    /**
     * Valor cobrado por dia de aluguel.
     * Usamos BigDecimal (e não double/float) porque tipos de ponto flutuante
     * têm erro de arredondamento e não são seguros para cálculos com dinheiro.
     * Ex: em double, 0.1 + 0.2 não resulta exatamente em 0.3.
     * BigDecimal é o tipo padrão usado em Java para valores monetários.
     */
    private BigDecimal taxaDiaria;

    /**
     * Aqui guardamos o OBJETO Categoria inteiro, e não apenas o nome dele
     * (String) ou o id (int). Isso é chamado de ASSOCIAÇÃO/COMPOSIÇÃO entre
     * classes: dizemos que "um Produto TEM UMA Categoria".
     * Vantagem: se os dados da categoria mudarem, todo produto que aponta
     * para ela reflete a mudança automaticamente, pois é a mesma referência
     * na memória. É exatamente essa relação que aparece como uma seta entre
     * as caixas no diagrama UML.
     */
    private Categoria categoria;

    // Mesma ideia da Categoria: o Produto "tem um" Fornecedor.
    private Fornecedor fornecedor;

    /**
     * Indica se o produto está livre para alugar (ex: "Disponivel" ou
     * "Alugado"). Está como String aqui para seguir fielmente o diagrama
     * UML do grupo. Em uma versão futura, mais robusta, o ideal seria
     * trocar por um enum (StatusItem.DISPONIVEL, StatusItem.ALUGADO...)
     * para evitar erros de digitação, mas isso é uma melhoria a se discutir
     * com o grupo, e não algo a já decidir sozinho.
     */
    private String disponibilidade;

    // Estado físico/conservação do item (ex: "Bom", "Ótimo", "Danificado").
    private String conservacao;

    /**
     * Valor que o cliente teria que pagar caso o item seja perdido ou
     * danificado de forma irreparável (RN02/RN03 do enunciado dependem
     * deste valor para calcular multas, mas esse cálculo NÃO é feito aqui).
     */
    private BigDecimal valorRepo;

    /**
     * Construtor: é o "molde" usado para criar um novo Produto já com
     * todos os dados preenchidos de uma vez.
     * Exemplo de uso (visto na classe de teste):
     *   new Produtos(1, "Furadeira", new BigDecimal("25.00"), categoria,
     *                fornecedor, "Disponivel", "Bom", new BigDecimal("450.00"));
     */
    public Produtos(int id, String nome, BigDecimal taxaDiaria, Categoria categoria,
                     Fornecedor fornecedor, String disponibilidade, String conservacao,
                     BigDecimal valorRepo) {
        this.id = id;
        this.nome = nome;
        this.taxaDiaria = taxaDiaria;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.disponibilidade = disponibilidade;
        this.conservacao = conservacao;
        this.valorRepo = valorRepo;
    }

    // ---------------------------------------------------------------
    // GETTERS e SETTERS
    // ---------------------------------------------------------------
    // Todos os atributos acima são "private": nenhuma outra classe pode
    // acessar ou alterar esses valores diretamente (ex: produto.id = 5
    // NÃO compila fora desta classe). Esse é o conceito de ENCAPSULAMENTO.
    // Os métodos abaixo são a única "porta de entrada/saída" controlada
    // para ler (get) ou alterar (set) cada atributo.
    // ---------------------------------------------------------------

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    /**
     * Sobrescrevemos o toString() só para fins de DEPURAÇÃO/TESTE.
     * Por padrão, se você imprimir um objeto Java sem isso, aparece algo
     * como "com.loja.model.Produtos@4b1210ee" (ilegível e inútil).
     * Com o método sobrescrito, conseguimos ver os dados reais do produto
     * no console, o que ajuda muito enquanto estamos testando a classe.
     * Isso NÃO é regra de negócio, é só apresentação para debug.
     */
    @Override
    public String toString() {
        return "Produtos{"
                + "id=" + id
                + ", nome='" + nome + "'"
                + ", taxaDiaria=" + taxaDiaria
                + ", categoria=" + (categoria != null ? categoria.getNome() : "null")
                + ", fornecedor=" + (fornecedor != null ? fornecedor.getNome() : "null")
                + ", disponibilidade='" + disponibilidade + "'"
                + ", conservacao='" + conservacao + "'"
                + ", valorRepo=" + valorRepo
                + "}";
    }
}