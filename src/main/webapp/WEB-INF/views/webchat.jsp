<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Title</title>
    <%@ include file="/component/bootcss.jsp" %>
    <style>
        .alert-auto {
            display: inline-block;
            margin: 0.2em;
        }
        .btn {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>

<h3>当前登陆昵称：${nickName}</h3>

<div class="container-fluid">
    <div class="row">
        <div id="chatArea" class="col-12"></div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="input-group input-group-lg">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroup-sizing-lg">输入框</span>
                </div>
                <input id="msgInput" type="text" class="form-control" aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-lg">
            </div>
        </div>
        <div class="col-12 col-md-2 offset-md-10">
            <button id="msgBtn" type="button" class="btn btn-primary btn-lg btn-block" style="display: block;">发送
            </button>
        </div>
    </div>
</div>

<%@ include file="/component/bootjs_jquery.jsp" %>
<script>
    $(function () {
        $("body").tooltip({"trigger": "click hover", "selector": ".msg", "html": true});
    });
</script>

<script>
    let chatArea = $("#chatArea");
    let ws = new WebSocket("ws://" + location.host + APP_PATH + "/webchat");
    let msgBtn = $("#msgBtn");

    ws.onmessage = function (event) {
        let data = JSON.parse(event.data);
        putMsg(data);
    };

    // 在input框输入回车时 触发提交按钮点击事件
    $('#msgInput').keypress(function (e) {
        if (e.which === 13) {
            msgBtn.click();
        }
    });

    // 当点击提交按钮
    msgBtn.click(function () {
        let msgInput = $('#msgInput');
        let val = msgInput.val().trim();
        msgInput.val("");
        if (val == "") {
            layer.msg("不能输入为空");
            return;
        }
        ws.send(val);
    });

    // 如果不是自己发送的消息，文字就在左边，否则移动到右边
    function putMsg(msg) {
        let date = new Date(msg.date);
        let wrapDiv = $("<div class='row'></div>");

        // 聊天气泡
        let contentAreaDiv = $("<div class='col' style='padding: 0'></div>");
        let contentArea = $("<p class='alert msg alert-auto' data-toggle='tooltip' data-placement='top'></p>");
        contentArea.text(msg.msg);
        contentAreaDiv.append(contentArea);

        let formateDate = date.getFullYear() + "-"
            + (date.getMonth() + 1) + "-"
            + date.getDate() + " "
            + date.getHours() + ":"
            + date.getMinutes() + ":"
            + date.getSeconds();
        contentArea.attr("title", "发送人：" + msg.nickName + "<br>发送时间：" + formateDate);

        if (msg.nickName === "${nickName}") {
            contentArea.addClass("alert-primary");
            wrapDiv.css("text-align", "right");
        } else {
            contentArea.addClass("alert-success");
            // 显示发送信息人的昵称
            wrapDiv.append($("<div class='col-3 col-sm-2 col-md-1' style='padding: 0'><button class='btn btn-warning btn-lg btn-block disabled'>" +
                msg.nickName + ": </button></div>"));
        }

        wrapDiv.append(contentAreaDiv).append("<br>").appendTo(chatArea);
    }
</script>
</body>
</html>
