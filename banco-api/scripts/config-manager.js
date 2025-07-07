const fs = require('fs');
const path = require('path');

class ConfigManager {
  constructor() {
    this.configPath = path.join(__dirname, '..', 'config', 'properties.json');
    this.envPath = path.join(__dirname, '..', '.env');
  }

  // Carregar configurações do arquivo properties.json
  loadConfig() {
    try {
      const configData = fs.readFileSync(this.configPath, 'utf8');
      return JSON.parse(configData);
    } catch (error) {
      console.error('❌ Erro ao carregar configurações:', error.message);
      return null;
    }
  }

  // Salvar configurações no arquivo properties.json
  saveConfig(config) {
    try {
      fs.writeFileSync(this.configPath, JSON.stringify(config, null, 2), 'utf8');
      console.log('✅ Configurações salvas com sucesso!');
      return true;
    } catch (error) {
      console.error('❌ Erro ao salvar configurações:', error.message);
      return false;
    }
  }

  // Atualizar porta do servidor REST
  updateRestPort(newPort) {
    const config = this.loadConfig();
    if (!config) return false;

    config.server.rest.port = parseInt(newPort);
    const success = this.saveConfig(config);
    
    if (success) {
      console.log(`🔄 Porta REST alterada para: ${newPort}`);
      this.updateEnvFile(config);
    }
    
    return success;
  }

  // Atualizar porta do servidor GraphQL
  updateGraphQLPort(newPort) {
    const config = this.loadConfig();
    if (!config) return false;

    config.server.graphql.port = parseInt(newPort);
    const success = this.saveConfig(config);
    
    if (success) {
      console.log(`🔄 Porta GraphQL alterada para: ${newPort}`);
      this.updateEnvFile(config);
    }
    
    return success;
  }

  // Atualizar ambas as portas
  updatePorts(restPort, graphqlPort) {
    const config = this.loadConfig();
    if (!config) return false;

    config.server.rest.port = parseInt(restPort);
    config.server.graphql.port = parseInt(graphqlPort);
    const success = this.saveConfig(config);
    
    if (success) {
      console.log(`🔄 Portas alteradas - REST: ${restPort}, GraphQL: ${graphqlPort}`);
      this.updateEnvFile(config);
    }
    
    return success;
  }

  // Atualizar arquivo .env baseado nas configurações
  updateEnvFile(config) {
    const envContent = `# MongoDB Atlas - String de conexao fornecida
MONGO_URI=${config.database.mongo_uri}

# Configuracao JWT
JWT_SECRET=${config.jwt.secret}

# Portas dos servidores
PORT=${config.server.rest.port}
GRAPHQLPORT=${config.server.graphql.port}
`;

    try {
      fs.writeFileSync(this.envPath, envContent, 'utf8');
      console.log('✅ Arquivo .env atualizado com sucesso!');
      
      // Sincronizar porta com o workflow do GitHub Actions
      this.syncWorkflowPort(config.server.rest.port);
    } catch (error) {
      console.error('❌ Erro ao atualizar arquivo .env:', error.message);
    }
  }

  // Sincronizar porta com o workflow do GitHub Actions
  syncWorkflowPort(port) {
    try {
      const WorkflowPortSync = require('./sync-workflow-port');
      const sync = new WorkflowPortSync();
      sync.updateWorkflowPort(port);
    } catch (error) {
      console.log('⚠️  Aviso: Não foi possível sincronizar com o workflow (pode ser normal em desenvolvimento)');
    }
  }

  // Exibir configurações atuais
  showConfig() {
    const config = this.loadConfig();
    if (!config) return;

    console.log('\n📋 Configurações Atuais:');
    console.log('========================');
    console.log(`🌐 Servidor REST: ${config.server.rest.host}:${config.server.rest.port}`);
    console.log(`🔗 Servidor GraphQL: ${config.server.graphql.host}:${config.server.graphql.port}`);
    console.log(`🗄️  MongoDB: ${config.database.mongo_uri.substring(0, 50)}...`);
    console.log(`🔐 JWT Secret: ${config.jwt.secret.substring(0, 20)}...`);
    console.log(`📚 Swagger: ${config.swagger.enabled ? 'Habilitado' : 'Desabilitado'} em ${config.swagger.path}`);
    console.log('========================\n');
  }

  // Listar portas disponíveis
  listAvailablePorts() {
    console.log('\n🚀 Portas Sugeridas:');
    console.log('===================');
    console.log('8080/8081 - Portas padrão para desenvolvimento web');
    console.log('4000/4001 - Portas alternativas populares');
    console.log('5000/5001 - Portas usadas anteriormente no projeto');
    console.log('8000/8001 - Portas de desenvolvimento');
    console.log('9000/9001 - Portas de alta numeração');
    console.log('===================\n');
  }

  // Sincronizar workflow manualmente
  syncWorkflow() {
    try {
      const WorkflowPortSync = require('./sync-workflow-port');
      const sync = new WorkflowPortSync();
      return sync.syncPort();
    } catch (error) {
      console.error('❌ Erro ao sincronizar workflow:', error.message);
      return false;
    }
  }
}

// Funções de conveniência para uso via linha de comando
const configManager = new ConfigManager();

// Verificar argumentos da linha de comando
const args = process.argv.slice(2);
const command = args[0];

switch (command) {
  case 'show':
    configManager.showConfig();
    break;
    
  case 'rest-port':
    if (args[1]) {
      configManager.updateRestPort(args[1]);
    } else {
      console.log('❌ Uso: node config-manager.js rest-port <porta>');
    }
    break;
    
  case 'graphql-port':
    if (args[1]) {
      configManager.updateGraphQLPort(args[1]);
    } else {
      console.log('❌ Uso: node config-manager.js graphql-port <porta>');
    }
    break;
    
  case 'update-ports':
    if (args[1] && args[2]) {
      configManager.updatePorts(args[1], args[2]);
    } else {
      console.log('❌ Uso: node config-manager.js update-ports <porta-rest> <porta-graphql>');
    }
    break;
    
  case 'list-ports':
    configManager.listAvailablePorts();
    break;
    
  case 'sync-workflow':
    configManager.syncWorkflow();
    break;
    
  default:
    console.log('\n🔧 Gerenciador de Configurações - Banco API');
    console.log('==========================================');
    console.log('Comandos disponíveis:');
    console.log('  show                    - Exibir configurações atuais');
    console.log('  rest-port <porta>       - Alterar porta do servidor REST');
    console.log('  graphql-port <porta>    - Alterar porta do servidor GraphQL');
    console.log('  update-ports <r> <g>    - Alterar ambas as portas');
    console.log('  list-ports              - Listar portas sugeridas');
    console.log('  sync-workflow           - Sincronizar porta com workflow GitHub Actions');
    console.log('\nExemplos:');
    console.log('  node config-manager.js show');
    console.log('  node config-manager.js rest-port 9090');
    console.log('  node config-manager.js update-ports 9090 9091');
    console.log('  node config-manager.js sync-workflow');
    console.log('==========================================\n');
}

module.exports = ConfigManager; 