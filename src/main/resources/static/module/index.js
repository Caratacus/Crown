layui.define(['config', 'crown', 'layer'], function (exports) {
    var $ = layui.$;
    var config = layui.config;
    var crown = layui.crown;
    var layer = layui.layer;

    var index = {
        // 渲染左侧导航栏
        initLeftNav: function () {
            var menus = config.getMenus();
            $('.layui-layout-admin .layui-side').vm({menus: menus});
            crown.activeNav(Q.lash);
        },
        // 路由注册
        initRouter: function () {
            index.regRouter(config.getMenus());
            Q.init({
                index: 'user'
            });
        },
        // 使用递归循环注册
        regRouter: function (menus) {
            $.each(menus, function (i, data) {
                if (data.router) {
                    Q.reg(data.router, function () {
                        crown.loadView('components/' + data.path);
                    });
                }
                if (data.childrens) {
                    index.regRouter(data.childrens);
                }
            });
        },
        // 从服务器获取登录用户的信息
        getUser: function (success) {
            crown.get('/account/info', {}, function (data) {
                config.putUser(data.result);
                success(data.result);
            });
        },

        // 页面元素绑定事件监听
        bindEvent: function () {
            // 退出登录
            $('#logout').click(function () {
                layer.confirm('确定退出登录？', function (i) {
                    layer.close(i);
                    config.removeAll();
                    location.replace('login.html');
                });
            });
            // 主题设置
            $('#setTheme').click(function () {
                crown.popupRight('components/tpl/theme.html');
            });
            // 修改密码
            $('#setPassword').click(function () {
                crown.popupRight('components/tpl/password.html');
            });
            // 个人信息
            $('#setInfo').click(function () {
                crown.popupRight('components/tpl/userinfo.html');
            });
            // 消息
            $('#btnMessage').click(function () {
                crown.popupRight('components/tpl/message.html');
            });
        }
    };

    exports('index', index);
});
