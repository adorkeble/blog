package com.cqu.newblog.controller.admin;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/11  21:18
 */
@Controller()
@RequestMapping("/admin")
public class TagAdminController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String typeList(Model model, User user) {
        if (user == null) {
            return "admin/login";
        }
        //type列表
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("tagList", tagList);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/tags";
    }

    @GetMapping("/tag/delete/{id}")
    public String delete(Model model, User user, @PathVariable("id") int id) {
        if (user == null) {
            return "admin/login";
        }
        tagService.delete(id);
        return "redirect:/admin/tags";

    }

    @GetMapping("/tag/update/{id}")
    public String update(Model model, User user, @PathVariable("id") int id) {
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag",tag);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/tag-input";


    }

    @PostMapping("tags/inputs/post")
    public String tagPost(Model model, User user, @ModelAttribute Tag tag) {

        boolean isSucess = tagService.createTag(tag);
        if (isSucess) {
            return "redirect:/admin/tags";
        }
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);

        model.addAttribute("message", "重复标签！");
        return "admin/tag-input";


    }
    @PostMapping("/tags/input/update")
    public String inputId(Model model, User user,@ModelAttribute Tag tag){

        tagService.updateTag(tag,tag.getId());

        return "redirect:/admin/tags";

    }

    @GetMapping("/tags/input")
    public String tagInput(Model model, User user) {
        if (user == null) {
            return "admin/login";
        }

        Tag tag = new Tag();
        model.addAttribute("tag", tag);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/tag-input";

    }

}

