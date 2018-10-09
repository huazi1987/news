package com.news.common.page;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;

public class Pagination extends PageRequest implements Serializable {

    private static final long serialVersionUID = -6506616916317342758L;

    public Pagination(int page, int size) {
        super(page, size);
    }

    /**
     * 当前起始位置
     * 
     * @return
     */
    public int getStart() {
        int result = (getPageNumber() - 1) * getPageSize();
        if (result < 0)
            result = 0;
        return result;
    }
}
