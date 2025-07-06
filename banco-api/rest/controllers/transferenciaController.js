const transferenciasService = require('../../src/services/transferenciasService');

async function realizarTransferencia(req, res, next) {
    const { contaOrigem, contaDestino, valor, token } = req.body;

    try {
        const result = await transferenciasService.realizarTransferencia(contaOrigem, contaDestino, valor, token);
        res.status(201).json(result);
    } catch (error) {
        next(error);
    }
}

async function getTransferencias(req, res, next) {
    const { page, limit } = req.query;

    try {
        const result = await transferenciasService.getTransferencias(page, limit);
        res.json(result);
    } catch (error) {
        next(error);
    }
}

async function getTransferencia(req, res, next) {
    const { id } = req.params;

    try {
        const result = await transferenciasService.getTransferencia(id);
        res.json(result);
    } catch (error) {
        next(error);
    }
}

async function atualizarTransferencia(req, res, next) {
    const { id } = req.params;
    const { contaOrigem, contaDestino, valor, token } = req.body;

    try {
        await transferenciasService.atualizarTransferencia(id, contaOrigem, contaDestino, valor, token);
        res.sendStatus(204); 
    } catch (error) {
        next(error);
    }
}

async function modificarTransferencia(req, res, next) {
    const { id } = req.params;
    const camposAtualizados = req.body;

    try {
        await transferenciasService.modificarTransferencia(id, camposAtualizados);
        res.sendStatus(204); 
    } catch (error) {
        next(error);
    }
}

async function removerTransferencia(req, res, next) {
    const { id } = req.params;

    try {
        await transferenciasService.removerTransferencia(id);
        res.sendStatus(204); 
    } catch (error) {
        next(error);
    }
}

module.exports = {
    realizarTransferencia,
    getTransferencias,
    getTransferencia,
    atualizarTransferencia,
    modificarTransferencia,
    removerTransferencia,
};