package com.itheima.web.servlet;


import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.service.impl.BrandServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


// alll the methods for the same entity, put into one servlet

// /brand/add,  /brand/selectAll can all be mapped into the below path

//  不能 extend httpservlet，因为不使用doGet, doPost方法

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet{
    private BrandService brandService = new BrandServiceImpl();

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Brand> brands = brandService.selectAll();


        String jsonString = JSON.toJSONString(brands);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }


    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        Brand brand = JSON.parseObject(params, Brand.class);

        brandService.add(brand);

        response.getWriter().write("success");
    }



    /**
     * delete by id
     */
    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        BufferedReader br = request.getReader();
        String param = br.readLine();//json字符串


        int id = JSON.parseObject(param, int.class);


        brandService.deleteById(id);


        response.getWriter().write("delete by ID is successful");
    }


    /**
     * series delete
     */
    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. get the data from frontend [1,2,3]
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //  change to int[]
        int[] ids = JSON.parseObject(params, int[].class);

        //2. call method in service layer
        brandService.deleteByIds(ids);

        //3. response to the front end
        response.getWriter().write("series delete is successful");
    }

    /**
     * search by page
     LIMIT param1, param2   first param means the starting index,
     second param means the index number each page

     backend shall send 1. overall list, 2. total record count to the frontend
     frontend send  1. currentPage 2. pageSize to the backend
     */

    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.   get the currentpage and pagesize from frontend   url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

//        transfer the string type into int type
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //2.  call method
        PageBean<Brand> pageBean = brandService.selectByPage(currentPage, pageSize);

        //2. change to JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. send the data to the front end
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }




    /**
     * Conditional search by splitting the pages
     */

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // get the object of searching conditions
        BufferedReader br = request.getReader();
        String params = br.readLine();

        // change to Brand type
        Brand brand = JSON.parseObject(params, Brand.class);


        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage,pageSize,brand);


        String jsonString = JSON.toJSONString(pageBean);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }



}
