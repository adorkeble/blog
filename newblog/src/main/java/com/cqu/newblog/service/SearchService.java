package com.cqu.newblog.service;

import com.cqu.newblog.dao.SearchDao;
import com.cqu.newblog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/4  19:37
 */
@Service
public class SearchService {
    @Autowired
    SearchDao searchDao;
    public void updateBlogVo(BlogVo blogVo) {
        String id = blogVo.getId();
        String content = blogVo.getContent();
        searchDao.updateBlogVo(id,content);
    }
}
