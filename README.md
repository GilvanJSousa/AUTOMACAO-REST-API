# Automação de Testes REST API

![](.gitbucket/images/banner.png)

# O Projeto
O projeto de automação de testes tem o objetivo de ajudar nos ciclos de testes regressivos de APIs REST, reduzindo o esforço manual e ganhando velocidade a cada ciclo e a cada entrega.

# Sumário

- [1- Download e configuração do Java](#1--download-do-java)
- [2- Instalação IntelliJ](#2--instalar-o-intellij)
- [3- Instalação Maven](#3--instalar-o-maven)
- [4- Instalação Git](#4--instalar-do-git)
- [5- Estrutura do projeto](#5--estrutura-do-projeto)

<h1>Configurando o ambiente</h1>

# 1- Download do Java

Baixar o JDK de acordo com o seu sistema operacional (Recomendamos instalar o Java 8 para evitar incompatibilidade com o framework)

- [Download Java 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html) - Java Development Kit significa Kit de Desenvolvimento Java, e é um conjunto de utilitários que permitem criar sistemas de software para a plataforma Java. É composto por compilador e bibliotecas.

<h3>1.1 - Configurando o JDK no Windows:</h3>
A) Vá até as variáveis de ambiente no Windows e adicione a seguinte variável com o respectivo valor e clique em OK:

- Nome da variável:  ***JAVA_HOME***
- Valor da variável: ***C:\Program Files\Java\jdk1.8.0_333***
- Nome da variável:  ***JRE_HOME***
- Valor da variável: ***C:\Program Files\Java\jdk1.8.0_333\jre***

> ***Atenção:*** O valor da variável é ilustrativo. O diretório correto deve ser o local onde está instalado o JDK, podendo haver diferenças na versão (números após o jdk1.8)

B) Selecione a variável PATH e clique em Editar... Clique no botão Novo e adicione o seguinte valor de variável ***%JAVA_HOME%\bin***

# 2- Instalar o IntelliJ

- [Download IntelliJ community version](https://www.jetbrains.com/pt-br/idea/download) - O IntelliJ IDEA é um ambiente de desenvolvimento integrado escrito em Java para o desenvolvimento de software de computador. É desenvolvido pela JetBrains e está disponível como uma edição da comunidade licenciada do Apache 2.

# 3- Instalar o Maven

- [Download Maven](https://maven.apache.org/download.cgi) - O Maven é uma ferramenta de automação de build utilizada principalmente em projetos Java.

<h3>3.1 - Configurando o Maven no Windows:</h3>
A) Após realizar o download deve ser descompactado o arquivo e movido para a pasta ***C:\Users\USUARIO\AppData\Local*** devendo alterar para o seu usuário.

B) Vá até as variáveis de ambiente no Windows e adicione a seguinte variável com o respectivo valor e clique em OK:
- Nome da variável:  ***MAVEN_HOME***
- Valor da variável: ***C:\Users\USUARIO\AppData\Local\maven***

C) Selecione a variável PATH e clique em Editar... Clique no botão Novo e adicione o seguinte valor de variável ***%MAVEN_HOME%\bin***

# 4- Instalar do Git

- [Download Git](https://git-scm.com/downloads) - Software utilizado para versionamento de código, para acompanhar novas mudanças e criar ambiente de trabalho coletivo.

Após o download apenas deve ser executado para realizar a instalação seguindo a instalação padrão.

<h3>Clone do projeto</h3>

1. Para realizar o clone do projeto deve se entrar no repositório, acessar o botão ***clone*** no topo do projeto, copiar a URL gerada.
2. Abrir o git bash
3. Navegar até a pasta que deseja fazer o clone do projeto usar o comando cd para acessar os arquivos e ls para verificar as pasta existentes no arquivo.
4. Usar o comando que foi copiado do repositório, semelhante ao seguinte:
```bash
git clone https://github.com/seu-usuario/seu-projeto.git
```

# 5- Estrutura do projeto

A estrutura de arquivos está organizada da seguinte maneira:

```
src/test/java/org/br/com/testes/
├── controllers/         # Controladores para as requisições HTTP
│   ├── usuarios/       # Controladores específicos para usuários
│   └── artigos/       # Controladores específicos para artigos
│
├── manager/            # Gerenciadores de estado e recursos
│   ├── TokenManager.java
│   ├── AuthenticationToken.java
│   └── ErrorManager.java
│
├── model/             # Modelos de dados
│   ├── usuarios/      # Modelos relacionados a usuários
│   └── artigos/      # Modelos relacionados a artigos
│
├── steps/             # Steps do Cucumber
│   ├── UsuarioCmsSteps.java
│   └── ArtigosSteps.java
│
├── tokens/            # Gerenciamento de tokens
│   ├── GerarTokenResquest.java
│   └── GerarToken.java
│
└── utils/             # Utilitários
    ├── JsonUtils.java
    ├── Utils.java
    ├── FakerApiData.java
    └── DataUtils.java
```

## Tecnologias Utilizadas
- Java
- REST Assured
- Cucumber
- JUnit
- Faker

## Executando os Testes

Para executar todos os testes:
```bash
mvn clean test
```

Para executar testes específicos usando tags do Cucumber:
```bash
mvn clean test -D"cucumber.filter.tags=@tag_name"
``` 

