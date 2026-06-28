package br.upe.lojao.persistencia;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import br.upe.lojao.entidades.Produtos;
import br.upe.lojao.persistencia.entidades.Categoria;
import br.upe.lojao.persistencia.entidades.Fornecedor;

public class PersistenciaProdutos {

	private String caminhoProduto = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "produtos.csv";
	
	public ArrayList<Produtos> lerProduto() {
		String cadaLinha;
		ArrayList<Produtos> listaProdutos = new ArrayList<>(); // receberá cada produto
		
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminhoProduto));
			while ((cadaLinha = leitor.readLine()) != null) {
				String[] partes = cadaLinha.split(",");
				
				// id,taxaDiaria,idCategoria,idFornecedor,disponibilidade,conservação,valorReposição
				int id = Integer.parseInt(partes[0]);
				BigDecimal taxaDiaria = new BigDecimal(partes[1]);
				int idCategoria = Integer.parseInt(partes[2]);
				int idFornecedor = Integer.parseInt(partes[3]);
				String disponibilidade = partes[4];
				String conservacao = partes[5];
				BigDecimal valorRepo = new BigDecimal(partes[6]); 
				//private String nome;
				
				// cria o produto e adiciona a lista.
				Produtos novoProduto = new Produtos(id, taxaDiaria, idCategoria, idFornecedor, disponibilidade, conservacao, valorRepo);
				listaProdutos.add(novoProduto);
				
			}
					
		} catch (IOException e) {
		// TODO: veja o que por aqui.	
		}
		return listaProdutos;
	}
	
	public void atualizarProdutos(List<Produtos> lista) {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoProduto));
			for(Produtos produto: lista) {
				// usar os getters pra pegar os atributos e costurar a nova linha.
				String novaLinha = produto.getId() + "," +
						produto.getTaxaDiaria() + "," +
						produto.getIdCategoria() + "," +
						produto.getIdFornecedor() + "," +
						produto.getDisponibilidade() + "," +
						produto.getConservacao() + "," +
						produto.getValorRepo();
				escritor.write(novaLinha);
				escritor.newLine();
				// se existe println pq não um writeln?
			}
		} catch (exception e) {
			// TODO: o que por aqui?
		}
	}
	
}

