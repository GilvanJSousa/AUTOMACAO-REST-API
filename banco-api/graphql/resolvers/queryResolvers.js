const { getContas } = require('../../src/services/contaService');
const { getTransferencias } = require('../../src/services/transferenciasService');

const queryResolvers = {
  Query: {
    contas: async () => {
      const { contas } = await getContas();
      return contas;
    },
    transferencias: async (_, { page = 1, limit = 10 }) => {
      const { transferencias } = await getTransferencias(page, limit);
      return transferencias;
    },
  }
}

module.exports = queryResolvers;