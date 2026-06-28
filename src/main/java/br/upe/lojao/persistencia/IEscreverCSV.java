package br.upe.lojao.persistencia;

import java.util.ArrayList;
import java.util.function.Function;

public interface IEscreverCSV<T> {
    boolean escrever(String caminho, String[] cabecalho, ArrayList<T> dados, Function<T, String[]> conversor);
}