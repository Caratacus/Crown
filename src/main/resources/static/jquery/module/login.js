if (storage.getToken()) {
    location.replace('index.html');
}
var vm = new Vue({
    el: '#loginapp',
    data: {
        loginName: 'crown',
        password: 'crown'
    },
    beforeCreate: function () {
        if (self != top) {
            top.location.href = self.location.href;
        }
    },
    methods: {
        login: function () {
            request.post(config.serverUrl + '/account/token', {data: vm.$data}, function (data) {
                storage.putUid(data.result.uid);
                storage.putToken(data.result.token);
                location.replace('index.html');
            });
        }
    }
});