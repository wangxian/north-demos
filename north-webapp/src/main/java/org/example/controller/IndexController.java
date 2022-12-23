package org.example.controller;

import top.xiqiu.north.annotation.*;
import top.xiqiu.north.core.ModelAndView;
import org.example.entity.Login;
import org.example.entity.User;
import top.xiqiu.north.core.NorthException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

@Controller("/")
public class IndexController {

    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new ModelAndView("index.html", "user", user);

        // // test jsp view engine
        // String user = "guest";
        // return new ModelAndView("test.jsp", "user", user);
    }

    /**
     * 测试 @RequestMapping
     * curl -X GET http://127.0.0.1:8080/request/mapping?name=guest
     * curl -X POST http://127.0.0.1:8080/request/mapping?name=guest
     */
    @RequestMapping("/request/mapping")
    public String requestMapping(String name) {
        return "user:" + name + "\nnow=" + new Date();
    }

    @GetMapping("/hello")
    public ModelAndView hello(String name) {
        int b = 0;
        int a= 15;
        if (true) {
            final int i = a / b;
        }

        if (name == null) {
            name = "World";
        }

        return new ModelAndView("hello.html", "name", name);
    }

    /**
     * http://127.0.0.1:8080/bean/json
     */
    @GetMapping("/bean/json")
    public User getUser() {
        User user = new User();
        user.email    = "a@company.com";
        user.password = "b**a";

        return user;
    }

    /**
     * http://127.0.0.1:8080/map/json
     */
    @GetMapping("/map/json")
    public HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", "a@company.com");
        map.put("name", "xiao.er");
        map.put("birthday", new Date());

        return map;
    }

    /**
     * 404 global page
     */
    @GetMapping("/404")
    public void page404(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        PrintWriter printWriter = response.getWriter();
        printWriter.write("404 NOT FOUND");
        printWriter.flush();
    }

    /**
     * 404 global page
     */
    @GetMapping("/500")
    public void page500(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");

        PrintWriter printWriter = response.getWriter();

        Throwable err = (Throwable) req.getAttribute("targetException");
        printWriter.write("发生错误 - 500 ERROR\n\n\n");

        if (err instanceof NorthException) {
            printWriter.write("异常类型：NorthException\n");
        } else if (err instanceof RuntimeException) {
            printWriter.write("异常类型：RuntimeException\n");
        } else {
            printWriter.write("异常类型：未知类型\n");
        }

        printWriter.write( "异常信息：" + err.getMessage());
        printWriter.flush();
    }

    /**
     * 测试编译参数 -parameters
     * <a href="http://127.0.0.1:8080/test500?id=188">test link</a>
     * <a href="http://127.0.0.1:8080/test500?id=88">test link</a>
     * <a href="http://127.0.0.1:8080/test500?id=20">test link</a>
     */
    @GetMapping("/test500")
    public String test(@RequestParam("id") Integer id) {
        if (id > 100) {
            throw new NorthException("抱歉，id不能大于100");
        } else if (id > 50) {
            throw new RuntimeException("抱歉，id不能大于50");
        }

        return String.format("/test500 --- id = %d", id);
    }

    /**
     * 测试 - 删除操作
     * curl -X DELETE http://127.0.0.1:8080/delete?id=1
     */
    @DeleteMapping("/delete")
    public void delete(HttpServletResponse resp, Long id) throws IOException {
        resp.getWriter().write("delete page id=" + id);
        resp.getWriter().flush();
    }

    /**
     * 测试 - 更新操作
     * curl -X PUT -d '{"email":"abc@def.com"}' http://127.0.0.1:8080/update?id=98
     */
    @PutMapping("/update")
    public void update(HttpServletResponse resp, Integer id, Login login) throws IOException {
        resp.getWriter().write("update id=" + id + ", email=" + login.email);
        resp.getWriter().flush();
    }
}
