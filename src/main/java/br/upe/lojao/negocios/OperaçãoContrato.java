package br.upe.lojao.negocios;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.LeituraContratos;
import br.upe.lojao.persistencia.PersistenciaProdutos;
import br.upe.lojao.persistencia.LeituraUsuario;
import br.upe.lojao.persistencia.Cliente;
import br.upe.lojao.entidades.Produtos;
import java.util.List;

public class OperaçãoContrato implements Serviços {

	private ArrayList<Produtos> listaProdutos;
	private ArrayList<Cliente> listaCliente;
	private ArrayList<Contrato> listaContrato;
	
	private LeituraContratos leitorContrato = new LeituraContratos();
	private PersistenciaProdutos leitorProduto = new PersistenciaProdutos();
	private LeituraUsuario leitorUsuario = new LeituraUsuario();
	
	public OperaçãoContrato() {
		
		this.listaProdutos = this.leitorProduto.lerProduto();
		this.listaContrato = this.leitorContrato.lerContratos();
		this.listaCliente = this.leitorUsuario.lerCliente();
		verificarMultas();
		
	}
	
	@Override
	public int gerarId() {
		if (verificarLista(1) == false) {
			return 1;
		}
		else {
			int maiorTemp = 0;
			for (int x = 0; x < listaContrato.size(); x++) {
				if (maiorTemp < listaContrato.get(x).id()) {
					maiorTemp = listaContrato.get(x).id();
				}
			}
			int idGerado = maiorTemp + 1;
			return idGerado;
		}
	}
	
	@Override
	public boolean verificarExclusão(int idContrato) {
		
		int indiceContrato = encontrarNaLista(idContrato, 2);
		if (listaContrato.get(indiceContrato).status().equals("CONCLUIDO")) {
			return true;
		}
		else {return false;}
	}
	
	public boolean verificarMultas() {
	    if(verificarLista(1) == false) {return false;}
	    OperaçãoMultas aplicador = new OperaçãoMultas();
	    for (int x = 0; x < this.listaContrato.size(); x++) {
	        if (LocalDateTime.now().isAfter(this.listaContrato.get(x).dataFinal()) && this.listaContrato.get(x).status().equals("CONCLUIDO") == false) {
	            aplicador.aplicarMulta(this.listaContrato.get(x).id());
	        }
	    }
	    this.listaContrato = this.leitorContrato.lerContratos();
	    boolean atualizado = false;
	    for (int x = 0; x < this.listaContrato.size(); x++) {
	        if (LocalDateTime.now().isAfter(this.listaContrato.get(x).dataFinal()) && this.listaContrato.get(x).status().equals("CONCLUIDO") == false) {
	            Contrato contratoAtrasado = new Contrato(this.listaContrato.get(x).id(), this.listaContrato.get(x).idCliente(), this.listaContrato.get(x).dataInicio(), this.listaContrato.get(x).dataFinal(), this.listaContrato.get(x).diasAlugados(), this.listaContrato.get(x).valorTotal(), this.listaContrato.get(x).idItem(), "ATRASADO", this.listaContrato.get(x).idMulta(), this.listaContrato.get(x).valorMulta());
	            this.listaContrato.remove(x);
	            this.listaContrato.add(x, contratoAtrasado);
	            atualizado = true;
	        }
	    }
	    if (atualizado) {
	        this.leitorContrato.atualizarContratos(this.listaContrato);
	        this.listaContrato = this.leitorContrato.lerContratos();
	    }
	    return true;
	}
	
	public boolean verificarLista(int lista) {
		
		if (lista == 1) {
			
			if (this.listaContrato.isEmpty() || this.listaContrato.get(0).id() == -1) {
				return false;
			}
			else {return true;}
		}
		
		else if (lista == 2) {
			if (this.listaProdutos.isEmpty() || this.listaProdutos.get(0).getId() == -1) {
				return false;
			}
			else {return true;}
		}
		
		
		else if (lista == 3) {
			if (this.listaCliente.isEmpty() || this.listaCliente.get(0).id() == -1) {
				return false;
			}
			else {return true;}
		}
		
		else {return false;}
		
	}
	
	public int encontrarNaLista(int id, int opcao) {
		int indice = -1;
		
		if (opcao == 1){
			for (int x = 0; x < listaProdutos.size(); x++) {
				if (id == listaProdutos.get(x).getId()){
					indice = x;
					return indice;
				}
				}
			}
		
		else if (opcao == 2){
			for (int x = 0; x < listaContrato.size(); x++) {
				if (id == listaContrato.get(x).id()){
					indice = x;
					return indice;
				}
			}
		}
		else if (opcao == 3){
			for (int x = 0; x < listaCliente.size(); x++) {
				if (id == listaCliente.get(x).id()){
					indice = x;
					return indice;
				}
			}
		}
		return indice;
	}
	
