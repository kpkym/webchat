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

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/webchat")
    public String webChat(HttpSession session) {
        if (null == session.getAttribute("nickName")) {
            return "redirect:/";
        }
        return "webchat";
    }
}
