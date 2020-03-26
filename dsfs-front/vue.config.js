module.exports = {
    pages: {
        index: {
            entry: 'src/entrys/index/main.js',
            template: 'public/index.html',
            filename: 'index.html'
        },
        admin: {
            entry: 'src/entrys/admin/main.js',
            template: 'public/admin.html',
            filename: 'admin.html'
        }
    },
    devServer: {
        proxy: {
            '/dsfs-server': {
                //要访问的跨域的域名
                target: 'http://localhost:8080',
                ws: false, // 是否启用websockets
                changOrigin: true,
            }
        }
    }
}
