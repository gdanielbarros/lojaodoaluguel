package br.upe.lojao.ui;

import br.upe.lojao.persistencia.entidades.Produtos;


public class MenuAdministrador extends MenuFuncionario{
    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada) {
    	System.out.printf("===========Produtos===========%n 1 - Listar disponiveis%n 2 - Ver todos%n 3 - Buscar pelo nome%n 0 - Voltar%n");
        try {
	    	int opcao = scanner.nextInt();
	        if (entrada == 1) { // Ver itens disponíveis
	            List<Produtos> disponiveis = facade.listarItemDisponivel();
	            if (disponiveis.isEmpty()) {
	                System.out.println("Nenhum item disponível no momento.");
	            } else {
	                System.out.println("===========Itens Disponíveis===========");
	                for (Produtos p : disponiveis) {
	                    System.out.println("ID: " + p.getId());
	                    System.out.println("Categoria: " + p.getIdCategoria());
	                    System.out.println("Fornecedor: " + p.getIdFornecedor());
	                    System.out.println("Taxa Diária: R$ " + p.getTaxaDiaria());
	                    System.out.println("Conservação: " + p.getConservacao());
	                    System.out.println("Valor Reposição: R$ " + p.getValorReposicao());
	                    System.out.println("---------------------------------------");
	                }
	            }
	        } else if (opcao ==2) { // todos os itens
	            List<Produtos> todos = facade.listarTodosItens();
	            if (todos.isEmpty()) {
	                System.out.println("Nenhum produto foi cadastrado.");
	            } else {
	                System.out.println("===========Todos os Produtos===========");
	                for (Produtos p : todos) {
	                    System.out.println("ID: " + p.getId());
	                    System.out.println("Categoria: " + p.getIdCategoria());
	                    System.out.println("Fornecedor: " + p.getIdFornecedor());
	                    System.out.println("Taxa Diária: R$ " + p.getTaxaDiaria());
	                    System.out.println("Disponibilidade: " + p.getDisponibilidade());
	                    System.out.println("Conservação: " + p.getConservacao());
	                    System.out.println("Valor Reposição: R$ " + p.getValorReposicao());
	                    System.out.println("---------------------------------------");
	                }
	            }
	        } else if (opcao ==3) { // buscapor nome
	        	System.out.println("Diga-me o nome do produto desejado.");
	        	String nome = scanner.nextLine();
	            List<Produtos> resultados = facade.listarItemNome(nome);
	            if (resultados.isEmpty()) {
	                System.out.println("Nenhum produto encontrado com o nome: " + nome);
	            } else {
	                System.out.println("===========Produtos Encontrados===========");
	                for (Produtos p : resultados) {
	                    System.out.println("ID: " + p.getId());
	                    System.out.println("Categoria: " + p.getIdCategoria());
	                    System.out.println("Fornecedor: " + p.getIdFornecedor());
	                    System.out.println("Taxa Diária: R$ " + p.getTaxaDiaria());
	                    System.out.println("Disponibilidade: " + p.getDisponibilidade());
	                    System.out.println("Conservação: " + p.getConservacao());
	                    System.out.println("Valor Reposição: R$ " + p.getValorReposicao());
	                    System.out.println("---------------------------------------");
	                }
	            }
	        } else if (opcao ==4) { // cadastrar novo produto
	        	
	        	// TODO: adaptar as chamadas de função para o que for futuramente definido.
	            try {
	                System.out.println("===========Cadastrar Produto===========");
	                
	                System.out.println("Categorias disponíveis:");
	                List<Categoria> categorias = facade.listarCategorias();
	                if (categorias.isEmpty()) {
	                    System.out.println("Nenhuma categoria cadastrada.");
	                }
	                for (Categoria c : categorias) {
	                    System.out.println("ID: " + c.getId() + " - Nome: " + c.getNome());
	                }
	                
	                System.out.print("ID da Categoria: ");
	                int idCategoria = scanner.nextInt();
	                
	                System.out.println("Fornecedores disponíveis:");
	                List<Fornecedor> fornecedores = facade.listarFornecedores();
	                if (fornecedores.isEmpty()) {
	                    System.out.println("Nenhum fornecedor cadastrado.");
	                }
	                for (Fornecedor f : fornecedores) {
	                    System.out.println("ID: " + f.getId() + " - Nome: " + f.getNome());
	                }
	                
	                System.out.print("ID do Fornecedor: ");
	                int idFornecedor = scanner.nextInt();
	                
	                System.out.print("Taxa Diária (R$): ");
	                BigDecimal taxaDiaria = scanner.nextBigDecimal();
	                
	                System.out.print("Valor de Reposição (R$): ");
	                BigDecimal valorReposicao = scanner.nextBigDecimal();
	                
	                limparBuffer();
	                
	                System.out.print("Disponibilidade (\"1\" para disponivel. \"0\"indisponivel): ");
	                int resposta = scanner.nextLine();
	                if (resposta == 0) {
	                	String disponibilidade = "indisponivel";
	                } else {
	                	String disponibilidade = "disponivel";
	                }
	                
	                System.out.print("Estado de Conservação: ");
	                String conservacao = scanner.nextLine();
	                	                
	                boolean resultado = facade.cadastrarProduto(idCategoria, idFornecedor, taxaDiaria, disponibilidade, conservacao, valorReposicao);
	                
	                if (resultado) {
	                    System.out.println("Produto cadastrado com sucesso!");
	                } else {
	                    System.out.println("Erro ao cadastrar produto. Verifique se a categoria e o fornecedor existem.");
	                }
	                
	            } catch (Exception e) {
	                System.out.println("Erro ao cadastrar produto: " + e.getMessage());
	                limparBuffer();
	            }	
	        } else if (opcao == 5) { // editar produto.
	        	
	        	
	            try {
	                System.out.print("ID do produto para editar: ");
	                int id = scanner.nextInt();
	                limparBuffer();
	                
	                // Buscar o produto para mostrar os dados atuais
	                Produtos produto = facade.buscarProdutoPorId(id);
	                if (produto == null) {
	                    System.out.println("Produto não encontrado!");
	                    return;
	                }
	                
	                System.out.println("Dados atuais do produto:");
	                System.out.println("ID: " + produto.getId());
	                System.out.println("Categoria: " + produto.getIdCategoria());
	                System.out.println("Fornecedor: " + produto.getIdFornecedor());
	                System.out.println("Taxa Diária: R$ " + produto.getTaxaDiaria());
	                System.out.println("Disponibilidade: " + produto.getDisponibilidade());
	                System.out.println("Conservação: " + produto.getConservacao());
	                System.out.println("Valor Reposição: R$ " + produto.getValorReposicao());
	                
	                System.out.println("\nDigite os novos dados (aperte enter para manter atual.):");
	                
	                System.out.print("Nova Taxa Diária (R$): ");
	                String taxaStr = scanner.nextLine();
	                BigDecimal novaTaxa = taxaStr.isEmpty() ? produto.getTaxaDiaria() : new BigDecimal(taxaStr);
	                
	                System.out.print("Nova Disponibilidade (\"1\" para disponivel. \"0\"indisponivel) ");
	                int resposta = scanner.nextLine();
	                if (resposta == 0) {
	                	String disponibilidade = "indisponivel";
	                } else if (resposta ==1){
	                	String disponibilidade = "disponivel";
	                } else if (disponibilidade.isEmpty()) {
	                	String disponibilidade = produto.getDisponibilidade();
	                }

	                System.out.print("Novo Estado de Conservação: ");
	                String conservacao = scanner.nextLine();
	                if (conservacao.isEmpty()) {
	                    conservacao = produto.getConservacao();
	                }
	                
	                System.out.print("Novo Valor de Reposição (R$): ");
	                String valorStr = scanner.nextLine();
	                BigDecimal novoValor = valorStr.isEmpty() ? produto.getValorReposicao() : new BigDecimal(valorStr);
	                
	                boolean resultado = facade.editarProduto(id, novaTaxa, disponibilidade, conservacao, novoValor);
	                
	                if (resultado) {
	                    System.out.println("Produto editado com sucesso!");
	                } else {
	                    System.out.println("Erro ao editar produto!");
	                }
	                
	            } catch (Exception e) {
	                System.out.println("Erro ao editar produto: " + e.getMessage());
	                limparBuffer();
	            }
	        } else if (opcao ==6) { // deletar produto.
	        	
	        	
	            try {
	                System.out.print("ID do produto para deletar: ");
	                int id = scanner.nextInt();
	                
	                // Verificar se o produto existe
	                Produtos produto = facade.buscarProdutoPorId(id);
	                if (produto == null) {
	                    System.out.println("Boas noticias! Já não existe!");
	                }
	                
	                System.out.println("Produto: ID " + produto.getId() + " - Categoria: " + produto.getIdCategoria());
	                System.out.println("Disponibilidade: " + produto.getDisponibilidade());
	                
	                System.out.print("Tem certeza que deseja deletar? (1-Sim / 2-Nao): ");
	                int confirmacao = scanner.nextInt();
	                
	                if (confirmacao == 1) {
	                    boolean resultado = facade.deletarProduto(id);
	                    if (resultado) {
	                        System.out.println("Produto deletado com sucesso!");
	                    } else {
	                        System.out.println("Erro ao deletar produto! O produto pode estar alugado.");
	                    }
	                } else {
	                    System.out.println("Operação cancelada!");
	                }
	                
	            } catch (Exception e) {
	                System.out.println("Erro ao deletar produto: " + e.getMessage());
	                limparBuffer();
	            }
	        	
	        	
	        }
	        
	        
	        
        } catch (Exception excecao) {
        	System.out.println("ERRO! Digite uma opção valida!");
            limparBuffer();
        }
    }

    @Override
    protected void imprimirRespostaFacadeListaContrato(int entrada){}

    @Override
    public void receberValidarEntradas(){}

    @Override
    protected void imprimirRespostaFacadeMapLista (int entrada){}

    @Override
    protected void imprimirRespostaFacadeContrato (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaCliente (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaCategoria (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaOcorrencia (int entrada){}

    @Override
    protected void imprimirRespostaFacadeListaFornecedor (int entrada){}
}
