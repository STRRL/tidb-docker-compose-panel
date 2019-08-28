module.exports = {
    outputDir: 'target/dist',
    assetsDir: 'static',
    lintOnSave: true,
    devServer: {
      proxy: {
        '/': {
          target: 'http://localhost:18080/',
          ws:false,
          changeOrigin: true,
          pathRewrite: {
            '^/': ''
          }
        }
      }
    }
  };
