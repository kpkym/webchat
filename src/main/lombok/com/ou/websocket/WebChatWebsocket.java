package com.ou.websocket;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author: kpkym
 * @date: 2018-09-15 23:14
 * @Description:
 */
@Slf4j
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class WebChatWebsocket {
    private Session wsSession;
    private HttpSession httpSession;

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.wsSession = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
    }

}
