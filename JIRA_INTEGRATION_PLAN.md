# Planejamento de Integração: Plugin de Log Cucumber + Jira

## Objetivo
Permitir que o plugin de resumo de logs do Cucumber envie automaticamente os resultados dos testes (por feature, cenário, tag ou TestID) para o Jira, facilitando rastreabilidade, métricas e automação de workflow.

## Requisitos
- Enviar status dos testes (passou/falhou/skipped) para o Jira
- Associar cada execução a um ticket (ex: via tag @ATJ-123)
- Registrar evidências (logs, prints, links de relatório)
- Autenticação segura (API Token Jira)
- Configuração flexível (URL, usuário, projeto, etc)

## Etapas Sugeridas
1. **Mapeamento de tags e TestIDs**
   - Garantir que cada cenário tenha tag Jira (@ATJ-123)
   - Extrair tag e status ao final do cenário
2. **Configuração do plugin**
   - Adicionar propriedades para Jira (URL, user, token, projeto)
   - Permitir ativar/desativar integração via config
3. **Implementação da chamada REST**
   - Usar HttpClient Java para POST/PUT no endpoint do Jira
   - Payload: status, evidências, comentários
4. **Tratamento de erros e logs**
   - Logar tentativas, falhas e sucesso da integração
   - Retry/backoff em caso de erro temporário
5. **Documentação e exemplos**
   - Exemplo de configuração
   - Exemplo de payload enviado
   - Exemplo de resposta esperada

## Pontos de Integração
- **Após cada cenário**: Enviar status individual
- **Após a feature**: Enviar resumo consolidado
- **Após o suite**: Enviar relatório geral (opcional)

## Referências
- [Jira REST API Docs](https://developer.atlassian.com/cloud/jira/platform/rest/v3/intro/)
- [Exemplo de integração Cucumber + Jira (GitHub)](https://github.com/search?q=cucumber+jira+integration)

---

> **Próximos passos:**
> - Definir formato do payload
> - Validar autenticação e permissões no Jira
> - Implementar mock/teste local
> - Revisar requisitos de segurança e LGPD 