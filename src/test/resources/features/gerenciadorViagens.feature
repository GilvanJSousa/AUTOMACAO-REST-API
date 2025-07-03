@viagem
Feature: Gerenciador Viagem

  Background: Gerar Token
    Given envio uma solicitação de requisição de login

    
  @CT-0001
  Scenario: Cadastrar uma viagem
    Given que envi uma requisição de cadastro de viagem
    Then a API cadastro de viagem deve retornar o código de status 201