package com.ou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kpkym
 * Date: 2018-09-17 21:54
 */
@Controller
public class DispatcharController {
    @RequestMapping("/webchat")
    public String webChat() {
        return "webchat";
    }
}
