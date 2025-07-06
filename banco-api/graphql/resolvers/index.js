const queryResolvers = require('./queryResolvers');
const mutationResolvers = require('./mutationResolvers');

module.exports = {
  ...queryResolvers,
  ...mutationResolvers,
};