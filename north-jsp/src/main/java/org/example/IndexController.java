package org.example;

import top.xiqiu.north.annotation.Controller;
import top.xiqiu.north.annotation.GetMapping;
import top.xiqiu.north.core.ModelAndView;

@Controller
public class IndexController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("test.jsp", "user", "wx");
    }
}
