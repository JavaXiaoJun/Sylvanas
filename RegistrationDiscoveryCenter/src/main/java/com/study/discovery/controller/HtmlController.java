package com.study.discovery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jn50 on 2018/1/31.
 */
@Controller
@RequestMapping("/rest/pages/*")
public class HtmlController {

    @RequestMapping("/apps")
    public String apps(Model model) {
        return "apps";
    }

    @RequestMapping("/appManager")
    public String appManager(Model model) {
        return "appManager";
    }

    @RequestMapping("/feedback")
    public String feedback(Model model) {
        return "feedback";
    }

    @RequestMapping("/team")
    public String group(Model model) {
        return "team";
    }

    @RequestMapping("/news")
    public String news(Model model) {
        return "news";
    }

}
