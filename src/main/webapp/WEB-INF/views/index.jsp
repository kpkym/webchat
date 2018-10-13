<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>

    <c:if test="${not empty nickName}">
    <%--如果设置了nickName--%>
    <c:redirect url="/webchat"/>
</c:if>

    输入昵称：<input type="text" id="nickname"><span id="tip"></span>
    <br>
    <button onclick="sendAjax()">send</button>

    <script>
        function sendAjax() {
            let xmlHttpRequest = new XMLHttpRequest();
            let val = document.getElementById("nickname").value.trim();

            xmlHttpRequest.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    if (this.responseText === "ok") {
                        location.href = "webchat";
                    } else if (this.responseText !== "ok") {
                        document.getElementById("tip").innerHTML = this.responseText;
                    } else {
                        document.getElementById("tip").innerHTML = "未知错误";
                    }
                }
            };

            xmlHttpRequest.open("get", "setNickName?nickName=" + val);
            xmlHttpRequest.send();
        }
    </script>
</body>

</html>