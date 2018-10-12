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
    private Map<Object, Session> sessions = Collections.synchronizedMap(new HashMap<>());

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
    public void addSession(Object uid, Session wsSession) {
        sessions.put(uid, wsSession);
    }

    /**
     * 删除 session
     * @param uid
     */
    public void delSession(Object uid) {
        sessions.remove(uid);
    }

    public void sentMessage(Message message, Object uid) throws IOException {
        Iterator<Map.Entry<Object, Session>> iterator = this.sessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Session> next = iterator.next();
            next.getValue().getBasicRemote().sendText(JSON.toJSONString(message));
        }
        log.info("发送用户" + uid + "===>消息: " + message + ". 成功");
    }
}
