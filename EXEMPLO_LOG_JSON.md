# Exemplo de Log JSON no Allure

## Como usar o LogFormatter com JSON

### 1. Log Simples (texto)
```java
LogFormatter.logStep("Enviando requisição de cadastro de usuário");
```

### 2. Log JSON Estruturado
```java
// Exemplo com dados de usuário
String userData = """
{
    "nomeCompleto": "Jerod Kohler",
    "nomeUsuario": "jerod_kohler", 
    "email": "kohler.jerod@hotmail.com",
    "id": "e08bf75b-1cc7-4eaa-8eaf-2a8c3a5e5fac",
    "dataCriacao": "2025-07-02T00:45:00.000Z"
}""";

LogFormatter.logStepJson("Cadastro de Usuário", userData);
```

### 3. Log JSON Simples (Recomendado)
```java
// Exemplo com dados de usuário formatado
String userData = """
{
    "nomeCompleto": "Edgar Prosacco",
    "nomeUsuario": "edgar_prosacco",
    "email": "prosacco.edgar@yahoo.com",
    "senha": "P@ssword46"
}""";

LogFormatter.logJson(userData);
```

### 3. Como vai aparecer no relatório Allure

**Log Simples:**
```
[02-01-2025 10:30:45.123] STEP: Enviando requisição de cadastro de usuário
```

**Log JSON Estruturado:**
```json
{
  "timestamp": "02-01-2025 10:30:45.123",
  "step": "Cadastro de Usuário",
  "data": {
    "nomeCompleto": "Jerod Kohler",
    "nomeUsuario": "jerod_kohler",
    "email": "kohler.jerod@hotmail.com",
    "id": "e08bf75b-1cc7-4eaa-8eaf-2a8c3a5e5fac",
    "dataCriacao": "2025-07-02T00:45:00.000Z"
  }
}
```

**Log JSON Simples:**
```json
{
  "nomeCompleto": "Edgar Prosacco",
  "nomeUsuario": "edgar_prosacco",
  "email": "prosacco.edgar@yahoo.com",
  "senha": "P@ssword46"
}
```

### 4. Como vai aparecer no console e relatório (RESULTADO REAL)

**Antes (JSON concatenado):**
```
[01-07-2025 21:50:24.985] STEP: Enviando requisição de cadastro de usuário{"nomeCompleto":"Devante Sanford","nomeUsuario":"devante_sanford","email":"sanford.devante@hotmail.com","id":"b298c6a1-8fe4-43d0-90b5-ad4890997d61","dataCriacao":"2025-07-02T00:55:23.000Z"}
```

**Depois (JSON formatado):**
```
[01-07-2025 21:50:24.985] STEP: Enviando requisição de cadastro de usuário

{
  "nomeCompleto": "Devante Sanford",
  "nomeUsuario": "devante_sanford",
  "email": "sanford.devante@hotmail.com",
  "id": "b298c6a1-8fe4-43d0-90b5-ad4890997d61",
  "dataCriacao": "2025-07-02T00:55:23.000Z"
}
```

### 5. Métodos disponíveis no LogFormatter

- `logStep(String message)` - Log simples com timestamp
- `logJson(String data)` - **Log JSON simples formatado (RECOMENDADO)**
- `logStepJson(String stepName, String data)` - Log JSON estruturado com metadata
- `logRequestJson(String method, String url, String body)` - Log de requisição em JSON
- `logResponseJson(String statusCode, String body)` - Log de resposta em JSON

### 6. Exemplo prático nos Controllers (Automático)

```java
// No UsuarioController.cadastrarNovoUsuario()
String body = response.asString();

// Log do step simples
LogFormatter.logStep("Enviando requisição de cadastro de usuário");

// Log do body da resposta formatado em JSON (AUTOMÁTICO)
LogFormatter.logJson(body);
```

### 7. Como funciona nos Steps

```java
@Step("Envio requisição de registro de usuário CMS")
@When("que envio uma requisição de registro de usuario CMS")
public void queEnvioUmaRequisicaoDeRegistroUsuarioCMS() {
    // O log JSON será gerado automaticamente no controller
    usuarioController.cadastrarNovoUsuario();
}
```

### 8. Benefícios

- **Legibilidade**: JSON formatado é mais fácil de ler
- **Estruturação**: Dados organizados em campos específicos
- **Timestamp**: Cada log tem timestamp automático
- **Attachments**: Aparecem como anexos no relatório Allure
- **Filtros**: Podem ser filtrados por tipo no relatório
- **Automático**: Não precisa adicionar logs manualmente nos steps 