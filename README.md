# A Loja que Aluga de um Tudo

Sistema de gerenciamento de aluguéis desenvolvido como trabalho final da disciplina de Programação Orientada a Objetos (UPE, Campus Caruaru). Permite o aluguel de qualquer tipo de item (ferramentas, eletrônicos, artigos para festas, veículos, roupas, etc.), com controle de clientes, funcionários, administradores, contratos, multas, categorias e fornecedores.

## Tecnologias

- Java 21
- Maven
- JUnit 5
- OpenCSV
- Interface via console (texto)

## Pré-requisitos

- JDK 21
- Maven (ou uso do Maven integrado de uma IDE como Eclipse/IntelliJ)

## Como executar

```bash
git clone https://github.com/gdanielbarros/lojaodoaluguel.git
cd lojaodoaluguel
mvn clean install
mvn exec:java -Dexec.mainClass="br.upe.lojao.ui.TelaLogin"
```

Pela IDE: importar como projeto Maven, aguardar o download das dependências e executar a classe `br.upe.lojao.ui.TelaLogin`.

Na primeira execução, os arquivos `.csv` necessários são criados automaticamente em `src/resources/java/br/upe/lojao/`, caso ainda não existam.

### Testes

```bash
mvn test
```

## Arquitetura

O sistema segue Arquitetura em 3 Camadas:

```
br.upe.lojao.ui           -> Apresentação (telas em console)
br.upe.lojao.facade       -> Ponto único de comunicação entre UI e Negócio
br.upe.lojao.negocios     -> Regras de negócio, validações e cálculos
br.upe.lojao.persistencia -> Leitura e escrita dos dados em CSV
```

A comunicação entre UI e Negócio é feita exclusivamente pela classe `Facade`; a UI nunca instancia diretamente classes de negócio ou de persistência. A comunicação entre camadas é sempre feita através de interfaces (`IOperacao*`, `IPersistencia*`).

Não há uso de banco de dados. A persistência é feita em memória (`List`/`ArrayList`), sincronizada com arquivos CSV a cada operação de escrita. A leitura e escrita são centralizadas nas classes genéricas `LerCSV` e `EscreverCSV`, reutilizadas por todas as classes de persistência.

## Estrutura do projeto

```
src/
├── main/java/br/upe/lojao/
│   ├── ui/            -> TelaLogin, Menu, MenuCliente, MenuFuncionario, MenuAdministrador
│   ├── facade/         -> Facade
│   ├── negocios/        -> OperacaoUsuario, OperacaoItem, OperacaoContrato,
│   │                       OperacaoMultas, OperacaoCategoria, OperacaoFornecedor
│   └── persistencia/
│       ├── PersistenciaUsuario, PersistenciaProdutos, PersistenciaCategoria,
│       │   PersistenciaFornecedor, PersistenciaContratos
│       ├── LerCSV / EscreverCSV
│       └── entidades/   -> Cliente, Funcionario, Administrador, Produtos,
│                            Categoria, Fornecedor, Contrato, Ocorrencias
├── resources/java/br/upe/lojao/ -> arquivos .csv e Relatorios/
└── test/java/br/upe/lojao/       -> testes JUnit 5
```

## Entidades

| Entidade | Atributos |
|---|---|
| Cliente | id, nome, login, senha, tipo, email, telefone, cpf, statusMulta, statusContrato |
| Funcionario | id, nome, login, senha, tipo, email, cpf, telefone, statusContrato |
| Administrador | id, nome, login, senha, tipo, email |
| Produtos | id, nome, taxaDiaria, idCategoria, idFornecedor, disponibilidade, conservacao, valorRepo |
| Categoria | id, nome, quantidade |
| Fornecedor | id, email, telefone, nome, status |
| Contrato | id, idCliente, dataInicio, dataFinal, diasAlugados, valorTotal, idItem, status, idMulta, valorMulta |
| Ocorrencias (multa) | id, idContrato, idCliente, valorBase, dataInicio, dataFinal, valorFinal, valorPorcentagem, avarias, status |

## Funcionalidades por perfil

### Cliente
- Ver itens disponíveis
- Ver aluguéis ativos
- Ver histórico
- Ver multas pendentes
- Editar informações
- Excluir conta

### Funcionário
- Ver itens disponíveis, ver todos os itens, buscar item por nome
- Registrar aluguel, processar devolução, editar contrato, deletar contrato
- Ver todos os contratos, ver contratos de cliente específico
- Ver multas pendentes (geral e por cliente), registrar avaria
- Cadastrar cliente, deletar cliente, listar clientes, buscar cliente por nome
- Ver categorias, buscar categoria por nome
- Ver fornecedores, buscar fornecedor por nome
- Relatório de itens disponíveis por categoria
- Relatório de itens alugados com previsão de devolução e clientes em atraso
- Relatório de histórico de cliente específico

### Administrador
- Todas as funcionalidades do Funcionário, mais:
- Adicionar, editar e deletar produto
- Deletar multa
- Cadastrar, editar, deletar, listar e buscar funcionário
- Adicionar, editar e deletar categoria
- Adicionar, editar e deletar fornecedor
- Relatório de faturamento em período

## Regras de negócio

| Código | Regra |
|---|---|
| RN01 | Um item não pode ser alugado se já estiver associado a um contrato ativo |
| RN02 | O valor do aluguel é a taxa diária do item multiplicada pela quantidade de dias |
| RN03 | Devolução após a data prevista gera multa: valor fixo de penalidade mais percentual sobre os dias de atraso |
| RN04 | Clientes com multas em aberto não podem realizar novos aluguéis |
| RN05 | Não é possível excluir item, cliente ou funcionário com histórico de contratos (exclusão lógica ou bloqueio) |

## Relatórios

- Itens disponíveis por categoria
- Histórico de aluguéis de um cliente específico
- Itens alugados no momento, com previsão de devolução e identificação de atraso
- Faturamento em período (exclusivo do Administrador)

## Testes

Testes de unidade com JUnit 5, com foco na camada de negócio (cálculo de multas, validação de disponibilidade, regras de acesso). Testes complementares também cobrem a camada de persistência.

## Responsabilidades
[Yan Alves](https://github.com/yan-dhsk) - CRUD Multas, Contrato, Testes.
[Vinicius Meneses](https://github.com/viniciusmeneses-tech) - CRUD Cliente, Administrador, Funcionario
[João Flávio](https://github.com/h-i-f-e-m) - CRUD de produtos
[Daniel Barros](https://github.com/gdanielbarros) - CRUD de fornecedor e categoria