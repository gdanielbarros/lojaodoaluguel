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
        int opcao = -1;
        while (opcao != 0) {
            System.out.printf("===================Funcionario===================%n 1 - Registrar novo aluguel%n 2 - Processar devolucao%n 3 - Clientes%n 4 - Relatorios%n 5 - Contratos%n 6 - Multas%n 7 - Produtos%n 8 - Categorias%n 9 - Fornecedores%n 0 - Sair%n");
            try {
                opcao = scanner.nextInt();
                if (opcao == 1) {
                    registrarAluguel();
                } else if (opcao == 2) {
                    processarDevolucao();
                } else if (opcao == 3) {
                    menuClientes();
                } else if (opcao == 4) {
                    menuRelatorios();
                } else if (opcao == 5) {
                    imprimirRespostaFacadeListaContrato(5);
                } else if (opcao == 6) {
                    imprimirRespostaFacadeListaOcorrencia(6);
                } else if (opcao == 7) {
                    imprimirRespostaFacadeListaProdutos(1);
                } else if (opcao == 8) {
                    menuCategorias();
                } else if (opcao == 9) {
                    menuFornecedores();
                } else if (opcao == 0) {
                    return;
                } else {
                    System.out.println("Opcao invalida.");
                }
            } catch (Exception excecao) {
                System.out.println("ERRO! Opcao invalida, digite um numero.");
                limparBuffer();
            }
        }
    }

    private void registrarAluguel() {
        try {
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
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite um numero valido.");
            limparBuffer();
        }
    }

    private void processarDevolucao() {
        try {
            System.out.printf("ID do contrato (0 para voltar): ");
            int idContrato = scanner.nextInt();
            if (idContrato != 0) {
                if (facade.processarDevolucao(idContrato)) {
                    System.out.println("Devolucao processada com sucesso.");
                } else {
                    System.out.println("ID Invalido.");
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite um numero valido.");
            limparBuffer();
        }
    }

    private void menuClientes() {
        System.out.printf("===========Clientes===========%n 1 - Cadastrar%n 2 - Deletar%n 3 - Listar%n 4 - Buscar por nome%n 0 - Voltar%n");
        try {
            int opcao = scanner.nextInt();
            limparBuffer();
            if (opcao == 1) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Login: ");
                String login = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Telefone: ");
                String telefone = scanner.nextLine();
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                br.upe.lojao.persistencia.entidades.Cliente cliente =
                        new br.upe.lojao.persistencia.entidades.Cliente(0, nome, login, senha, "Cliente", email, telefone, cpf, false, false);
                System.out.println(facade.cadastrarCliente(cliente));
            } else if (opcao == 2) {
                System.out.print("ID do cliente: ");
                int id = scanner.nextInt();
                System.out.println(facade.deletarCliente(id));
            } else if (opcao == 3) {
                List<br.upe.lojao.persistencia.entidades.Cliente> clientes = facade.lerCliente();
                if (clientes.isEmpty()) {
                    System.out.println("Nenhum cliente cadastrado.");
                } else {
                    for (br.upe.lojao.persistencia.entidades.Cliente c : clientes) {
                        System.out.println("ID: " + c.id() + " | Nome: " + c.nome() + " | Login: " + c.login());
                    }
                }
            } else if (opcao == 4) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                List<br.upe.lojao.persistencia.entidades.Cliente> encontrados = facade.buscarCliente(nome);
                if (encontrados.isEmpty()) {
                    System.out.println("Nenhum cliente encontrado.");
                } else {
                    for (br.upe.lojao.persistencia.entidades.Cliente c : encontrados) {
                        System.out.println("ID: " + c.id() + " | Nome: " + c.nome());
                    }
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite uma opcao valida.");
            limparBuffer();
        }
    }

    private void menuRelatorios() {
        System.out.printf("===========Relatorios===========%n 1 - Itens disponiveis por categoria%n 2 - Itens alugados com previsao de devolucao e clientes em atraso%n 3 - Historico de cliente especifico%n 0 - Voltar%n");
        try {
            int opcaoRelatorio = scanner.nextInt();
            if (opcaoRelatorio == 1) {
                System.out.printf("1 - Visualizar%n 2 - Salvar em arquivo%n 0 - Voltar%n");
                int opcaoAcao = scanner.nextInt();
                if (opcaoAcao == 1) {
                    List<String[]> dados = facade.listarItensDisponiveisPorCategoria();
                    if (dados.isEmpty()) {
                        System.out.println("Nenhum item disponivel no momento.");
                    } else {
                        System.out.println("===========Itens Disponiveis por Categoria===========");
                        String categoriaAtual = "";
                        for (int i = 0; i < dados.size(); i++) {
                            String[] linha = dados.get(i);
                            if (!linha[0].equals(categoriaAtual)) {
                                categoriaAtual = linha[0];
                                System.out.println("Categoria: " + categoriaAtual);
                            }
                            System.out.println("  ID: " + linha[1] + " | Nome: " + linha[2] +
                                               " | Taxa: R$ " + linha[3] + " | Conservacao: " + linha[4]);
                        }
                        System.out.println("---------------------------------------");
                    }
                } else if (opcaoAcao == 2) {
                    List<String[]> dados = facade.listarItensDisponiveisPorCategoria();
                    if (dados.isEmpty()) {
                        System.out.println("Nenhum dado para salvar.");
                    } else {
                        if (facade.salvarItensDisponiveisPorCategoria(dados)) {
                            System.out.println("Relatorio salvo com sucesso.");
                        } else {
                            System.out.println("Erro ao salvar relatorio.");
                        }
                    }
                }
            } else if (opcaoRelatorio == 2) {
                System.out.printf("1 - Visualizar%n 2 - Salvar em arquivo%n 0 - Voltar%n");
                int opcaoAcao = scanner.nextInt();
                if (opcaoAcao == 1) {
                    List<String[]> dados = facade.listarProdutosAlugados();
                    if (dados.isEmpty()) {
                        System.out.println("Nenhum item alugado no momento.");
                    } else {
                        System.out.println("===========Itens Alugados no Momento===========");
                        for (int i = 0; i < dados.size(); i++) {
                            String[] linha = dados.get(i);
                            System.out.println("ID Produto: " + linha[0]);
                            System.out.println("Nome: " + linha[1]);
                            System.out.println("Taxa Diaria: R$ " + linha[2]);
                            System.out.println("ID Cliente: " + linha[3]);
                            System.out.println("Previsao Devolucao: " + linha[4]);
                            System.out.println("Atrasado: " + linha[5]);
                            System.out.println("---------------------------------------");
                        }
                    }
                } else if (opcaoAcao == 2) {
                    List<String[]> dados = facade.listarProdutosAlugados();
                    if (dados.isEmpty()) {
                        System.out.println("Nenhum dado para salvar.");
                    } else {
                        if (facade.salvarProdutosAlugados(dados)) {
                            System.out.println("Relatorio salvo com sucesso.");
                        } else {
                            System.out.println("Erro ao salvar relatorio.");
                        }
                    }
                }
            } else if (opcaoRelatorio == 3) {
                System.out.printf("ID do cliente (0 para voltar): ");
                int idCliente = scanner.nextInt();
                if (idCliente != 0) {
                    System.out.printf("1 - Visualizar%n 2 - Salvar em arquivo%n 0 - Voltar%n");
                    int opcaoAcao = scanner.nextInt();
                    if (opcaoAcao == 1) {
                        List<Contrato> historico = facade.historicoCliente(idCliente, opcaoAcao);
                        if (historico.isEmpty()) {
                            System.out.println("Nenhum historico encontrado.");
                        } else {
                            System.out.println("===========Historico de Cliente===========");
                            for (int x = 0; x < historico.size(); x++) {
                                System.out.println("ID: " + historico.get(x).id());
                                System.out.println("Item: " + historico.get(x).idItem());
                                System.out.println("Inicio: " + historico.get(x).dataInicio());
                                System.out.println("Encerramento: " + historico.get(x).dataFinal());
                                System.out.println("Valor total: " + historico.get(x).valorTotal());
                                System.out.println("---------------------------------------");
                            }
                        }
                    } else if (opcaoAcao == 2) {
                        if (!facade.historicoCliente(idCliente, opcaoAcao).isEmpty() && facade.historicoCliente(idCliente, opcaoAcao).get(0).id() == -2) {
                            System.out.println("Relatorio salvo com sucesso.");
                        } else {
                            System.out.println("Erro ao salvar relatorio.");
                        }
                    }
                }
            } else if (opcaoRelatorio != 0) {
                System.out.println("Opcao invalida.");
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite um numero valido.");
            limparBuffer();
        }
    }

    private void menuCategorias() {
        System.out.printf("===========Categorias===========%n 1 - Listar%n 2 - Buscar por nome%n 0 - Voltar%n");
        try {
            int opcao = scanner.nextInt();
            limparBuffer();
            if (opcao == 1) {
                List<br.upe.lojao.persistencia.entidades.Categoria> categorias = facade.listarCategorias();
                if (categorias.isEmpty()) {
                    System.out.println("Nenhuma categoria cadastrada.");
                } else {
                    for (br.upe.lojao.persistencia.entidades.Categoria c : categorias) {
                        System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Quantidade: " + c.getQuantidade());
                    }
                }
            } else if (opcao == 2) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                List<br.upe.lojao.persistencia.entidades.Categoria> encontradas = facade.buscarCategoria(nome);
                if (encontradas.isEmpty()) {
                    System.out.println("Nenhuma categoria encontrada.");
                } else {
                    for (br.upe.lojao.persistencia.entidades.Categoria c : encontradas) {
                        System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
                    }
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite uma opcao valida.");
            limparBuffer();
        }
    }

    private void menuFornecedores() {
        System.out.printf("===========Fornecedores===========%n 1 - Listar%n 2 - Buscar por nome%n 0 - Voltar%n");
        try {
            int opcao = scanner.nextInt();
            limparBuffer();
            if (opcao == 1) {
                List<br.upe.lojao.persistencia.entidades.Fornecedor> fornecedores = facade.listarFornecedores();
                if (fornecedores.isEmpty()) {
                    System.out.println("Nenhum fornecedor cadastrado.");
                } else {
                    for (br.upe.lojao.persistencia.entidades.Fornecedor f : fornecedores) {
                        System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome() + " | Status: " + f.getStatus());
                    }
                }
            } else if (opcao == 2) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                List<br.upe.lojao.persistencia.entidades.Fornecedor> encontrados = facade.buscarFornecedor(nome);
                if (encontrados.isEmpty()) {
                    System.out.println("Nenhum fornecedor encontrado.");
                } else {
                    for (br.upe.lojao.persistencia.entidades.Fornecedor f : encontrados) {
                        System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome());
                    }
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite uma opcao valida.");
            limparBuffer();
        }
    }

    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada) {}

    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada) {
        if (entrada == 1) {
            System.out.printf("===========Produtos===========%n 1 - Listar disponiveis%n 2 - Ver todos%n 3 - Buscar pelo nome%n 0 - Voltar%n");
            try {
                int opcao = scanner.nextInt();
                if (opcao == 1) {
                    List<Produtos> disponiveis = facade.listarItemDisponivel();
                    if (disponiveis.isEmpty()) {
                        System.out.println("Nenhum item disponível no momento.");
                    } else {
                        System.out.println("===========Itens Disponíveis===========");
                        imprimirProdutos(disponiveis, true);
                    }
                } else if (opcao == 2) {
                    List<Produtos> todos = facade.listarTodosItens();
                    if (todos.isEmpty()) {
                        System.out.println("Nenhum produto foi cadastrado.");
                    } else {
                        System.out.println("===========Todos os Produtos===========");
                        imprimirProdutos(todos, true);
                    }
                } else if (opcao == 3) {
                    limparBuffer();
                    System.out.println("Diga-me o nome do produto desejado.");
                    String nome = scanner.nextLine();
                    List<Produtos> resultados = facade.listarItemNome(nome);
                    if (resultados.isEmpty()) {
                        System.out.println("Nenhum produto encontrado com o nome: " + nome);
                    } else {
                        System.out.println("===========Produtos Encontrados===========");
                        imprimirProdutos(resultados, true);
                    }
                }
            } catch (Exception excecao) {
                System.out.println("ERRO! Digite um numero valido.");
                limparBuffer();
            }
        }
    }

    @Override
    protected void imprimirRespostaFacadeListaContrato(int entrada) {
        if (entrada == 5) {
            System.out.printf("===========Contratos===========%n 1 - Listar todos%n 2 - Contratos de cliente%n 3 - Editar contrato%n 4 - Deletar contrato%n 0 - Voltar%n");
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
                    System.out.print("ID do contrato: ");
                    int idContrato = scanner.nextInt();
                    System.out.printf("Qual campo editar?%n 1 - ID Cliente%n 2 - ID Item%n 3 - Data Inicio%n 4 - Data Fim%n");
                    int campo = scanner.nextInt();
                    if (campo == 1 || campo == 2) {
                        System.out.print("Novo valor (numero): ");
                        int valor = scanner.nextInt();
                        if (facade.editarContratoInt(idContrato, valor, campo)) {
                            System.out.println("Contrato editado com sucesso.");
                        } else {
                            System.out.println("Erro ao editar contrato.");
                        }
                    } else if (campo == 3 || campo == 4) {
                        System.out.print("Nova data (DD/MM/AAAA): ");
                        String dataStr = scanner.next();
                        try {
                            LocalDateTime valor = LocalDate.parse(dataStr, formatter).atStartOfDay();
                            if (facade.editarContratoData(idContrato, valor, campo - 2)) {
                                System.out.println("Contrato editado com sucesso.");
                            } else {
                                System.out.println("Erro ao editar contrato.");
                            }
                        } catch (DateTimeParseException excecaoData) {
                            System.out.println("ERRO! Data invalida.");
                        }
                    } else {
                        System.out.println("Campo invalido.");
                    }
                } else if (opcao == 4) {
                    System.out.print("ID do contrato para deletar: ");
                    int idContrato = scanner.nextInt();
                    if (facade.deletarContrato(idContrato)) {
                        System.out.println("Contrato deletado com sucesso.");
                    } else {
                        System.out.println("Erro ao deletar contrato. Verifique se esta concluido.");
                    }
                }
            } catch (Exception excecao) {
                System.out.println("ERRO! Digite um numero valido.");
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
    protected void imprimirRespostaFacadeString(int entrada, int escolha, String dado) {}

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