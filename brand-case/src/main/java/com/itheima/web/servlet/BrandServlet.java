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


//    all the methods for the same entity, put into one servlet
//    /brand/add,  /brand/selectAll can all be mapped into the below path
//    don't extend httpservlet，because we did not use doGet, doPost method here

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet{
    private BrandService brandService = new BrandServiceImpl();  // new the object of implementation class, assign it to interface type

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Brand> brands = brandService.selectAll();
        String jsonString = JSON.toJSONString(brands);  // change to JSON
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br = request.getReader();
        String params = br.readLine();
        Brand brand = JSON.parseObject(params, Brand.class);   // JSON.parseObject 是将Json字符串转化为相应的对象；
        brandService.add(brand);
        response.getWriter().write("success");
    }

    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader br = request.getReader();
        String param = br.readLine();//   get the json-type string
        int id = JSON.parseObject(param, int.class);
        brandService.deleteById(id);
        response.getWriter().write("delete by ID is successful");
    }

    /**
     * series delete
     */
    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. get the data from frontend,
        BufferedReader br = request.getReader();
        String params = br.readLine();
        //  json type changes to int[]
        int[] ids = JSON.parseObject(params, int[].class);
        //2. call method in service layer
        brandService.deleteByIds(ids);
        //3. response to the front end
        response.getWriter().write("series delete is successful");  // the string shall keep the same with brand.html  L315
    }


    /**
     * Pagination search
     LIMIT param1, param2   first param means the starting index,  second param means the index number each page
     backend shall send  1. overall list,  2. total record count to the front-end
     frontend shall send  1. currentPage 2. pageSize to the back-end
     */
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  get the currentpage and pagesize from frontend :  url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        //  transfer the string type into int type
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        //   call method "selectByPage",  get the result after handling by service layer and dao layer, the result is in pageBean object
        PageBean<Brand> pageBean = brandService.selectByPage(currentPage, pageSize);
        //   change to JSON
        String jsonString = JSON.toJSONString(pageBean);
        //   send the data to the front-end
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

        //   call method,  get the result after handling by service layer and dao layer
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage,pageSize,brand);
        String jsonString = JSON.toJSONString(pageBean);

        //   send the data to the front end
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }


}
