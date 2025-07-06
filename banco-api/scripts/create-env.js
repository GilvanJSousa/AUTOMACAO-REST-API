const fs = require('fs');
const path = require('path');

const createEnvFile = () => {
  const envContent = `# MongoDB Atlas - String de conexao fornecida
MONGO_URI=mongodb+srv://gillvanjs:KNAqSlJj0n6mawLV@cluster0.yia8ilv.mongodb.net/banco?retryWrites=true&w=majority&appName=Cluster0

# Configuracao JWT
JWT_SECRET=banco_api_jwt_secret_key_2024

# Portas dos servidores
PORT=3000
GRAPHQLPORT=3001
`;

  const envPath = path.join(__dirname, '..', '.env');
  
  try {
    fs.writeFileSync(envPath, envContent, 'utf8');
    console.log('✅ Arquivo .env criado com sucesso!');
    console.log('📁 Localização:', envPath);
    console.log('\n🔧 Configurações:');
    console.log('   - MongoDB Atlas: Configurado');
    console.log('   - JWT Secret: Definido');
    console.log('   - Portas: 3000 (REST) e 3001 (GraphQL)');
    console.log('\n🚀 Próximo passo: npm run seed');
  } catch (error) {
    console.error('❌ Erro ao criar arquivo .env:', error.message);
    process.exit(1);
  }
};

// Executar apenas se chamado diretamente
if (require.main === module) {
  createEnvFile();
}

module.exports = createEnvFile; 