package br.upe.lojao.camada2_negocios;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.upe.lojao.models.Contrato;
import br.upe.lojao.models.Cliente;
import br.upe.lojao.models.Produtos;
import java.util.List;

public class OperaçãoContrato implements Serviços {

	private ArrayList<Produtos> listaProdutos;
	private ArrayList<Cliente> listaCliente;
	private ArrayList<Contrato> listaContrato;
	
	public OperaçãoContrato() {}
	
	@Override
	public int gerarId() {}
	
	@Override
	public boolean verificarExclusão(int idContrato) {}
	
	public boolean verificarItem(int idProduto) {}
	
	public boolean verificarCliente(int idCliente) {}
	
	public BigDecimal calcularAluguel(int dias, Produtos item) {}
	
	public boolean registrar(Contrato aluguel) {}
	
	public boolean atualizar(int idContrato, int opção) {}
	
	public Contrato buscarContrato(int idContrato) {}
	
	public List<Contrato> listarTodos(){}
	
	
}
