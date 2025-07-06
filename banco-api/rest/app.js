const express = require('express');
const swaggerUi = require('swagger-ui-express');
const swaggerDocument = require('./config/swagger');
const { connectDB } = require('../src/models/db');
require('dotenv').config();

const loginRoutes = require('./routes/loginRoutes');
const transferenciaRoutes = require('./routes/transferenciaRoutes');
const contaRoutes = require('./routes/contaRoutes');

const gerenciarErros = require('./middleware/gerenciarErros');

const app = express();

app.use(express.json());
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

app.use('/login', loginRoutes);
app.use('/transferencias', transferenciaRoutes);
app.use('/contas', contaRoutes);

app.use(gerenciarErros);

// Conectar ao MongoDB antes de iniciar o servidor
const startServer = async () => {
  try {
    await connectDB();
    app.listen(process.env.PORT, () => {
      console.log(`Servidor REST rodando na porta ${process.env.PORT}`);
    });
  } catch (error) {
    console.error('Erro ao iniciar servidor:', error);
    process.exit(1);
  }
};

startServer();