package br.upe.lojao.negocios;

import br.upe.lojao.persistencia.PersistenciaProdutos;
import br.upe.lojao.persistencia.entidades.Produtos;
import br.upe.lojao.persistencia.entidades.Categoria;
import br.upe.lojao.persistencia.entidades.Fornecedor;

import java.util.ArrayList;
import java.util.List;

public class OperacaoItem implements IOperacaoItem {

	private ArrayList<br.upe.lojao.persistencia.entidades.Produtos> listaProdutos;
	private ArrayList<Categoria> listaCategoria;
	private ArrayList<Fornecedor> listaFornecedor;
	
	
	public OperacaoItem(){
		PersistenciaProdutos leitorProduto = new PersistenciaProdutos();
		LeituraCategoria = leitorCategoria = new LeituraCategoria();
		LeituraFornecedor = leitorFornecedor = new LeituraFornecedor();
		this.listaProdutos = leitorProduto.lerProduto();
		this.listaCategoria = leitorCategoria.lerCategoria();
		this.listaFornecedor = leitorFornecedor.lerFornecedor();
	}
	
	@Override
	public boolean cadastrar(Produtos item) {
		// verificar, criar ID e settar.
		if(verificarCategoriaFornecedor(item)) {
			int novoId = gerarId();
			item.setId(novoId);
			// adiciona a lista e atualiza o CSV
			listaProdutos.add(item);
			leitorProduto.atualizarProdutos(listaProdutos);
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public boolean verificarCategoriaFornecedor(Produtos item) {
		boolean existenciaFornecedor, existenciaCategoria = false;
		
		// As sequintes usam um loop para procurar se o ID da categoria
		// do produto bate com um ID de categoria já registrado.
		
		// não consegui fazer sem break, desculpa Jackson.
		for(Categoria categoria : listaCategoria) {
			if(categoria.equals(item.getCategoria())) {
				existenciaCategoria = true;
				break;
			}
		}
		for(Fornecedor fornecedor : listaFornecedor) {
			if(fornecedor.equals(item.getFornecedor())) {
				existenciaFornecedor = true;
				break;
			}
		}

		return existenciaFornecedor && existenciaCategoria;
	}
	
	@Override
	public boolean editarItem(int id) {
		boolean retorno = false;
		// varra a lista de produtos até achar o id certo.
		for(Produtos produto : listaProdutos) {
			if(produto.getId() == id) {
				
				// TODO: logica de edição
				retorno = true;
			}
		}
		return retorno;
	}
	
	@Override
	public List<Produtos> buscarItem(String nome) {
		List<Produtos> itens = new ArrayList<>();
		// varre e procura itens com o mesmo nome.
		for (Produtos produto : listaProdutos) {
			if(produto.getNome().contains(nome)) {
				itens.add(produto);
			}
		}
		
		return itens;
	}
	
	@Override
	public List<Produtos> listarItemDisponivel() {
		List<Produtos> disponiveis = new ArrayList<>();
		// varrer por itens disponiveis
		for(Produtos produto : listaProdutos) {
			if(produto.getDisponibilidade().equalsIgnoreCase("disponivel")) {
				disponiveis.add(produto);
			}
		}
		return disponiveis;
	}
	
	@Override
	public List<Produtos> listarTodosItens() {
		// basta mandar todos
		return this.listaProdutos;
	}
	
	@Override
	public boolean deletarItem(int id) {
		boolean sucesso = false;
		
		if(verificarExclusão(id)) {
			for(Produtos produto : listaProdutos) {
				
			}
		}
	}
	
	
	@Override
	public int gerarId() {
	    int maiorId = 0;
	    for(Produtos produto : listaProdutos) {
	    	if(produto.getId() > maiorId) {
	    		maiorId = produto.getId();
	    	}
	    }
	    return maiorId + 1;
	}

	@Override
	public boolean verificarExclusão(int id) {
		boolean retorno = false;
	    for(Produtos produto : listaProdutos) {
	    	if((produto.getId() == id) && 
	    			produto.getDisponibilidade().equalsIgnoreCase("disponivel")) {
	    		retorno = true;
	    	}
	    }
	    return retorno;
	}
}
