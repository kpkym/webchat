<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/component/bootcss.jsp" %>
    <style>
        .alert-auto {
            display: inline-block;
            margin: 0.2em;
        }
    </style>
</head>
<body>

<h3>当前随机标识符：${uid}</h3>

<div class="container-fluid">
    <div class="row">
        <div id="chatArea" class="col-sm-12"></div>
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

<%@ include file="/component/bootjs_jquery.jsp" %>
<script>
    $(function () {
        $("body").tooltip({"trigger": "hover", "selector": ".msg"});
    });
</script>

<script>
    let chatArea = $("#chatArea");
    let ws = new WebSocket("ws://" + location.host + APP_PATH + "/webchat");
    let msgBtn = $("#msgBtn");

    ws.onmessage = function (event) {
        let data = JSON.parse(event.data);
        console.log("接受消息: " + event.data);
        putMsg(data);
    };

    // 当点击提交按钮
    msgBtn.click(function () {
        let msgInput = $('#msgInput');
        ws.send(msgInput.val());
        msgInput.val("");
    });


    // 如果不是自己发送的消息，文字就在左边，否则浮动到右边
    function putMsg(msg) {
        let date = new Date(msg.date);
        let wrapDiv = $("<div></div>");
        let contentArea = $("<p class='alert msg alert-auto' data-toggle='tooltip' data-placement='top'></p>");

        let formateDate = date.getFullYear() + "-"
            + (date.getMonth() + 1) + "-"
            + date.getDate() + " "
            + date.getHours() + ":"
            + date.getMinutes() + ":"
            + date.getSeconds();
        contentArea.attr("title", "发送时间：" + formateDate);

        if (msg.uid === ${uid}) {
            contentArea.addClass("alert-primary text-sm-right");
            wrapDiv.css("text-align", "right");
        } else {
            contentArea.addClass("alert-success");
        }

        contentArea.text(msg.msg);
        wrapDiv.append(contentArea).append("<br>").appendTo($(chatArea));
    }
</script>
</body>
</html>