	public BigDecimal calcularAluguel(long dias, int idProduto) {
		if(verificarLista(2) == false ) {return new BigDecimal("1");}
		int indiceProduto = encontrarNaLista(idProduto, 1);
		if (indiceProduto == -1) {
			return new BigDecimal("1");
		}
		BigDecimal valorTotal = this.listaProdutos.get(indiceProduto).getTaxaDiaria().multiply(new BigDecimal(dias));
		return valorTotal;
	}
	
	public boolean registrar(int idProduto, LocalDateTime dataInicio, LocalDateTime dataFinal, int idCliente) {
		if(verificarLista(2) == false || verificarLista(3) == false) {return false;}
		if(this.listaContrato.isEmpty() == false && this.listaContrato.get(0).id() == -1) {return false;}
		int indiceProduto = encontrarNaLista(idProduto, 1);
		if (indiceProduto == -1) {
			return false;
		}
		int indiceCliente = encontrarNaLista(idCliente, 3);
		if (indiceCliente == -1) {
			return false;
		}
		if (dataInicio.isAfter(dataFinal)) {
			return false;
		}
		if (dataFinal.isBefore(LocalDateTime.now())) {
			return false;
		}
		int id = gerarId();
		long diasAlugados = Duration.between(dataInicio, dataFinal).toDays();
		BigDecimal valorTotal = calcularAluguel(diasAlugados, idProduto);
		String status = "ATIVO";
		int idMulta = 0;
		BigDecimal valorMulta = new BigDecimal("0");
		
		Contrato novoContrato = new Contrato(id, idCliente, dataInicio, dataFinal, diasAlugados, valorTotal, idProduto, status, idMulta, valorMulta);
		this.listaContrato.add(novoContrato);
		this.leitorContrato.atualizarContratos(this.listaContrato);
		return true;
	}
	
	public boolean atualizar(int idContrato, int valor, int opção) {
		if(verificarLista(2) == false || verificarLista(1) == false || verificarLista(3) == false) {return false;}
		int indiceContrato = encontrarNaLista(idContrato, 2);
		if (indiceContrato == -1) {return false;}
	
		if (opção == 1) {
			int indiceCliente = encontrarNaLista(valor, 3);
			if (indiceCliente == -1) {
				return false;
			}
			Contrato contratoAtualizado = new Contrato(this.listaContrato.get(indiceContrato).id(), valor, this.listaContrato.get(indiceContrato).dataInicio(), this.listaContrato.get(indiceContrato).dataFinal(), this.listaContrato.get(indiceContrato).diasAlugados(), this.listaContrato.get(indiceContrato).valorTotal(), this.listaContrato.get(indiceContrato).idItem(), this.listaContrato.get(indiceContrato).status(), this.listaContrato.get(indiceContrato).idMulta(), this.listaContrato.get(indiceContrato).valorMulta());
			this.listaContrato.remove(indiceContrato);
			this.listaContrato.add(indiceContrato, contratoAtualizado);
		}
		else if (opção == 2) {
			int indiceProduto = encontrarNaLista(valor, 1);
			if (indiceProduto == -1) {
				return false;
			}
			BigDecimal valorTotal = calcularAluguel(this.listaContrato.get(indiceContrato).diasAlugados(), valor);
			Contrato contratoAtualizado = new Contrato(this.listaContrato.get(indiceContrato).id(), this.listaContrato.get(indiceContrato).idCliente(), this.listaContrato.get(indiceContrato).dataInicio(), this.listaContrato.get(indiceContrato).dataFinal(), this.listaContrato.get(indiceContrato).diasAlugados(), valorTotal, valor, this.listaContrato.get(indiceContrato).status(), this.listaContrato.get(indiceContrato).idMulta(), this.listaContrato.get(indiceContrato).valorMulta());
			this.listaContrato.remove(indiceContrato);
			this.listaContrato.add(indiceContrato, contratoAtualizado);
		}
		
		else {return false;}
		
		this.leitorContrato.atualizarContratos(this.listaContrato);
		verificarMultas();
		return true;
	}
	
