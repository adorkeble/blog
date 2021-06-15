package com.cqu.newblog.controller;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import com.cqu.newblog.service.UserService;
import com.cqu.newblog.vo.TagDetailVo;
import com.cqu.newblog.vo.TagVo;
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
 * @date 2021/5/31  12:41
 */
@Controller
@RequestMapping("/")
public class TagController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @GetMapping("/tag")
    public String tag(Model model) {

        int activeTagId=1;
        model.addAttribute("activeTagId",activeTagId);

        List<TagVo> tagVoList = tagService.getTagVoList();
        Integer tagNum=tagVoList.size();
        tagNum=tagNum==null?0:tagNum;
        model.addAttribute("tagNum",tagNum);

        model.addAttribute("tagVoList",tagVoList);

        Integer tagId = tagVoList.get(2).getTag().getId();
        tagId=tagId==null?0:tagId;

        List<TagDetailVo> tagDetailVos = tagService.getTagDetailVoListByTagId(tagId);
        model.addAttribute("tagDetailVos",tagDetailVos);

        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);
        model.addAttribute("blogList",blogList);
        return "tags";


    }
    @GetMapping("/tag/{id}")
    public String tagForId(Model model, @PathVariable("id") int id) {

        int activeTagId=id;
        model.addAttribute("activeTagId",activeTagId);

        List<TagVo> tagVoList = tagService.getTagVoList();
        Integer tagNum=tagVoList.size();
        tagNum=tagNum==null?0:tagNum;
        model.addAttribute("tagNum",tagNum);

        model.addAttribute("tagVoList",tagVoList);

        Integer tagId = id;
        tagId=tagId==null?0:tagId;

        List<TagDetailVo> tagDetailVos = tagService.getTagDetailVoListByTagId(tagId);
        model.addAttribute("tagDetailVos",tagDetailVos);

        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);
        model.addAttribute("blogList",blogList);
        return "tags";


    }
}
