package com.news.service;

import com.news.common.base.BaseService;
import com.news.common.page.PageRecord;
import com.news.common.page.Pagination;
import com.news.mapper.NewsMapper;
import com.news.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NewsService extends BaseService {

    @Autowired
    private NewsMapper newsMapper;


    public Page<News> queryNewsList(Pagination pagination){

        Map<String, Object> page = new HashMap<>(2);
        page.put("start", pagination.getStart());
        page.put("pageSize", pagination.getPageSize());

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);

        int count = newsMapper.findNewsCount(params);

        List<News> list = newsMapper.findNewsList(params);
        PageRecord<News> result = new PageRecord<>(list,pagination,count);

        return result;
    }


    public int insert(News news){
        try {
            return newsMapper.insert(news);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }


    public int update(News news){
        try {
            return newsMapper.update(news);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public News getNewsById(String id){
        return newsMapper.findById(id);
    }

}
