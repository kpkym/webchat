<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/bootstrap-4.1.3/css/bootstrap.css"/>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div id="chatArea"></div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="input-group input-group-lg">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroup-sizing-lg">输入框</span>
                </div>
                <input id="msgInput" type="text" class="form-control" aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-lg">
            </div>
        </div>
        <div class="col-md-2 offset-md-10">
            <button id="msgBtn" type="button" class="btn btn-primary btn-lg btn-block" style="display: block;">发送
            </button>
        </div>
    </div>

</div>

<script src="/static/jquery/jquery-3.3.1.js"></script>
<script src="/static/bootstrap-4.1.3/js/bootstrap.js"></script>
<script>
    let chatArea = $("#chatArea");
    let ws = new WebSocket("ws://" + location.host + "/webchat");
    let msgBtn = $("#msgBtn");

    ws.onmessage = function (event) {
        console.log("接受消息: " + event.data);
        putMsg(event.data);
    };

    // 当点击提交按钮
    msgBtn.click(function () {
        let msg = $('#msgInput').val();
        putMsg(msg, false);
        ws.send(msg);
        $('#msgInput').val("");
    });


    // 如果不是自己发送的消息，文字就在左边，否则浮动到右边
    function putMsg(msg, notSelf = true) {
        let p = notSelf ? $("<p class='alert alert-success'></p>") : $("<p class='alert alert-primary' style='text-align: right'></p>");
        p.text(msg).appendTo(chatArea);
    }

</script>
</body>
</html>
