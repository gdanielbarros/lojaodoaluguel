package br.upe.lojao.persistencia;

import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Produtos;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProdutos implements IPersistenciaProduto {
    private String caminhoProduto = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "produtos.csv";
    private ILerCSV leitor = new LerCSV();
    private IEscreverCSV escritor = new EscreverCSV();

    public List<Produtos> lerProdutos() {
        List<Produtos> todos = leitor.ler(caminhoProduto, linha -> {
            int id = Integer.parseInt(linha[0]);
            String nome = linha[1];
            BigDecimal taxaDiaria = new BigDecimal(linha[2]);
            int idCategoria = Integer.parseInt(linha[3]);
            int idFornecedor = Integer.parseInt(linha[4]);
            String disponibilidade = linha[5];
            String conservacao = linha[6];
            BigDecimal valorRepo = new BigDecimal(linha[7]);
            return new Produtos(id, nome, taxaDiaria, idCategoria, idFornecedor, disponibilidade, conservacao, valorRepo);
        });
        
        todos.removeIf(p -> p.getId() == 0 && "id".equalsIgnoreCase(p.getNome()));
        return todos;
    }

    public boolean escreverProdutos(List<Produtos> produtos) {
        String[] cabecalho = {"id", "nome", "taxaDiaria", "idCategoria", "idFornecedor", "disponibilidade", "conservacao", "valorReposicao"};
        return escritor.escrever(caminhoProduto, cabecalho, produtos, produto -> new String[]{
            String.valueOf(produto.getId()),
            produto.getNome(),
            produto.getTaxaDiaria().toString(),
            String.valueOf(produto.getIdCategoria()),
            String.valueOf(produto.getIdFornecedor()),
            produto.getDisponibilidade(),
            produto.getConservacao(),
            produto.getValorRepo().toString()
        });
    }

    public Produtos buscarPorId(int id) {
        List<Produtos> todos = lerProdutos();
        Produtos encontrado = null;
        for (int indice = 0; indice < todos.size(); indice++) {
            Produtos produtoAtual = todos.get(indice);
            if (produtoAtual.getId() == id) {
                encontrado = produtoAtual;
                break;
            }
        }
        return encontrado;
    }

    public boolean produtoExiste(int id) {
        boolean existe = false;
        Produtos produto = buscarPorId(id);
        if (produto != null) {
            existe = true;
        }
        return existe;
    }

    public BigDecimal getTaxaDiaria(int id) {
        BigDecimal taxa = BigDecimal.ZERO;
        Produtos produto = buscarPorId(id);
        if (produto != null) {
            taxa = produto.getTaxaDiaria();
        }
        return taxa;
    }

    public int maiorIdProduto() {
        List<Produtos> produtos = lerProdutos();
        int maior = 0;
        for (int i = 0; i < produtos.size(); i++) {
            Produtos produtoAtual = produtos.get(i);
            if (produtoAtual.getId() > maior) {
                maior = produtoAtual.getId();
            }
        }
        return maior;
    }
    
    public boolean escreverRelatorioItensPorCategoria(List<String[]> dados) {
        String caminho = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "Relatorios" + File.separator + "itensDisponivelCategoria.csv";
        String[] cabecalho = {"Categoria", "ID_Produto", "Nome", "TaxaDiaria", "Conservacao"};
        IEscreverCSV escritor = new EscreverCSV();
        return escritor.escrever(caminho, cabecalho, dados, linha -> linha);
    }
    
    public List<String[]> listarDadosProdutosAlugados(List<Contrato> contratos) {
        List<Produtos> todos = lerProdutos();
        List<String[]> dados = new ArrayList<>();
        for (int i = 0; i < contratos.size(); i++) {
            Contrato c = contratos.get(i);
            String nome = "";
            String taxa = "";
            for (int j = 0; j < todos.size(); j++) {
                Produtos p = todos.get(j);
                if (p.getId() == c.idItem()) {
                    nome = p.getNome();
                    taxa = p.getTaxaDiaria().toString();
                    break;
                }
            }
            String atrasado = "Nao";
            if (LocalDateTime.now().isAfter(c.dataFinal())) {
                atrasado = "Sim";
            }
            String[] linha = {
                String.valueOf(c.idItem()),
                nome,
                taxa,
                String.valueOf(c.idCliente()),
                c.dataFinal().toString(),
                atrasado
            };
            dados.add(linha);
        }
        return dados;
    }

    public boolean escreverProdutosAlugados(List<String[]> dados) {
        String caminho = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "java" + File.separator + "br" + File.separator + "upe" + File.separator + "lojao" + File.separator + "Relatorios" + File.separator + "itensAlugados.csv";
        String[] cabecalho = {"ID_Produto", "Nome", "Taxa_Diaria", "ID_Cliente", "Data_Devolucao", "Atrasado"};
        IEscreverCSV escritor = new EscreverCSV();
        return escritor.escrever(caminho, cabecalho, dados, linha -> linha);
    }
    
}