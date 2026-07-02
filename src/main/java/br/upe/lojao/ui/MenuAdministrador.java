package br.upe.lojao.ui;

import br.upe.lojao.facade.Facade;
import br.upe.lojao.persistencia.entidades.Categoria;
import br.upe.lojao.persistencia.entidades.Contrato;
import br.upe.lojao.persistencia.entidades.Fornecedor;
import br.upe.lojao.persistencia.entidades.Funcionario;
import br.upe.lojao.persistencia.entidades.Ocorrencias;
import br.upe.lojao.persistencia.entidades.Produtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuAdministrador extends MenuFuncionario {

    private DateTimeFormatter formatterAdmin = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MenuAdministrador(int id, String login, String senha, String tipo, Scanner scanner, Facade facade) {
        super(id, login, senha, tipo, scanner, facade);
    }

    @Override
    public void receberValidarEntradas() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.printf("===================Administrador===================%n"
                    + " 1 - Registrar novo aluguel%n"
                    + " 2 - Processar devolucao%n"
                    + " 3 - Clientes%n"
                    + " 4 - Relatorios%n"
                    + " 5 - Contratos%n"
                    + " 6 - Multas%n"
                    + " 7 - Produtos%n"
                    + " 8 - Categorias%n"
                    + " 9 - Fornecedores%n"
                    + " 10 - Funcionarios%n"
                    + " 0 - Sair%n");
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
                    imprimirRespostaFacadeListaProdutos(7);
                } else if (opcao == 8) {
                    menuCategorias();
                } else if (opcao == 9) {
                    menuFornecedores();
                } else if (opcao == 10) {
                    menuFuncionarios();
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
                LocalDateTime dataInicio = LocalDate.parse(dataInicioStr, formatterAdmin).atStartOfDay();
                LocalDateTime dataFim = LocalDate.parse(dataFimStr, formatterAdmin).atStartOfDay();
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

    private void menuFuncionarios() {
        System.out.printf("===========Funcionarios===========%n 1 - Cadastrar%n 2 - Editar%n 3 - Deletar%n 4 - Listar%n 5 - Buscar por nome%n 0 - Voltar%n");
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
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("Telefone: ");
                String telefone = scanner.nextLine();
                Funcionario funcionario = new Funcionario(0, nome, login, senha, "Funcionario", email, cpf, telefone, false);
                System.out.println(facade.cadastreFuncionario(funcionario));
            } else if (opcao == 2) {
                System.out.print("ID do funcionario: ");
                int id = scanner.nextInt();
                limparBuffer();
                System.out.printf("Qual dado deseja editar?%n 1 - Nome%n 2 - Login%n 3 - Senha%n 4 - Email%n 5 - CPF%n 6 - Telefone%n");
                int campo = scanner.nextInt();
                limparBuffer();
                System.out.print("Novo valor: ");
                String valor = scanner.nextLine();
                System.out.println(facade.editarFuncionario(id, campo, valor));
            } else if (opcao == 3) {
                System.out.print("ID do funcionario: ");
                int id = scanner.nextInt();
                System.out.println(facade.deletarFuncionario(id));
            } else if (opcao == 4) {
                List<Funcionario> funcionarios = facade.lerFuncionario();
                if (funcionarios.isEmpty()) {
                    System.out.println("Nenhum funcionario cadastrado.");
                } else {
                    for (Funcionario f : funcionarios) {
                        System.out.println("ID: " + f.id() + " | Nome: " + f.nome() + " | Login: " + f.login());
                    }
                }
            } else if (opcao == 5) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                List<Funcionario> encontrados = facade.buscarFuncionario(nome);
                if (encontrados.isEmpty()) {
                    System.out.println("Nenhum funcionario encontrado.");
                } else {
                    for (Funcionario f : encontrados) {
                        System.out.println("ID: " + f.id() + " | Nome: " + f.nome());
                    }
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite uma opcao valida.");
            limparBuffer();
        }
    }

    private void menuCategorias() {
        System.out.printf("===========Categorias===========%n 1 - Listar%n 2 - Buscar por nome%n 3 - Cadastrar%n 4 - Editar%n 5 - Deletar%n 0 - Voltar%n");
        try {
            int opcao = scanner.nextInt();
            limparBuffer();
            if (opcao == 1) {
                List<Categoria> categorias = facade.listarCategorias();
                if (categorias.isEmpty()) {
                    System.out.println("Nenhuma categoria cadastrada.");
                } else {
                    for (Categoria c : categorias) {
                        System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Quantidade: " + c.getQuantidade());
                    }
                }
            } else if (opcao == 2) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                List<Categoria> encontradas = facade.buscarCategoria(nome);
                if (encontradas.isEmpty()) {
                    System.out.println("Nenhuma categoria encontrada.");
                } else {
                    for (Categoria c : encontradas) {
                        System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
                    }
                }
            } else if (opcao == 3) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                if (facade.cadastrarCategoria(nome)) {
                    System.out.println("Categoria cadastrada com sucesso.");
                } else {
                    System.out.println("Erro ao cadastrar categoria (nome ja existe?).");
                }
            } else if (opcao == 4) {
                System.out.print("ID da categoria: ");
                int id = scanner.nextInt();
                limparBuffer();
                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                if (facade.editarCategoria(id, novoNome)) {
                    System.out.println("Categoria editada com sucesso.");
                } else {
                    System.out.println("Erro ao editar categoria.");
                }
            } else if (opcao == 5) {
                System.out.print("ID da categoria: ");
                int id = scanner.nextInt();
                if (facade.deletarCategoria(id)) {
                    System.out.println("Categoria deletada com sucesso.");
                } else {
                    System.out.println("Erro ao deletar categoria.");
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite uma opcao valida.");
            limparBuffer();
        }
    }

    private void menuFornecedores() {
        System.out.printf("===========Fornecedores===========%n 1 - Listar%n 2 - Buscar por nome%n 3 - Cadastrar%n 4 - Editar%n 5 - Deletar%n 0 - Voltar%n");
        try {
            int opcao = scanner.nextInt();
            limparBuffer();
            if (opcao == 1) {
                List<Fornecedor> fornecedores = facade.listarFornecedores();
                if (fornecedores.isEmpty()) {
                    System.out.println("Nenhum fornecedor cadastrado.");
                } else {
                    for (Fornecedor f : fornecedores) {
                        System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome() + " | Status: " + f.getStatus());
                    }
                }
            } else if (opcao == 2) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                List<Fornecedor> encontrados = facade.buscarFornecedor(nome);
                if (encontrados.isEmpty()) {
                    System.out.println("Nenhum fornecedor encontrado.");
                } else {
                    for (Fornecedor f : encontrados) {
                        System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome());
                    }
                }
            } else if (opcao == 3) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Telefone: ");
                String telefone = scanner.nextLine();
                if (facade.cadastrarFornecedor(email, telefone, nome)) {
                    System.out.println("Fornecedor cadastrado com sucesso.");
                } else {
                    System.out.println("Erro ao cadastrar fornecedor (nome ja existe?).");
                }
            } else if (opcao == 4) {
                System.out.print("ID do fornecedor: ");
                int id = scanner.nextInt();
                limparBuffer();
                System.out.print("Novo email: ");
                String novoEmail = scanner.nextLine();
                System.out.print("Novo telefone: ");
                String novoTelefone = scanner.nextLine();
                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                if (facade.editarFornecedor(id, novoEmail, novoTelefone, novoNome)) {
                    System.out.println("Fornecedor editado com sucesso.");
                } else {
                    System.out.println("Erro ao editar fornecedor.");
                }
            } else if (opcao == 5) {
                System.out.print("ID do fornecedor: ");
                int id = scanner.nextInt();
                if (facade.deletarFornecedor(id)) {
                    System.out.println("Fornecedor deletado (inativado) com sucesso.");
                } else {
                    System.out.println("Erro ao deletar fornecedor.");
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite uma opcao valida.");
            limparBuffer();
        }
    }

    private void menuRelatorios() {
        System.out.printf("===========Relatorios===========%n 1 - Itens disponiveis por categoria%n 2 - Itens alugados / atrasados%n 3 - Historico de cliente especifico%n 4 - Faturamento em periodo%n 0 - Voltar%n");
        try {
            int opcaoRelatorio = scanner.nextInt();
            if (opcaoRelatorio == 4) {
                System.out.printf("Data inicio (DD/MM/AAAA): ");
                String inicioStr = scanner.next();
                System.out.printf("Data fim (DD/MM/AAAA): ");
                String fimStr = scanner.next();
                try {
                    LocalDateTime inicio = LocalDate.parse(inicioStr, formatterAdmin).atStartOfDay();
                    LocalDateTime fim = LocalDate.parse(fimStr, formatterAdmin).atStartOfDay();
                    System.out.printf("1 - Visualizar%n 2 - Salvar em arquivo%n");
                    int opcaoAcao = scanner.nextInt();
                    BigDecimal resultado = facade.faturamento(inicio, fim, opcaoAcao);
                    if (opcaoAcao == 2) {
                        System.out.println("Relatorio de faturamento salvo com sucesso.");
                    } else if (resultado.signum() < 0) {
                        System.out.println("Nao foi possivel calcular o faturamento para o periodo informado.");
                    } else {
                        System.out.println("Faturamento no periodo: R$ " + resultado);
                    }
                } catch (DateTimeParseException excecaoData) {
                    System.out.println("ERRO! Data invalida. Use o formato DD/MM/AAAA.");
                }
            } else if (opcaoRelatorio >= 1 && opcaoRelatorio <= 3) {
                relatorioHerdado(opcaoRelatorio);
            } else if (opcaoRelatorio != 0) {
                System.out.println("Opcao invalida.");
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite um numero valido.");
            limparBuffer();
        }
    }

    private void relatorioHerdado(int opcaoRelatorio) {
        try {
            if (opcaoRelatorio == 1) {
                System.out.printf("1 - Visualizar%n 2 - Salvar em arquivo%n 0 - Voltar%n");
                int opcaoAcao = scanner.nextInt();
                if (opcaoAcao == 1) {
                    List<String[]> dados = facade.listarItensDisponiveisPorCategoria();
                    if (dados.isEmpty()) {
                        System.out.println("Nenhum item disponivel no momento.");
                    } else {
                        String categoriaAtual = "";
                        for (String[] linha : dados) {
                            if (!linha[0].equals(categoriaAtual)) {
                                categoriaAtual = linha[0];
                                System.out.println("Categoria: " + categoriaAtual);
                            }
                            System.out.println("  ID: " + linha[1] + " | Nome: " + linha[2] +
                                    " | Taxa: R$ " + linha[3] + " | Conservacao: " + linha[4]);
                        }
                    }
                } else if (opcaoAcao == 2) {
                    List<String[]> dados = facade.listarItensDisponiveisPorCategoria();
                    if (dados.isEmpty() || !facade.salvarItensDisponiveisPorCategoria(dados)) {
                        System.out.println("Erro ao salvar relatorio.");
                    } else {
                        System.out.println("Relatorio salvo com sucesso.");
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
                        for (String[] linha : dados) {
                            System.out.println("ID Produto: " + linha[0] + " | Nome: " + linha[1] +
                                    " | Taxa: R$ " + linha[2] + " | Cliente: " + linha[3] +
                                    " | Devolucao: " + linha[4] + " | Atrasado: " + linha[5]);
                        }
                    }
                } else if (opcaoAcao == 2) {
                    List<String[]> dados = facade.listarProdutosAlugados();
                    if (dados.isEmpty() || !facade.salvarProdutosAlugados(dados)) {
                        System.out.println("Erro ao salvar relatorio.");
                    } else {
                        System.out.println("Relatorio salvo com sucesso.");
                    }
                }
            } else if (opcaoRelatorio == 3) {
                System.out.printf("ID do cliente (0 para voltar): ");
                int idCliente = scanner.nextInt();
                if (idCliente != 0) {
                    System.out.printf("1 - Visualizar%n 2 - Salvar em arquivo%n 0 - Voltar%n");
                    int opcaoAcao = scanner.nextInt();
                    List<Contrato> historico = facade.historicoCliente(idCliente, opcaoAcao);
                    if (opcaoAcao == 1) {
                        if (historico.isEmpty()) {
                            System.out.println("Nenhum historico encontrado.");
                        } else {
                            for (Contrato c : historico) {
                                System.out.println("ID: " + c.id() + " | Item: " + c.idItem() +
                                        " | Inicio: " + c.dataInicio() + " | Fim: " + c.dataFinal() +
                                        " | Valor: " + c.valorTotal());
                            }
                        }
                    } else if (opcaoAcao == 2) {
                        if (!historico.isEmpty() && historico.get(0).id() == -2) {
                            System.out.println("Relatorio salvo com sucesso.");
                        } else {
                            System.out.println("Erro ao salvar relatorio.");
                        }
                    }
                }
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite um numero valido.");
            limparBuffer();
        }
    }

    @Override
    protected void imprimirRespostaFacadeBoolean(int entrada) {
    }

    @Override
    protected void imprimirRespostaFacadeListaProdutos(int entrada) {
        System.out.printf("===========Produtos===========%n 1 - Listar disponiveis%n 2 - Ver todos%n 3 - Buscar pelo nome%n 4 - Cadastrar produto%n 5 - Editar produto%n 6 - Deletar produto%n 0 - Voltar%n");
        try {
            int opcao = scanner.nextInt();
            if (opcao == 1) {
                List<Produtos> disponiveis = facade.listarItemDisponivel();
                if (disponiveis.isEmpty()) {
                    System.out.println("Nenhum item disponivel no momento.");
                } else {
                    imprimirProdutos(disponiveis, false);
                }
            } else if (opcao == 2) {
                List<Produtos> todos = facade.listarTodosItens();
                if (todos.isEmpty()) {
                    System.out.println("Nenhum produto foi cadastrado.");
                } else {
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
                    imprimirProdutos(resultados, true);
                }
            } else if (opcao == 4) {
                cadastrarProduto();
            } else if (opcao == 5) {
                editarProduto();
            } else if (opcao == 6) {
                deletarProduto();
            }
        } catch (Exception excecao) {
            System.out.println("ERRO! Digite um numero valido.");
            limparBuffer();
        }
    }

    @Override
    protected void imprimirProdutos(List<Produtos> lista, boolean comDisponibilidade) {
        System.out.println("---------------------------------------");
        for (Produtos p : lista) {
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Categoria: " + p.getIdCategoria());
            System.out.println("Fornecedor: " + p.getIdFornecedor());
            System.out.println("Taxa Diaria: R$ " + p.getTaxaDiaria());
            if (comDisponibilidade) {
                System.out.println("Disponibilidade: " + p.getDisponibilidade());
            }
            System.out.println("Conservacao: " + p.getConservacao());
            System.out.println("Valor Reposicao: R$ " + p.getValorRepo());
            System.out.println("---------------------------------------");
        }
    }

    private void cadastrarProduto() {
        try {
            limparBuffer();
            System.out.println("===========Cadastrar Produto===========");
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine();

            System.out.println("Categorias disponiveis:");
            List<Categoria> categorias = facade.listarCategorias();
            if (categorias.isEmpty()) {
                System.out.println("Nenhuma categoria cadastrada.");
            }
            for (Categoria c : categorias) {
                System.out.println("ID: " + c.getId() + " - Nome: " + c.getNome());
            }
            System.out.print("ID da Categoria: ");
            int idCategoria = scanner.nextInt();

            System.out.println("Fornecedores disponiveis:");
            List<Fornecedor> fornecedores = facade.listarFornecedores();
            if (fornecedores.isEmpty()) {
                System.out.println("Nenhum fornecedor cadastrado.");
            }
            for (Fornecedor f : fornecedores) {
                System.out.println("ID: " + f.getId() + " - Nome: " + f.getNome());
            }
            System.out.print("ID do Fornecedor: ");
            int idFornecedor = scanner.nextInt();

            System.out.print("Taxa Diaria (R$): ");
            BigDecimal taxaDiaria = scanner.nextBigDecimal();

            System.out.print("Valor de Reposicao (R$): ");
            BigDecimal valorReposicao = scanner.nextBigDecimal();

            limparBuffer();

            System.out.print("Disponibilidade (1 para disponivel, 0 para indisponivel): ");
            int respostaDisp = scanner.nextInt();
            limparBuffer();
            String disponibilidade = (respostaDisp == 0) ? "INDISPONIVEL" : "DISPONIVEL";

            System.out.print("Estado de Conservacao: ");
            String conservacao = scanner.nextLine();

            boolean resultado = facade.cadastrarProduto(nome, idCategoria, idFornecedor, taxaDiaria, disponibilidade, conservacao, valorReposicao);

            if (resultado) {
                System.out.println("Produto cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar produto. Verifique se a categoria e o fornecedor existem.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            limparBuffer();
        }
    }

    private void editarProduto() {
        try {
            System.out.print("ID do produto para editar: ");
            int id = scanner.nextInt();
            limparBuffer();

            Produtos produto = facade.buscarProdutoPorId(id);
            if (produto == null) {
                System.out.println("Produto nao encontrado!");
                return;
            }

            System.out.println("Dados atuais do produto:");
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Taxa Diaria: R$ " + produto.getTaxaDiaria());
            System.out.println("Disponibilidade: " + produto.getDisponibilidade());
            System.out.println("Conservacao: " + produto.getConservacao());
            System.out.println("Valor Reposicao: R$ " + produto.getValorRepo());

            System.out.println();
            System.out.println("Digite os novos dados (aperte ENTER para manter o atual):");

            System.out.print("Nova Taxa Diaria (R$): ");
            String taxaStr = scanner.nextLine();
            BigDecimal novaTaxa = taxaStr.isEmpty() ? produto.getTaxaDiaria() : new BigDecimal(taxaStr);

            System.out.println("Fornecedores disponiveis:");
            List<Fornecedor> fornecedores = facade.listarFornecedores();
            if (fornecedores.isEmpty()) {
                System.out.println("Nenhum fornecedor cadastrado.");
            }
            for (Fornecedor f : fornecedores) {
                System.out.println("ID: " + f.getId() + " - Nome: " + f.getNome());
            }
            System.out.print("Novo ID do Fornecedor (ENTER para manter o atual");
            String fornStr = scanner.nextLine();
            int novoFornecedor = fornStr.isEmpty() ? produto.getIdFornecedor() : Integer.parseInt(fornStr);

            System.out.print("Novo Estado de Conservacao: ");
            String conservacao = scanner.nextLine();
            if (conservacao.isEmpty()) {
                conservacao = produto.getConservacao();
            }

            System.out.print("Novo Valor de Reposicao (R$): ");
            String valorStr = scanner.nextLine();
            BigDecimal novoValor = valorStr.isEmpty() ? produto.getValorRepo() : new BigDecimal(valorStr);

            boolean resultado = facade.editarProduto(id, novaTaxa, novoFornecedor, conservacao, novoValor);

            if (resultado) {
                System.out.println("Produto editado com sucesso!");
            } else {
                System.out.println("Erro ao editar produto!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao editar produto: " + e.getMessage());
            limparBuffer();
        }
    }

    private void deletarProduto() {
        try {
            System.out.print("ID do produto para deletar: ");
            int id = scanner.nextInt();

            Produtos produto = facade.buscarProdutoPorId(id);
            if (produto == null) {
                System.out.println("Produto nao encontrado (ja pode ter sido removido).");
                return;
            }

            System.out.println("Produto: ID " + produto.getId() + " - Nome: " + produto.getNome());
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
                System.out.println("Operacao cancelada!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
            limparBuffer();
        }
    }

    @Override
    protected void imprimirRespostaFacadeListaOcorrencia(int entrada) {
        if (entrada == 6) {
            System.out.printf("===========Multas===========%n 1 - Multas pendentes%n 2 - Multas de cliente especifico%n 3 - Registrar avaria%n 4 - Deletar multa%n 0 - Voltar%n");
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
                } else if (opcao == 4) {
                    System.out.print("ID da multa para deletar: ");
                    int idMulta = scanner.nextInt();
                    if (facade.deletarMulta(idMulta)) {
                        System.out.println("Multa deletada com sucesso.");
                    } else {
                        System.out.println("Erro ao deletar multa. Verifique se esta paga.");
                    }
                }
            } catch (Exception excecao) {
                System.out.println("ERRO! Opcao invalida, digite um numero.");
                limparBuffer();
            }
        }
    }
}