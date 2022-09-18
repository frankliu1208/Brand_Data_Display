package com.itheima.web.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用请求路径进行方法分发， 但是 httpservlet根据请求方式进行方法分发
 * 这里替换HttpServlet, 根据请求的最后一段路径来进行方法分发
 */

public class BaseServlet extends HttpServlet {

    // conduct method distribution according to the last path of request url
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.  get the request path
        String uri = req.getRequestURI();  // for example:  /brand-case/brand/selectAll

        //2.   get the index of "/", because we want to get the content after the "/"
        int index = uri.lastIndexOf('/');
        String methodName = uri.substring(index + 1);   // get the method name, which is the content after the "/"

        //3. execute the method
        Class<? extends BaseServlet> cls = this.getClass();
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
