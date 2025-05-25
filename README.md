# Automação de Testes API REST

![](.gitbucket/images/banner.png)

# O Projeto
Este projeto de automação de testes tem como objetivo validar a funcionalidade de APIs REST, reduzindo o esforço manual e ganhando velocidade a cada ciclo de testes.

# Sumário

- [1- Configurando o Ambiente](#1--configurando-o-ambiente)
- [2- Clonando o Projeto](#2--clonando-o-projeto)
- [3- Estrutura do Projeto](#3--estrutura-do-projeto)
- [4- Tecnologias Utilizadas](#4--tecnologias-utilizadas)
- [5- Executando os Testes](#5--executando-os-testes)

---

<h1><a name="1--configurando-o-ambiente"></a>1- Configurando o Ambiente</h1>

Para executar este projeto, você precisará do seguinte:

### 1.1- Java Development Kit (JDK)
- Recomendamos o Java 8 ou superior
- **Instalação:** Faça o download no [site oficial da Oracle](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)
- **Configuração:**
    - Defina a variável de ambiente `JAVA_HOME` para o diretório de instalação do JDK
    - Adicione `%JAVA_HOME%\bin` (Windows) ou `$JAVA_HOME/bin` (Linux/macOS) à sua variável de ambiente `PATH`

### 1.2- Maven
- Utilizado para gerenciamento de dependências e build do projeto
- **Instalação:** Faça o download no [site oficial do Maven](https://maven.apache.org/download.cgi)
- **Configuração:**
    - Defina a variável de ambiente `MAVEN_HOME` para o diretório de instalação do Maven
    - Adicione `%MAVEN_HOME%\bin` (Windows) ou `$MAVEN_HOME/bin` (Linux/macOS) à sua variável de ambiente `PATH`

### 1.3- IntelliJ IDEA
- Recomendamos o [IntelliJ IDEA Community Edition](https://www.jetbrains.com/pt-br/idea/download)
- **Instalação:** Baixe e instale a partir do site oficial

### 1.4- Git
- Para controle de versão
- **Instalação:** Faça o download no [site oficial do Git](https://git-scm.com/downloads)

---

<h1><a name="2--clonando-o-projeto"></a>2- Clonando o Projeto</h1>

1. Abra seu terminal ou Git Bash
2. Navegue até o diretório onde deseja clonar o projeto
3. Execute o comando:
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    ```

---

<h1><a name="3--estrutura-do-projeto"></a>3- Estrutura do Projeto</h1>

A estrutura de arquivos está organizada da seguinte maneira:

```
src/
└── test/
    ├── java/
    │   └── org/
    │       └── br/
    │           └── com/
    │               └── testes/
    │                   ├── controllers/    # Controladores para gerenciar requisições HTTP
    │                   │   └── usuarios/   # Controladores específicos de usuários
    │                   ├── manager/        # Gerenciadores de recursos e configurações
    │                   ├── model/          # Classes de modelo de dados
    │                   ├── steps/          # Passos de teste e definições
    │                   ├── utils/          # Utilitários e helpers
    │                   └── tokens/         # Gerenciamento de tokens e autenticação
    └── resources/
        └── features/    # Arquivos de feature do Cucumber
```

### Descrição dos Diretórios

#### src/test/java
- **controllers/**: Contém as classes responsáveis por gerenciar as requisições HTTP e interações com a API
- **manager/**: Classes para gerenciamento de recursos, configurações e estados do teste
- **model/**: Classes que representam os modelos de dados utilizados nos testes
- **steps/**: Implementações dos passos de teste usando Cucumber
- **utils/**: Classes utilitárias e helpers para suporte aos testes
- **tokens/**: Gerenciamento de tokens de autenticação e autorização

#### src/test/resources
- **features/**: Arquivos de feature do Cucumber que descrevem os cenários de teste

---

<h1><a name="4--tecnologias-utilizadas"></a>4- Tecnologias Utilizadas</h1>

- Java: Linguagem de programação principal
- JUnit 5: Framework para escrita e execução de testes
- REST Assured: Biblioteca para testes de API REST
- Maven: Ferramenta de automação de build e gerenciamento de dependências
- Cucumber: Framework para testes BDD (Behavior Driven Development)
- Lombok: Redução de código boilerplate
- Faker: Geração de dados fake para testes

---

<h1><a name="5--executando-os-testes"></a>5- Executando os Testes</h1>

Para executar os testes:

1. Abra o terminal na raiz do projeto
2. Execute o comando Maven:
    ```bash
    mvn clean test
    ```

Você também pode executar os testes diretamente pela sua IDE (IntelliJ, Eclipse, etc.). Geralmente, clicando com o botão direito no arquivo de teste ou na classe e selecionando "Run Test".

---

**Próximos Passos:**
À medida que o projeto evoluir, este README será atualizado com mais detalhes sobre a arquitetura, novos tipos de teste e configurações adicionais.
