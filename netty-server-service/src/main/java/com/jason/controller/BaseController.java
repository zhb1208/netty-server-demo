package com.jason.controller;

import com.jason.business.BusinessException;
import com.jason.dto.ReplyDto;
import com.jason.dto.RequestDto;
import com.jason.netty.service.snapshot.MessageWatch;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 14-1-10
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
public interface BaseController {

    /**
     * 请求Controller接口
     * @param requestDto
     * @param messageWatch
     * @return
     * @throws Exception
     */
    public ReplyDto excute(RequestDto requestDto,
                           MessageWatch messageWatch) throws BusinessException;

}
