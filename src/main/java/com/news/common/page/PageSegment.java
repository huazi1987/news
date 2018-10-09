package com.news.common.page;

import java.util.Iterator;

public class PageSegment implements Iterable<Integer> {
    private int perSize = 10;
    private int pageNumber;
    private int pageTotal;

    public PageSegment(int pageNumber, int pageTotal, int perSize) {
        this.pageNumber = pageNumber;
        this.pageTotal = pageTotal;
        this.perSize = perSize;
    }

    public PageSegment(int pageNumber, int pageTotal) {
        this.pageNumber = pageNumber;
        this.pageTotal = pageTotal;
    }

    public int getStartPage() {
        return pageNumber / perSize * perSize;
    }

    public int getEndPage() {
        int result = getStartPage() + perSize;
        return Math.min(pageTotal, result) - 1;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int page = getStartPage();
            private int max = getEndPage();

            @Override
            public boolean hasNext() {
                return page <= max;
            }

            @Override
            public Integer next() {
                return page++;
            }

            @Override
            public void remove() {}
        };
    }

    public int getSegmentSize() {
        return getEndPage() - getStartPage() + 1;
    }

    public int getSegmentPerSize() {
        return perSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageTotal() {
        return pageTotal;
    }
}
