package com.ou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author kpkym
 * Date: 2018-09-17 21:54
 */
@Controller
public class DispatcharController {
    @RequestMapping("/webchat")
    public String webChat(HttpSession session) {
        // 模拟uid
        if (null == session.getAttribute("uid")) {
            session.setAttribute("uid", (int)(Math.random() * 1000000));
        }
        return "webchat";
    }
}
