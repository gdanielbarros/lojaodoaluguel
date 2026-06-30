package br.upe.lojao.persistencia;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;

public class PersistenciaContratos implements IPersistenciaContrato {

    private String caminhoContrato = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "contratos.csv";
    private String caminhoMultas = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "ocorrencias.csv";
    private String caminhoFaturamento = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "Relátorios" + File.separator + "faturamento.csv";
    private String caminhoHistorico = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "Relátorios" + File.separator + "historicoCliente.csv";
    
    private List<Contrato> listaContratos = new ArrayList<>();
    private List<Ocorrencias> listaMultas = new ArrayList<>();

    private ILerCSV leitor = new LerCSV();
    private IEscreverCSV escritor = new EscreverCSV();

    public PersistenciaContratos() {
        carregarDados();
    }

    public void carregarDados() {
        this.listaContratos = leitor.ler(caminhoContrato, linha -> {
            int id = Integer.parseInt(linha[0]);
            int idCliente = Integer.parseInt(linha[1]);
            LocalDateTime dataInicio = LocalDateTime.parse(linha[2]);
            LocalDateTime dataFinal = LocalDateTime.parse(linha[3]);
            long diasAlugados = Long.parseLong(linha[4]);
            BigDecimal valorTotal = new BigDecimal(linha[5]);
            int idItem = Integer.parseInt(linha[6]);
            String status = linha[7];
            int idMulta = Integer.parseInt(linha[8]);
            BigDecimal valorMulta = new BigDecimal(linha[9]);
            return new Contrato(id, idCliente, dataInicio, dataFinal, diasAlugados, valorTotal, idItem, status, idMulta, valorMulta);
        });

        this.listaMultas = leitor.ler(caminhoMultas, linha -> {
            int id = Integer.parseInt(linha[0]);
            int idContrato = Integer.parseInt(linha[1]);
            int idCliente = Integer.parseInt(linha[2]);
            BigDecimal valorBase = new BigDecimal(linha[3]);
            LocalDateTime dataInicio = LocalDateTime.parse(linha[4]);
            LocalDateTime dataFinal = LocalDateTime.parse(linha[5]);
            BigDecimal valorFinal = new BigDecimal(linha[6]);
            BigDecimal valorPorcentagem = new BigDecimal(linha[7]);
            String avarias = linha[8];
            String status = linha[9];
            return new Ocorrencias(id, idContrato, idCliente, valorBase, dataInicio, dataFinal, valorFinal, valorPorcentagem, avarias, status);
        });
    }

    private boolean escreverContratos() {
        String[] cabecalho = {"id", "idCliente", "dataInicio", "dataFinal", "diasAlugados", "valortotal", "idItem", "status", "idMulta", "valorMulta"};
        boolean escrita = escritor.escrever(caminhoContrato, cabecalho, this.listaContratos, (Contrato contrato) -> new String[]{
            String.valueOf(contrato.id()), String.valueOf(contrato.idCliente()),
            contrato.dataInicio().toString(), contrato.dataFinal().toString(),
            String.valueOf(contrato.diasAlugados()), contrato.valorTotal().toString(),
            String.valueOf(contrato.idItem()), contrato.status(),
            String.valueOf(contrato.idMulta()), contrato.valorMulta().toString()
        });
        return escrita;
    }

    private boolean escreverMultas() {
        String[] cabecalho = {"id", "idContrato", "idCliente", "valorBase", "dataInicio", "dataFinal", "valorFinal", "valorPorcentagem", "avarias", "status"};
        boolean escrita = escritor.escrever(caminhoMultas, cabecalho, this.listaMultas, (Ocorrencias multa) -> new String[]{
            String.valueOf(multa.id()), String.valueOf(multa.idContrato()),
            String.valueOf(multa.idCliente()), multa.valorBase().toString(),
            multa.dataInicio().toString(), multa.dataFinal().toString(),
            multa.valorFinal().toString(), multa.valorPorcentagem().toString(),
            multa.avarias(), multa.status()
        });
        return escrita;
    }

    public boolean escreverRelatorios(LocalDateTime dataInicio, LocalDateTime dataFinal, BigDecimal valor) {
        boolean resultado = false;
        String[] cabecalho = {"Data de inicio", "Data final", "Valor"};
        List<String[]> dados = new ArrayList<>();
        
        String[] linha = {dataInicio.toString(), dataFinal.toString(), valor.toString()};
        dados.add(linha);
        resultado = escritor.escrever(this.caminhoFaturamento, cabecalho, dados, registro -> registro);
            
        return resultado;
    }

    public boolean escreverRelatorios(List<Contrato> contratos) {
        boolean resultado = false;
        String[] cabecalho = {"id", "idCliente", "dataInicio", "dataFinal", "diasAlugados", "valortotal", "idItem", "status", "idMulta", "valorMulta"};
        
        resultado = escritor.escrever(this.caminhoHistorico, cabecalho, contratos, contrato -> new String[]{
          String.valueOf(contrato.id()), String.valueOf(contrato.idCliente()),
          contrato.dataInicio().toString(), contrato.dataFinal().toString(),
          String.valueOf(contrato.diasAlugados()), contrato.valorTotal().toString(),
          String.valueOf(contrato.idItem()), contrato.status(),
          String.valueOf(contrato.idMulta()), contrato.valorMulta().toString()
            });
        
        return resultado;
    }

    public ArrayList<Contrato> lerContratos() {
        return new ArrayList<>(listaContratos);
    }

    public ArrayList<Ocorrencias> lerMultas() {
        return new ArrayList<>(listaMultas);
    }

