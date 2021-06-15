package com.cqu.newblog.controller;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.redis.NameKey;
import com.cqu.newblog.redis.RedisService;
import com.cqu.newblog.redis.UserKey;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import com.cqu.newblog.service.UserService;
import com.cqu.newblog.vo.IndexVo;
import com.cqu.newblog.vo.TagVo;
import com.cqu.newblog.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  14:12
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;


    @GetMapping("/index")
    public String index(Model model) {
        //内容
        List<IndexVo> indexVos = blogService.getIndexVoList();
        List<Blog> blogs = new ArrayList<Blog>();
        model.addAttribute("indexVos", indexVos);
        //博客数量
        int totalElements = indexVos.size();
        model.addAttribute("totalElements", totalElements);
        //分类
        List<TypeVo> typeVos = typeService.getTypeVoList();
        model.addAttribute("typeVos", typeVos);
        //标签
        List<TagVo> tagVos = tagService.getTagVoList();
        model.addAttribute("tagVos", tagVos);


        //最新推荐
        List<Blog> blogRecent=blogService.getRecentBlog(5);
        model.addAttribute("blogRecent",blogRecent);

        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);;
        model.addAttribute("blogList",blogList);

        return "index";
    }
    @GetMapping("/test")
    public String test(Model model) {
        String name="测试哈哈哈哈";
        redisService.set(NameKey.testName, "1",name);
        String s = redisService.get(NameKey.testName, "1", String.class);
        if(s!=null){
            System.out.println("redis Test");
            System.out.println(s);
        }
        model.addAttribute("name",s);
        return "hello";
    }


}
