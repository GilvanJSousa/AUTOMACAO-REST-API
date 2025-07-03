@viagem
Feature: Gerenciador Viagem

  Background: Gerar Token
    Given envio uma solicitação de requisição de login

    
  @CT-0001
  Scenario: Cadastrar uma viagem
    Given que envio uma requisição de cadastro de viagem
    Then a API cadastro de viagem deve retornar o código de status 201

  @CT-0002
  Scenario: Retornar todas as viagens
    Given que envio uma requisição GET para retornar todas as viagens
    Then a API Retorna todas as viagens deve retornar o código de status 201