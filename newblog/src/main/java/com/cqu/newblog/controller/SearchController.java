package com.cqu.newblog.controller;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.BlogService;
import com.cqu.newblog.service.UserService;
import com.cqu.newblog.vo.BlogVo;
import com.cqu.newblog.vo.IndexVo;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.cqu.newblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/3  12:56*/


@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired(required =false)
    private SolrTemplate solrTemplate;
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @PostMapping("/value")
    public String serachBlog(Model model, @RequestParam("searchValue") String searchValue){

        //搜索内容
        model.addAttribute("searchContent",searchValue);
        //Spring data 通用条件模板
        Criteria c = Criteria.where("zh_all").is(searchValue);
        //查询条件对象
        Query query = Query.query(c);
        //分页
        query.setPageRequest(PageRequest.of(0, 100));
        //spring data 中排序规则
        query.addSort(Sort.by(Sort.Direction.ASC, "id"));


        ScoredPage<BlogVo> page = solrTemplate.queryForPage("search", query, BlogVo.class);

        Float maxScore = page.getMaxScore();

        if (page == null) {
            System.out.println("不存在该关键词");
        }
        List<BlogVo> blogVos = page.getContent();
        Integer searchNum=blogVos.size();
        solrTemplate.commit("search");
        if(searchNum!=null){
            model.addAttribute("searchNum",searchNum);
        }
        List<IndexVo> indexVos=blogService.getIndexVoListByBlogVoList(blogVos);


        if(indexVos!=null){
            model.addAttribute("indexVos", indexVos);
        }

        //联系我
        User user = userService.getUserByUserId(1);
        model.addAttribute("user",user);
        //最新博客
        List<Blog> blogList=blogService.getRecentBlog(3);;
        model.addAttribute("blogList",blogList);
        return "search";


    }

}
