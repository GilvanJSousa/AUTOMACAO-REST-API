![](.gitbucket/images/banner.png)
<h1> Automação Aplicativo Digio</h1>

# O Projeto
O projeto de automação de testes tem o objetivo de ajudar nos ciclos de testes regressivos, reduzindo o esforço manual e ganhando velocidade a cada ciclo e a cada entrega.

# Sumário

-   [1- Download e configuração do Java](#1--download-do-java)

-   [2- Instalação Intellij](#2--instalar-o-intellij)

-   [3- Instalação NodeJS](#3--instalar-o-nodejs)

-   [4- Instalação Appium Desktop](#4--instalar-o-appium-desktop)

-   [5- Instalação Appium Inspector](#5--instalar-o-appium-inspector)

-   [6- Instalação Maven](#6--instalar-o-maven)

-   [7- Instalação Lighshot](#7--instalar-do-lightshot--sharex)

-   [8- Instalação Git](#8--instalar-do-git)

-   [9- Configuração Tokens](#9--configuracao-dos-tokens)

-   [10- Configuração VPN](#10--instalacao-do-fortclient)

-   [11- Configuração do Run/Bug](#11--configuracao-do-rundebug)

-   [12- Estrutura do projeto](#12--estrutura-do-projeto)


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

> ***Atenção:*** Se a variável PATH não existir, crie a variável com o mesmo valor de variável.

<h3>1.2 - Configurando o JDK no Linux e Mac OS:</h3>
Abra seu arquivo **.bash_profile** ou **.zshrc.** E cole as seguintes variáveis de ambiente:
  ~~~~
  export HOME=/Users/**nomedousuario**
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk**suaversaodojdk**.jdk/Contents/Home/
  export ANDROID_HOME=$HOME/Library/Android/sdk
  export PATH=$PATH:$JAVA_HOME/bin:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools/bin
  ~~~~

> ***Atenção:*** se a variável PATH não existir, crie a variável com o mesmo valor de variável.

# 2- Instalar o IntelliJ

- [Download IntelliJ community version](https://www.jetbrains.com/pt-br/idea/download) - O IntelliJ IDEA é um ambiente de desenvolvimento integrado escrito em Java para o desenvolvimento de software de computador. Ele é desenvolvido pela JetBrains e está disponível como uma edição da comunidade licenciada do Apache 2 e em uma edição comercial proprietária.

> ***Atenção:*** Realizar o download da versão 2021.3.2

<h3>2.1 Configuração dos plugins</h3>
-  <h4> Plugin do SDK Android</h4>
Criar um novo projeto e selecionar projeto Android, será apresentado a tela para realizar instalação do SDK, realizar a instalação, selecionando as opções disponiveis.
![](.gitbucket/images/configIntellij1.png)
![](.gitbucket/images/configIntellij2.png)

- <h4>Plugin do Lab Mobile</h4>
  Para poder utilizar os devices do Lab Mobile dentro do Intellij, seguir os passo abaixo:

<br>1- Acessar Files, depois settings ![](.gitbucket/images/configIntellij3.png)
<br>2- Acessar opção plugins ![](.gitbucket/images/configIntellij4.png)
<br>3- Realizar a busca e instalação do ***UFT Digital Lab*** ![](.gitbucket/images/configIntellij6.png)

Após a instalação do plugin deve acessar o LabMobile na engrenagem ![](.gitbucket/images/configIntellij7.png), clicar no **+** para gerar uma nova chave de acesso![](.gitbucket/images/configIntellij8.png), deve ser preenchida com nome a sua escolha para melhor identificar o motivo da chave, o tipo a ser selecionado é ***Execution*** e deve ser escolhido uma data de expiração ou selecionar para nunca expirar.

> ***Atenção:*** Deve salvar as informações apresentadas pois serão necessarias futuramente.

Dentro do Intellij na lateral direta vai ter uma opção ![](.gitbucket/images/configIntellij5.png) chamada ***UFT Digital Lab*** dentro dela em **Settings** deve ser colocado a URL do LabMobile, em **Access key** deve ser colocado o **client=oauth2**. Após isso é só realizar o login.

# 3- Instalar o NodeJS

> ****Atenção:**** Escolher a última versão estável.

- [Download NodeJS](https://nodejs.org/en/download/) - Node.js é um software de código aberto, multiplataforma, baseado no interpretador V8 do Google e que permite a execução de códigos JavaScript fora de um navegador web.

# 4- Instalar o Appium desktop

> ***Atenção:*** Escolher a última versão estável.

- [Download Appium Desktop](https://github.com/appium/appium-desktop/releases/tag/v1.21.0) - O Appium é uma ferramenta de automação de código aberto para executar scripts e testar aplicativos nativos, aplicativos da Web para dispositivos móveis e aplicativos híbridos no Android ou iOS usando um driver de web.

# 5- Instalar o Appium Inspector

- [Download Appium Inspector](https://github.com/appium/appium-inspector/releases) - O Appium inspector é uma ferramenta para inspeção dos elementos existente nos aplicativos que estão sendo executados nos devices, auxiliando a pegar o metodos que iremos utilizar para interagir com o aplicativo.

<h3>5.1 Instalar plugin appium NodeJs</h3>

- 1- Abrar um terminal CMD
- 2- Rode o comando npm install appium-doctor -g

> ****Atenção:**** Para verificação se o appium foi instalado corretamente abrir o cmd e rodar o comando ***appium-doctor***

![](.gitbucket/images/resultadoAppiumDoctor.png)

<h3>5.2 Capability - Android</h3>

~~~~ java
"appium:noReset": true,
"appium:fullReset": false,
"appium:appPackage": "br.com.digio.homol",
"appium:udid": "xxxxxxxx",
"appium:appActivity": "br.com.digio.newarchitecture.ui.splash.activity.SplashActivity",
"uftm:oauthClientId": "XXXXXXXXXXXmicrofocus.com",
"uftm:oauthClientSecret": "XXXXXXX",
"uftm:tenantId": "999999999",
"platformName": "Android",
"appium:mcWorkspaceName": "regressivo",
"uftm:appiumVersion": "v2.x"
~~~~

As seguintes chaves abaixo, são geradas pelo Token do LabMobile - Token LabMobile - [QA] Qualidade de Software - Digio - Confluence (atlassian.net).

~~~~
uftm:oauthClientId
uftm:oauthClientSecret
uftm:tenantId
~~~~

<h3>5.3 Capability - IOS</h3>

~~~~ Java
"appium:noReset": true,
"appium:fullReset": false,
"appium:bundleId": "br.com.digio-Hml",
"appium:udid": "xxxxxxxx",
"uftm:oauthClientId": "XXXXXXXXXXXmicrofocus.com",
"uftm:oauthClientSecret": "XXXXXXX",
"uftm:tenantId": "999999999",
"platformName": "iOS",
"uftm:mcWorkspaceName": "regressivo",
"uftm:appiumVersion": "v2.x"
~~~~

As seguintes chaves abaixo, são geradas pelo Token do labMobile - Token LabMobile - [QA] Qualidade de Software - Digio - Confluence (atlassian.net).

~~~~
uftm:oauthClientId
uftm:oauthClientSecret
uftm:tenantId
~~~~

# 6- Instalar o maven

> ***Atenção:*** Escolher a última versão 3 ou superior com a extensão .zip.

- [Download Maven](https://github.com/appium/appium-desktop/releases/tag/v1.21.0) - O Appium é uma ferramenta de automação de código aberto para executar scripts e testar aplicativos nativos, aplicativos da Web para dispositivos móveis e aplicativos híbridos no Android ou iOS usando um driver de web.

<br>A) Após realizar o download deve ser descompactado o arquivo e movido para a pasta ***C:\Users\USUARIO\AppData\Local*** devendo alterar para o seu usuário.
<br>B) Vá até as variáveis de ambiente no Windows e adicione a seguinte variável com o respectivo valor e clique em OK:
- Nome da variável:  ***MAVEN_HOME***
- Valor da variável: ***C:\Users\abdiel.cordeiro\AppData\Local\maven***

<br>C) Selecione a variável PATH e clique em Editar... Clique no botão Novo e adicione o seguinte valor de variável ***%MAVEN_HOME%\bin***

> ***Atenção:*** O valor da variável é ilustrativo. O diretório correto deve ser o local onde está instalado o Maven.

# 7- Instalar do Lightshot / Sharex

- [Download Lightshot/Sharex](https://getsharex.com/downloads) - Softwares de captura de imagem e para gravar vídeo/gif para gerar evidências.

# 8- Instalar do Git

- [Download Git](https://git-scm.com/downloads) - Software utilizado para versionamento de código, para acompanhar novas mudanças e criar ambiente de trabalho coletivo.

Após o download apenas deve ser executado para realizar a instalação seguindo a instalação padrão.

<h3>Clone do projeto</h3>

- 1-Para realizar o clone do projeto deve se entrar no bitbucket, acessar o botão ***clone*** no topo do projeto, copiar a URL gerada.
- 2-Abrir o git bash
- 3- Navegar até a pasta que deseja fazer o clone do projeto usar o comando cd para acessar os arquivos e ls para verificar as pasta existentes no arquivo.
- 4- usar o comando que foi copiado do BitBucket, semelhante ao seguinte.
~~~~
git clone https://user.email/cbssmobile/digio-qa-mobile-tests.git
~~~~

<h3>Configuração da execução</h3>

- 1- Criar dentro da pasta ***resources*** a pasta com nome de ***config***
- 2- Criar o arquivo com nome de ***execution.json*** dentro do arquivo colocar as linhas abaixo.

~~~~ java
{
  "connector": {
    "host": "",
    "port": ,
    "auth": {
      "userName": "",
      "password": "",
      "oauthClientId": "",
      "oauthClientSecret": "",
      "tenantId":
    },
    "targets": [{
      "capabilities": {
        "udid": "",
        "platformName": "",
        "noReset" : true
      }
    }
    ],
    "globalCapabilities": {
      "android": {
        "appPackage": "br.com.digio.homol",
        "appActivity": "br.com.digio.newarchitecture.ui.splash.activity.SplashActivity",
        "appium:autoGrantPermissions": true,
        "uftm:mcWorkspaceName": "Automação",
        "uftm:appiumVersion": "v2.x"
      },
      "ios": {
        "bundleId": "br.com.digio-Hml",
        "uftm:appiumVersion": "v2.x"
      }
    },
    "application": {
      "name": "Digio App",
      "android": {
        "version": "2.22.3.0-homol",
        "build": "406"
      },
      "ios": {
        "version": "2.20.50",
        "build": "79"
      },
      "installLatest": false
    },
    "look": {
      "enabled": false,
      "apiAddress": "http://localhost:9090",
      "windowResizeTimeout": 1000
    },
    "evidence": {
      "exportScreenshot": true,
      "exportAppiumLogs": true,
      "exportTraceFile": false
    }
  },
  "runner": {
    "cucumber": {
      "dryRun" : false,
      "tags" : "",
      "glue": [
        "br.com.digio.steps"
      ],
      "snippets": "camelCase",
      "strict": false,
      "monochrome": true
    },
    "disablePassedStepEvidence": false,
    "integration": {
      "knooly": {
        "enabled": false,
        "host": "",
        "port": "",
        "clientID": "",
        "clientSecret": "",
        "tenantID": "",
        "projectId": ,
        "cycleStartDate": "",
        "customerName": "",
        "uploadEvidenceOnStatus": "all"
      }
    }
  },
  "report": {
    "enabled": true,
    "persistenceHost": "",
    "persistencePort":
  }
}
~~~~

# 9- Configuracao dos Tokens

<h3>9.1 Token Knooly</h3>

Efetuar login no Knooly, através do link: [Knooly](https://knooly-digio.keeggo.com/#/login)

![](.gitbucket/images/img_7.png)
![](.gitbucket/images/configKnooly1.png)
![](.gitbucket/images/configKnooly2.png)
![](.gitbucket/images/configKnooly3.png)
![](.gitbucket/images/configKnooly4.png)

<h3>9.2 Token BitBucket</h3>

Efetuar login no Bitbucket, através do link: [BitBucket](https://bitbucket.org/cbssmobile/workspace/overview)

![](.gitbucket/images/configBitBucket1.png)
![](.gitbucket/images/configBitBucket2.png)
![](.gitbucket/images/configBitBucket3.png)
![](.gitbucket/images/configBitBucket4.png)
![](.gitbucket/images/configBitBucket5.png)
![](.gitbucket/images/configBitBucket6.png)
![](.gitbucket/images/configBitBucket7.png)

# 10- Instalacao do FortClient

- [Instalar FortClient](https://forticlient.informer.com/versions/) - Software para realização da VPN.
- [Instalar FortToken Windows](https://www.microsoft.com/store/productId/9P0TDH1J7WFZ) - Sistema para realizar a autenticação na VPN.

> ***Atenção:*** Após a instalação desses dois softwares para realizar a configuração necessário contato com felipe.pereira@digio.com.br

# 11- Configuracao do Run/Debug

Configuração do Main para a execução dos testes

![](.gitbucket/images/configRun1.png)
![](.gitbucket/images/configRun2.png)
![](.gitbucket/images/configRun3.png)
![](.gitbucket/images/configRun4.png)

# 12- Estrutura do projeto

A estrutura de arquivos está da seguinte maneira:

```
digio-qa-uber-mobile
├── .gitbucket/
│   ├── images/
├── feature/
├── src
│   └── main/
│        └── java
│              └── br
│                  └── com
│                       └── digio
│                             ├── core/
│                                   └── core
│                                   └── screens
│                                   └── steps
│                                   └── util
│                             ├── Main.java
│       └── resources
│               └── config
│                      └── execution_local.json
│               └── features/
│                      └── hml
│               └── massa/
│               └── application.yaml
│               └── application-context.xml
│               └── log4j.properties
│               └── log4j2.properties
├── target/
├── .gitignore
├── intellij_google_style.xml
├── lombok.config
├── pom.xml
├── README.md
└── settings.xml
```