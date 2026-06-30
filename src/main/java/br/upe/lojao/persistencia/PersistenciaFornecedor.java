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

import br.upe.lojao.persistencia.entidades.Fornecedor;

public class PersistenciaFornecedor {
	
	private String caminhoFornecedor = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "fornecedor.csv";

	public ArrayList<Fornecedor> lerFornecedor() {
		String cadaLinha;
		ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();
		
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminhoFornecedor));
			while ((cadaLinha = leitor.readLine()) != null) {
				String[] partes = cadaLinha.split(",");
				
				//id,nome,email,telefone,status
				int id = Integer.parseInt(partes[0]);
				String nome = partes[1];
				String email = partes[2];
				String telefone = partes[3];
				String status = partes[4];

				Fornecedor novoFornecedor = new Fornecedor(id, email, telefone, nome, status);
				listaFornecedores.add(novoFornecedor);
			}
			leitor.close();
		} catch (IOException e) {
		// TODO: veja o que por aqui.	
		}
		return listaFornecedores;
		
	}
	
	public void atualizarFornecedor(List<Fornecedor> lista) {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoFornecedor));
			for(Fornecedor fornecedor: lista) {
				String novaLinha = fornecedor.getId() + "," +
						fornecedor.getNome() + "," +
						fornecedor.getEmail() + "," +
						fornecedor.getTelefone() + "," +
						fornecedor.getStatus();
				escritor.write(novaLinha);
				escritor.newLine();
			}
			escritor.close();
		} catch (Exception e) {
			// TODO: o que por aqui?
		}
	}

}
