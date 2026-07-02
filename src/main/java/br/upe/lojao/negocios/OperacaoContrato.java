package br.upe.lojao.negocios;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import br.upe.lojao.persistencia.entidades.Produtos;
import br.upe.lojao.persistencia.IPersistenciaContrato;
import br.upe.lojao.persistencia.IPersistenciaUsuario;
import br.upe.lojao.persistencia.PersistenciaContratos;
import br.upe.lojao.persistencia.PersistenciaProdutos;
import br.upe.lojao.persistencia.IPersistenciaProduto;
import br.upe.lojao.persistencia.PersistenciaUsuario;

public class OperacaoContrato implements IOperacaoContrato {

    private IPersistenciaContrato persistencia = new PersistenciaContratos();
    private IPersistenciaUsuario persistenciaUsuario = new PersistenciaUsuario();
    private IPersistenciaProduto persistenciaProduto = new PersistenciaProdutos();
    
    public int gerarId() {
        return persistencia.maiorIdContrato() + 1;
    }

    public boolean verificarExclusao(int idContrato) {
        boolean permissao;
    	Contrato excluido = persistencia.buscarContrato(idContrato);
        if (excluido.id() == -1) {
        	permissao = false;
        }
        else{
        	permissao = excluido.status().equals("CONCLUIDO");
        	}
        return permissao;
    }

    public BigDecimal calcularAluguel(long dias, int idProduto) {
        IPersistenciaProduto persistenciaProduto = new PersistenciaProdutos();
        BigDecimal taxaDiaria = persistenciaProduto.getTaxaDiaria(idProduto);
        return taxaDiaria.multiply(new BigDecimal(dias));
    }

    public void verificarMultas() {
        List<Contrato> atrasados = persistencia.contratosAtrasados();
        if (atrasados.isEmpty()) {
            return;
        }

        OperacaoMultas operacaoMultas = new OperacaoMultas();
        for (int x = 0; x < atrasados.size(); x++) {
            operacaoMultas.aplicarMulta(atrasados.get(x).id());
        }

       
        persistencia.carregarDados();

        for (int x = 0; x < atrasados.size(); x++) {
            Contrato atual = persistencia.buscarContrato(atrasados.get(x).id());
            if (atual.id() != -1 && !atual.status().equals("CONCLUIDO")) {
                Contrato atualizado = new Contrato(
                    atual.id(), atual.idCliente(), atual.dataInicio(), atual.dataFinal(),
                    atual.diasAlugados(), atual.valorTotal(), atual.idItem(),
                    "ATRASADO", atual.idMulta(), atual.valorMulta());
                persistencia.atualizarContrato(atualizado);
            }
        }
    }

    public boolean registrar(int idProduto, LocalDateTime dataInicio, LocalDateTime dataFinal, int idCliente) {
        
        if (persistencia.itemContratoAtivo(idProduto)) {
            return false;
        }
        
        if (!persistencia.clienteMultaPendente(idCliente).isEmpty()) {
            return false;
        }
        if (dataInicio.isAfter(dataFinal)) {
            return false;
        }
        if (dataFinal.isBefore(LocalDateTime.now())) {
            return false;
        }
        if (!persistenciaUsuario.clienteExiste(idCliente)) {
            return false;
        }
        IPersistenciaProduto persistenciaProduto = new PersistenciaProdutos();
        if (!persistenciaProduto.produtoExiste(idProduto)) {
            return false;
        }

        int id = gerarId();
        long diasAlugados = Duration.between(dataInicio, dataFinal).toDays();
        if (diasAlugados <= 0) {
            diasAlugados = 1; 
        }
        BigDecimal valorTotal = calcularAluguel(diasAlugados, idProduto);
        Contrato novoContrato = new Contrato(id, idCliente, dataInicio, dataFinal, diasAlugados, valorTotal, idProduto, "ATIVO", 0, new BigDecimal("0"));
        boolean contratoSalvo = persistencia.adicionarContrato(novoContrato);
        if (contratoSalvo) {
            Produtos produto = persistenciaProduto.buscarPorId(idProduto);
            if (produto != null) {
                produto.setDisponibilidade("INDISPONIVEL");
                List<Produtos> todos = persistenciaProduto.lerProdutos();
                for (int i = 0; i < todos.size(); i++) {
                    if (todos.get(i).getId() == idProduto) {
                        todos.set(i, produto);
                        break;
                    }
                }
                persistenciaProduto.escreverProdutos(todos);
            }
        }
        return contratoSalvo;
    }

