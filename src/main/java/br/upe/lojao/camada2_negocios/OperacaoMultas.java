package br.upe.lojao.camada2_negocios;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import br.upe.lojao.models.Contrato;
import br.upe.lojao.models.Ocorrencias;
import java.util.List;
import br.upe.lojao.camada3_persistencia.LeituraContratos;

public class OperaçãoMultas implements Serviços {

	private ArrayList<Contrato> listaContrato;
	private ArrayList<Ocorrencias> listaOcorrencias;
	private LeituraContratos leitor = new LeituraContratos();
	
	public OperaçãoMultas() {
		
		this.listaContrato = this.leitor.lerContratos();
		this.listaOcorrencias = this.leitor.lerMultas();
		
	}
	
	@Override
	public int gerarId() {
		if (listaOcorrencias.isEmpty() == true){
			return 1;
		}
		else {
			int maiorTemp = 0;
			for (int x = 0; x < listaOcorrencias.size(); x++) {
				if (maiorTemp < listaOcorrencias.get(x).id()) {
					maiorTemp = listaOcorrencias.get(x).id();
				}
			}
			int idGerado = maiorTemp + 1;
			return idGerado;
		}
	}
	
	@Override
	public boolean verificarExclusão(int idMulta) {
		
		
		if (listaOcorrencias.isEmpty() == true){
			return false;
		}
		
		int indiceMulta = encontrarNaLista(idMulta, 1);
		
		if (listaOcorrencias.get(indiceMulta).status().equals("PAGO")) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean verificarLeitura(int lista) {
		if (lista == 1) {
			if (listaContrato.isEmpty() == true || listaContrato.get(0).id() == -1) {
				return false;
			}
			else {
				return true;
			}
		}
		if (lista == 2) {
			if (listaOcorrencias.isEmpty() == true || listaOcorrencias.get(0).id() == -1) {
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
	
	public int encontrarNaLista(int id, int opcao) {
		int indice = -1;
		
		if (opcao == 1){
			for (int x = 0; x < listaOcorrencias.size(); x++) {
				if (id == listaOcorrencias.get(x).id()){
					indice = x;
					return indice;
				}
				}
			}
		
		else if (opcao == 2){
			for (int x = 0; x < listaContrato.size(); x++) {
				if (id == listaContrato.get(x).id()){
					indice = x;
					return indice;
				}
			}
		}
		return indice;
	}
	
	public BigDecimal calcularMulta(int idContrato) {
		
		int indiceContrato = encontrarNaLista(idContrato, 2);
		BigDecimal taxaProduto = listaContrato.get(indiceContrato).valorTotal().divide(new BigDecimal(listaContrato.get(indiceContrato).diasAlugados()));
		
		LocalDateTime dataFinal = listaContrato.get(indiceContrato).dataFinal();
		LocalDateTime dataAtual = LocalDateTime.now();
		long dias = Duration.between(dataFinal, dataAtual).toDays();
		
		if (dias <= 0) {
			return new BigDecimal("0");
		}
		
		BigDecimal multaDaTaxa = taxaProduto.multiply(new BigDecimal("0.10"));
		BigDecimal multa = multaDaTaxa.multiply(new BigDecimal(dias));
		multa = multa.add(new BigDecimal("20"));

		return multa;
		
	}
	
	public boolean aplicarMulta(int idContrato) {
		
		if (verificarLeitura(1) == false) {
			return false;
		}
		if (verificarLeitura(2) == false) {
			return false;
		}
		int indiceContrato = encontrarNaLista(idContrato, 2);
		if (indiceContrato == -1) {
			return false;
		}
		
		int indiceMulta = encontrarNaLista(listaContrato.get(indiceContrato).idMulta(), 1);
		if (indiceMulta == -1) {
			return false;
		}
		
		int idMulta = listaContrato.get(indiceContrato).idMulta();
		BigDecimal multa = calcularMulta(idContrato);
		BigDecimal zero = new BigDecimal("0");
		if (idMulta == 0 && multa.compareTo(zero) > 0) {
			idMulta = gerarId();
		}
		Contrato contratoNovaMulta = new Contrato(listaContrato.get(indiceContrato).id(), listaContrato.get(indiceContrato).idCliente(), listaContrato.get(indiceContrato).dataInicio(), listaContrato.get(indiceContrato).dataFinal(), listaContrato.get(indiceContrato).diasAlugados(), listaContrato.get(indiceContrato).valorTotal(), listaContrato.get(indiceContrato).idItem(), listaContrato.get(indiceContrato).status(), idMulta, multa);
		Ocorrencias multaAtualizada = new Ocorrencias(idMulta, listaOcorrencias.get(indiceMulta).idContrato(), listaOcorrencias.get(indiceMulta).idCliente(), listaOcorrencias.get(indiceMulta).valorBase(), listaOcorrencias.get(indiceMulta).dataInicio(), listaOcorrencias.get(indiceMulta).dataFinal(), multa, listaOcorrencias.get(indiceMulta).valorPorcentagem(), listaOcorrencias.get(indiceMulta).avarias(), listaOcorrencias.get(indiceMulta).status());
		
		this.listaContrato.remove(indiceContrato);
		this.listaOcorrencias.remove(indiceMulta);
		
		this.listaContrato.add(indiceContrato, contratoNovaMulta);
		this.listaOcorrencias.add(indiceMulta, multaAtualizada);
		
		boolean novoContrato = this.leitor.atualizarContratos(listaContrato);
		boolean novaMulta = this.leitor.atualizarMultas(listaOcorrencias);
	
		if (novoContrato == false || novaMulta == false) {
			return false;
		}
		return true;
	}
	
	public boolean marcarPago(int idMulta) {
	    if (verificarLeitura(2) == false) {return false;}
	    int indiceMulta = encontrarNaLista(idMulta, 1);
	    if (indiceMulta == -1) {return false;}

	    Ocorrencias multaPaga = new Ocorrencias(listaOcorrencias.get(indiceMulta).id(), listaOcorrencias.get(indiceMulta).idContrato(), listaOcorrencias.get(indiceMulta).idCliente(), listaOcorrencias.get(indiceMulta).valorBase(), listaOcorrencias.get(indiceMulta).dataInicio(), listaOcorrencias.get(indiceMulta).dataFinal(), listaOcorrencias.get(indiceMulta).valorFinal(), listaOcorrencias.get(indiceMulta).valorPorcentagem(), listaOcorrencias.get(indiceMulta).avarias(), "PAGO");
	    listaOcorrencias.remove(indiceMulta);
	    listaOcorrencias.add(indiceMulta, multaPaga);
	    this.leitor.atualizarMultas(listaOcorrencias);
	    return true;
	}
	
	public boolean deletarMulta(int idMulta) {
		
		if (verificarLeitura(1) == false) {
			return false;
		}
		if (verificarLeitura(2) == false) {
			return false;
		}
		
		int indiceMulta = encontrarNaLista(idMulta, 1);
		if (indiceMulta == -1) {
			return false;
		}
		int indiceContrato = encontrarNaLista(listaOcorrencias.get(indiceMulta).idContrato(), 2);
		
		if (indiceContrato == -1) {
			return false;
		}
		
		if (verificarExclusão(idMulta) == false) {
			return false;
		}
		
		Contrato contratoNovaMulta = new Contrato(listaContrato.get(indiceContrato).id(), listaContrato.get(indiceContrato).idCliente(), listaContrato.get(indiceContrato).dataInicio(), listaContrato.get(indiceContrato).dataFinal(), listaContrato.get(indiceContrato).diasAlugados(), listaContrato.get(indiceContrato).valorTotal(), listaContrato.get(indiceContrato).idItem(), listaContrato.get(indiceContrato).status(), listaContrato.get(indiceContrato).idMulta(), new BigDecimal("0"));
		this.listaOcorrencias.remove(indiceMulta);
		this.listaContrato.remove(indiceContrato);
		this.listaContrato.add(indiceContrato, contratoNovaMulta);
		
		boolean novoContrato = this.leitor.atualizarContratos(listaContrato);
		boolean novaMulta = this.leitor.atualizarMultas(listaOcorrencias);
	
		if (novoContrato == false || novaMulta == false) {
			return false;
		}
		return true;
		
		
	}
	
	public boolean registrarAvaria(int idMulta, String avaria) {
		if (verificarLeitura(2) == false) {
			return false;
		
		}
	
		int indiceMulta = encontrarNaLista(idMulta, 1);
		
		if (indiceMulta == -1) {
			return false;
		}
		
		Ocorrencias novaAvaria = new Ocorrencias(listaOcorrencias.get(indiceMulta).id(), listaOcorrencias.get(indiceMulta).idContrato(), listaOcorrencias.get(indiceMulta).idCliente(), listaOcorrencias.get(indiceMulta).valorBase(), listaOcorrencias.get(indiceMulta).dataInicio(), listaOcorrencias.get(indiceMulta).dataFinal(), listaOcorrencias.get(indiceMulta).valorFinal(), listaOcorrencias.get(indiceMulta).valorPorcentagem(), avaria, listaOcorrencias.get(indiceMulta).status());
	
		listaOcorrencias.remove(indiceMulta);
		listaOcorrencias.add(indiceMulta, novaAvaria);
		
		boolean novaMulta = this.leitor.atualizarMultas(listaOcorrencias);
		
		if (novaMulta == false) {
			return false;
		}
		return true;
		
	}
	
	public List<Ocorrencias> multasPendentes () {
		if (verificarLeitura(2) == false) {
			ArrayList<Ocorrencias> listaMultasErro = new ArrayList<>();
	        Ocorrencias unico = new Ocorrencias(-1, -1, -1, BigDecimal.ZERO, LocalDateTime.MIN, LocalDateTime.MIN, BigDecimal.ZERO, BigDecimal.ZERO, "ERRO", "ERRO");
	        listaMultasErro.add(unico);
	        return listaMultasErro;
		}
		ArrayList<Ocorrencias> multasPendentes = new ArrayList<>();
		
		for (int x = 0; x < listaOcorrencias.size(); x++) {
			if (listaOcorrencias.get(x).status().equals("Pendente")){
				multasPendentes.add(listaOcorrencias.get(x));
			}
		}
		
		return multasPendentes;
		
	}
	
	public List<Ocorrencias> buscarMultaCliente(int idCliente){
		if (verificarLeitura(2) == false) {
			ArrayList<Ocorrencias> listaMultasErro = new ArrayList<>();
	        Ocorrencias unico = new Ocorrencias(-1, -1, -1, BigDecimal.ZERO, LocalDateTime.MIN, LocalDateTime.MIN, BigDecimal.ZERO, BigDecimal.ZERO, "ERRO", "ERRO");
	        listaMultasErro.add(unico);
	        return listaMultasErro;
		}
		
		ArrayList<Ocorrencias> multasCliente = new ArrayList<>();
		
		for (int x = 0; x < listaOcorrencias.size(); x++) {
			
			if (listaOcorrencias.get(x).idCliente() == idCliente) {
				multasCliente.add(listaOcorrencias.get(x));
			}
			
		}
		return multasCliente;
		
	}
	
}
