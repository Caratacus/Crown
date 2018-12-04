layui.define(function (exports) {

    var config = {
        serverUrl: 'http://localhost:8088', // 服务器地址
        scope: 'crown',  // 作用域
        autoRender: false,  // 窗口大小改变后是否自动重新渲染表格，解决layui数据表格非响应式的问题
        request: {
            //页码的参数名称，默认：page
            pageName: 'cursor',
            //每页数据量的参数名，默认：limit
            limitName: 'limit'
        },
        parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.status, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.result.total, //解析数据长度
                "data": res.result.records //解析数据列表
            };
        },
        response: {
            //规定成功的状态码，默认：0
            statusCode: 200
        },
        // 清空本地缓存
        removeAll: function () {
            layui.data(config.scope, null);
        },
        // 获取缓存的token
        getToken: function () {
            var token = layui.data(config.scope).token;
            if (token) {
                return JSON.parse(token);
            }
        },
        // 清除user
        removeToken: function () {
            layui.data(config.scope, {
                key: 'token',
                remove: true
            });
        },
        // 缓存token
        putToken: function (token) {
            layui.data(config.scope, {
                key: 'token',
                value: JSON.stringify('Bearer ' + token)
            });
        },
        // 获取缓存的token
        getUid: function () {
            var uid = layui.data(config.scope).uid;
            if (uid) {
                return JSON.parse(uid);
            }
        },
        // 清除Uid
        removeUid: function () {
            layui.data(config.scope, {
                key: 'uid',
                remove: true
            });
        },
        // 缓存Uid
        putUid: function (uid) {
            layui.data(config.scope, {
                key: 'uid',
                value: JSON.stringify(uid)
            });
        },
        // 导航菜单
        menus: [{
            name: '系统管理',
            icon: 'layui-icon-set',
            subMenus: [
                {
                    name: '项目主页',
                    url: 'console',
                    path: 'console.html'
                }, {
                    name: '用户管理',
                    url: 'user',  // 这里url不能带斜杠，因为是用递归循环进行关键字注册，带斜杠会被q.js理解为其他注册模式
                    path: 'views/user/index.html',
                    auth: 'post:/user/query'
                }, {
                    name: '角色管理',
                    url: 'role',
                    path: 'views/role/index.html',
                    auth: 'get:/role'
                },
                {
                    name: '菜单管理',
                    url: 'menu',
                    path: 'views/menu/index.html',
                    auth: 'get:/authorities'
                },
                {
                    name: '资源管理',
                    url: 'resource',
                    path: 'views/resource/index.html',
                    auth: 'get:/authorities'
                }
            ]
        }],
        // 当前登录的用户
        getUser: function () {
            var user = layui.data(config.scope).user;
            if (user) {
                return JSON.parse(user);
            }
        },
        // 缓存user
        putUser: function (user) {
            layui.data(config.scope, {
                key: 'user',
                value: JSON.stringify(user)
            });
        }
    };
    exports('config', config);
});
