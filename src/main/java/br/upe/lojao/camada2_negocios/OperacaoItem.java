package br.upe.lojao.camada2_negocios;

import br.upe.lojao.camada3_persistencia.LeituraProdutos;
import br.upe.lojao.models.Produtos;
import br.upe.lojao.models.Categoria;
import br.upe.lojao.models.Fornecedor;

import java.util.ArrayList;
import java.util.List;

public class OperacaoItem implements Servicos {

	private ArrayList<Produtos> listaProdutos;
	private ArrayList<Categoria> listaCategoria;
	private ArrayList<Fornecedor> listaFornecedor;
	
	
	public OperacaoItem(){
		LeituraProdutos leitor = new LeituraProdutos();
		this.listaProdutos = leitor.lerProduto();
		this.listaCategoria = leitor.lerCategoria();
		this.listaFornecedor = leitor.lerFornecedor();
	}
	
	
	public boolean cadastrar(Produtos item) {
		
	}
	
	public boolean verificarCategoriaFornecedor() {
		
	}
	
	public boolean editarItem(int id) {
		
	}
	
	public List<Produtos> buscarItem(String nome) {
		
	}
	
	public List<Produtos> listarItemDisponivel() {
		
	}
	
	public List<Produtos> lisarTodosItens() {
		
	}
	
	public boolean deletarItem(int id) {
		
	}
	
	
	@Override
	public int gerarID() {
	    
	}

	@Override
	public boolean verificarExclusão(int id) {
	    
	}
	
}
