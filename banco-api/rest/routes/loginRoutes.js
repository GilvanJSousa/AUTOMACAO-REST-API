const express = require('express');
const loginController = require('../controllers/loginController');

const router = express.Router();

router.post('/', loginController.login);
router.all('/', (req, res) => {
    res.status(405).json({ error: 'Método não permitido.' });
});

module.exports = router;