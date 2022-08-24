package com.itheima.pojo;

import java.util.List;

//   JavaBean  used for by-page search,  enclose the current page data -- rows,
//   and total record number -- totalCount
public class PageBean<T> {

    private int totalCount;

    // data in current page
    private List<T> rows;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
