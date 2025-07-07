const fs = require('fs');
const path = require('path');

/**
 * Script para sincronizar a porta do arquivo .env com o workflow do GitHub Actions
 * Lê a porta atual do arquivo .env e atualiza o arquivo testes-11-e-21.yml
 */
class WorkflowPortSync {
  constructor() {
    this.envPath = path.join(__dirname, '..', '.env');
    this.workflowPath = path.join(__dirname, '..', '..', '.github', 'workflows', 'testes-11-e-21.yml');
  }

  /**
   * Lê a porta atual do arquivo .env
   */
  readPortFromEnv() {
    try {
      if (!fs.existsSync(this.envPath)) {
        console.error('❌ Arquivo .env não encontrado. Execute primeiro: npm run create-env');
        return null;
      }

      const envContent = fs.readFileSync(this.envPath, 'utf8');
      const portMatch = envContent.match(/PORT=(\d+)/);
      
      if (!portMatch) {
        console.error('❌ Porta não encontrada no arquivo .env');
        return null;
      }

      return portMatch[1];
    } catch (error) {
      console.error('❌ Erro ao ler arquivo .env:', error.message);
      return null;
    }
  }

  /**
   * Atualiza a porta no arquivo de workflow
   */
  updateWorkflowPort(newPort) {
    try {
      if (!fs.existsSync(this.workflowPath)) {
        console.error('❌ Arquivo de workflow não encontrado:', this.workflowPath);
        return false;
      }

      let workflowContent = fs.readFileSync(this.workflowPath, 'utf8');
      
      // Substituir a porta atual pela nova porta
      const updatedContent = workflowContent.replace(
        /PORT: \d+/g,
        `PORT: ${newPort}`
      );

      // Verificar se houve mudança
      if (workflowContent === updatedContent) {
        console.log(`ℹ️  Porta já está atualizada: ${newPort}`);
        return true;
      }

      // Salvar o arquivo atualizado
      fs.writeFileSync(this.workflowPath, updatedContent, 'utf8');
      console.log(`✅ Workflow atualizado com sucesso!`);
      console.log(`🔄 Porta alterada para: ${newPort}`);
      
      return true;
    } catch (error) {
      console.error('❌ Erro ao atualizar workflow:', error.message);
      return false;
    }
  }

  /**
   * Sincroniza a porta do .env com o workflow
   */
  syncPort() {
    console.log('🔄 Sincronizando porta do .env com o workflow...');
    
    const currentPort = this.readPortFromEnv();
    if (!currentPort) {
      return false;
    }

    console.log(`📋 Porta atual no .env: ${currentPort}`);
    
    const success = this.updateWorkflowPort(currentPort);
    
    if (success) {
      console.log('✅ Sincronização concluída com sucesso!');
    } else {
      console.log('❌ Falha na sincronização');
    }
    
    return success;
  }

  /**
   * Exibe informações sobre a sincronização
   */
  showInfo() {
    console.log('\n📋 Informações de Sincronização:');
    console.log('================================');
    console.log(`📁 Arquivo .env: ${this.envPath}`);
    console.log(`📁 Workflow: ${this.workflowPath}`);
    
    const currentPort = this.readPortFromEnv();
    if (currentPort) {
      console.log(`🔌 Porta atual: ${currentPort}`);
    } else {
      console.log(`🔌 Porta: Não encontrada`);
    }
    
    console.log('================================\n');
  }
}

// Função principal para uso via linha de comando
function main() {
  const sync = new WorkflowPortSync();
  const args = process.argv.slice(2);
  const command = args[0];

  switch (command) {
    case 'sync':
      sync.syncPort();
      break;
      
    case 'info':
      sync.showInfo();
      break;
      
    default:
      console.log('\n🔄 Sincronizador de Porta - Workflow GitHub Actions');
      console.log('==================================================');
      console.log('Comandos disponíveis:');
      console.log('  sync                    - Sincronizar porta do .env com workflow');
      console.log('  info                    - Exibir informações de sincronização');
      console.log('\nExemplos:');
      console.log('  node sync-workflow-port.js sync');
      console.log('  node sync-workflow-port.js info');
      console.log('==================================================\n');
  }
}

// Executar apenas se chamado diretamente
if (require.main === module) {
  main();
}

module.exports = WorkflowPortSync; 