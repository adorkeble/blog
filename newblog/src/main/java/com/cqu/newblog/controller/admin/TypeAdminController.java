package com.cqu.newblog.controller.admin;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import com.cqu.newblog.vo.BlogInputVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/11  14:13
 */
@Controller()
@RequestMapping("/admin")
public class TypeAdminController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @GetMapping("/types")
    public String typeList(Model model, User user){
        if (user == null) {
            return "admin/login";
        }
        //type列表
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/types";
    }
    @PostMapping("/types/input/post")
    public String typeInput(Model model,User user, @ModelAttribute Type type){


        boolean isSuccess=typeService.createType(type);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);

        if(isSuccess){
            return "redirect:/admin/types";

        }

        model.addAttribute("message","类型重复");
        return "admin/type-input";
    }
    @PostMapping("/types/input/update")
    public String typeUpdate(Model model,User user, @ModelAttribute Type type){
        if (user == null) {
            return "admin/login";
        }
        typeService.updateType(type);
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/input")
    public String edit(Model model, User user, @PathVariable("id") int id){
        if (user == null) {
            return "admin/login";
        }
        Type type = typeService.getTypeByTypeId(id);
        System.out.println("type");
        System.out.println(type.toString());
        model.addAttribute("type",type);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/type-input";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(Model model, User user, @PathVariable("id") int id){
        if (user == null) {
            return "admin/login";
        }
        typeService.delete(id);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/input")
    public String typeInputIn(Model model,User user){

        if (user == null) {
            return "admin/login";
        }
        Type type = new Type();
        model.addAttribute("type",type);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/type-input";
    }
}
