package com.study.monitor.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lf52 on 2018/7/10.
 */
@Controller
@RequestMapping("/monitor/*")
public class HtmlController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/details")
    public String details(Model model) {
        return "details";
    }

    @RequestMapping("/metrics")
    public String metrics(Model model) {
        return "metrics";
    }

    @RequestMapping("/searchs")
    public String searchs(Model model) {
        return "searchs";
    }

    @RequestMapping("/customize")
    public String customize(Model model) {
        return "customize";
    }

}
