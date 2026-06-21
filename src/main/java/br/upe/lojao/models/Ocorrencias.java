package br.upe.lojao.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Ocorrencias(BigDecimal valorBase, LocalDateTime dataInicio, LocalDateTime dataFinal, BigDecimal valorFinal, BigDecimal valorPorcentagem, String avarias) {

}
