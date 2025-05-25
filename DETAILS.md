# Detalhes das Classes

## Controllers

### Usuários
- **UsuarioCmsController.java**
  - Responsável por gerenciar as requisições relacionadas a usuários CMS
  - Métodos implementados:
    - `cadastrarNovoUsuario`: Cadastra um novo usuário
    - `realizarLogin`: Realiza autenticação
    - `listarUsuariosComAutenticacao`: Lista todos os usuários
    - `consultarUsuarioPorId`: Busca usuário por ID
    - `atualizarUsuarioPorId`: Atualiza dados do usuário
    - `excluirUsuarioPorId`: Remove um usuário

## Manager

### TokenManager.java
- Gerenciamento de tokens de autenticação
- Armazenamento e validação de tokens
- Controle de sessão
- Implementação thread-safe
- Verificação automática de expiração

### AuthenticationToken.java
- Classe para manipulação de tokens de autenticação
- Métodos para geração e validação de tokens
- Controle de expiração
- Renovação automática

### ErrorManager.java
- Gerenciamento de erros da aplicação
- Tratamento de exceções
- Logs de erro
- Mensagens personalizadas

## Model

### Usuários
- **UsuarioCms.java**
  - Modelo para representação de usuários CMS
  - Atributos:
    - nomeCompleto: Nome completo do usuário
    - nomeUsuario: Nome de usuário para login
    - email: Email do usuário
    - senha: Senha do usuário
  - Anotações Lombok para redução de boilerplate
  - Validações de dados

### Login
- Modelos relacionados à autenticação
- Estruturas para requisições de login
- Respostas de autenticação
- Validação de credenciais

## Steps

### UsuarioCmsSteps.java
- Implementação dos passos do Cucumber para usuários CMS
- Cenários:
  - Cadastro de usuário
  - Login
  - Listagem
  - Consulta
  - Atualização
  - Exclusão
- Integração com TokenManager

### UsuariosSteps.java
- Steps para funcionalidades gerais de usuários
- Implementação de cenários de teste
- Validações de resposta
- Tratamento de erros

### LoginServeRestSteps.java
- Steps específicos para autenticação
- Cenários de login e validação de token
- Gerenciamento de sessão
- Verificação de tokens

## Tokens

### GerarTokenResquest.java
- Modelo para requisição de geração de token
- Estrutura de dados para autenticação
- Validação de campos obrigatórios

### GerarToken.java
- Implementação da geração de tokens
- Validação e renovação de tokens
- Controle de expiração
- Armazenamento seguro

## Utils

### JsonUtils.java
- Utilitário para manipulação de JSON
- Métodos para leitura e escrita de arquivos JSON
- Conversão de objetos
- Validação de estrutura

### Utils.java
- Funções utilitárias gerais
- Métodos auxiliares
- Constantes
- Validações comuns

### FakerApiData.java
- Geração de dados fake para testes
- Nomes, emails, senhas
- Dados aleatórios realistas
- Conversão de preços
- Geração de usuários CMS

### DataUtils.java
- Manipulação de datas
- Formatação
- Cálculos com datas
- Validação de formatos

# Detalhes da Implementação

## Gerenciamento de Tokens

### Visão Geral
O sistema implementa um gerenciamento robusto de tokens de autenticação utilizando o `TokenManager`. Esta implementação oferece uma solução mais segura e eficiente para o gerenciamento de tokens, eliminando a necessidade de arquivos JSON estáticos.

### Componentes Principais

#### TokenManager
- Gerencia tokens de autenticação de forma thread-safe
- Implementa verificação de expiração de tokens
- Armazena tokens temporariamente em memória usando ThreadLocal
- Mantém backup do token em arquivo temporário
- Validação automática de tokens

#### UsuarioCmsController
- Integrado com TokenManager para gerenciamento de tokens
- Realiza operações CRUD com autenticação automática
- Gerencia sessões de usuário de forma segura
- Tratamento de erros de autenticação

### Fluxo de Autenticação

1. **Login**
   ```java
   usuarioCmsController.realizarLogin(email, senha);
   ```
   - Realiza login com credenciais
   - Token é automaticamente salvo no TokenManager
   - Retorna status 200 em caso de sucesso

2. **Operações Autenticadas**
   ```java
   // Listar usuários
   usuarioCmsController.listarUsuariosComAutenticacao();
   
   // Consultar usuário
   usuarioCmsController.consultarUsuarioPorId();
   
   // Atualizar usuário
   usuarioCmsController.atualizarUsuarioPorId(nomeCompleto, nomeUsuario);
   
   // Excluir usuário
   usuarioCmsController.excluirUsuarioPorId();
   ```
   - Todas as operações usam o token do TokenManager
   - Validação automática de expiração do token
   - Tratamento de erros de autenticação

