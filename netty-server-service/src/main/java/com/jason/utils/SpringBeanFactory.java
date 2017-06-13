package com.jason.utils;

import com.jason.controller.ActionMapTool;
import com.jason.controller.BaseController;
import com.jason.netty.startup.HttpServer;
import com.jason.util.ApplicationContextSingleton;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 14-1-10
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
public class SpringBeanFactory {

    public static ApplicationContext getContext(){
        return ApplicationContextSingleton.getInstance().getContext();
    }

    /**
     * 获得controllername
     * @param action
     * @return
     */
    public static String getControllerName(Integer action){
        ApplicationContext context = SpringBeanFactory.getContext();
        String beanName = "actionMapTool";
        ActionMapTool actionMapTool = (ActionMapTool) context.getBean(beanName);
        String controllerName = actionMapTool.getControllerName(action);
        return controllerName;
    }

    /**
     * 获得HttpServer
     * @return
     */
    public static HttpServer getHttpServer(){
        String beanName = "httpServer";
        ApplicationContext context = SpringBeanFactory.getContext();
        HttpServer httpServer = (HttpServer) context.getBean(beanName);
        return httpServer;
    }

    /**
     * 获得controller
     * @param beanName
     * @return
     */
    public static BaseController getController(String beanName){
        ApplicationContext context = SpringBeanFactory.getContext();
        BaseController baseController = (BaseController) context.getBean(beanName);
        return baseController;
    }
}
