const express = require('express');
const transferenciaController = require('../controllers/transferenciaController');
const autenticarToken = require('../middleware/autenticarToken');

const router = express.Router();

router.post('/', autenticarToken, transferenciaController.realizarTransferencia);
router.put('/:id', autenticarToken, transferenciaController.atualizarTransferencia);
router.patch('/:id', autenticarToken, transferenciaController.modificarTransferencia);
router.delete('/:id', autenticarToken, transferenciaController.removerTransferencia);
router.get('/', autenticarToken, transferenciaController.getTransferencias);
router.get('/:id', autenticarToken, transferenciaController.getTransferencia);
router.all('/', (req, res) => {
    res.status(405).json({ error: 'Método não permitido.' });
});

module.exports = router;