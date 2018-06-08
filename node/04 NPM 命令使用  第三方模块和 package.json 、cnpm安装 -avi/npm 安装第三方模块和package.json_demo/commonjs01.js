//1.npm i silly-datetime

//2.引入模块

var sd = require('silly-datetime');

var http=require('http');

var app=http.createServer(function(req,res){

    res.writeHead(200,{"Content-Type":"text/html;charset=utf-8"});

    //var d=sd.format(new Date(), 'YYYY-MM-DD HH:mm');

    var d=sd.format(new Date(), 'YYYY-MM-DD');

    res.write('你好 nodejs'+d);

    res.end();
})
app.listen(8002,'127.0.0.1');

