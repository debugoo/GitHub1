//npm install md5-node

//注意：以后安装模块的时候我们要把这个模块写入到package.json这个配置文件

//npm install md5-node --save    或者   npm install md5-node --save-dev


//npm install md5-node --save       写入到package.json 里面的  dependencies

//npm install md5-node --save-dev   写入到package.json 里面的  devDependencies


//cnpm install  表示安装package.json 里面的依赖


//有时候的话 npm install 模块

// 下载不下来  或者很慢



//如果安装完成淘宝镜像就可以用 cnpm 安装

//npm install 模块   /  cnpm install 模块




var md5=require('md5-node');

console.log(md5('123456'));



var sd = require('silly-datetime');

var d=sd.format(new Date(), 'YYYY-MM-DD');

console.log(d);
