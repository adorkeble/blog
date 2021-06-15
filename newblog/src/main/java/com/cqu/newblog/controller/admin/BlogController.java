package com.cqu.newblog.controller.admin;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.TagService;
import com.cqu.newblog.service.TypeService;
import com.cqu.newblog.utils.MarkdownUtils;
import com.cqu.newblog.vo.BlogInputVo;
import com.cqu.newblog.vo.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/9  22:10
 */
@Controller()
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/blogs")
    public String Blogs(Model model, User user) {
        if (user == null) {
            return "admin/login";
        }
        //博客列表
        List<IndexVo> indexVoList = blogService.getIndexVoList();
        model.addAttribute("indexVoList", indexVoList);

        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/blogs";
    }

    @PostMapping("/blogs/input")
    public String blogInput(Model model, User user,
                            @ModelAttribute BlogInputVo blogInputVo
                           ) {
        if (user == null) {
            return "admin/login";
        }

        //blog数据
        boolean isSucess = blogService.createBlogInputVo(blogInputVo,user);

        if(!isSucess){
            return "admin/blogs-input";
        }

        //发布成功
        model.addAttribute("message","success");
        return "redirect:/admin/blogs";

    }

    @GetMapping("/blog/{id}/delete")
    public String delete(Model model, User user,@PathVariable("id") int id){
        if (user == null) {
            return "admin/login";
        }
        blogService.delete(id);
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blog/{id}/input")
    public String edit(Model model, User user,@PathVariable("id") int id){
        if (user == null) {
            return "admin/login";
        }
        Blog blog = blogService.getBlogAllById(id);
        String content = blog.getContent();
        String markdownToHtml = MarkdownUtils.markdownToHtml(content);
        blog.setContent(markdownToHtml);
        Type type=typeService.getTypeByTypeId(blog.getTypeId());
        BlogInputVo blogInputVo = new BlogInputVo();
        blogInputVo.setBlog(blog);
        blogInputVo.setType(type);


        model.addAttribute("blogInputVo",blogInputVo);

        //分类列表
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
        //标签列表
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("tagList",tagList);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/blogs-input";


    }

    @GetMapping("/blog")
    public String blogto(Model model, User user) {
        if (user == null) {
            return "admin/login";
        }
        BlogInputVo blogInputVo = new BlogInputVo();
        model.addAttribute("blogInputVo",blogInputVo);

        //分类列表
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
        //标签列表
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("tagList",tagList);
        //联系我
        model.addAttribute("user", user);
        //最新博客
        List<Blog> blogList = blogService.getRecentBlog(3);
        model.addAttribute("blogList", blogList);
        return "admin/blogs-input";
    }

}
