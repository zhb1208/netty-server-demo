package com.jason.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 14-1-10
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationContextSingleton {
    private static ApplicationContextSingleton ourInstance = new ApplicationContextSingleton();

    public static ApplicationContextSingleton getInstance() {
        return ourInstance;
    }

    private ApplicationContext context;

    private ApplicationContextSingleton() {
        // load and start spring
        context = new ClassPathXmlApplicationContext
                (
                        new String[] { "/spring/applicationContext.xml" }
                );

    }

    public ApplicationContext getContext(){
        return context;
    }
}
