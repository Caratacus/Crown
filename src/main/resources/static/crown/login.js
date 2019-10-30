$(function () {
    validateKickout();
    $('#captchaSp').sliderCaptcha({
        repeatIcon: 'fa fa-redo',
        setSrc: function () {
            return ctx + "img/slidercaptcha.jpg";
        },
        onSuccess: function () {
            login();
        },
        remoteUrl: ctx + "captcha"
    });
});

function login() {
    var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    if (username==null || username==''){
        $.modal.msg("请输入您的用户名");
        $('#captchaSp').sliderCaptcha('reset');
        return;
    }
    if (password==null || password==''){
        $.modal.msg("请输入您的密码");
        $('#captchaSp').sliderCaptcha('reset');
        return;
    }
    $.modal.loading("正在验证登录，请稍后...");
    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            "username": username,
            "password": password
        },
        success: function (result) {
            var status = result.status;
            if (status >= 200 && status <= 299) {
                location.href = ctx + 'index';
            } else {
                $.modal.closeLoading();
                $('.imgcode').click();
                $.modal.msg(result.msg);
                $('#captchaSp').sliderCaptcha('reset');
            }
        }
    });
}

function validateKickout() {
    if (getParam("kickout") == 1) {
        layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
                icon: 0,
                title: "系统提示"
            },
            function (index) {
                //关闭弹窗
                layer.close(index);
                if (top != self) {
                    top.location = self.location;
                } else {
                    var url = location.search;
                    if (url) {
                        var oldUrl = window.location.href;
                        var newUrl = oldUrl.substring(0, oldUrl.indexOf('?'));
                        self.location = newUrl;
                    }
                }
            });
    }
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}