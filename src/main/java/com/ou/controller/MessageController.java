package com.ou.controller;

import com.ou.util.WebSocketSessionMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author kpkym
 * Date: 2018-10-13 12:32
 */
@RestController
public class MessageController {
    @RequestMapping(value="/setNickName", produces = "application/json;charset=UTF-8")
    public Object setNickName(@RequestParam(defaultValue = "") String nickName, HttpSession session) {
        nickName = StringUtils.trimWhitespace(nickName);

        if ("".equals(nickName)) {
            return "不能输入为空";
        }
        if (WebSocketSessionMap.getWebSocketSessionMap().hasNickName(nickName)) {
            return "已经存在当前昵称，换一个";
        }

        if (null == session.getAttribute("nickName")) {
            session.setAttribute("nickName", nickName);
        }
        return "ok";
    }
}
