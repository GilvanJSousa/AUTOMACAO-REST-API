# Tutorial: Como Trocar as Portas da API

Este guia ensina como alterar rapidamente as portas dos servidores REST e GraphQL do projeto usando o sistema de configuração centralizada.

---

## 1. Verifique as portas atuais (opcional)

```bash
npm run config:show
```

Saída esperada:
```
🌐 Servidor REST: localhost:9090
🔗 Servidor GraphQL: localhost:9091
```

---

## 2. Altere as portas para 5000 e 5001

Execute o comando abaixo:

```bash
node scripts/config-manager.js update-ports 5000 5001
```

Saída esperada:
```
✅ Configurações salvas com sucesso!
🔄 Portas alteradas - REST: 5000, GraphQL: 5001
✅ Arquivo .env atualizado com sucesso!
```

---

## 3. Confirme a alteração

```bash
npm run config:show
```

Saída esperada:
```
🌐 Servidor REST: localhost:5000
🔗 Servidor GraphQL: localhost:5001
```

---

## 4. Reinicie o(s) servidor(es)

### Para REST:
```bash
npm run rest-api
```

### Para GraphQL:
```bash
npm run graphql-api
```

---

## Observações
- Sempre reinicie o servidor após trocar as portas.
- O arquivo `.env` é atualizado automaticamente, não precisa editar manualmente.
- Se quiser voltar para outras portas, basta repetir o comando com os valores desejados.

---

**Dica:**
Para ver todas as opções do gerenciador de configuração, rode:
```bash
npm run config
``` 