### Benefícios da Implementação

1. **Segurança**
   - Tokens não são mais armazenados em arquivos estáticos
   - Gerenciamento thread-safe
   - Verificação automática de expiração
   - Proteção contra vazamento de dados

2. **Manutenibilidade**
   - Código mais limpo e organizado
   - Centralização do gerenciamento de tokens
   - Facilidade de manutenção
   - Documentação clara

3. **Performance**
   - Tokens armazenados em memória
   - Redução de operações de I/O
   - Melhor gerenciamento de recursos
   - Otimização de requisições

### Uso em Testes

```java
@Given("que eu tenho um token de autenticação válido")
public void queEuTenhoUmTokenDeAutenticacaoValido() {
    if (!TokenManager.hasValidToken()) {
        queEuTenhoUmUsuarioCMSCadastrado();
        euEnvioARequisicaoDeLoginComAsCredenciaisDoUsuario();
    }
}
```

### Tratamento de Erros

O sistema implementa tratamento de erros para:
- Tokens expirados
- Tokens inválidos
- Falhas de autenticação
- Erros de conexão
- Timeouts
- Validações de dados

### Boas Práticas

1. **Gerenciamento de Sessão**
   - Sempre verificar a validade do token antes de operações
   - Implementar renovação automática quando necessário
   - Limpar tokens ao finalizar sessões
   - Monitoramento de uso

2. **Segurança**
   - Não expor tokens em logs
   - Implementar timeout adequado
   - Validar tokens em todas as requisições
   - Criptografia de dados sensíveis

3. **Testes**
   - Testar cenários de expiração
   - Validar comportamento com tokens inválidos
   - Verificar limpeza de sessão
   - Cobertura de casos de erro

### Exemplos de Uso

```java
// Login e obtenção de token
usuarioCmsController.realizarLogin("usuario@email.com", "senha123");
String token = usuarioCmsController.obterToken();

// Verificação de token válido
if (TokenManager.hasValidToken()) {
    // Realizar operações autenticadas
}

// Limpeza de sessão
TokenManager.remove();
```

# Tutorial de Uso

## 1. Configurando o Ambiente

### 1.1 Configuração Inicial
```java
// Configuração básica do projeto
public class ConfiguracaoInicial {
    @Before
    public void setup() {
        // Configurar base URI
        RestAssured.baseURI = "https://api.exemplo.com";
        
        // Configurar headers padrão
        RestAssured.defaultHeaders = new Headers(
            new Header("Content-Type", "application/json"),
            new Header("Accept", "application/json")
        );
    }
}
```

## 2. Usando o UsuarioCms

### 2.1 Criando um Novo Usuário
```java
// Exemplo de criação de usuário
public class ExemploUsuarioCms {
    @Test
    public void criarNovoUsuario() {
        // Criar objeto de usuário
        UsuarioCms usuario = UsuarioCms.builder()
            .nomeCompleto("João Silva")
            .nomeUsuario("joao.silva")
            .email("joao.silva@exemplo.com")
            .senha("Senha@123")
            .build();

        // Enviar requisição de cadastro
        Response response = usuarioCmsController.cadastrarNovoUsuario(usuario);
        
        // Validar resposta
        response.then()
            .statusCode(201)
            .body("nomeCompleto", equalTo("João Silva"));
    }
}
```

### 2.2 Realizando Login
```java
// Exemplo de login
public class ExemploLogin {
    @Test
    public void realizarLogin() {
        // Realizar login
        Response response = usuarioCmsController.realizarLogin(
            "joao.silva@exemplo.com",
            "Senha@123"
        );
        
        // Validar token
        String token = response.jsonPath().getString("token");
        assertNotNull(token);
        
        // Salvar token
        TokenManager.setToken(token);
    }
}
```

## 3. Gerenciando Tokens

### 3.1 Usando o TokenManager
```java
// Exemplo de uso do TokenManager
public class ExemploTokenManager {
    @Test
    public void gerenciarToken() {
        // Verificar se existe token válido
        if (!TokenManager.hasValidToken()) {
            // Realizar login se não houver token
            realizarLogin();
        }
        
        // Obter token atual
        String token = TokenManager.getToken();
        
        // Usar token em requisição
        Response response = given()
            .header("Authorization", "Bearer " + token)
            .get("/api/usuarios");
            
        // Validar resposta
        response.then().statusCode(200);
    }
}
```

## 4. Usando o ErrorManager

