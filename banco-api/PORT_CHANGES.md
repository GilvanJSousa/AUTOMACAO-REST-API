# Documentação de Mudança de Portas

Este documento detalha as alterações realizadas para modificar as portas das APIs REST e GraphQL de `3000` e `3001` para `5000` e `5001`, respectivamente.

## Arquivos Modificados

Abaixo estão os arquivos que foram atualizados para refletir a mudança de portas:

1.  **`scripts/create-env.js`**:
    -   **O que mudou**: Os valores das portas no script que cria o arquivo `.env` foram atualizados.
    -   **Linhas modificadas**:
        -   `PORT` alterado de `3000` para `5000`.
        -   `GRAPHQLPORT` alterado de `3001` para `5001`.

2.  **`README.md`**:
    -   **O que mudou**: A documentação principal do projeto foi atualizada para refletir as novas portas.
    -   **Seções atualizadas**:
        -   Descrição das APIs e suas portas.
        -   Exemplos de URLs e comandos `curl`.

3.  **Arquivos de Scripts (`.bat`)**:
    -   **O que mudou**: Os scripts de exemplo que fazem chamadas para a API REST foram atualizados com a nova porta.
    -   **Arquivos**: `01-login.bat`, `02-transferencia.bat`, etc.

4.  **Arquivos de Documentação Adicional**:
    -   **`config/mongodb-atlas.md`**: Atualizado para refletir as novas portas.
    -   **`curls-postman.txt`**: Comandos `curl` de exemplo atualizados.
    -   **`rest/swagger/swagger.json`**: Configuração do Swagger atualizada.
    -   **`rest/swagger/v1.json`**: Configuração do Swagger atualizada.

## Como Aplicar as Mudanças

Para que as mudanças tenham efeito, é necessário executar o script `create-env.js` para gerar um novo arquivo `.env` com as portas atualizadas.

```bash
node scripts/create-env.js
```

Após a execução do script, as APIs REST e GraphQL estarão configuradas para rodar nas portas `5000` e `5001`.
