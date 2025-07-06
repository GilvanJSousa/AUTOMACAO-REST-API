const { mongoose } = require('./db');

// Schema para Contas
const ContaSchema = new mongoose.Schema({
  titular: {
    type: String,
    required: true,
    trim: true
  },
  saldo: {
    type: Number,
    required: true,
    default: 0,
    min: 0
  },
  ativa: {
    type: Boolean,
    default: true
  }
}, {
  timestamps: true
});

const Conta = mongoose.model('Conta', ContaSchema);

// Funções do Model
async function getContas() {
  return await Conta.find().sort({ titular: 1 });
}

async function getContaById(id) {
  return await Conta.findById(id);
}

async function atualizarSaldo(id, valor) {
  return await Conta.findByIdAndUpdate(
    id,
    { $inc: { saldo: valor } },
    { new: true }
  );
}

module.exports = { 
  Conta,
  getContas,
  getContaById, 
  atualizarSaldo 
};