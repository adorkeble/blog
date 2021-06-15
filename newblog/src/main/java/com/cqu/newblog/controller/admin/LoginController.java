package com.cqu.newblog.controller.admin;

import com.alibaba.druid.util.StringUtils;
import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/7  21:14
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpServletResponse httpServletResponse
            , HttpSession session, RedirectAttributes attributes, Model model) {
        boolean isTruePassword = userService.login(httpServletResponse, username, password);
        if (isTruePassword) {
            return "redirect:/admin/index";
        } else {
            String message = null;
            if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                message = "用户名或密码错误！";
            }
            model.addAttribute("message", message);
            return "admin/login";
        }

    }

    @RequestMapping(path = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(Model model, User user) {
        if (user == null) {
            return "admin/login";
        }
        //欢迎语
        String word="Hi ,"+user.getUsername()+"!";
        model.addAttribute("word",word);
        //联系我
        model.addAttribute("user", user);

        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);

        model.addAttribute("blogList", blogList);
        return "admin/index";
    }

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("name", "hehhe");
        return "admin/register";
    }

    @PostMapping("/register/out")
    public String registered(Model model, @ModelAttribute User user) {

        boolean isCteateUser=userService.createUser(user);
        if(isCteateUser){
            return "admin/login";
        }
        model.addAttribute("user",new User());
        model.addAttribute("name", "hehhe");
        return "admin/register";
    }
}