	public boolean atualizar(int idContrato, LocalDateTime valor, int opção) {
	    if(verificarLista(2) == false || verificarLista(1) == false || verificarLista(3) == false) {return false;}
	    int indiceContrato = encontrarNaLista(idContrato, 2);
	    if (indiceContrato == -1) {return false;}

	    if (opção == 1) {
	        if (valor.isAfter(this.listaContrato.get(indiceContrato).dataFinal())) {return false;}
	        long diasAlugados = Duration.between(valor, this.listaContrato.get(indiceContrato).dataFinal()).toDays();
	        BigDecimal valorTotal = calcularAluguel(diasAlugados, this.listaContrato.get(indiceContrato).idItem());
	        Contrato contratoAtualizado = new Contrato(this.listaContrato.get(indiceContrato).id(), this.listaContrato.get(indiceContrato).idCliente(), valor, this.listaContrato.get(indiceContrato).dataFinal(), diasAlugados, valorTotal, this.listaContrato.get(indiceContrato).idItem(), this.listaContrato.get(indiceContrato).status(), this.listaContrato.get(indiceContrato).idMulta(), this.listaContrato.get(indiceContrato).valorMulta());
	        this.listaContrato.remove(indiceContrato);
	        this.listaContrato.add(indiceContrato, contratoAtualizado);
	    }
	    else if (opção == 2) {
	        if (this.listaContrato.get(indiceContrato).dataInicio().isAfter(valor)) {return false;}
	        long diasAlugados = Duration.between(this.listaContrato.get(indiceContrato).dataInicio(), valor).toDays();
	        BigDecimal valorTotal = calcularAluguel(diasAlugados, this.listaContrato.get(indiceContrato).idItem());
	        Contrato contratoAtualizado = new Contrato(this.listaContrato.get(indiceContrato).id(), this.listaContrato.get(indiceContrato).idCliente(), this.listaContrato.get(indiceContrato).dataInicio(), valor, diasAlugados, valorTotal, this.listaContrato.get(indiceContrato).idItem(), this.listaContrato.get(indiceContrato).status(), this.listaContrato.get(indiceContrato).idMulta(), this.listaContrato.get(indiceContrato).valorMulta());
	        this.listaContrato.remove(indiceContrato);
	        this.listaContrato.add(indiceContrato, contratoAtualizado);
	    }
	    else {return false;}

	    this.leitorContrato.atualizarContratos(this.listaContrato);
	    verificarMultas();
	    return true;
	}
	
	public boolean atualizar(int idContrato) {
	    if(verificarLista(1) == false) {return false;}
	    int indiceContrato = encontrarNaLista(idContrato, 2);
	    if (indiceContrato == -1) {return false;}
	    
	    int idMulta = this.listaContrato.get(indiceContrato).idMulta();
	    LocalDateTime dataFinal = this.listaContrato.get(indiceContrato).dataFinal();
	    LocalDateTime dataAtual = LocalDateTime.now();
	    long diasAlugados = this.listaContrato.get(indiceContrato).diasAlugados();
	    BigDecimal valorTotal = this.listaContrato.get(indiceContrato).valorTotal();

	    if (dataAtual.isBefore(dataFinal)) {
	        dataFinal = dataAtual;
	        diasAlugados = Duration.between(this.listaContrato.get(indiceContrato).dataInicio(), dataAtual).toDays();
	        valorTotal = calcularAluguel(diasAlugados, this.listaContrato.get(indiceContrato).idItem());
	    }

	    Contrato contratoAtualizado = new Contrato(this.listaContrato.get(indiceContrato).id(), this.listaContrato.get(indiceContrato).idCliente(), this.listaContrato.get(indiceContrato).dataInicio(), dataFinal, diasAlugados, valorTotal, this.listaContrato.get(indiceContrato).idItem(), "CONCLUIDO", this.listaContrato.get(indiceContrato).idMulta(), this.listaContrato.get(indiceContrato).valorMulta());
	    this.listaContrato.remove(indiceContrato);
	    this.listaContrato.add(indiceContrato, contratoAtualizado);
	    this.leitorContrato.atualizarContratos(this.listaContrato);

	    if (idMulta != 0) {
	        OperaçãoMultas pagador = new OperaçãoMultas();
	        pagador.marcarPago(idMulta);
	    }

	    return true;
	}
	
	public boolean deletarContrato(int idContrato) {
		if(verificarLista(1) == false) {return false;}
		int indiceContrato = encontrarNaLista(idContrato, 2);
		if (indiceContrato == -1) {return false;}
		
		if (verificarExclusão(idContrato)) {
			this.listaContrato.remove(indiceContrato);
			this.leitorContrato.atualizarContratos(listaContrato);
			return true;
		}
		else {
			return false;
		}
		
		
	}
	
	public Contrato buscarContrato(int idContrato) {
		Contrato erro = new Contrato(-1, -1, LocalDateTime.MIN, LocalDateTime.MIN, -1L, BigDecimal.ZERO, -1, "ERRO", -1, BigDecimal.ZERO);
		if(verificarLista(1) == false) {return erro;}
		int indiceContrato = encontrarNaLista(idContrato, 2);
		if (indiceContrato == -1) {
			return erro;
		}
		return this.listaContrato.get(indiceContrato);
	}
	
	public List<Contrato> listarTodos(){
		ArrayList<Contrato> erro = new ArrayList<>();
		Contrato erroo = new Contrato(-1, -1, LocalDateTime.MIN, LocalDateTime.MIN, -1L, BigDecimal.ZERO, -1, "ERRO", -1, BigDecimal.ZERO);
		erro.add(erroo);
		if(verificarLista(1) == false) {return erro;}
		return this.listaContrato;
	}
	
	
}