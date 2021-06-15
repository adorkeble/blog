package com.cqu.newblog.controller;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import com.cqu.newblog.service.UserService;
import com.cqu.newblog.vo.ArchivesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  18:17
 */
@Controller
@RequestMapping("/")
public class ArchiveController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @GetMapping("/archive")
    public String archives(Model model) {
        List<ArchivesVo> blogAllYearList=blogService.getBlogListForEveryYear();
        model.addAttribute("blogAllYearList",blogAllYearList);

        Integer blogCount = blogService.getBLogCount();
        model.addAttribute("blogCount",blogCount);
        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "archives";

    }

}
