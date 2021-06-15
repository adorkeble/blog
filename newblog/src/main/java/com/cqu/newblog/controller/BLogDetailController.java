package com.cqu.newblog.controller;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.UserService;
import com.cqu.newblog.vo.BlogDetailVo;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/5  13:52
 */
@Controller
@RequestMapping("/blogDetail")
public class BLogDetailController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public String blogDetail(Model model, @PathVariable("id")Integer blogId) {

        System.out.println("---blogid----");
        System.out.println(blogId);
        //博客正文
        BlogDetailVo blogDetailVo = blogService.getBlogDetailVo(blogId);
        model.addAttribute("blogDetailVo", blogDetailVo);

        System.out.println("copyright");
        System.out.println(blogDetailVo.getBlog().isCopyRight());

        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user", user);
        System.out.println("user");
        System.out.println(user.toString());
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);

        model.addAttribute("blogList", blogList);
        System.out.println("blogList");
        System.out.println(blogList.toString());
        return "blog";
    }
}
