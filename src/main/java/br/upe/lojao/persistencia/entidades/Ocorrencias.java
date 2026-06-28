package br.upe.lojao.persistencia.entidades;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Ocorrencias(int id, int idContrato, int idCliente, BigDecimal valorBase, LocalDateTime dataInicio, LocalDateTime dataFinal, BigDecimal valorFinal, BigDecimal valorPorcentagem, String avarias, String status) {

}
