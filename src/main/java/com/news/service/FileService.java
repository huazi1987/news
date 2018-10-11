package com.news.service;

import com.news.common.base.BaseService;
import com.news.common.page.PageRecord;
import com.news.common.page.Pagination;
import com.news.mapper.FileMapper;
import com.news.model.NFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FileService extends BaseService {

    @Autowired
    private FileMapper fileMapper;


    public Page<NFile> queryFileList(Pagination pagination){

        Map<String, Object> page = new HashMap<>(2);
        page.put("start", pagination.getStart());
        page.put("pageSize", pagination.getPageSize());

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);

        int count = fileMapper.findFileCount(params);

        List<NFile> list = fileMapper.findFileList(params);

        PageRecord<NFile> result = new PageRecord<>(list,pagination,count);

        return result;
    }


    public int insert(NFile NFile){
        try {
            return fileMapper.insert(NFile);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int update(NFile NFile){
        try {
            return fileMapper.update(NFile);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public NFile getFileById(String id){
        return fileMapper.findById(id);
    }
}
