const authService = require('../../src/services/loginService');
const createError = require('http-errors');

async function login(req, res, next) {
    const { username, senha } = req.body;

    try {
        const usuario = await authService.autenticarUsuario(username, senha);
        const token = authService.gerarToken(usuario);

        res.json({ token });
    } catch (error) {
        next(error);
    }
}

module.exports = {
    login,
};