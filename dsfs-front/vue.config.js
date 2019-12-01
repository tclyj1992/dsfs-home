module.exports = {
    devServer: {
        proxy: {
            '/dsfs-server': {
                //要访问的跨域的域名
                target: 'http://localhost:8084',
                ws: false, // 是否启用websockets
                changOrigin: true,
            }
        }
    }
}
