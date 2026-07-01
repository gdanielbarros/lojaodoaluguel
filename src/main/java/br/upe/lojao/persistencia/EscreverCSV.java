package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.function.Function;

public class EscreverCSV implements IEscreverCSV {

	@Override
	public <T> boolean escrever(String caminho, String[] cabecalho, List<T> dados, Function<T, String[]> conversor){
	    boolean resposta = false;
	    try {
	        File arquivo = new File(caminho);
	        if (arquivo.getParentFile() != null) {
	            arquivo.getParentFile().mkdirs();
	        }
	        try (CSVWriter escritor = new CSVWriter(new BufferedWriter(new FileWriter(caminho)))){
	            escritor.writeNext(cabecalho);
	            for (T entidade : dados){
	                escritor.writeNext(conversor.apply(entidade));
	            }
	            resposta = true;
	        }
	    } catch (Exception e){
	        resposta = false;
	    }
	    return resposta;
	}
}
