# Configuração Allure para Relatórios de Teste

## Visão Geral

Este projeto foi configurado com o Allure Framework para gerar relatórios detalhados e interativos dos testes automatizados. O Allure é compatível com Java 21 e integrado com Cucumber e Rest Assured.

## Configurações Implementadas

### Dependências Adicionadas
- `allure-cucumber7-jvm`: Integração com Cucumber 7
- `allure-junit4`: Suporte ao JUnit 4
- `allure-rest-assured`: Integração com Rest Assured
- `aspectjweaver`: Para captura automática de logs e screenshots

### Plugins Configurados
- **Allure Maven Plugin**: Para geração de relatórios
- **AspectJ Weaver**: Para captura automática de dados

## Como Usar

### 1. Executar os Testes
```bash
# Executar todos os testes
mvn clean test

# Executar com tags específicas
mvn clean test -Dcucumber.filter.tags="@Usuario"

# Executar apenas um cenário específico
mvn clean test -Dcucumber.filter.tags="@Usuario and @smoke"
```

### 2. Gerar Relatório Allure
```bash
# Gerar relatório após execução dos testes
mvn allure:report

# Abrir relatório no navegador
mvn allure:serve
```

### 3. Comandos Úteis
```bash
# Limpar resultados anteriores
mvn allure:clean

# Gerar e abrir relatório em uma única execução
mvn clean test allure:report allure:serve
```

## Estrutura de Arquivos

```
src/test/resources/
├── allure.properties          # Configuração principal do Allure
├── allure-categories.json     # Categorização de falhas
└── allure-environment.properties # Configuração do ambiente

target/
├── allure-results/            # Resultados dos testes (JSON)
└── allure-report/             # Relatório HTML gerado
```

## Funcionalidades do Relatório

### 1. Dashboard
- Resumo geral dos testes
- Gráficos de execução
- Estatísticas por categoria

### 2. Detalhes dos Testes
- Logs detalhados de cada step
- Screenshots automáticos (quando aplicável)
- Dados de requisição/resposta do Rest Assured
- Tempo de execução

### 3. Categorização
- **Falhas de Teste**: Testes que falharam
- **Testes Ignorados**: Testes pulados
- **Problemas de Configuração**: Testes com problemas técnicos
- **Testes Passaram**: Testes executados com sucesso

## Anotações Úteis

### Para Adicionar Informações aos Relatórios

```java
import io.qameta.allure.*;

@Epic("Gerenciamento de Usuários")
@Feature("Cadastro de Usuário")
@Story("Cadastro de Usuário Válido")
@Description("Teste para validar o cadastro de um usuário com dados válidos")
public class UsuarioSteps {

    @Step("Preencher dados do usuário")
    public void preencherDadosUsuario(String nome, String email) {
        // implementação
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] capturarScreenshot() {
        // implementação
    }
}
```

### Anotações Disponíveis
- `@Epic`: Agrupa funcionalidades relacionadas
- `@Feature`: Define uma funcionalidade específica
- `@Story`: Define um cenário de uso
- `@Step`: Marca um método como um step do teste
- `@Attachment`: Anexa arquivos ao relatório
- `@Description`: Adiciona descrição detalhada

## Configurações Avançadas

### Personalizar Categorias
Edite o arquivo `src/test/resources/allure-categories.json` para adicionar novas categorias de falha.

### Configurar Ambiente
Edite o arquivo `src/test/resources/allure-environment.properties` para adicionar informações específicas do ambiente.

### Configurações Globais
Edite o arquivo `src/test/resources/allure.properties` para personalizar:
- Diretórios de resultados
- Configurações de anexos
- Padrões de links para issues/TMS
- Idioma do relatório

## Troubleshooting

### Problema: AspectJ não funciona
**Solução**: Verifique se o AspectJ Weaver está sendo carregado corretamente no `pom.xml`.

### Problema: Relatório não gera
**Solução**: Execute `mvn clean` antes de executar os testes novamente.

### Problema: Screenshots não aparecem
**Solução**: Verifique se o `allure.cucumber.attach.screenshot=true` está configurado.

## Benefícios do Allure

1. **Relatórios Interativos**: Interface web moderna e responsiva
2. **Integração Completa**: Funciona com Cucumber, JUnit e Rest Assured
3. **Captura Automática**: Logs, screenshots e dados de API
4. **Categorização Inteligente**: Organiza falhas por tipo
5. **Histórico**: Mantém histórico de execuções
6. **Personalização**: Altamente configurável
7. **Compatibilidade**: Funciona com Java 21

## Próximos Passos

1. Execute os testes para verificar a configuração
2. Gere o primeiro relatório com `mvn allure:serve`
3. Adicione anotações `@Step` nos métodos de teste
4. Personalize as categorias conforme necessário
5. Configure links para seu sistema de issues/TMS 