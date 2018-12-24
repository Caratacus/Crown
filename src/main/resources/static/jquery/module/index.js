var vm = new Vue({
    el: '#wrapper',
    data: {
        user: {},
        menuList: {},
        menus: [],
        main: config.serverUrl + "/views/index/crown.html"
    },
    methods: {
        getMenuList: function () {
            request.get(config.serverUrl + '/account/menus', {async: false}, function (data) {
                vm.menus = data.result;
            });
        },
        getUser: function () {
            /*$.getJSON(baseURL + "sys/user/info", function(r){
                vm.user = r.user;
            });*/
        },
        logout: function () {
            request.delete(config.serverUrl + '/account/token', {}, function () {
                storage.clear();
                location.href = 'login.html'
            });
        }
    },
    created: function () {
        this.$nextTick(function () {
            this.getMenuList();
            this.getUser();
        });
    }
});