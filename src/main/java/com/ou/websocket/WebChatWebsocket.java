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
@ServerEndpoint(value = "/webchat", configurator = GetHttpSessionConfigurator.class, encoders = MessageEncoder.class)
public class WebChatWebsocket {
    private Session wsSession;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.wsSession = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        WebSocketSessionMap.getWebSocketSessionMap().addSession(this.httpSession, this.wsSession);
        log.info("用户: " + this.httpSession + ". 建立了一个连接. wsSession id: " + this.wsSession.getId());
    }

    @OnMessage
    public void message(String msg) throws IOException, EncodeException {
        log.info("接受用户" + this.httpSession + "===>消息: " + msg);
        Message message = new Message(msg, new Date());
        WebSocketSessionMap.getWebSocketSessionMap().sentMessage(message, this.httpSession);
    }

    @OnClose
    public void close() {
        WebSocketSessionMap.getWebSocketSessionMap().delSession(this.httpSession);
        log.info("用户: " + this.httpSession + ". 断开了连接. wsSession id: " + this.wsSession.getId());
    }
}
