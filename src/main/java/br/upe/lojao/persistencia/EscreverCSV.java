package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.util.function.Function;

public class EscreverCsv<T> implements IEscreverCSV {

    public boolean escrever(String caminho, String[] cabecalho, ArrayList<T> dados, Function<T, String[]> conversor){
        boolean resposta = false;

        try (CSVWriter escritor = new CSVWriter(new FileWriter(caminho))){
            escritor.writeNext(cabecalho);
            for (T entidade : dados){
                escritor.writeNext(conversor.apply(entidade));
            }
            resposta = true;
        } catch (Exception e){
            resposta = false;
        } finally {
            return resposta;
        }
    }
}
