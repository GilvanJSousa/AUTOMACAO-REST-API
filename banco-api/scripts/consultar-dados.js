const mongoose = require('mongoose');
require('dotenv').config();

// Schemas para consulta
const UsuarioSchema = new mongoose.Schema({
  username: String,
  senha: String
}, { collection: 'usuarios' });

const ContaSchema = new mongoose.Schema({
  titular: String,
  saldo: Number,
  ativa: Boolean
}, { collection: 'contas' });

const TransferenciaSchema = new mongoose.Schema({
  contaOrigem: { type: mongoose.Schema.Types.ObjectId, ref: 'Conta' },
  contaDestino: { type: mongoose.Schema.Types.ObjectId, ref: 'Conta' },
  valor: Number,
  autenticada: Boolean
}, { collection: 'transferencias' });

const Usuario = mongoose.model('Usuario', UsuarioSchema);
const Conta = mongoose.model('Conta', ContaSchema);
const Transferencia = mongoose.model('Transferencia', TransferenciaSchema);

async function consultarDados() {
  try {
    console.log('🔍 Conectando ao MongoDB Atlas...');
    console.log('🔍 Verificando configuração:');
    console.log(`   MONGO_URI definida: ${process.env.MONGO_URI ? '✅ Sim' : '❌ Não'}`);
    console.log(`   Usando URI: ${process.env.MONGO_URI?.includes('mongodb.net') ? '✅ Atlas' : '❌ Local'}`);
    
    await mongoose.connect(process.env.MONGO_URI);
    console.log('✅ MongoDB Atlas conectado com sucesso');
    
    console.log('\n🔄 Consultando dados no MongoDB Atlas...\n');
    
    // Consultar usuários
    console.log('👤 USUARIOS:');
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    const usuarios = await Usuario.find().select('username _id');
    if (usuarios.length === 0) {
      console.log('   ❌ Nenhum usuário encontrado');
    } else {
      usuarios.forEach((usuario, index) => {
        console.log(`  ${index + 1}. ${usuario.username}`);
        console.log(`     ID: ${usuario._id}`);
      });
    }
    
    console.log('\n💰 CONTAS:');
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    const contas = await Conta.find().select('titular saldo ativa _id').sort({ titular: 1 });
    if (contas.length === 0) {
      console.log('   ❌ Nenhuma conta encontrada');
    } else {
      contas.forEach((conta, index) => {
        const status = conta.ativa ? '✅ Ativa' : '❌ Inativa';
        console.log(`  ${index + 1}. ${conta.titular}`);
        console.log(`     Saldo: R$ ${conta.saldo.toFixed(2)}`);
        console.log(`     Status: ${status}`);
        console.log(`     ID: ${conta._id}`);
      });
    }
    
    console.log('\n💸 TRANSFERENCIAS:');
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    const transferencias = await Transferencia.find()
      .populate('contaOrigem', 'titular')
      .populate('contaDestino', 'titular')
      .select('valor autenticada createdAt _id')
      .sort({ createdAt: -1 });
    
    if (transferencias.length === 0) {
      console.log('   ❌ Nenhuma transferência encontrada');
    } else {
      transferencias.forEach((transferencia, index) => {
        const autenticada = transferencia.autenticada ? '✅ Sim' : '❌ Não';
        const data = transferencia.createdAt ? new Date(transferencia.createdAt).toLocaleString('pt-BR') : 'N/A';
        console.log(`  ${index + 1}. Transferência ${transferencia._id}`);
        console.log(`     De: ${transferencia.contaOrigem?.titular || 'N/A'}`);
        console.log(`     Para: ${transferencia.contaDestino?.titular || 'N/A'}`);
        console.log(`     Valor: R$ ${transferencia.valor.toFixed(2)}`);
        console.log(`     Autenticada: ${autenticada}`);
        console.log(`     Data: ${data}`);
      });
    }
    
    console.log('\n📊 RESUMO:');
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    console.log(`   👤 Usuários: ${usuarios.length}`);
    console.log(`   💰 Contas: ${contas.length}`);
    console.log(`   💸 Transferências: ${transferencias.length}`);
    
    console.log('\n🚀 Dados consultados com sucesso!');
    console.log('💡 Use os IDs das contas para testar transferências');
    
  } catch (error) {
    console.error('❌ Erro ao consultar dados:', error.message);
  } finally {
    await mongoose.disconnect();
    console.log('🔌 Desconectado do MongoDB Atlas');
  }
}

// Executar consulta
consultarDados(); 