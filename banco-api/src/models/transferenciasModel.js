const { mongoose } = require('./db');

// Schema para Transferências
const TransferenciaSchema = new mongoose.Schema({
  contaOrigem: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Conta',
    required: true
  },
  contaDestino: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Conta',
    required: true
  },
  valor: {
    type: Number,
    required: true,
    min: 10
  },
  autenticada: {
    type: Boolean,
    default: false
  }
}, {
  timestamps: true
});

const Transferencia = mongoose.model('Transferencia', TransferenciaSchema);

// Funções do Model
async function inserirTransferencia(contaOrigem, contaDestino, valor, autenticada) {
  const transferencia = new Transferencia({
    contaOrigem,
    contaDestino,
    valor,
    autenticada
  });
  return await transferencia.save();
}

async function getTransferenciasPaginadas(limit, offset) {
  return await Transferencia.find()
    .populate('contaOrigem', 'titular')
    .populate('contaDestino', 'titular')
    .sort({ createdAt: -1 })
    .limit(parseInt(limit))
    .skip(parseInt(offset));
}

async function getTotalTransferencias() {
  return await Transferencia.countDocuments();
}

async function getTransferenciaById(id) {
  return await Transferencia.findById(id)
    .populate('contaOrigem', 'titular')
    .populate('contaDestino', 'titular');
}

async function atualizarTransferencia(id, contaOrigem, contaDestino, valor, autenticada) {
  return await Transferencia.findByIdAndUpdate(
    id,
    {
      contaOrigem,
      contaDestino,
      valor,
      autenticada
    },
    { new: true }
  );
}

async function modificarTransferencia(id, campos) {
  const camposConvertidos = {};

  for (const chave in campos) {
    if (chave === 'token') {
      continue; // ignora o campo token
    }

    switch (chave) {
      case 'contaOrigem':
        camposConvertidos['contaOrigem'] = campos[chave];
        break;
      case 'contaDestino':
        camposConvertidos['contaDestino'] = campos[chave];
        break;
      default:
        camposConvertidos[chave] = campos[chave];
    }
  }

  return await Transferencia.findByIdAndUpdate(
    id,
    camposConvertidos,
    { new: true }
  );
}

async function removerTransferencia(id) {
  return await Transferencia.findByIdAndDelete(id);
}

module.exports = { 
  Transferencia,
  inserirTransferencia, 
  getTransferenciasPaginadas, 
  getTotalTransferencias,
  getTransferenciaById,
  atualizarTransferencia,
  modificarTransferencia,
  removerTransferencia
};