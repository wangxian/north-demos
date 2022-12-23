package org.example;

import top.xiqiu.north.annotation.Controller;
import top.xiqiu.north.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {
    @GetMapping("/")
    public void index(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/static/index.html");
    }
}
