package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Administrador;
import br.upe.lojao.persistencia.entidades.Cliente;
import br.upe.lojao.persistencia.entidades.Funcionario;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.util.function.Function;

public class LerCSV implements ILerCSV {

	@Override
    public <T> List<T> ler(String caminho, Function<String[], T> aplicador){
        ArrayList<T> dadosLidos = new ArrayList<>();

        try (CSVReader leitor = new CSVReader(new BufferedReader(new FileReader(caminho)))){
            String[] linha;
            leitor.readNext();
            while ((linha = leitor.readNext()) != null) {
                T entidade = aplicador.apply(linha);
                dadosLidos.add(entidade);
            }    
        }
        catch (Exception e) {
            dadosLidos.clear();
        }
        finally {
            return dadosLidos;
        }
    }
}