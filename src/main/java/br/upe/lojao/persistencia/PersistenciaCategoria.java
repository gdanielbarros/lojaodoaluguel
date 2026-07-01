package br.upe.lojao.persistencia;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.upe.lojao.persistencia.entidades.Categoria;

public class PersistenciaCategoria implements IPersistenciaCategoria {
	
	private String caminhoCategoria = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "categoria.csv";

	public ArrayList<Categoria> lerCategoria() {
		String cadaLinha;
		ArrayList<Categoria> listaCategorias = new ArrayList<>();
		
		File arquivo = new File(caminhoCategoria);
		if (!arquivo.exists()) {
			
			try {
				arquivo.getParentFile().mkdirs();
				BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoCategoria));
				escritor.write("id,nome,quantidade");
				escritor.newLine();
				escritor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return listaCategorias;
		}
		
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminhoCategoria));
			leitor.readLine(); 
			while ((cadaLinha = leitor.readLine()) != null) {
				if (cadaLinha.trim().isEmpty()) continue;
				String[] partes = cadaLinha.split(",");
				
				int id = Integer.parseInt(partes[0].trim());
				String nome = partes[1].trim();
				int quantidade = Integer.parseInt(partes[2].trim());

				Categoria novaCategoria = new Categoria(id, nome, quantidade);
				listaCategorias.add(novaCategoria);
			}
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaCategorias;
	}
	
	public void atualizarCategoria(List<Categoria> lista) {
		try {
			File arquivo = new File(caminhoCategoria);
			arquivo.getParentFile().mkdirs();
			BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoCategoria));
			
			escritor.write("id,nome,quantidade");
			escritor.newLine();
			for(Categoria categoria : lista) {
				String novaLinha = categoria.getId() + "," +
						categoria.getNome() + "," +
						categoria.getQuantidade();
				escritor.write(novaLinha);
				escritor.newLine();
			}
			escritor.flush();
			escritor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}