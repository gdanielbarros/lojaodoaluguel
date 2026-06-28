package br.upe.lojao.negocios;

public interface IOperacaoItem {
	
	boolean cadastrar(Produtos item);
	
	boolean verificarCategoriaFornecedor(Produtos item);
	
	boolean editarItem(int id);
	
	List<Produtos> buscarItem(String nome);
	
	List<Produtos> listarItemDisponivel();
	
	List<Produtos> lisarTodosItens();
	
	boolean deletarItem(int id);
	
	int gerarId();
	
	boolean verificarExclusão(int id);
}
