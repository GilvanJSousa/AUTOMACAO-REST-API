@FuncionalideUsuario
Feature: Validar funcionalidades de usuario


 Cadastrar usuario
  @CT-1001
  Scenario: Cadastrar usuário válido para os testes
    Given que cadastro um usuario valido para os testes

  # Buscar usuarios por id
  @CT-1002
  Scenario: Validar consulta de usuario por ID
    Given envio a solicitação de GET com id
    Then deve retornar usuario com status code 200

# Alteração de usuario
  @CT-1003
  Scenario: Validar alteração de ususario
    Given que envio a solicitação de PUT com id
    Then deve retornar usuario atualizado com status code 200 e nesagem 'Registro alterado com sucesso'

# Excluir usuario
  @CT-1004
  Scenario: Validar exclusão de usuario
    Given envio uma solicitação de PUT para o id
    Then deve retornar o status code 200 e mensagem 'Registro excluido com sucesso'

# Buscar usuario por id incorreto
  @CT-1005
  Scenario: Validar consulta de usuario com ID incorreto
    Given envio a solicitação de GET com id incorreto
    Then deve retornar erro com status code 400 e mensagem 'Usuário não encontrado'

# Cadastrar usuario com email ja utilizado
  @CT-1006
  Scenario: Validar cadastro com email ja utilizado
    Given que cadastro um usuario valido para os testes
    And envio a solicitação de cadastro de usuario com email ja cadastrado
    Then valido a resposta da API retornar o status code 400 e mensagem 'Este email já esta sendo usado'

# Listar usuarios cadastrados
  @CT-1007
  Scenario: Validar lista de usuarios
    Given envio a solicitação de GET
    Then deve retornar a lista de usuarios cadastrados com status code 200

# Consultar usuario por id fixo
  @CT-1008
  Scenario: Consultar usuario por id fixo
    Given que consulto o usuario fixo por id
    Then valido a resposta da API retornar o status code 200 para consulta por id fixo
