package com.cqu.newblog.vo;


import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/3  12:57
 */
@SolrDocument
@Data
public class BlogVo {
    @Id
    private String id;
    @Field
    private String content;

}
