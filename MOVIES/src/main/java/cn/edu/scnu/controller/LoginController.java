package cn.edu.scnu.controller;

import cn.edu.scnu.entity.User;
import cn.edu.scnu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public String dologin(String username, String pwd, HttpSession session) {
        User user = userService.login(username, pwd);
        if (user != null) {
            session.setAttribute("user", user);
            return "成功";
        } else {
            return "失败";
        }
    }

    @RequestMapping("/register")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public String doRegister(String username, String pwd) {
        if (username == null || pwd == null) {
            return "Username and password must not be null";
        }

        boolean isRegistered = userService.register(username, pwd);
        if (isRegistered) {
            return "注册成功！";
        } else {
            return "注册失败，用户名已存在！";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "/login";
    }
}
