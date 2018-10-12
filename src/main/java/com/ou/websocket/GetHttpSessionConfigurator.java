package com.ou.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author: kpkym
 * @date: 2018-09-16 21:12
 * @Description:
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    // fixme 由于切换账号时 session 不会变 所以需要修改此处, key 设置为识别账号唯一标示符
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}
