const createError = require('http-errors');

function gerenciarErros(err, req, res, next) {
    if (createError.isHttpError(err)) {
        res.status(err.status).json({ error: err.message });
    } else {
        console.error(err);
        res.status(500).json({ error: 'Erro interno do servidor.' });
    }
}

module.exports = gerenciarErros;