package com.news.common.page;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: liyj12
 * Date: 2014/9/28
 * Time: 11:22
 */
public class PageRecord<T> extends PageImpl<T> {
	private static final long serialVersionUID = -6042161078504534437L;
	private int pagePerMark = 20;

    public PageRecord(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageRecord(List<T> content) {
        super(content);
    }

    public int getStart(int pageNum) {
        int result = (pageNum - 1) * getSize();
        if (result < 0)
            result = 0;
        return result;
    }

    /**
     * 当前起始页码
     * @return
     */
    public int getPageStart() {
        return getNumber() / pagePerMark * pagePerMark + 1;
    }

    /**
     * 当前结束页码
     * @return
     */
    public int getPageEnd() {
        int start = getPageStart();
        int end = start + pagePerMark - 1;
        end = Math.min(end, getTotalPages());
        if (end < start)
            end = start;
        return end;
    }
}
