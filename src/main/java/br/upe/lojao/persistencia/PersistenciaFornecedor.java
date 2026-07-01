package br.upe.lojao.persistencia;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.upe.lojao.persistencia.entidades.Fornecedor;

public class PersistenciaFornecedor implements IPersistenciaFornecedor {
	
	private String caminhoFornecedor = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "fornecedor.csv";

	public ArrayList<Fornecedor> lerFornecedor() {
		String cadaLinha;
		ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();
		
		File arquivo = new File(caminhoFornecedor);
		if (!arquivo.exists()) {
			
			try {
				arquivo.getParentFile().mkdirs();
				BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoFornecedor));
				escritor.write("id,nome,email,telefone,status");
				escritor.newLine();
				escritor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return listaFornecedores;
		}
		
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminhoFornecedor));
			leitor.readLine(); 
			while ((cadaLinha = leitor.readLine()) != null) {
				if (cadaLinha.trim().isEmpty()) continue;
				String[] partes = cadaLinha.split(",");
				
				int id = Integer.parseInt(partes[0].trim());
				String nome = partes[1].trim();
				String email = partes[2].trim();
				String telefone = partes[3].trim();
				String status = partes[4].trim();

				Fornecedor novoFornecedor = new Fornecedor(id, email, telefone, nome, status);
				listaFornecedores.add(novoFornecedor);
			}
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaFornecedores;
	}
	
	public void atualizarFornecedor(List<Fornecedor> lista) {
		try {
			File arquivo = new File(caminhoFornecedor);
			arquivo.getParentFile().mkdirs();
			BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoFornecedor));
			
			escritor.write("id,nome,email,telefone,status");
			escritor.newLine();
			for(Fornecedor fornecedor : lista) {
				String novaLinha = fornecedor.getId() + "," +
						fornecedor.getNome() + "," +
						fornecedor.getEmail() + "," +
						fornecedor.getTelefone() + "," +
						fornecedor.getStatus();
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