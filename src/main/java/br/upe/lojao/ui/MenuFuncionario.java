package br.upe.lojao.ui;

import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import br.upe.lojao.persistencia.entidades.Produtos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MenuFuncionario extends Menu {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void receberValidarEntradas() {
        System.out.printf("===================Funcionario===================%n 1 - Registrar novo aluguel%n 2 - Processar devolucao%n 3 - Clientes%n 4 - Emitir relatorio operacional%n 5 - Contratos%n 6 - Multas%n 7 - Produtos%n 0 - Sair%n");
        try {
            int opcao = scanner.nextInt();
            if (opcao == 1) {
                System.out.printf("ID do cliente: ");
                int idCliente = scanner.nextInt();
                System.out.printf("ID do produto: ");
                int idProduto = scanner.nextInt();
                System.out.printf("Data de inicio (DD/MM/AAAA): ");
                String dataInicioStr = scanner.next();
                System.out.printf("Data de fim (DD/MM/AAAA): ");
                String dataFimStr = scanner.next();
                try {
                    LocalDateTime dataInicio = LocalDate.parse(dataInicioStr, formatter).atStartOfDay();
                    LocalDateTime dataFim = LocalDate.parse(dataFimStr, formatter).atStartOfDay();
                    if (facade.registrarAluguel(idProduto, dataInicio, dataFim, idCliente)) {
                        System.out.println("Aluguel registrado com sucesso.");
                    } else {
                        System.out.println("Erro ao registrar aluguel. Verifique disponibilidade do item e multas do cliente.");
                    }
                } catch (DateTimeParseException excecaoData) {
                    System.out.println("ERRO! Data invalida. Use o formato DD/MM/AAAA.");
                }
            } else if (opcao == 2) {
                System.out.printf("ID do contrato (0 para voltar): ");
                int idContrato = scanner.nextInt();
                if (idContrato != 0) {
                    if (facade.processarDevolucao(idContrato)) {
                        System.out.println("Devolucao processada com sucesso.");
                    } else {
                        System.out.println("ID Inválido.");
                    }
                }
            } else if (opcao == 3) {
            } else if (opcao == 4) {
            } else if (opcao == 5) {
                imprimirRespostaFacadeListaContrato(opcao);
            } else if (opcao == 6) {
                imprimirRespostaFacadeListaOcorrencia(opcao);
            } else if (opcao == 0) {
                return;
            } else {
                System.out.println("Opcao invalida.");
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Opcao invalida, digite um numero.");
            limparBuffer();
        } finally {
            receberValidarEntradas();
        }
    }
    
    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada) {}

    // TODO: funcionar e verificar
    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada) {
        
        if (entrada == 1) {
        	System.out.printf("===========Produtos===========%n 1 - Listar disponiveis%n 2 - Ver todos%n 3 - Buscar pelo nome%n 0 - Voltar%n");
            try {
	        	int opcao = scanner.nextInt();
	        	if (opcao == 1) { // itens disponiveis
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
		        }
            } catch (Exception excecao) {
                System.out.println("ERRO!Digite um numero valido!");
                limparBuffer();
              }
        }
    }

    @Override
    protected void imprimirRespostaFacadeString(int entrada, int escolha, String dado) {}

    @Override
    protected void imprimirRespostaFacadeListaContrato(int entrada) {
        if (entrada == 5) {
            System.out.printf("===========Contratos===========%n 1 - Listar todos%n 2 - Contratos de cliente%n 3 - Historico de cliente%n 0 - Voltar%n");
            try {
                int opcao = scanner.nextInt();
                if (opcao == 1) {
                    List<Contrato> contratos = facade.listarTodosContratos();
                    if (contratos.isEmpty()) {
                        System.out.println("Nenhum contrato encontrado.");
                    } else {
                        for (int x = 0; x < contratos.size(); x++) {
                            System.out.println("ID: " + contratos.get(x).id());
                            System.out.println("Cliente: " + contratos.get(x).idCliente());
                            System.out.println("Item: " + contratos.get(x).idItem());
                            System.out.println("Inicio: " + contratos.get(x).dataInicio());
                            System.out.println("Previsao devolucao: " + contratos.get(x).dataFinal());
                            System.out.println("Valor total: " + contratos.get(x).valorTotal());
                            System.out.println("Status: " + contratos.get(x).status());
                            System.out.println("---------------------------------------");
                        }
                    }
                } else if (opcao == 2) {
                    System.out.printf("ID do cliente (0 para voltar): ");
                    int idCliente = scanner.nextInt();
                    if (idCliente != 0) {
                        List<Contrato> contratos = facade.contratosClienteEspecifico(idCliente);
                        if (contratos.isEmpty()) {
                            System.out.println("Nenhum contrato encontrado.");
                        } else {
                            for (int x = 0; x < contratos.size(); x++) {
                                System.out.println("ID: " + contratos.get(x).id());
                                System.out.println("Cliente: " + contratos.get(x).idCliente());
                                System.out.println("Item: " + contratos.get(x).idItem());
                                System.out.println("Inicio: " + contratos.get(x).dataInicio());
                                System.out.println("Previsao devolucao: " + contratos.get(x).dataFinal());
                                System.out.println("Valor total: " + contratos.get(x).valorTotal());
                                System.out.println("Status: " + contratos.get(x).status());
                                System.out.println("---------------------------------------");
                            }
                        }
                    }
                } else if (opcao == 3) {
                    System.out.printf("ID do cliente (0 para voltar): ");
                    int idCliente = scanner.nextInt();
                    if (idCliente != 0) {
                        List<Contrato> historico = facade.historicoCliente(idCliente);
                        if (historico.isEmpty()) {
                            System.out.println("Nenhum historico encontrado.");
                        } else {
                            for (int x = 0; x < historico.size(); x++) {
                                System.out.println("ID: " + historico.get(x).id());
                                System.out.println("Item: " + historico.get(x).idItem());
                                System.out.println("Inicio: " + historico.get(x).dataInicio());
                                System.out.println("Encerramento: " + historico.get(x).dataFinal());
                                System.out.println("Valor total: " + historico.get(x).valorTotal());
                                System.out.println("---------------------------------------");
                            }
                        }
                    }
                }
            } catch (Exception excecao) {
                System.out.println("ERRO!Digite um numero!");
                limparBuffer();
            }
        }
    }

    @Override
    protected void imprimirRespostaFacadeListaOcorrencia(int entrada) {
        if (entrada == 6) {
            System.out.printf("===========Multas===========%n 1 - Multas pendentes%n 2 - Multas de cliente especifico%n 3 - Registrar avaria%n 0 - Voltar%n");
            try {
                int opcao = scanner.nextInt();
                if (opcao == 1) {
                    List<Ocorrencias> multas = facade.multasPendentesGeral();
                    if (multas.isEmpty() || multas.get(0).id() == -1) {
                        System.out.println("Nenhuma multa pendente encontrada.");
                    } else {
                        for (int x = 0; x < multas.size(); x++) {
                            System.out.println("ID: " + multas.get(x).id());
                            System.out.println("Contrato: " + multas.get(x).idContrato());
                            System.out.println("Cliente: " + multas.get(x).idCliente());
                            System.out.println("Valor: " + multas.get(x).valorFinal());
                            System.out.println("Avarias: " + multas.get(x).avarias());
                            System.out.println("Status: " + multas.get(x).status());
                            System.out.println("---------------------------------------");
                        }
                    }
                } else if (opcao == 2) {
                    System.out.printf("ID do cliente (0 para voltar): ");
                    int idCliente = scanner.nextInt();
                    if (idCliente != 0) {
                        List<Ocorrencias> multas = facade.buscarMultaCliente(idCliente);
                        if (multas.isEmpty() || multas.get(0).id() == -1) {
                            System.out.println("Nenhuma multa encontrada.");
                        } else {
                            for (int x = 0; x < multas.size(); x++) {
                                System.out.println("ID: " + multas.get(x).id());
                                System.out.println("Contrato: " + multas.get(x).idContrato());
                                System.out.println("Cliente: " + multas.get(x).idCliente());
                                System.out.println("Valor: " + multas.get(x).valorFinal());
                                System.out.println("Avarias: " + multas.get(x).avarias());
                                System.out.println("Status: " + multas.get(x).status());
                                System.out.println("---------------------------------------");
                            }
                        }
                    }
                } else if (opcao == 3) {
                    System.out.printf("ID da multa (0 para voltar): ");
                    int idMulta = scanner.nextInt();
                    if (idMulta != 0) {
                        limparBuffer();
                        System.out.printf("Descricao da avaria: ");
                        String avaria = scanner.nextLine();
                        if (facade.registrarAvaria(idMulta, avaria)) {
                            System.out.println("Avaria registrada com sucesso.");
                        } else {
                            System.out.println("Erro ao registrar avaria.");
                        }
                    }
                } 
            } catch (Exception excecao) {
                System.out.println("ERRO! Opcao invalida, digite um numero.");
                limparBuffer();
            }
        }
    }

    @Override
    protected void imprimirRespostaFacadeMapLista(int entrada) {}

    @Override
    protected void imprimirRespostaFacadeContrato(int entrada) {}

    @Override
    protected void imprimirRespostaFacadeListaCliente(int entrada) {}

    @Override
    protected void imprimirRespostaFacadeListaCategoria(int entrada) {}

    @Override
    protected void imprimirRespostaFacadeListaFornecedor(int entrada) {}
}