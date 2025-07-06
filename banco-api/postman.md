# Teste de Transferencias entre Contas Ativas

@ATJ-001 @all

**Feature:** Transferencias entre contas ativas  
Como usuario do sistema  
Eu quero realizar transferencias entre contas ativas  
Para garantir que o fluxo de transferencia esta funcionando corretamente

---

## Cenários

### 1. Transferir 50 reais de Joao Silva para Maria Santos

- **contaOrigem:** `6866ef0c822da5a2bb628767` (Joao Silva)
- **contaDestino:** `6866ef0c822da5a2bb628768` (Maria Santos)
- **valor:** `50.00`
- **Mensagem esperada:** `"Transferencia realizada com sucesso."`

---

### 2. Transferir 50 reais de Maria Santos para Pedro Oliveira

- **contaOrigem:** `6866ef0c822da5a2bb628768` (Maria Santos)
- **contaDestino:** `6866ef0c822da5a2bb628769` (Pedro Oliveira)
- **valor:** `50.00`
- **Mensagem esperada:** `"Transferencia realizada com sucesso."`

---

### 3. Transferir 50 reais de Pedro Oliveira para Joao Silva

- **contaOrigem:** `6866ef0c822da5a2bb628769` (Pedro Oliveira)
- **contaDestino:** `6866ef0c822da5a2bb628767` (Joao Silva)
- **valor:** `50.00`
- **Mensagem esperada:** `"Transferencia realizada com sucesso."`

---

> **Observacao:**  
> - Sempre utilize um token JWT valido no header `Authorization` para cada requisicao.  
> - O campo `token` no corpo da requisicao tambem deve ser preenchido com o mesmo JWT. 