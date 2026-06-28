package br.upe.lojao.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface IEscreverCSV {
	<T> boolean escrever(String caminho, String[] cabecalho, List<T> dados, Function<T, String[]> conversor);
}