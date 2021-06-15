package com.cqu.newblog.controller;

import com.cqu.newblog.dao.TagDao;
import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  20:17
 */
@Controller
@RequestMapping("/")
public class AboutService {
    @Autowired
    TagDao tagDao;
    @Autowired
    BlogService blogService;
    @Autowired
    BlogTagService blogTagService;
    @Autowired
    TypeService typeService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;

    @GetMapping("/about")
    public String index(Model model) {

        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);
        model.addAttribute("blogList",blogList);
        return "about";

    }
}
