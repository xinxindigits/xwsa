const path = require("path");

const resolve = dir => {
  return path.join(__dirname, dir);
};
module.exports = {
  css: {
    loaderOptions: {
      less: {
        javascriptEnabled: true
      }
    }
  },
  chainWebpack: config => {
    config.plugins.delete("prefetch");
    config.resolve.alias.set("@", resolve("src"));
  }
};
