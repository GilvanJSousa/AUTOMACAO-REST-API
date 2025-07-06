const mongoose = require('mongoose');
require('dotenv').config();

const connectDB = async () => {
  try {
    const mongoURI = process.env.MONGO_URI || 'mongodb://localhost:27017/banco';
    
    // Debug: verificar se a variável está sendo carregada
    console.log('🔍 Verificando configuração:');
    console.log(`   MONGO_URI definida: ${process.env.MONGO_URI ? '✅ Sim' : '❌ Não'}`);
    console.log(`   Usando URI: ${mongoURI.includes('mongodb+srv') ? '✅ Atlas' : '❌ Local'}`);
    
    await mongoose.connect(mongoURI, {
      maxPoolSize: 10, // Máximo de conexões no pool
      serverSelectionTimeoutMS: 5000, // Timeout para seleção de servidor
      socketTimeoutMS: 45000, // Timeout para operações
    });
    
    console.log('✅ MongoDB Atlas conectado com sucesso');
    
    // Configurar listeners para eventos de conexão
    mongoose.connection.on('error', (err) => {
      console.error('❌ Erro na conexão MongoDB:', err);
    });
    
    mongoose.connection.on('disconnected', () => {
      console.log('⚠️  MongoDB desconectado');
    });
    
    mongoose.connection.on('reconnected', () => {
      console.log('🔄 MongoDB reconectado');
    });
    
  } catch (error) {
    console.error('❌ Erro ao conectar com MongoDB Atlas:', error.message);
    console.error('💡 Verifique se o arquivo .env está na raiz do projeto');
    console.error('💡 Verifique se a variável MONGO_URI está definida corretamente');
    process.exit(1);
  }
};

module.exports = { connectDB, mongoose };