    public boolean atualizar(int idContrato, int valor, int opcao) {
        Contrato atualizar = persistencia.buscarContrato(idContrato);
        boolean permissao = true;
        
        if (atualizar.id() == -1) {
        	permissao = false;
        }

        Contrato atualizado = new Contrato(0, 1, LocalDateTime.MIN, LocalDateTime.MIN, 0L, BigDecimal.ZERO, 0, "ATIVO", 0, BigDecimal.ZERO);
        if (opcao == 1) {
            
            if (persistenciaUsuario.clienteExiste(valor) == false) {
            	permissao = false;
            }
            atualizado = new Contrato(atualizar.id(), valor, atualizar.dataInicio(), atualizar.dataFinal(), atualizar.diasAlugados(), atualizar.valorTotal(), atualizar.idItem(), atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else if (opcao == 2) { 
            if (persistencia.itemContratoAtivo(valor)) {
            	permissao = false;
            }
            IPersistenciaProduto persistenciaProduto = new PersistenciaProdutos();
            if (persistenciaProduto.produtoExiste(valor) == false) {
            	permissao = false;
            }
            BigDecimal novoValor = calcularAluguel(atualizar.diasAlugados(), valor);
            atualizado = new Contrato(atualizar.id(), atualizar.idCliente(), atualizar.dataInicio(), atualizar.dataFinal(), atualizar.diasAlugados(), novoValor, valor, atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else {
            permissao = false;
        }
        
        if (permissao != false) {
        permissao = persistencia.atualizarContrato(atualizado);
        }
        return permissao;
    }

    public boolean atualizar(int idContrato, LocalDateTime valor, int opcao) {
        Contrato atualizar = persistencia.buscarContrato(idContrato);
        boolean permissao = true;
        
        if (atualizar.id() == -1) {
        	permissao = false;
        }

        Contrato atualizado = null;
        if (opcao == 1) {
            if (valor.isAfter(atualizar.dataFinal())) {
            	permissao = false;
            }
            long diasAlugados = Duration.between(valor, atualizar.dataFinal()).toDays();
            BigDecimal novoValor = calcularAluguel(diasAlugados, atualizar.idItem());
            atualizado = new Contrato(atualizar.id(), atualizar.idCliente(), valor, atualizar.dataFinal(), diasAlugados, novoValor, atualizar.idItem(), atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else if (opcao == 2) { 
            if (atualizar.dataInicio().isAfter(valor)) return false;
            long diasAlugados = Duration.between(atualizar.dataInicio(), valor).toDays();
            BigDecimal novoValor = calcularAluguel(diasAlugados, atualizar.idItem());
            atualizado = new Contrato(atualizar.id(), atualizar.idCliente(), atualizar.dataInicio(), valor, diasAlugados, novoValor, atualizar.idItem(), atualizar.status(), atualizar.idMulta(), atualizar.valorMulta());
        } else {
            permissao = false;
        }

        if (permissao != false) {
            permissao = persistencia.atualizarContrato(atualizado);
            }
            return permissao;
    }

    public boolean concluirContrato(int idContrato) {
        Contrato concluir = persistencia.buscarContrato(idContrato);
        if (concluir.id() == -1) return false;

        LocalDateTime dataFinal = concluir.dataFinal();
        long diasAlugados = concluir.diasAlugados();
        BigDecimal valorTotal = concluir.valorTotal();
        if (LocalDateTime.now().isBefore(dataFinal)) {
            dataFinal = LocalDateTime.now();
            diasAlugados = Duration.between(concluir.dataInicio(), dataFinal).toDays();
            if (diasAlugados <= 0) {
                diasAlugados = 1;
            }
            valorTotal = calcularAluguel(diasAlugados, concluir.idItem());
        }

        boolean atrasado = LocalDateTime.now().isAfter(concluir.dataFinal());  // ✅ Agora calculado corretamente

        int idMulta = concluir.idMulta();
        BigDecimal valorMulta = concluir.valorMulta();

        if (atrasado) {
            
            OperacaoMultas operacaoMultas = new OperacaoMultas();
            operacaoMultas.aplicarMulta(idContrato);
            Contrato comMulta = persistencia.buscarContrato(idContrato);
            idMulta = comMulta.idMulta();
            valorMulta = comMulta.valorMulta();
        }

        Contrato concluido = new Contrato(concluir.id(), concluir.idCliente(), concluir.dataInicio(), dataFinal, diasAlugados, valorTotal, concluir.idItem(), "CONCLUIDO", idMulta, valorMulta);

        
        return persistencia.atualizarContrato(concluido);
    }

    public boolean deletarContrato(int idContrato) {
        if (!verificarExclusao(idContrato)) {
            return false;
        }
        return persistencia.deletarContrato(idContrato);
    }

    public Contrato buscarContrato(int idContrato) {
        return persistencia.buscarContrato(idContrato);
    }

    public List<Contrato> listarTodos() {
        return persistencia.lerContratos();
    }
    
    public List<Contrato> listarAtivos(int idCliente){
    	List<Contrato> contratosCliente = new ArrayList<>();
    	
        if (persistenciaUsuario.clienteExiste(idCliente) == true) {
        	contratosCliente = persistencia.contratosClienteAtivos(idCliente);
        }
    	return contratosCliente;
    }
    
    public List<Ocorrencias> multasPendentes(int idCliente){
    	List<Ocorrencias> multasCliente = new ArrayList<>();
    	
        if (persistenciaUsuario.clienteExiste(idCliente) == true) {
        	multasCliente = persistencia.clienteMultaPendente(idCliente);
        }
    	return multasCliente;
    }
    
    public List<Contrato> historicoCliente(int idCliente, int opcao){
    	List<Contrato> historico = new ArrayList<>();
    	
        if (persistenciaUsuario.clienteExiste(idCliente) == true) {
        	historico = persistencia.clienteHistorico(idCliente, opcao);
        }
        if (opcao == 2 && !historico.isEmpty()) {
        	historico.clear();
        	historico.add(new Contrato(-2, 1, LocalDateTime.MIN, LocalDateTime.MIN, 0L, BigDecimal.ZERO, 0, "ATIVO", 0, BigDecimal.ZERO));
        }
    	return historico;
    }
    
    public List<Contrato> listarContratosCliente(int idCliente) {
        List<Contrato> todosContratos = new ArrayList<>();
        todosContratos.addAll(persistencia.contratosClienteAtivos(idCliente));
        todosContratos.addAll(persistencia.clienteHistorico(idCliente, 1));
        return todosContratos;
    }

    public BigDecimal faturamento(LocalDateTime dataInicio, LocalDateTime dataFim, int opcao){
        BigDecimal faturamentoPeriodo = new BigDecimal("0");
        List<BigDecimal> valorContratosPeriodo = new ArrayList<>();

        if (dataInicio.isAfter(LocalDateTime.now()) || dataFim.isAfter(LocalDateTime.now())){
            faturamentoPeriodo = new BigDecimal("-1");
        }
        else if (dataInicio.isEqual(dataFim)){
            faturamentoPeriodo = new BigDecimal("-2");
        }
        else if (dataInicio.isAfter(dataFim)){
            faturamentoPeriodo = new BigDecimal("-3");
        }
        else{
            valorContratosPeriodo = persistencia.valorContratosPeriodo(dataInicio, dataFim, opcao);
            if (valorContratosPeriodo.isEmpty() == false){
            for (int x = 0; x < valorContratosPeriodo.size(); x++){
                faturamentoPeriodo = faturamentoPeriodo.add(valorContratosPeriodo.get(x));
            }}
            else {
                faturamentoPeriodo = new BigDecimal("-5");
            }
        }
        if (opcao == 2){
        	persistencia.escreverRelatorios(dataInicio, dataFim, faturamentoPeriodo);
            faturamentoPeriodo = new BigDecimal("-4");
        }
        return faturamentoPeriodo;
    }
}