package com.ou.websocket;

import com.ou.bean.Message;
import com.ou.util.WebSocketSessionMap;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;


@Slf4j
@ServerEndpoint(value = "/webchat", configurator = GetHttpSessionConfigurator.class)
public class WebChatWebsocket {
    private Session wsSession;
    private Object uid;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.wsSession = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        this.uid = httpSession.getAttribute("uid");
        WebSocketSessionMap.getWebSocketSessionMap().addSession(this.uid, this.wsSession);
        log.info("用户: " + this.uid + ". 建立了一个连接. wsSession id: " + this.wsSession.getId());
    }

    @OnMessage
    public void message(String msg) throws IOException {
        Message message = new Message(this.uid, msg, new Date());
        WebSocketSessionMap.getWebSocketSessionMap().sentMessage(message, this.uid);
        log.info("接受用户" + this.uid + "===>消息: " + msg);
    }

    @OnClose
    public void close() {
        WebSocketSessionMap.getWebSocketSessionMap().delSession(this.uid);
        log.info("用户: " + this.uid + ". 断开了连接. wsSession id: " + this.wsSession.getId());
    }
}
