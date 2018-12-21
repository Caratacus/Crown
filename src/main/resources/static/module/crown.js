var crown = {
    get: function (url, params, success) {
        params.method = "GET";
        return this.request(url, params, success);
    },
    post: function (url, params, success) {
        params.method = "POST";
        return this.request(url, params, success);
    },
    put: function (url, params, success) {
        params.method = "PUT";
        return this.request(url, params, success);
    },
    delete: function (url, params, success) {
        params.method = "DELETE";
        return this.request(url, params, success);
    },
    request: function (url, params, success) {
        params.method = params.method ? params.method : "GET";
        if (!params.contentType) {
            switch (params.method) {
                case "GET":
                    params.contentType = '';
                    break;
                case "POST":
                    params.data = JSON.stringify(params.data);
                    params.contentType = 'application/json; charset=UTF-8';
                    break;
                case "PUT":
                    params.data = JSON.stringify(params.data);
                    params.contentType = 'application/json; charset=UTF-8';
                    break;
                case "DELETE":
                    params.contentType = '';
                    break;
                default:
                    params.contentType = '';
            }
        }
        $.ajax({
            url: config.serverUrl + url,
            data: params.data ? params.data : {},
            type: params.method,
            contentType: params.contentType,
            xhrFields: {
                withCredentials: false
            },
            async: params.async !== false,
            crossDomain: true,
            success: function (data) {
                success(data);
            },
            error: function (xhr) {
                if (xhr.responseJSON.status === 401) {
                    config.clear();
                    layer.msg('登录过期', {icon: 2}, function () {
                        location.href = 'login.html';
                    });
                    return false;
                }
                if (xhr.responseJSON.status === 404) {
                    location.href = '404.html';
                    return false;
                }
                if (xhr.responseJSON.status === 500) {
                    location.href = '500.html';
                    return false;
                }
                layer.msg(JSON.parse(xhr.responseText).msg, {icon: 5});
            },
            beforeSend: function (xhr) {
                var token = config.getToken();
                if (token) {
                    xhr.setRequestHeader('Authorization', token);
                }
            }
        });
    },
    hasPerm: function (buttonAlias) {
        var permButtons = config.getPermButtons();
        if (permButtons) {
            for (var i = 0; i < permButtons.length; i++) {
                if (buttonAlias == permButtons[i]) {
                    return true;
                }
            }
        }
        return false;
    },
    getSearchForm: function () {
        var val = {};
        var inputVals = $('#searchForm').find(':input').filter(function () {
            return $.trim(this.value).length > 0;
        }).serializeArray();
        $(inputVals).each(function () {
            if (val[this.name] !== undefined) {
                if (!Array.isArray(val[this.name])) {
                    val[this.name] = [val[this.name]];
                }
                val[this.name].push(this.value);
            } else {
                val[this.name] = this.value;
            }
        });
        return val;
    }
};

