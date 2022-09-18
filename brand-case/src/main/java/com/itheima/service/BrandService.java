package com.itheima.service;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandService {

    List<Brand> selectAll();

    void add(Brand brand);

    void deleteById(int id);

    /**
     * series delete
     */
    void deleteByIds( int[] ids);

    /**
     * search by splitting the pages
     */
    PageBean<Brand>  selectByPage(int currentPage,int pageSize);

    /**
     * conditional search by splitting the pages
     */
    PageBean<Brand>  selectByPageAndCondition(int currentPage,int pageSize, Brand brand);

}
