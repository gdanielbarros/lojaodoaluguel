package br.upe.lojao.persistencia;

import java.util.List;
import java.util.function.Function;

public interface ILerCSV<T> {
    List<T> ler(String caminho, Function<String[], T> aplicador);
}