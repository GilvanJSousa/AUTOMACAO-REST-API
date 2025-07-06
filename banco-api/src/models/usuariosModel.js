const { mongoose } = require('./db');

// Schema para Usuários
const UsuarioSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true,
    unique: true,
    trim: true
  },
  senha: {
    type: String,
    required: true
  }
}, {
  timestamps: true
});

const Usuario = mongoose.model('Usuario', UsuarioSchema);

// Funções do Model
async function getUsuarioByCredenciais(username, senha) {
  return await Usuario.findOne({ username, senha });
}

module.exports = { 
  Usuario,
  getUsuarioByCredenciais 
};