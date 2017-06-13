package com.jason.netty.startup;
/**
 * 服务接口
 */
public interface Server {

    /**
     * 初始化接口方法
     */
	public abstract void init();

    /**
     * 启动接口方法
     */
	public abstract void start() throws Exception;

    /**
     * 停止接口方法
     */
	public abstract void stop();

    /**
     * 服务状态接口方法
     */
	public abstract void status();

}