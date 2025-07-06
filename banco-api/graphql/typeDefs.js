const { gql } = require('apollo-server');

const typeDefs = gql`
  type Conta {
    id: Int
    titular: String
    saldo: Float
    ativa: Boolean
  }

  type Transferencia {
    id: Int
    conta_origem_id: Int
    conta_destino_id: Int
    valor: Float
    data_hora: String
  }

  type Query {
    contas: [Conta]
    transferencias(page: Int, limit: Int): [Transferencia]
  }

  type AuthPayload {
    token: String
    message: String
  }

  type Mutation {
    login(username: String!, senha: String!): AuthPayload
    transferir(
      contaOrigem: Int!
      contaDestino: Int!
      valor: Float!
      mfaToken: String!
    ): String
  }
`;

module.exports = typeDefs