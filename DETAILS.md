# Detalhes das Classes

## Controllers

### Usuários
- **UsuarioController.java**
  - Responsável por gerenciar as requisições relacionadas a usuários
  - Métodos implementados:
    - `cadastrarNovoUsuario`: Cadastra um novo usuário usando dados do Faker
    - `realizarLogin`: Realiza autenticação e armazena o token
    - `listarUsuariosComAutenticacao`: Lista todos os usuários
    - `consultarUsuarioPorId`: Busca usuário por ID
    - `atualizarNomeUsuario`: Atualiza apenas o nome de usuário
    - `excluirUsuarioPorId`: Remove um usuário
    - `validarStatusCode`: Valida o código de status da resposta
    - `validarNomeUsuario`: Valida se o usuário está na lista retornada

## Manager

### TokenManager.java
- Gerenciamento de tokens de autenticação
- Armazenamento thread-safe usando ThreadLocal
- Métodos:
  - `getToken()`: Obtém o token atual
  - `setToken(String token)`: Armazena um novo token
  - `remove()`: Remove o token armazenado

### UsuarioManager.java
- Gerenciamento de dados do usuário
- Armazenamento thread-safe usando ThreadLocal
- Métodos:
  - `getEmailUsuario()`: Obtém o email do usuário
  - `setEmailUsuario(String email)`: Armazena o email
  - `getSenhaUsuario()`: Obtém a senha
  - `setSenhaUsuario(String senha)`: Armazena a senha
  - `getIdUsuario()`: Obtém o ID do usuário
  - `setIdUsuario(String id)`: Armazena o ID
  - `remove()`: Limpa todos os dados armazenados

## Model

### Usuario.java
- Modelo para representação de usuários
- Atributos:
  - nomeCompleto: Nome completo do usuário
  - nomeUsuario: Nome de usuário para login
  - email: Email do usuário
  - senha: Senha do usuário
- Anotações Lombok para redução de boilerplate

## Steps

### UsuarioSteps.java
- Implementação dos passos do Cucumber para usuários
- Cenários:
  - Cadastro de usuário
  - Login
  - Listagem
  - Consulta
  - Atualização
  - Exclusão
- Integração com TokenManager e UsuarioManager

## Tokens

### GerarToken.java
- Implementação da geração de tokens
- Validação e renovação de tokens
- Controle de expiração
- Armazenamento seguro

### GerarTokenRequest.java
- Modelo para requisição de geração de token
- Estrutura de dados para autenticação
- Validação de campos obrigatórios

## Utils

### FakerApiData.java
- Geração de dados fake para testes
- Utiliza a biblioteca Faker
- Métodos:
  - `gerarUsuarioFake()`: Gera um usuário com dados aleatórios
  - `gerarSenhaValida()`: Gera uma senha que atende aos requisitos

# Detalhes da Implementação

## Gerenciamento de Tokens

### Visão Geral
O sistema implementa um gerenciamento robusto de tokens de autenticação utilizando o `TokenManager`. Esta implementação oferece uma solução segura e eficiente para o gerenciamento de tokens.

### Componentes Principais

#### TokenManager
- Gerencia tokens de autenticação de forma thread-safe
- Implementa verificação de expiração de tokens
- Armazena tokens temporariamente em memória usando ThreadLocal
- Validação automática de tokens

#### UsuarioController
- Integrado com TokenManager para gerenciamento de tokens
- Realiza operações CRUD com autenticação automática
- Gerencia sessões de usuário de forma segura
- Tratamento de erros de autenticação

### Fluxo de Autenticação

1. **Login**
   ```java
   usuarioController.realizarLogin();
   ```
   - Realiza login com credenciais do UsuarioManager
   - Token é automaticamente salvo no TokenManager
   - Retorna status 200 em caso de sucesso

2. **Operações Autenticadas**
   ```java
   // Listar usuários
   usuarioController.listarUsuariosComAutenticacao();
   
   // Consultar usuário
   usuarioController.consultarUsuarioPorId();
   
   // Atualizar usuário
   usuarioController.atualizarNomeUsuario();
   
   // Excluir usuário
   usuarioController.excluirUsuarioPorId();
   ```
   - Todas as operações usam o token do TokenManager
   - Validação automática de expiração do token
   - Tratamento de erros de autenticação

### Benefícios da Implementação

1. **Segurança**
   - Tokens não são armazenados em arquivos estáticos
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
        queEuTenhoUmUsuarioCadastrado();
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
usuarioController.realizarLogin();
String token = TokenManager.getToken();

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
        RestAssured.baseURI = "http://localhost:3000";
        
        // Configurar headers padrão
        RestAssured.defaultHeaders = new Headers(
            new Header("Content-Type", "application/json"),
            new Header("Accept", "application/json")
        );
    }
}
```

## 2. Usando o UsuarioController

### 2.1 Criando um Novo Usuário
```java
// Exemplo de criação de usuário
public class ExemploUsuario {
    @Test
    public void criarNovoUsuario() {
        // Criar e cadastrar usuário
        usuarioController.cadastrarNovoUsuario();
        
        // Validar resposta
        usuarioController.validarStatusCode(201);
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
        usuarioController.realizarLogin();
        
        // Validar token
        usuarioController.validarStatusCode(200);
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
            usuarioController.realizarLogin();
        }
        
        // Obter token atual
        String token = TokenManager.getToken();
        
        // Usar token em requisição
        usuarioController.listarUsuariosComAutenticacao();
    }
}
```

## 4. Usando o FakerApiData

### 4.1 Gerando Dados Fake
```java
// Exemplo de uso do FakerApiData
public class ExemploFakerApiData {
    @Test
    public void gerarDadosFake() {
        // Gerar usuário fake
        Usuario usuarioFake = FakerApiData.gerarUsuarioFake();
        
        // Validar dados gerados
        assertNotNull(usuarioFake.getEmail());
        assertTrue(usuarioFake.getEmail().contains("@"));
        assertTrue(usuarioFake.getSenha().length() >= 8);
    }
}
```

## 5. Implementando Steps do Cucumber

### 5.1 Criando Steps
```java
// Exemplo de implementação de steps
public class ExemploSteps {
    @Given("que eu tenho um usuário cadastrado")
    public void queEuTenhoUmUsuarioCadastrado() {
        usuarioController.cadastrarNovoUsuario();
    }
    
    @When("eu envio a requisição de login com as credenciais do usuário")
    public void euEnvioARequisicaoDeLogin() {
        usuarioController.realizarLogin();
    }
    
    @Then("o login é realizado com sucesso")
    public void oLoginERealizadoComSucesso() {
        usuarioController.validarStatusCode(200);
    }
}
```

## 6. Exemplo Completo de Fluxo

### 6.1 Fluxo Completo de Usuário
```java
// Exemplo de fluxo completo
public class ExemploFluxoCompleto {
    @Test
    public void fluxoCompletoUsuario() {
        // 1. Criar usuário
        usuarioController.cadastrarNovoUsuario();
        
        // 2. Realizar login
        usuarioController.realizarLogin();
        
        // 3. Consultar usuário
        usuarioController.consultarUsuarioPorId();
        
        // 4. Atualizar usuário
        usuarioController.atualizarNomeUsuario();
        
        // 5. Excluir usuário
        usuarioController.excluirUsuarioPorId();
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