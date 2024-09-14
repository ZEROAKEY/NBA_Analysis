module.exports = {
    devServer: {
      proxy: {
        '/api': {
          target: 'http://52.62.87.19:8888',  // 你的后端地址
          changeOrigin: true,  // 允许跨域
          pathRewrite: { '^/api': '' }  // 重写路径，将 '/api' 替换为空字符串
        }
      }
    }
  }
  