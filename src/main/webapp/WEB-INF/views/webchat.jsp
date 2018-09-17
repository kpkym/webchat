<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div id="chatArea"></div>
    <input id="msgInput" type="text" style="display: block;">
    <button id="msgBtn" style="display: block;">发送</button>

    <script src="/static/jquery-3.3.1.js"></script>
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
        });


        // 如果不是自己发送的消息，文字就在左边，否则浮动到右边
        function putMsg(msg, notSelf=true) {
            let p = notSelf ? $("<p></p>") : $("<p style='text-align: right'></p>");
            p.text(msg).appendTo(chatArea);
        }

    </script>
</body>
</html>
