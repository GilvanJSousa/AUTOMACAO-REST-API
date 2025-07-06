Feature: Serviço de Autenticação de Usuário

  Rule: Autenticação de Usuário
    Scenario: Usuário fornece nome de usuário e senha corretos
      Given que um usuário com nome "admin" e senha "senha123" está cadastrado
      When o usuário fornece o nome "admin" e a senha "senha123" para autenticação
      Then o sistema valida as credenciais e permite o acesso

    Scenario: Usuário fornece nome de usuário ou senha incorretos
      Given que um usuário com nome "admin" e senha "senha123" está cadastrado
      When o usuário fornece o nome "admin" e a senha "senhaErrada" para autenticação
      Then o sistema retorna um erro informando que o usuário ou senha são inválidos

  Rule: Geração de Token
    Scenario: Gerar um token de autenticação
      Given que o usuário está autenticado
      When o usuário solicita a geração de um token
      Then o sistema gera um token com tempo de expiração de 1 hora

  Rule: Verificação de Token
    Scenario: Verificar um token válido
      Given que um token válido foi gerado
      When o token é verificado
      Then o sistema valida que o token é válido

    Scenario: Verificar um token expirado
      Given que um token expirado foi gerado
      When o token é verificado
      Then o sistema retorna um erro de autenticação informando que o token está expirado

    Scenario: Verificar um token inválido
      Given que um token inválido foi fornecido
      When o token é verificado
      Then o sistema retorna um erro de autenticação informando que o token é inválido
