package br.upe.lojao.camada2_negocios;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.upe.lojao.models.Contrato;
import br.upe.lojao.models.Ocorrencias;
import java.util.List;

public class OperaçãoMultas implements Serviços {

	private ArrayList<Contrato> listaContrato;
	private ArrayList<Ocorrencias> listaOcorrencias;
	
	@Override
	public int gerarId() {}
	
	@Override
	public boolean verificarExclusão(int idMulta) {}
	
	public BigDecimal calcularMulta(int idContrato) {}
	
	public boolean aplicarMulta(int idContrato) {}
	
	public boolean registrarAvaria(int idContrato) {}
	
	public List<Ocorrencias> multasPendentes () {}
	
	public List<Ocorrencias> buscarMultaCliente(int idCliente){}
	
}
