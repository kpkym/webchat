package com.ou.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * WebSocketSession的管理
 *
 * @author kpkym
 * Date: 2018-09-17 10:59
 */
@Slf4j
public class WebSocketSessionMap {
    private static WebSocketSessionMap webSocketSessionMap = new WebSocketSessionMap();
    private Map<HttpSession, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    private WebSocketSessionMap(){}

    /**
     * 单例模式
     *
     * @return
     */
    public static WebSocketSessionMap getWebSocketSessionMap() {
        return WebSocketSessionMap.webSocketSessionMap;
    }

    /**
     * 添加 session
     * @param wsSession
     */
    public void addSession(HttpSession httpSession, Session wsSession) {
        sessions.put(httpSession, wsSession);
    }

    /**
     * 删除 session
     * @param httpSession
     */
    public void delSession(HttpSession httpSession) {
        sessions.remove(httpSession);
    }

    public void sentMessage(String msg, HttpSession httpSession) throws IOException {
        Iterator<Map.Entry<HttpSession, Session>> iterator = this.sessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<HttpSession, Session> next = iterator.next();
            // 通知非当前对象
            if (!next.getKey().equals(httpSession)) {
                next.getValue().getBasicRemote().sendText(msg);
            }
        }
        log.info("发送用户" + httpSession + "===>消息: " + msg + ". 成功");
    }
}
