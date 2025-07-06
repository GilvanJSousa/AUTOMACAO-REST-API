require('dotenv').config();
const { ApolloServer } = require('apollo-server');
const { typeDefs, resolvers } = require('./schema');
const { connectDB } = require('../src/models/db');

const server = new ApolloServer({
  typeDefs,
  resolvers,
  context: ({ req }) => ({ req }),
});

// Conectar ao MongoDB antes de iniciar o servidor
const startServer = async () => {
  try {
    await connectDB();
    const { url } = await server.listen({ port: process.env.GRAPHQLPORT });
    console.log(`Servidor GraphQL rodando em ${url}`);
  } catch (error) {
    console.error('Erro ao iniciar servidor GraphQL:', error);
    process.exit(1);
  }
};

startServer();
