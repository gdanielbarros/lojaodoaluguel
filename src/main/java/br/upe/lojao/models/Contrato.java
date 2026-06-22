package br.upe.lojao.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Contrato(int id, int idCliente, LocalDateTime dataInicio, LocalDateTime dataFinal, long diasAlugados, BigDecimal valorTotal, int idItem, String status, int idMulta, BigDecimal valorMulta) {
	}
