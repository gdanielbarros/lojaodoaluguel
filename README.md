# Lojão do Aluguel - Sistema de Gestão de Locações

Este projeto prático foi desenvolvido para a composição de nota da Segunda Unidade da disciplina de Programação II da Universidade de Pernambuco (UPE). O sistema consiste em uma plataforma de gerenciamento de locações para a empresa fictícia "A Loja que Aluga de um Tudo", permitindo o aluguel de itens diversos como ferramentas, eletrônicos, artigos para festas, veículos e roupas.

## 1. Visão Geral e Funcionalidades

O sistema gerencia o fluxo de um estabelecimento de aluguel multi-categoria através de uma interface de linha de comando:

* **Controle de Acesso por Perfil**:
    * **Cliente**: Visualiza itens disponíveis, consulta aluguéis ativos, histórico e multas.
    * **Funcionário**: Cadastra clientes, realiza novos aluguéis, processa devoluções e aplica multas.
    * **Administrador**: Controle total do sistema, gerenciamento de funcionários, taxas, itens e relatórios financeiros.
* **Relatórios Exigidos**:
    * Listagem de itens disponíveis para aluguel por Categoria.
    * Histórico de aluguéis de um cliente específico.
    * Monitoramento de itens alugados no momento com identificação de atrasos.
    * Relatório de faturamento total em um determinado período.

## 2. Arquitetura e Padrões de Projeto

O software segue rigorosos padrões de qualidade e arquitetura moderna:

* **Arquitetura de 3 Camadas**: O sistema é dividido entre as camadas de Apresentação (UI), Negócio (Services/Controllers) e Persistência (Repositories).
* **Padrão Facade**: A comunicação entre a Interface de Usuário e a Camada de Negócio é mediada exclusivamente por uma classe Facade.
* **Persistência de Dados**: O sistema utiliza estruturas de dados em memória (Coleções) salvas e carregadas em arquivos estruturados, sem o uso de banco de dados SQL/NoSQL.
* **Linguagem e Build**: Implementado em Java 21 utilizando Maven para gerenciamento de dependências.

## 3. Requisitos de Ambiente

* **Java SDK**: Versão 21 (LTS).
* **Apache Maven**: Versão 3.8 ou superior.
* **Testes Automatizados**: JUnit 5 para validação da Camada de Negócios.

## 4. Equipe e Divisão de Responsabilidades

O grupo é composto por 4 integrantes, com a seguinte divisão de módulos:

* **Daniel Barros (Líder Arquitetural)**:
    * CRUD de Categorias (Classificação dos itens para relatórios).
    * Implementação da Facade e controle de sessão do usuário.
* **Yan**:
    * CRUD de Itens/Produtos (Cadastro, taxas diárias e conservação).
    * CRUD de Fornecedores/Parceiros (Origem dos itens sublocados).
* **Vinicius**:
    * CRUD de Contratos de Aluguel (Registro de transações e cálculo de datas).
* **Integrante 4**:
    * CRUD de Usuários (Clientes, Funcionários e Administradores).
    * CRUD de Multas e Ocorrências (Registro de avarias e penalidades).

## 5. Regras de Negócio Principais

* **RN01 - Disponibilidade**: Itens com status "Alugado" ou "Em Manutenção" não podem ser vinculados a novos contratos.
* **RN02 - Cálculo de Valor**: Taxa diária multiplicada pela quantidade de dias solicitados.
* **RN03 - Multa por Atraso**: Geração automática de cobrança extra com valor fixo mais percentual sobre dias de atraso.
* **RN04 - Inadimplência**: Clientes com multas em aberto são bloqueados para novos aluguéis.
* **RN05 - Integridade**: Uso de exclusão lógica para registros com histórico de contratos vinculados.

## 6. Como Compilar e Executar
```bash
   mvn clean compile

  2. Rodar os testes unitários:
  mvn test

  mvn exec:java -Dexec.mainClass="br.upe.lojao.Main"

1. **Compilar o projeto**:
```bash
   mvn clean compile
