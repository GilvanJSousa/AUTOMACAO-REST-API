const jwt = require('jsonwebtoken');
const JWT_SECRET = process.env.JWT_SECRET || 'secret_key';

function autenticarToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) {
        return res.status(401).json({ error: 'Token de autenticação não fornecido.' });
    }

    jwt.verify(token, JWT_SECRET, (err, usuario) => {

        if (err) {
            if (err.name === 'JsonWebTokenError') {
                return res.status(401).json({ error: 'Token inválido.' });
            }

            if (err.name === 'TokenExpiredError') {
                return res.status(401).json({ error: 'Token expirado.' });
            }

            return res.status(500).json({ error: 'Erro ao autenticar o token.' });
        }

        if (req.baseUrl == '/transferencias' && req.method == 'POST' && usuario.username == 'junior.lima') {
            return res.status(403).json({ error: 'Acesso não permitido.' });
        }

        req.usuario = usuario;
        next();
    });
}

module.exports = autenticarToken;