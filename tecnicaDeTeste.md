# Técnicas de Teste Automatizado - REST API

## 📋 Índice
1. [Técnicas Identificadas nas Features](#técnicas-identificadas-nas-features)
2. [Exemplos Práticos de Implementação](#exemplos-práticos-de-implementação)
3. [Técnicas Avançadas Recomendadas](#técnicas-avançadas-recomendadas)
4. [Padrões de Nomenclatura](#padrões-de-nomenclatura)
5. [Boas Práticas](#boas-práticas)

---

## 🎯 Categorias Fundamentais de Teste

### **1. Teste de Caixa Preta (Black Box Testing)**
**Definição:** Teste baseado na especificação funcional, sem conhecimento da implementação interna.

**Características:**
- Foco na funcionalidade e comportamento externo
- Testa entradas e saídas esperadas
- Independente da implementação

**Exemplos no Projeto:**
```gherkin
@CT-3001 @CaixaPretaTransferencia
Scenario: Validar comportamento da API de transferencia sem conhecer implementacao
  Given que envio uma requisição POST para /transferencias
  When o payload contem dados validos de transferencia
  Then a API retorna status code 201
  And a resposta contem o ID da transferencia criada
  And o saldo das contas foi atualizado corretamente
```

### **2. Teste de Caixa Branca (White Box Testing)**
**Definição:** Teste baseado na estrutura interna do código, conhecendo a implementação.

**Características:**
- Cobertura de código (statements, branches, paths)
- Teste de lógica interna
- Validação de fluxos de execução

**Exemplos no Projeto:**
```gherkin
@CT-3002 @CaixaBrancaValidacao
Scenario: Validar todos os caminhos de validacao de transferencia
  Given que conheco a logica de validacao da API
  When testo o caminho de validacao de saldo insuficiente
  Then valido que a excecao SaldoInsuficienteException e lancada
  And valido que o rollback da transacao foi executado
  And valido que o log de erro foi registrado
```

### **3. Teste de Fumaça (Smoke Testing)**
**Definição:** Teste básico para verificar se as funcionalidades críticas estão funcionando.

**Características:**
- Testes rápidos e essenciais
- Foco em funcionalidades críticas
- Execução antes de testes mais profundos

**Exemplos no Projeto:**
```gherkin
@CT-3003 @SmokeTest
Scenario: Validar funcionalidades criticas do sistema
  Given que o sistema esta online
  When realizo login como admin
  And consulto a lista de contas
  And realizo uma transferencia simples
  Then valido que todas as operacoes basicas funcionam
  And valido que o sistema esta estavel
```

### **4. Teste Funcional**
**Definição:** Teste que valida se o sistema atende aos requisitos funcionais especificados.

**Características:**
- Baseado em casos de uso
- Validação de regras de negócio
- Teste de cenários reais

**Exemplos no Projeto:**
```gherkin
@CT-3004 @TesteFuncional
Scenario: Validar fluxo completo de transferencia bancaria
  Given que sou um cliente do banco
  And tenho saldo suficiente na conta origem
  And a conta destino esta ativa
  When realizo uma transferencia entre minhas contas
  Then valido que a transferencia foi processada
  And valido que os saldos foram atualizados
  And valido que o comprovante foi gerado
  And valido que a notificacao foi enviada
```

### **5. Teste Unitário**
**Definição:** Teste de unidades individuais de código (métodos, classes, funções).

**Características:**
- Teste isolado de componentes
- Execução rápida
- Cobertura de código detalhada

**Exemplos no Projeto:**
```java
@Test
@DisplayName("CT-3005: Validar calculo de taxa de transferencia")
public void testCalcularTaxaTransferencia() {
    // Arrange
    TransferenciaService service = new TransferenciaService();
    double valor = 1000.00;
    
    // Act
    double taxa = service.calcularTaxa(valor);
    
    // Assert
    assertEquals(5.00, taxa, 0.01);
}

@Test
@DisplayName("CT-3006: Validar validacao de CPF")
public void testValidarCPF() {
    // Arrange
    ValidacaoService service = new ValidacaoService();
    String cpfValido = "12345678901";
    String cpfInvalido = "11111111111";
    
    // Act & Assert
    assertTrue(service.validarCPF(cpfValido));
    assertFalse(service.validarCPF(cpfInvalido));
}
```

### **6. Teste Regressivo**
**Definição:** Teste para garantir que funcionalidades existentes continuam funcionando após mudanças.

**Características:**
- Reexecução de testes existentes
- Detecção de regressões
- Garantia de estabilidade

**Exemplos no Projeto:**
```gherkin
@CT-3007 @TesteRegressivo
Scenario: Validar que funcionalidades existentes nao foram quebradas
  Given que o sistema foi atualizado
  When executo todos os testes de transferencia existentes
  Then valido que CT-1001 ainda funciona (criar transferencia)
  And valido que CT-1002 ainda funciona (listar transferencias)
  And valido que CT-1003 ainda funciona (consultar transferencia)
  And valido que CT-1004 ainda funciona (atualizar transferencia)
  And valido que CT-1005 ainda funciona (atualizar parcialmente)
  And valido que CT-1006 ainda funciona (remover transferencia)
```

---

## 🎯 Técnicas de Design de Teste Identificadas nas Features

### 1. **Data-Driven Testing (Teste Orientado a Dados)**
**Identificado em:** `transferencias.feature` - Scenario Outline

**Características:**
- Uso de `Scenario Outline` com `Examples`
- Testes parametrizados com diferentes conjuntos de dados
- Reutilização de cenários com múltiplos valores

**Exemplo Identificado:**
```gherkin
@CT-1011 @TransferenciaAtualizarDados
Scenario Outline: Atualizar todos os dados de uma transferência
  Given que a transferencia de R$ 80.00 foi realizada entre a conta de origem "<CONTA_ORIGEM>" e a conta de destino "<CONTA_DESTINO>"
  When a transferencia e atualizada com novos dados valor R$ 120.00 conta de destino "<CONTA_DESTINO_NOVO>"
  Then todos os dados da transferencia sao atualizados com sucesso

Examples:
  | CONTA_ORIGEM             | CONTA_DESTINO            | CONTA_DESTINO_NOVO       |
  | 686fa208cbdb4375dbb8ed47 | 686fa208cbdb4375dbb8ed48 | 686fa208cbdb4375dbb8ed46 |
```

### 2. **Boundary Value Analysis (Análise de Valores Limite)**
**Identificado em:** `transferencias.feature` - CT-1007, CT-1010

**Características:**
- Teste de valores mínimos e máximos
- Validação de regras de negócio
- Teste de limites aceitáveis

**Exemplos Identificados:**
- Valor mínimo de transferência (R$ 10.00)
- Valores divergentes (R$ 1.00)
- Valores dentro do limite (R$ 30.00)

### 3. **Positive/Negative Testing (Teste Positivo/Negativo)**
**Identificado em:** `transferencias.feature` - CT-1001 vs CT-1007

**Características:**
- Testes de cenários de sucesso
- Testes de cenários de erro
- Validação de mensagens de erro

**Exemplos Identificados:**
- **Positivo:** Transferência válida retorna 201
- **Negativo:** Transferência inválida retorna 422

### 4. **State-Based Testing (Teste Baseado em Estado)**
**Identificado em:** `transferencias.feature` - CT-1013

**Características:**
- Validação de mudanças de estado
- Verificação de reversão de dados
- Teste de integridade de dados

**Exemplo Identificado:**
```gherkin
@CT-1013 @TransferenciaRemover
Scenario Outline: Remover uma transferência e reverter saldos
  Given que a transferencia de R$ 90.00 foi realizada entre a conta de origem "<CONTA_ORIGEM>" e a conta de destino "<CONTA_DESTINO>"
  When a transferencia e removida
  Then o saldo da conta de origem e da conta de destino e revertido
```

### 5. **Authentication Testing (Teste de Autenticação)**
**Identificado em:** `login.feature`, `transferencias.feature` - CT-1008

**Características:**
- Validação de tokens de acesso
- Teste de diferentes níveis de autenticação
- Verificação de autorização

### 6. **CRUD Testing (Create, Read, Update, Delete)**
**Identificado em:** `transferencias.feature` - CT-1001 a CT-1006

**Características:**
- Teste completo do ciclo de vida dos dados
- Validação de operações básicas
- Verificação de integridade

---

## 🚀 Exemplos Práticos de Implementação

### 1. **Data-Driven Testing - Usuários**

```gherkin
@CT-2001 @UsuarioCadastroMultiplo
Scenario Outline: Cadastrar usuários com diferentes perfis
  Given que estou na pagina de cadastro de usuario
  When preencho o formulario com os dados:
    | Nome Completo | <NOME>           |
    | Email         | <EMAIL>          |
    | Senha         | <SENHA>          |
    | Perfil        | <PERFIL>         |
  And clico no botao 'Cadastrar'
  Then valido que o usuario foi cadastrado com sucesso
  And valido que o perfil foi definido como <PERFIL>

Examples:
  | NOME           | EMAIL                    | SENHA      | PERFIL |
  | Joao Silva     | joao.silva@email.com     | Senha123!  | ADMIN  |
  | Maria Santos   | maria.santos@email.com   | Senha456!  | USER   |
  | Pedro Oliveira | pedro.oliveira@email.com | Senha789!  | USER   |
```

### 2. **Boundary Value Analysis - Validação de Campos**

```gherkin
@CT-2002 @ValidacaoCamposUsuario
Scenario Outline: Validar limites de campos do formulario de usuario
  Given que estou na pagina de cadastro de usuario
  When preencho o campo '<CAMPO>' com o valor '<VALOR>'
  And clico no botao 'Cadastrar'
  Then valido a mensagem de erro '<MENSAGEM_ESPERADA>'

Examples:
  | CAMPO      | VALOR                    | MENSAGEM_ESPERADA                    |
  | Nome       | A                        | Nome deve ter pelo menos 3 caracteres |
  | Nome       | Joao Silva da Costa      | Nome deve ter no maximo 50 caracteres |
  | Email      | email_invalido           | Email deve ter formato valido        |
  | Email      | a@b.c                    | Email deve ter formato valido        |
  | Senha      | 123                      | Senha deve ter pelo menos 8 caracteres |
  | Senha      | 123456789012345678901234 | Senha deve ter no maximo 20 caracteres |
```

### 3. **State-Based Testing - Fluxo de Pedidos**

```gherkin
@CT-2003 @FluxoPedidoCompleto
Scenario: Validar fluxo completo de pedido com mudancas de estado
  Given que estou logado como cliente
  And tenho produtos no carrinho
  When realizo o checkout do pedido
  Then valido que o pedido foi criado com status 'PENDENTE'
  
  When o pagamento e processado com sucesso
  Then valido que o status do pedido mudou para 'PAGO'
  
  When o pedido e enviado para producao
  Then valido que o status do pedido mudou para 'EM_PRODUCAO'
  
  When o pedido e enviado
  Then valido que o status do pedido mudou para 'ENVIADO'
  
  When o pedido e entregue
  Then valido que o status do pedido mudou para 'ENTREGUE'
```

### 4. **Performance Testing - Carga de Dados**

```gherkin
@CT-2004 @TesteCargaTransferencias
Scenario Outline: Validar performance com diferentes volumes de transferencias
  Given que existem <QUANTIDADE> transferencias no sistema
  When consulto a lista de transferencias
  Then valido que a resposta foi retornada em menos de <TEMPO_MAXIMO> segundos
  And valido que todas as <QUANTIDADE> transferencias foram retornadas

Examples:
  | QUANTIDADE | TEMPO_MAXIMO |
  | 10         | 1            |
  | 100        | 2            |
  | 1000       | 5            |
  | 10000      | 10           |
```

### 5. **Security Testing - Validação de Acesso**

```gherkin
@CT-2005 @TesteSegurancaAcesso
Scenario Outline: Validar controle de acesso por perfil de usuario
  Given que estou logado como usuario com perfil '<PERFIL>'
  When tento acessar a funcionalidade '<FUNCIONALIDADE>'
  Then valido que o acesso foi '<RESULTADO_ESPERADO>'

Examples:
  | PERFIL | FUNCIONALIDADE           | RESULTADO_ESPERADO |
  | ADMIN  | Gerenciar Usuarios       | PERMITIDO          |
  | USER   | Gerenciar Usuarios       | NEGADO             |
  | ADMIN  | Visualizar Relatorios    | PERMITIDO          |
  | USER   | Visualizar Relatorios    | PERMITIDO          |
  | ADMIN  | Configurar Sistema       | PERMITIDO          |
  | USER   | Configurar Sistema       | NEGADO             |
```

### 6. **Integration Testing - Fluxo Entre Módulos**

```gherkin
@CT-2006 @IntegracaoTransferenciaConta
Scenario: Validar integracao entre modulos de transferencia e conta
  Given que existe uma conta de origem com saldo de R$ 1000.00
  And que existe uma conta de destino com saldo de R$ 500.00
  When realizo uma transferencia de R$ 300.00 entre as contas
  Then valido que a transferencia foi processada com sucesso
  And valido que o saldo da conta de origem foi debitado em R$ 300.00
  And valido que o saldo da conta de destino foi creditado em R$ 300.00
  And valido que o historico de transacoes foi registrado
  And valido que a notificacao foi enviada para ambas as contas
```

### 7. **Error Handling Testing - Tratamento de Erros**

```gherkin
@CT-2007 @TratamentoErrosSistema
Scenario Outline: Validar tratamento de erros do sistema
  Given que o sistema esta em estado '<ESTADO_SISTEMA>'
  When realizo a operacao '<OPERACAO>'
  Then valido que o erro '<CODIGO_ERRO>' foi retornado
  And valido que a mensagem de erro contem '<MENSAGEM_ESPERADA>'
  And valido que o log de erro foi registrado

Examples:
  | ESTADO_SISTEMA | OPERACAO           | CODIGO_ERRO | MENSAGEM_ESPERADA           |
  | BANCO_OFFLINE  | Realizar Transferencia | 503        | Servico temporariamente indisponivel |
  | TOKEN_INVALIDO | Consultar Conta     | 401         | Token de autenticacao invalido |
  | DADOS_INVALIDOS| Cadastrar Usuario   | 422         | Dados obrigatorios nao informados |
  | RECURSO_NAO_ENCONTRADO | Consultar Transferencia | 404 | Transferencia nao encontrada |
```

### 8. **Concurrent Testing - Acesso Simultâneo**

```gherkin
@CT-2008 @TesteConcorrencia
Scenario: Validar comportamento com acessos simultaneos
  Given que dois usuarios estao logados simultaneamente
  And ambos tentam realizar a mesma operacao
  When o primeiro usuario executa a operacao
  And o segundo usuario executa a mesma operacao
  Then valido que apenas uma operacao foi processada
  And valido que a segunda operacao retornou erro de conflito
  And valido que a integridade dos dados foi mantida
```

---

## 🔧 Técnicas Avançadas Recomendadas

### 1. **API Contract Testing**
```gherkin
@CT-2009 @ContratoAPI
Scenario: Validar contrato da API de transferencia
  Given que a API de transferencia esta disponivel
  When consulto a documentacao da API
  Then valido que o schema de resposta esta correto
  And valido que todos os campos obrigatorios estao presentes
  And valido que os tipos de dados estao corretos
```

### 2. **Database State Testing**
```gherkin
@CT-2010 @EstadoBancoDados
Scenario: Validar estado do banco de dados apos operacao
  Given que o banco de dados esta em estado inicial conhecido
  When realizo uma operacao de transferencia
  Then valido que os registros foram inseridos corretamente
  And valido que as constraints foram respeitadas
  And valido que os relacionamentos estao corretos
```

### 3. **Cross-Browser Testing**
```gherkin
@CT-2011 @TesteMultiplosNavegadores
Scenario Outline: Validar funcionalidade em diferentes navegadores
  Given que estou usando o navegador '<NAVEGADOR>'
  When acesso a aplicacao web
  Then valido que todas as funcionalidades funcionam corretamente

Examples:
  | NAVEGADOR |
  | Chrome    |
  | Firefox   |
  | Safari    |
  | Edge      |
```

---

## 📝 Padrões de Nomenclatura

### **Tags de Cenários**
- `@CT-[ID]` - Identificador único do caso de teste
- `@[MODULO]` - Módulo da aplicação (ex: `@transferencia`, `@contas`)
- `@[TIPO_TESTE]` - Tipo de teste (ex: `@smoke`, `@regression`, `@performance`)
- `@[CATEGORIA]` - Categoria de teste (ex: `@caixaPreta`, `@caixaBranca`, `@unitario`, `@funcional`)

### **Estratégia de Tags por Categoria**
```gherkin
# Categorias Fundamentais
@caixaPreta     # Testes sem conhecimento da implementação
@caixaBranca    # Testes com conhecimento da implementação
@smoke          # Testes básicos de funcionalidade crítica
@funcional      # Testes de requisitos funcionais
@unitario       # Testes de unidades de código
@regressivo     # Testes de regressão

# Técnicas de Design
@dataDriven     # Testes orientados a dados
@boundary       # Testes de valores limite
@positive       # Testes de cenários positivos
@negative       # Testes de cenários negativos
@stateBased     # Testes baseados em estado
@integration    # Testes de integração
```

### **Nomenclatura de Steps**
- **Given:** Configuração e pré-condições
- **When:** Ações do usuário ou sistema
- **Then:** Validações e verificações

### **Exemplos de Steps**
```gherkin
# Configuração
Given que estou logado como admin
Given que existe uma conta com saldo de R$ 1000.00

# Ações
When realizo uma transferencia de R$ 500.00
When consulto o historico de transacoes

# Validações
Then valido que a transferencia foi processada com sucesso
Then valido que o saldo foi atualizado corretamente
```

---

## ✅ Boas Práticas

### 1. **Organização de Features**
- Uma feature por funcionalidade
- Cenários independentes e isolados
- Uso consistente de tags

### 2. **Data Management**
- Dados de teste isolados
- Limpeza de dados após testes
- Uso de factories para geração de dados

### 3. **Assertions**
- Validações específicas e claras
- Mensagens de erro informativas
- Verificação de múltiplos aspectos

### 4. **Performance**
- Timeouts adequados
- Otimização de queries
- Cache quando apropriado

### 5. **Maintenance**
- Steps reutilizáveis
- Código limpo e documentado
- Refatoração regular

---

## 📊 Métricas de Qualidade

### **Cobertura de Testes por Categoria**
- **Caixa Preta:** 100% dos cenários de negócio
- **Caixa Branca:** 90% de cobertura de código
- **Smoke:** 100% das funcionalidades críticas
- **Funcional:** 95% dos requisitos funcionais
- **Unitário:** 85% de cobertura de código
- **Regressivo:** 100% dos testes existentes

### **Cobertura por Técnica de Design**
- **Data-Driven:** 100% dos cenários parametrizados
- **Boundary Value:** 100% dos valores limite
- **Positive/Negative:** 100% dos cenários de sucesso e erro
- **State-Based:** 100% das mudanças de estado
- **Integration:** 85% dos fluxos entre módulos

### **Indicadores de Qualidade**
- **Taxa de Falha:** < 5%
- **Tempo de Execução:** < 30 minutos
- **Manutenibilidade:** Código limpo e documentado
- **Cobertura Total:** > 90%

---

## 🔄 Ciclo de Vida dos Testes

### **1. Planejamento**
- Definição de cenários por categoria (caixa preta, funcional, etc.)
- Seleção de técnicas de design apropriadas
- Definição de critérios de aceitação

### **2. Implementação**
- Criação de testes unitários para lógica de negócio
- Implementação de testes de integração
- Desenvolvimento de testes funcionais com Cucumber

### **3. Execução**
- **Smoke Tests:** Execução rápida para validar estabilidade
- **Testes Funcionais:** Validação de requisitos
- **Testes Regressivos:** Garantia de que nada foi quebrado

### **4. Análise**
- Revisão de resultados por categoria
- Análise de cobertura de código
- Identificação de gaps de teste

### **5. Manutenção**
- Atualização de testes conforme mudanças
- Refatoração para melhorar manutenibilidade
- Expansão de cobertura conforme necessário

---

## 🎯 Mapeamento das Features Existentes

### **Análise das Features Atuais**

#### **transferencias.feature**
- **CT-1001 a CT-1006:** Testes CRUD (Funcional/Caixa Preta)
- **CT-1007:** Teste de valor limite (Boundary Value/Caixa Preta)
- **CT-1008:** Teste de autenticação (Funcional/Caixa Preta)
- **CT-1009:** Teste de valor válido (Positive/Caixa Preta)
- **CT-1010:** Teste de valor mínimo (Boundary Value/Caixa Preta)
- **CT-1011 a CT-1014:** Data-Driven Testing (Funcional/Caixa Preta)

#### **contas.feature**
- **contasBancarias:** Teste de listagem (Funcional/Caixa Preta)
- **contasObeterDetalhesContaBancaria:** Data-Driven Testing (Funcional/Caixa Preta)

#### **login.feature**
- **loginAdmin:** Teste de autenticação (Funcional/Caixa Preta)
- **loginUsuario:** Teste de autenticação (Funcional/Caixa Preta)

### **Gaps Identificados**
- ❌ **Testes Unitários:** Não implementados
- ❌ **Testes de Caixa Branca:** Não implementados
- ❌ **Smoke Tests:** Não implementados
- ❌ **Testes Regressivos:** Não implementados
- ❌ **Testes de Performance:** Não implementados

### **Recomendações de Implementação**
1. **Implementar Smoke Tests** para validação rápida
2. **Criar testes unitários** para lógica de negócio
3. **Adicionar testes de caixa branca** para cobertura de código
4. **Implementar suite de regressão** automatizada
5. **Expandir testes de performance** para cenários críticos

---

*Este documento deve ser atualizado conforme novas técnicas são implementadas e boas práticas são identificadas.* 