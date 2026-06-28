package br.upe.lojao.negocios;

import java.math.BigDecimal;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Ocorrencias;

public interface IOperacaoMultas {
    int gerarId();
    boolean verificarExclusão(int idMulta);
    BigDecimal calcularMulta(int idContrato);
    boolean aplicarMulta(int idContrato);
    boolean marcarPago(int idMulta);
    boolean deletarMulta(int idMulta);
    boolean registrarAvaria(int idMulta, String avaria);
    List<Ocorrencias> multasPendentes();
    List<Ocorrencias> buscarMultaCliente(int idCliente);
}