const contaService = require('../../src/services/contaService');

async function getContas(req, res, next) {
    try {
        const result = await contaService.getContas();
        res.json(result);
    } catch (error) {
        next(error);
    }
}

async function getConta(req, res, next) {
    const { id } = req.params;
    try {
        const result = await contaService.getContaById(id);
        res.json(result);
    } catch (error) {
        next(error);
    }
}

module.exports = {
    getContas,
    getConta
};
