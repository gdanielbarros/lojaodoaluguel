package br.upe.lojao.persistencia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.math.BigDecimal;

public class PersistenciaContratos implements IPersistenciaContratos {

	private String caminhoContrato = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "contratos.csv";
	private String caminhoMultas = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "ocorrencias.csv";
	
	public ArrayList<Contrato> lerContratos() {
		
		int verificaErro = 0;
		ArrayList<Contrato> listaContratos = new ArrayList<>();
		String[] linha;
		try (CSVReader leitor = new CSVReader(new BufferedReader(new FileReader(this.caminhoContrato)))) {
			leitor.readNext();
			while ((linha = leitor.readNext()) != null){
				
				int id = Integer.parseInt(linha[0]);
				int idCliente = Integer.parseInt(linha[1]);
				LocalDateTime dataInicio = LocalDateTime.parse(linha[2]);
				LocalDateTime dataFinal = LocalDateTime.parse(linha[3]);
				long diasAlugados = Long.parseLong(linha[4]);
				BigDecimal valorTotal = new BigDecimal(linha[5]);
				int idItem = Integer.parseInt(linha[6]);
				String status = linha[7];
				int idMulta = Integer.parseInt(linha[8]);
				BigDecimal valorMulta = new BigDecimal(linha[9]);
				
				Contrato contrato = new Contrato(id, idCliente, dataInicio, dataFinal, diasAlugados, valorTotal, idItem, status, idMulta, valorMulta);
				listaContratos.add(contrato);
			}
		}
		
		catch (IOException e) {
			verificaErro++;
		}
		
		catch (CsvValidationException e) {
			verificaErro++;
		}
		
		if (verificaErro > 0) {
		
			ArrayList<Contrato> listaContratosErro = new ArrayList<>();
			Contrato unico = new Contrato(-1, -1, LocalDateTime.MIN, LocalDateTime.MIN, -1L, BigDecimal.ZERO, -1, "ERRO", -1, BigDecimal.ZERO);
			listaContratosErro.add(unico);
			return listaContratosErro;
			
		}
		else {
		return listaContratos;
	}
		}
	
	public boolean atualizarContratos(List<Contrato> listaContratos) {
		
		int verificaErro = 0;
		ArrayList<String[]> listaContratosAtualizada = new ArrayList<>();
		
		try (CSVWriter escritor = new CSVWriter(new BufferedWriter(new FileWriter(this.caminhoContrato)))){
			String[] cabeçalho = {"id","idCliente","dataInicio","dataFinal","diasAlugados","valorTotal","idItem","status","idMulta","valorMulta"};
			escritor.writeNext(cabeçalho);
			
			for (int x = 0; x < listaContratos.size(); x++) {
				String id = String.valueOf(listaContratos.get(x).id());
				String idCliente = String.valueOf(listaContratos.get(x).idCliente());
				String dataInicio = listaContratos.get(x).dataInicio().toString();
				String dataFinal = listaContratos.get(x).dataFinal().toString();
				String diasAlugados = String.valueOf(listaContratos.get(x).diasAlugados());
				String valortotal = listaContratos.get(x).valorTotal().toString();
				String idItem = String.valueOf(listaContratos.get(x).idItem());
				String status = listaContratos.get(x).status();
				String idMulta = String.valueOf(listaContratos.get(x).idMulta());
				String valorMulta = listaContratos.get(x).valorMulta().toString();
				
				String[] linha = {id, idCliente, dataInicio, dataFinal, diasAlugados, valortotal, idItem, status, idMulta, valorMulta};
				listaContratosAtualizada.add(linha);
			}
			
			escritor.writeAll(listaContratosAtualizada);
			
		}
		
		catch (IOException e) {
			verificaErro++;
		}
		if (verificaErro == 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<Ocorrencias> lerMultas() {

	    int verificaErro = 0;
	    ArrayList<Ocorrencias> listaMultas = new ArrayList<>();
	    String[] linha;
	    try (CSVReader leitor = new CSVReader(new BufferedReader(new FileReader(this.caminhoMultas)))) {
	        leitor.readNext();
	    	while ((linha = leitor.readNext()) != null){

	            int id = Integer.parseInt(linha[0]);
	            int idContrato = Integer.parseInt(linha[1]);
	            int idCliente = Integer.parseInt(linha[2]);
	            BigDecimal valorBase = new BigDecimal(linha[3]);
	            LocalDateTime dataInicio = LocalDateTime.parse(linha[4]);
	            LocalDateTime dataFinal = LocalDateTime.parse(linha[5]);
	            BigDecimal valorFinal = new BigDecimal(linha[6]);
	            BigDecimal valorPorcentagem = new BigDecimal(linha[7]);
	            String avarias = linha[8];
	            String status = linha[9];

	            Ocorrencias ocorrencia = new Ocorrencias(id, idContrato, idCliente, valorBase, dataInicio, dataFinal, valorFinal, valorPorcentagem, avarias, status);
	            listaMultas.add(ocorrencia);
	        }
	    }

	    catch (IOException e) {
	        verificaErro++;
	    }

	    catch (CsvValidationException e) {
	        verificaErro++;
	    }

	    if (verificaErro > 0) {

	        ArrayList<Ocorrencias> listaMultasErro = new ArrayList<>();
	        Ocorrencias unico = new Ocorrencias(-1, -1, -1, BigDecimal.ZERO, LocalDateTime.MIN, LocalDateTime.MIN, BigDecimal.ZERO, BigDecimal.ZERO, "ERRO", "ERRO");
	        listaMultasErro.add(unico);
	        return listaMultasErro;

	    }
	    else {
	        return listaMultas;
	    }
	}

	public boolean atualizarMultas(List<Ocorrencias> listaMultas) {

	    int verificaErro = 0;
	    ArrayList<String[]> listaMultasAtualizada = new ArrayList<>();

	    try (CSVWriter escritor = new CSVWriter(new BufferedWriter(new FileWriter(this.caminhoMultas)))){
	        String[] cabeçalho = {"id","idContrato","idCliente","valorBase","dataInicio","dataFinal","valorFinal","valorPorcetangem","avarias","status"};
	        escritor.writeNext(cabeçalho);

	        for (int x = 0; x < listaMultas.size(); x++) {
	            String id = String.valueOf(listaMultas.get(x).id());
	            String idContrato = String.valueOf(listaMultas.get(x).idContrato());
	            String idCliente = String.valueOf(listaMultas.get(x).idCliente());
	            String valorBase = listaMultas.get(x).valorBase().toString();
	            String dataInicio = listaMultas.get(x).dataInicio().toString();
	            String dataFinal = listaMultas.get(x).dataFinal().toString();
	            String valorFinal = listaMultas.get(x).valorFinal().toString();
	            String valorPorcentagem = listaMultas.get(x).valorPorcentagem().toString();
	            String avarias = listaMultas.get(x).avarias();
	            String status = listaMultas.get(x).status();

	            String[] linha = {id, idContrato, idCliente, valorBase, dataInicio, dataFinal, valorFinal, valorPorcentagem, avarias, status};
	            listaMultasAtualizada.add(linha);
	        }

	        escritor.writeAll(listaMultasAtualizada);

	    }

	    catch (IOException e) {
	        verificaErro++;
	    }
	    if (verificaErro == 0){
	        return true;
	    }
	    else {
	        return false;
	    }
	}
	
	
}
