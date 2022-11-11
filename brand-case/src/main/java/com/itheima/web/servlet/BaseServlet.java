package com.itheima.web.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  do method distribution according to request path. (in httpservlet, do method distribution according to request method)
 *  do the method distribution according to the last path
 */
public class BaseServlet extends HttpServlet {

    // conduct method distribution according to the last path of request url, override the service method in HttpServlet class
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
