# Tutorial de Onboarding e Desenvolvimento de Testes Automatizados de API

## 1. Visão Geral da Estrutura do Projeto

O projeto segue uma arquitetura modular para automação de testes de API utilizando Java, Cucumber, RestAssured e boas práticas de Clean Code.

```
src/test/java/org/br/com/testes/
├── controllers/   # Lógica de requisições HTTP
├── manager/       # Gerenciamento de dados compartilhados
├── model/         # Modelos de request/response (DTOs)
├── steps/         # Implementação dos steps do Cucumber
├── utils/         # Utilitários (ex: geração de dados fake)
└── ExecucaoTestes.java # Runner do Cucumber
src/test/resources/features/ # Features Gherkin
```

---

## 2. Passo a Passo para Organização e Início do Projeto

### A. Preparação Inicial

1. **Clone o repositório e instale as dependências:**
   ```bash
   git clone <repo-url>
   cd AUTOMACAO-REST-API
   mvn clean install
   ```
2. **Garanta que a API esteja rodando em `http://localhost:3000`.**
3. **Runner dos testes:**
   - O arquivo `ExecucaoTestes.java` é o ponto de entrada dos testes.
   - Exemplo:
     ```java
     @RunWith(Cucumber.class)
     @CucumberOptions(
         features = "src/test/resources/features",
         glue = "org.br.com.testes.steps",
         tags = "@FuncionalideUsuario",
         plugin = {"pretty", "html:target/cucumber-reports/cucumber.html"}
     )
     public class ExecucaoTestes {}
     ```

---

### B. Criação de uma Nova Feature

1. **Crie um arquivo `.feature` em `src/test/resources/features/`**
   - Nome descritivo, sem acentos: `cadastro_usuario.feature`
   - Prefixos do Cucumber em inglês: `Feature:`, `Scenario:`, `Given`, etc.
   - Exemplo:
     ```gherkin
     @All @FuncionalideUsuario
     Feature: Validar funcionalidades de usuario
         Como um usuario do sistema
         Eu quero cadastrar, editar, listar e excluir usuarios
         Para que eu possa gerenciar os usuarios do sistema

       @CT-1001
       Scenario: Cadastrar usuario valido para os testes
         Given que cadastro um usuario valido para os testes
         Then deve retornar usuario com status code 201 e mensagem 'Cadastro realizado com sucesso'
     ```

---

### C. Implementação dos Steps

- Crie uma classe em `src/test/java/org/br/com/testes/steps/`.
- Cada método deve delegar a chamada para o controller correspondente.
- Exemplo:
  ```java
  public class UsuarioSteps {
      private final UsuarioController usuarioController = new UsuarioController();

      @Given("que cadastro um usuario valido para os testes")
      public void queCadastroUmUsuarioValidoParaOsTestes() {
          usuarioController.cadastrarNovoUsuario();
      }

      @Then("deve retornar usuario com status code {int} e mensagem {string}")
      public void deveRetornarUsuarioComStatusCodeEMensagem(int statusCode, String mensagem) {
          usuarioController.validarStatusCodeEMensagem(statusCode, mensagem);
      }
  }
  ```

---

### D. Implementação dos Controllers

- Local: `src/test/java/org/br/com/testes/controllers/usuarios/UsuarioController.java`
- Responsável por toda a lógica de requisição e validação.
- Exemplo:
  ```java
  public class UsuarioController {
      private Response response;
      private static final String BASE_URL = "http://localhost:3000";
      private static final String ENDPOINT_USUARIOS = "/usuarios";

      public void cadastrarNovoUsuario() {
          UsuarioRequest usuarioRequest = UsuarioRequest.builder()
              .nome(FakerApiData.gerarUsuarioFake().getNome())
              .email(FakerApiData.gerarUsuarioFake().getEmail())
              .password(FakerApiData.gerarUsuarioFake().getSenha())
              .administrador(FakerApiData.gerarUsuarioFake().getAdministrador())
              .build();

          response = given()
              .contentType(ContentType.JSON)
              .body(usuarioRequest)
              .when()
              .post(BASE_URL + ENDPOINT_USUARIOS)
              .then()
              .extract().response();
      }

      public void validarStatusCodeEMensagem(int statusCode, String mensagem) {
          response.then().statusCode(statusCode);
          String mensagemResposta = response.jsonPath().getString("message");
          assertTrue(mensagemResposta.toLowerCase().contains(mensagem.toLowerCase()));
      }
  }
  ```

