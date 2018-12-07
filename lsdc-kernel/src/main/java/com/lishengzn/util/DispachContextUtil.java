package com.lishengzn.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletContext;

public class DispachContextUtil {
    private static ApplicationContext application;
    static {
        WebApplicationContext web= ContextLoader.getCurrentWebApplicationContext();
        ServletContext ctx=web.getServletContext();
        application=(ApplicationContext)ctx.getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX+"dispatcher");
    }

    public static <T> T  getBean(Class<T> clazz){
        return application.getBean(clazz);
    }
    public static Object getBean(String name){
        return application.getBean(name);
    }
}
