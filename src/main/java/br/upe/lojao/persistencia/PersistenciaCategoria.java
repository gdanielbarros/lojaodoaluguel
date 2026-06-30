package br.upe.lojao.persistencia;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.upe.lojao.persistencia.entidades.Categoria;


public class PersistenciaCategoria implements IPersistenciaCategoria {
	
	private String caminhoCategoria = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "categoria.csv";

	public ArrayList<Categoria> lerCategoria() {
		String cadaLinha;
		ArrayList<Categoria> listaCategorias = new ArrayList<>();
		
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminhoCategoria));
			while ((cadaLinha = leitor.readLine()) != null) {
				String[] partes = cadaLinha.split(",");
				
				//id,nome,quantidade
				int id = Integer.parseInt(partes[0]);
				String nome = partes[1];
				int quantidade = Integer.parseInt(partes[2]);

				Categoria novaCategoria = new Categoria(id, nome, quantidade);
				listaCategorias.add(novaCategoria);
			}
			leitor.close();
		} catch (IOException e) {
		// TODO: veja o que por aqui.	
		}
		return listaCategorias;
	}
	
	public void atualizarCategoria(List<Categoria> lista) {
		
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoCategoria));
			for(Categoria categoria: lista) {
				String novaLinha = categoria.getId() + "," +
						categoria.getNome() + "," +
						categoria.getQuantidade();
				escritor.write(novaLinha);
				escritor.newLine();
			}
			escritor.close();
		} catch (Exception e) {
			// TODO: o que por aqui?
		}
	}

}