---

### E. Models (DTOs)

- Local: `src/test/java/org/br/com/testes/model/usuario/UsuarioRequest.java`
- Exemplo:
  ```java
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class UsuarioRequest {
      private String nome;
      private String email;
      private String password;
      private String administrador;
  }
  ```

---

### F. Managers (Gerenciamento de Dados Compartilhados)

- Local: `src/test/java/org/br/com/testes/manager/UsuarioManager.java`
- Exemplo:
  ```java
  public class UsuarioManager {
      private static final ThreadLocal<String> email = new ThreadLocal<>();
      private static final ThreadLocal<String> password = new ThreadLocal<>();
      private static final ThreadLocal<String> id = new ThreadLocal<>();

      public static String getEmail() { return email.get(); }
      public static void setEmail(String tk) { email.set(tk); }
      // ... demais métodos
  }
  ```

---

### G. Utilitários (Geração de Dados Dinâmicos)

- Local: `src/test/java/org/br/com/testes/utils/FakerApiData.java`
- Exemplo:
  ```java
  @Data
  @Builder
  public class FakerApiData {
      private static final Faker faker = new Faker(new Locale("pt-BR"));
      private String nome;
      private String email;
      private String senha;
      private String administrador;
      // ...
      public static FakerApiData gerarUsuarioFake() {
          return FakerApiData.builder()
              .nome(faker.name().fullName())
              .email(faker.internet().emailAddress())
              .senha("@password" + faker.number().randomDigit())
              .administrador("true")
              .build();
      }
  }
  ```

---

## 3. Boas Práticas e Regras de Ouro

- Nunca duplique steps. Reutilize steps genéricos, mas crie steps específicos quando necessário para clareza.
- Não use Map como parâmetro em steps. Prefira parâmetros diretos.
- Não coloque lógica de negócio nos steps. Toda lógica deve estar nos controllers.
- Sempre valide status code e mensagens.
- Mantenha logs claros e sem acentos.
- Documente alterações relevantes.

---

## 4. Fluxo Resumido para Nova Feature

1. Crie o arquivo de feature.
2. Descreva os cenários.
3. Implemente os steps.
4. Implemente métodos no controller/model.
5. Rode os testes e analise os relatórios.

---

## 5. Exemplo Completo de Fluxo

### Feature (Gherkin)
```gherkin
@All @FuncionalideUsuario
Feature: Validar funcionalidades de usuario
  Scenario: Cadastrar usuario valido para os testes
    Given que cadastro um usuario valido para os testes
    Then deve retornar usuario com status code 201 e mensagem 'Cadastro realizado com sucesso'
```

### Step
```java
@Given("que cadastro um usuario valido para os testes")
public void queCadastroUmUsuarioValidoParaOsTestes() {
    usuarioController.cadastrarNovoUsuario();
}
```

### Controller
```java
public void cadastrarNovoUsuario() {
    UsuarioRequest usuarioRequest = UsuarioRequest.builder()
        .nome(FakerApiData.gerarUsuarioFake().getNome())
        .email(FakerApiData.gerarUsuarioFake().getEmail())
        .password(FakerApiData.gerarUsuarioFake().getSenha())
        .administrador(FakerApiData.gerarUsuarioFake().getAdministrador())
        .build();
    response = given()
        .contentType(ContentType.JSON)
        .body(usuarioRequest)
        .when()
        .post(BASE_URL + ENDPOINT_USUARIOS)
        .then()
        .extract().response();
}
```

### Model
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}
```

### Manager
```java
public class UsuarioManager {
    private static final ThreadLocal<String> email = new ThreadLocal<>();
    public static String getEmail() { return email.get(); }
    public static void setEmail(String tk) { email.set(tk); }
}
```

### Utilitário
```java
public static FakerApiData gerarUsuarioFake() {
    return FakerApiData.builder()
        .nome(faker.name().fullName())
        .email(faker.internet().emailAddress())
        .senha("@password" + faker.number().randomDigit())
        .administrador("true")
        .build();
}
```

---

**Dúvidas? Consulte sempre as regras do time e mantenha a documentação atualizada!** 