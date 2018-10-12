package com.ou.websocket;

import com.alibaba.fastjson.JSON;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author kpkym
 * Date: 2018-10-12 14:05
 */
public class MessageEncoder implements Encoder.Text {
    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Object object) {
        return JSON.toJSONString(object);
    }
}
