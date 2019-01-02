package com.ou.util;

import com.alibaba.fastjson.JSON;
import com.ou.bean.Message;
import lombok.extern.slf4j.Slf4j;

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
    private Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());

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
    public void addSession(String nickName, Session wsSession) {
        sessions.put(nickName, wsSession);
    }

    /**
     * 删除 session
     * @param nickName
     */
    public void delSession(String nickName) {
        sessions.remove(nickName);
    }

    public boolean hasNickName(String nickName) {
        return sessions.containsKey(nickName);
    }

    public void sentMessage(Message message, String nickName) throws IOException {
        Iterator<Map.Entry<String, Session>> iterator = this.sessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Session> next = iterator.next();
            next.getValue().getBasicRemote().sendText(JSON.toJSONString(message));
        }
        log.info("发送用户" + nickName + "===>消息: " + message + ". 成功");
    }
}
