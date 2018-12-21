var tokenKey = "tokenKey";
var uidKey = "uidKey";
var menuKey = "menuKey";
var permButtonsKey = "permButtonsKey";
var userKey = "userKey";

var config = {
    serverUrl: '', // 服务器地址
    getToken: function () {
        return window.localStorage.getItem(tokenKey);
    },

    putToken: function (token) {
        return window.localStorage.setItem(tokenKey, 'Bearer '+token);
    },

    clear: function () {
        window.localStorage.clear();
        return window.sessionStorage.clear();
    },
    // 获取缓存的菜单
    getMenus: function () {
        var menus= window.sessionStorage.getItem(menuKey);
        if (menus) {
            return JSON.parse(menus);
        }
    },
    // 缓存菜单
    putMenus: function (menus) {
        return window.sessionStorage.setItem(menuKey, JSON.stringify(menus));
    },
    // 获取缓存的权限按钮
    getPermButtons: function () {
        var permButtons= window.sessionStorage.getItem(permButtonsKey);
        if (permButtons) {
            return JSON.parse(permButtons);
        }
    },
    // 缓存权限按钮
    putPermButtons: function (permButtons) {
        return window.sessionStorage.setItem(permButtonsKey, JSON.stringify(permButtons));
    },
    // 获取缓存的token
    getUid: function () {
        var uidKey= window.sessionStorage.getItem(uidKey);
        if (uidKey) {
            return JSON.parse(uidKey);
        }
    },
    // 缓存Uid
    putUid: function (uid) {
        return window.sessionStorage.setItem(uidKey, JSON.stringify(uid));
    },
    // 当前登录的用户
    getUser: function () {
        var user= window.sessionStorage.getItem(userKey);
        if (user) {
            return JSON.parse(user);
        }
    },
    // 缓存user
    putUser: function (user) {
        return window.sessionStorage.setItem(uidKey, JSON.stringify(user));
    }
};