    public int maiorIdContrato() {
        int maior = 0;
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (listaContratos.get(x).id() > maior) {
            	maior = listaContratos.get(x).id();
            }
        }
        return maior;
    }

    public int maiorIdMulta() {
        int maior = 0;
        for (int x = 0; x < this.listaMultas.size(); x++) {
            if (this.listaMultas.get(x).id() > maior) {
            	maior = this.listaMultas.get(x).id();
            }
        }
        return maior;
    }

    public Contrato buscarContrato(int id) {
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (this.listaContratos.get(x).id() == id) {
            	return this.listaContratos.get(x);
            }
        }
        return new Contrato(-1, -1, LocalDateTime.MIN, LocalDateTime.MIN, -1L, BigDecimal.ZERO, -1, "ERRO", -1, BigDecimal.ZERO);
    }

    public boolean adicionarContrato(Contrato contrato) {
        listaContratos.add(contrato);
        boolean escrita = escreverContratos();
        return escrita;
    }

    public boolean atualizarContrato(Contrato contrato) {
    	boolean resultado = false;
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (listaContratos.get(x).id() == contrato.id()) {
                listaContratos.set(x, contrato);
                resultado = escreverContratos();
            }
        }
        return resultado;
    }

    public boolean deletarContrato(int id) {
    	boolean resultado = false;
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (listaContratos.get(x).id() == id) {
                listaContratos.remove(x);
                resultado = escreverContratos();
            }
        }
        return resultado;
    }

    public boolean itemContratoAtivo(int idItem) {
    	boolean resultado = false;
    	
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (this.listaContratos.get(x).idItem() == idItem && (this.listaContratos.get(x).status().equals("ATIVO") || this.listaContratos.get(x).status().equals("ATRASADO"))) {
                resultado = true;
            }
        }
        return resultado;
    }

    public List<Ocorrencias> clienteMultaPendente(int idCliente) {
    	List<Ocorrencias> multas = new ArrayList<>();
        for (int x = 0; x < this.listaMultas.size(); x++) {
            if (this.listaMultas.get(x).idCliente() == idCliente && !this.listaMultas.get(x).status().equals("PAGO")) {
                multas.add(this.listaMultas.get(x));
            }
        }
     
       return multas;
    }

    public List<Contrato> clienteHistorico(int idCliente, int opcao) {
    	List<Contrato> historico = new ArrayList<>();
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (this.listaContratos.get(x).idCliente() == idCliente && this.listaContratos.get(x).status().equals("CONCLUIDO") == true) {
            	historico.add(this.listaContratos.get(x));
            }
        }
        if (opcao == 2){
            escreverRelatorios(historico);
        }
      
       return historico;
    }

    public List<Contrato> contratosAtrasados() {
        List<Contrato> atrasados = new ArrayList<>();
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (LocalDateTime.now().isAfter(this.listaContratos.get(x).dataFinal()) && !this.listaContratos.get(x).status().equals("CONCLUIDO")) {
                atrasados.add(this.listaContratos.get(x));
            }
        }
        return atrasados;
    }
    
    public List<Contrato> contratosClienteAtivos(int idCliente){
    	List<Contrato> ativos = new ArrayList<>();
        for (int x = 0; x < this.listaContratos.size(); x++) {
            if (this.listaContratos.get(x).idCliente() == idCliente && !this.listaContratos.get(x).status().equals("CONCLUIDO")) {
                ativos.add(this.listaContratos.get(x));
            }
        }
        return ativos;
    }

    public Ocorrencias buscarMulta(int id) {
        Ocorrencias resultado = new Ocorrencias(-1, -1, -1, BigDecimal.ZERO, LocalDateTime.MIN, LocalDateTime.MIN, BigDecimal.ZERO, BigDecimal.ZERO, "ERRO", "ERRO");
        for (int x = 0; x < listaMultas.size(); x++) {
            if (listaMultas.get(x).id() == id) {
                resultado = listaMultas.get(x);
            }
        }
        return resultado;
    }

    public boolean adicionarMulta(Ocorrencias multa) {
        listaMultas.add(multa);
        return escreverMultas();
    }

    public boolean atualizarMulta(Ocorrencias multa) {
        boolean resultado = false;
        for (int x = 0; x < listaMultas.size(); x++) {
            if (listaMultas.get(x).id() == multa.id()) {
                listaMultas.set(x, multa);
                resultado = escreverMultas();
            }
        }
        return resultado;
    }

    public boolean deletarMulta(int id) {
        boolean resultado = false;
        for (int x = 0; x < listaMultas.size(); x++) {
            if (listaMultas.get(x).id() == id) {
                listaMultas.remove(x);
                resultado = escreverMultas();
            }
        }
        return resultado;
    }

    public List<BigDecimal> valorContratosPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim, int opcao){
        List<BigDecimal> valorContratos = new ArrayList<>();
        
        for (int x = 0; x < this.listaContratos.size(); x++){
            if (!this.listaContratos.get(x).dataInicio().isBefore(dataInicio) && !this.listaContratos.get(x).dataFinal().isAfter(dataFim) && this.listaContratos.get(x).status().equals("CONCLUIDO")){
            	valorContratos.add(this.listaContratos.get(x).valorTotal());
            	valorContratos.add(this.listaContratos.get(x).valorMulta());
            }
        }
        return valorContratos;
    }
    
    public List<Contrato> listarContratosAtivos() {
        List<Contrato> ativos = new ArrayList<>();
        for (int i = 0; i < listaContratos.size(); i++) {
            Contrato c = listaContratos.get(i);
            if (!c.status().equals("CONCLUIDO")) {
                ativos.add(c);
            }
        }
        return ativos;
    }

}