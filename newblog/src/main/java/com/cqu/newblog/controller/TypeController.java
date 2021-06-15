package com.cqu.newblog.controller;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import com.cqu.newblog.service.UserService;
import com.cqu.newblog.vo.TypeDetailVo;
import com.cqu.newblog.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/30  20:31
 */
@Controller
@RequestMapping("/")
public class TypeController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @GetMapping("/type")
    public String index(Model model) {
        int activeTypeId=1;
        model.addAttribute("activeTypeId",activeTypeId);

        List<TypeVo> typeVos = typeService.getTypeVoList();
        model.addAttribute("typeVos", typeVos);
        Integer typeNum=typeVos.size();
        typeNum=typeNum==null?0:typeNum;
        model.addAttribute("typeNum",typeNum);
        int typeId = typeVos.get(0).getType().getId();
        List<TypeDetailVo>  typeDetailVos= blogService.getTypeDetailVoByTypeId(typeId);
        model.addAttribute("typeDetailVos",typeDetailVos);
        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);
        model.addAttribute("blogList",blogList);
        return "types";

    }
    @GetMapping("/type/{id}")
    public String typeToId(Model model, @PathVariable("id") int id) {
        int activeTypeId=id;
        model.addAttribute("activeTypeId",activeTypeId);

        List<TypeVo> typeVos = typeService.getTypeVoList();
        model.addAttribute("typeVos", typeVos);
        Integer typeNum=typeVos.size();
        typeNum=typeNum==null?0:typeNum;
        model.addAttribute("typeNum",typeNum);

        int typeId = id;
        List<TypeDetailVo>  typeDetailVos= blogService.getTypeDetailVoByTypeId(typeId);
        model.addAttribute("typeDetailVos",typeDetailVos);
        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);
        model.addAttribute("blogList",blogList);
        return "types";

    }

}

