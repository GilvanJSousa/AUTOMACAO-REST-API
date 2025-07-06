const { autenticarUsuario, gerarToken, verificarTokenAutenticacao } = require('../../src/services/loginService');
const { realizarTransferencia } = require('../../src/services/transferenciasService');

const mutationResolvers = {
    Mutation: {
        login: async (_, { username, senha }) => {
            try {
                const usuario = await autenticarUsuario(username, senha);
                const token = gerarToken(usuario);
                return { token, message: 'Login realizado com sucesso' };
            } catch (error) {
                throw new Error(error.message);
            }
        },
        transferir: async (_, { contaOrigem, contaDestino, valor, mfaToken }, context) => {
            try {
                const authHeader = context.req.headers.authorization;
                if (!authHeader) {
                    throw new Error('Cabeçalho de autenticação ausente.');
                }

                const token = authHeader.split(' ')[1];
                const usuario = verificarTokenAutenticacao(token);
                if (!usuario) {
                    throw new Error('Usuário não autenticado. Faça login primeiro.');
                }

                const result = await realizarTransferencia(contaOrigem, contaDestino, valor, mfaToken);
                return result.message;
            } catch (error) {
                throw new Error(error.message);
            }
        },
    },
};

module.exports = mutationResolvers