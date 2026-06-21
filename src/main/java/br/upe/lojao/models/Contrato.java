package br.upe.lojao.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Contrato(int id, Cliente cliente, LocalDateTime dataInicio, LocalDateTime dataFinal, int diasAlugados, BigDecimal valorTotal, Produtos item, Ocorrencias problemas, String status) {}
