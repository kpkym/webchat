package com.ou.websocket;

import com.ou.bean.Message;
import com.ou.util.WebSocketSessionMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;


@Slf4j
@ServerEndpoint(value = "/webchat", configurator = GetHttpSessionConfigurator.class)
public class WebChatWebsocket {
    private Session wsSession;
    private String nickName;

    @OnOpen
    public void open(Session session, EndpointConfig config) throws IOException {
        this.wsSession = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        this.nickName = (String) httpSession.getAttribute("nickName");

        // 如果没有设置nickName 忽略 || 简单处理
        if (null == nickName) {
            session.close();
            return;
        }
        WebSocketSessionMap.getWebSocketSessionMap().addSession(this.nickName, this.wsSession);
        log.info("用户: " + this.nickName + ". 建立了一个连接. wsSession id: " + this.wsSession.getId());
    }

    @OnMessage
    public void message(String msg) throws IOException {
        // 忽略空字符串
        if ("".equals(StringUtils.trimWhitespace(msg))) {
            return;
        }
        Message message = new Message(this.nickName, msg, new Date());
        WebSocketSessionMap.getWebSocketSessionMap().sentMessage(message, this.nickName);
        log.info("接受用户" + this.nickName + "===>消息: " + msg);
    }

    @OnClose
    public void close() {
        WebSocketSessionMap.getWebSocketSessionMap().delSession(this.nickName);
        log.info("用户: " + this.nickName + ". 断开了连接. wsSession id: " + this.wsSession.getId());
    }

    @OnError
    public void error(Throwable e) {
        log.info("出错：" + e.toString());
    }
}
