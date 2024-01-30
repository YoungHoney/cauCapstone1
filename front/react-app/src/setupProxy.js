const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api/ancestor/name',
    createProxyMiddleware({
      target: 'http://3.39.127.44:8080',
      changeOrigin: true,
    })
  );

  app.use(
    '/api/search/clan',
    createProxyMiddleware({
      target: 'http://3.39.127.44:8080',
      changeOrigin: true,
    })
  );
  app.use(
    '/api/ancestor',
    createProxyMiddleware({
      target: 'http://3.39.127.44:8080',
      changeOrigin: true,
    })
  );

  app.use(
    '/api/search',
    createProxyMiddleware({
      target: 'http://3.39.127.44:8080',
      changeOrigin: true,
    })
  );
  
};

