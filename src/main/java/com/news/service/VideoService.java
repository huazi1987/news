package com.news.service;

import com.news.common.base.BaseService;
import com.news.common.page.PageRecord;
import com.news.common.page.Pagination;
import com.news.mapper.VideoMapper;
import com.news.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VideoService extends BaseService {

    @Autowired
    private VideoMapper videoMapper;


    public Page<Video> queryVideoList(Pagination pagination){

        Map<String, Object> page = new HashMap<>(2);
        page.put("start", pagination.getStart());
        page.put("pageSize", pagination.getPageSize());

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);

        int count = videoMapper.findVideoCount(params);

        List<Video> list = videoMapper.findVideoList(params);
        PageRecord<Video> result = new PageRecord<>(list,pagination,count);

        return result;
    }


    public int insert(Video video){
        try {
            return videoMapper.insert(video);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int update(Video video){
        try {
            return videoMapper.update(video);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(String id){
        try {
            return videoMapper.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

}