### 4.1 Tratando Erros
```java
// Exemplo de tratamento de erros
public class ExemploErrorManager {
    @Test
    public void tratarErro() {
        try {
            // Tentar operação que pode falhar
            usuarioCmsController.consultarUsuarioPorId("id_invalido");
        } catch (Exception e) {
            // Registrar erro
            ErrorManager.registrarErro("Erro ao consultar usuário", e);
            
            // Validar mensagem de erro
            assertTrue(ErrorManager.getUltimoErro()
                .contains("Usuário não encontrado"));
        }
    }
}
```

## 5. Usando Utilitários

### 5.1 Manipulando JSON
```java
// Exemplo de uso do JsonUtils
public class ExemploJsonUtils {
    @Test
    public void manipularJson() {
        // Criar objeto
        UsuarioCms usuario = new UsuarioCms();
        usuario.setNomeCompleto("Maria Santos");
        
        // Converter para JSON
        String json = JsonUtils.toJson(usuario);
        
        // Validar estrutura
        assertTrue(JsonUtils.isValidJson(json));
        
        // Converter de volta para objeto
        UsuarioCms usuarioConvertido = JsonUtils.fromJson(json, UsuarioCms.class);
        assertEquals("Maria Santos", usuarioConvertido.getNomeCompleto());
    }
}
```

### 5.2 Gerando Dados Fake
```java
// Exemplo de uso do FakerApiData
public class ExemploFakerApiData {
    @Test
    public void gerarDadosFake() {
        // Gerar usuário fake
        UsuarioCms usuarioFake = FakerApiData.gerarUsuarioCms();
        
        // Validar dados gerados
        assertNotNull(usuarioFake.getEmail());
        assertTrue(usuarioFake.getEmail().contains("@"));
        assertTrue(usuarioFake.getSenha().length() >= 8);
    }
}
```

## 6. Implementando Steps do Cucumber

### 6.1 Criando Steps
```java
// Exemplo de implementação de steps
public class ExemploSteps {
    @Given("que eu tenho um usuário CMS cadastrado")
    public void queEuTenhoUmUsuarioCMSCadastrado() {
        // Criar usuário
        UsuarioCms usuario = FakerApiData.gerarUsuarioCms();
        
        // Cadastrar usuário
        Response response = usuarioCmsController.cadastrarNovoUsuario(usuario);
        
        // Salvar dados para uso posterior
        ScenarioContext.set("usuario", usuario);
    }
    
    @When("eu envio a requisição de login com as credenciais do usuário")
    public void euEnvioARequisicaoDeLogin() {
        // Obter usuário do contexto
        UsuarioCms usuario = ScenarioContext.get("usuario");
        
        // Realizar login
        Response response = usuarioCmsController.realizarLogin(
            usuario.getEmail(),
            usuario.getSenha()
        );
        
        // Salvar resposta
        ScenarioContext.set("response", response);
    }
    
    @Then("o login é realizado com sucesso")
    public void oLoginERealizadoComSucesso() {
        // Obter resposta do contexto
        Response response = ScenarioContext.get("response");
        
        // Validar resposta
        response.then()
            .statusCode(200)
            .body("token", notNullValue());
    }
}
```

## 7. Exemplo Completo de Fluxo

### 7.1 Fluxo Completo de Usuário
```java
// Exemplo de fluxo completo
public class ExemploFluxoCompleto {
    @Test
    public void fluxoCompletoUsuario() {
        // 1. Criar usuário
        UsuarioCms usuario = FakerApiData.gerarUsuarioCms();
        Response responseCadastro = usuarioCmsController.cadastrarNovoUsuario(usuario);
        String idUsuario = responseCadastro.jsonPath().getString("id");
        
        // 2. Realizar login
        Response responseLogin = usuarioCmsController.realizarLogin(
            usuario.getEmail(),
            usuario.getSenha()
        );
        TokenManager.setToken(responseLogin.jsonPath().getString("token"));
        
        // 3. Consultar usuário
        Response responseConsulta = usuarioCmsController.consultarUsuarioPorId(idUsuario);
        responseConsulta.then()
            .statusCode(200)
            .body("nomeCompleto", equalTo(usuario.getNomeCompleto()));
        
        // 4. Atualizar usuário
        usuario.setNomeCompleto("Nome Atualizado");
        Response responseAtualizacao = usuarioCmsController.atualizarUsuarioPorId(
            idUsuario,
            usuario
        );
        responseAtualizacao.then().statusCode(200);
        
        // 5. Excluir usuário
        Response responseExclusao = usuarioCmsController.excluirUsuarioPorId(idUsuario);
        responseExclusao.then().statusCode(204);
    }
}
```

Estes exemplos demonstram o uso prático das principais classes do projeto. Cada exemplo inclui:
- Configuração necessária
- Código de exemplo
- Validações básicas
- Tratamento de erros
- Boas práticas de uso

Para mais detalhes sobre cada classe, consulte a documentação específica na seção anterior. 