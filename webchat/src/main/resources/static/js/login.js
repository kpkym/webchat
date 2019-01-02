$('.register_tip').popover({
    trigger: 'hover'
});

let usernameInput = $("#inputUsername");
let passwordInput = $("#inputPassword");
usernameInput[0].oninput = validCSS;
passwordInput[0].oninput = validCSS;

function validCSS() {
    if (!validateUserData($(this).val())) {
        // 如果不合法
        $(this).removeClass("is-valid");
        $(this).addClass("is-invalid");
        $("#subBtn").prop("disabled", true);
    } else {
        $(this).removeClass("is-invalid");
        $(this).addClass("is-valid");
    }
    // 如果用户名跟密码都合法
    if (usernameInput.hasClass("is-valid") && passwordInput.hasClass("is-valid")) {
        $("#subBtn").prop("disabled", false);
    }
}

// 校验输入信息 并修改提交按钮
function validateUserData(str) {
    // todo
    return true;
}

// Ajax 请求注册或登陆
$("#subBtn").click(function () {
    let username = usernameInput.val();
    let password = passwordInput.val();
    let rememberMe = $("#inputRememberMe").prop("checked");
    let userData = {
        "username": username,
        "password": password,
        "rememberMe": rememberMe
    };
    if (!validateUserData(username)) {
        // todo 账号不合法
        layer.msg("账号不合法")
        return;
    }
    if (!validateUserData(password)) {
        // todo 密码不合法
        layer.msg("密码不合法")
        return;
    }
    login(userData);
});

function login(userData, loginAfterRegister = false) {
    $.post(APP_PATH + "/users/login", userData, function (data) {
        if (1 === data.code && loginAfterRegister) {
            // 注册后又登陆失败，一般是服务器问题
            layer.msg("登陆失败，一般是服务器问题");
        } else if (1 === data.code) {
            // 登陆失败, 询问是否注册
            layer.confirm(data.data.msg + " 或不存在此账号\n是否用此用户名进行注册？", {
                btn: ['确认', '取消'] //按钮
            }, function () {
                register(userData);
            });
        } else if (0 === data.code) {
            // 登陆成功 跳转页面
            // todo
            layer.msg("登陆成功");
        } else {
            layer.msg("未知错误");
        }
    });
}

function register(userData) {
    $.post(APP_PATH + "/users/register", userData, function (data) {
        if (1 === data.code) {
            // 注册失败
            layer.msg("注册失败: " + data.data.msg);
        } else if (0 === data.code) {
            // 注册成功
            login(userData, true);
        } else {
            layer.msg("未知错误");
        }
    });
}