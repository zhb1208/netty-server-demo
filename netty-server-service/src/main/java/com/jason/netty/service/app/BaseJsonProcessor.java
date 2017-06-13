package com.jason.netty.service.app;

import com.jason.business.BusinessException;
import com.jason.controller.BaseController;
import com.jason.dto.ActionInfo;
import com.jason.dto.ReplyDto;
import com.jason.dto.RequestDto;
import com.jason.netty.service.snapshot.MessageWatch;
import com.jason.netty.service.snapshot.SubPointEnum;
import com.jason.util.JSONTool;
import com.jason.utils.Constants;
import com.jason.utils.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author zhanghongbing
 * @version 15/3/26
 */
public class BaseJsonProcessor {

    private static Logger logger = LoggerFactory.getLogger(BaseJsonProcessor.class);
    private MessageWatch messageWatch;
    private List<Exception> listException;

    public BaseJsonProcessor() {

    }

    public BaseJsonProcessor(MessageWatch messageWatch, List<Exception> listException) {
        this.messageWatch = messageWatch;
        this.listException = listException;
    }

    /**
     * 获取requestto
     * @param request
     * @return
     */
    protected RequestDto read(String request){
        RequestDto requestDto = JSONTool.parseString2Object(request, RequestDto.class);
        return requestDto;
    }

    /**
     * 获取内容
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public StringBuilder makeAppContent(String content)
            throws UnsupportedEncodingException {
        // 业务开始
        messageWatch.setBusiness();

        // decode and parse json start
        messageWatch.setSubBusiness(SubPointEnum.DECODEANDPARSEJSON.getName(),
                SubPointEnum.DECODEANDPARSEJSON.getIndex());
        // 获取dto
        RequestDto requestDto = this.read(content);
        assert requestDto == null;

        // 执行方法
        ReplyDto replyDto = this.excuteCommand(requestDto);

        // 组装结果
        StringBuilder buf = new StringBuilder();
        String resultString = JSONTool.toJSONString(replyDto);
        buf.append(resultString);
        return buf;
    }

    /**
     * 执行指令
     * @param requestDto
     * @return
     */
    public ReplyDto excuteCommand(RequestDto requestDto)  {
        // 获取指令id
        ActionInfo ActionInfo = requestDto.getActionInfo();
        Integer actionValue = ActionInfo.getValue();

        String beanName = null;
        ReplyDto replyDto = null;
        try {
            //执行方法
            beanName = SpringBeanFactory.getControllerName(actionValue);

            //执行控制器
            BaseController baseController = SpringBeanFactory.getController(beanName);
            replyDto = baseController.excute(requestDto, this.messageWatch);

        } catch (BusinessException e) {
            replyDto = errorMsg(actionValue, replyDto);
            logger.error("BaseJsonProcessor.process BusinessException", e);
            messageWatch.setResponseCode(Integer.parseInt(Constants.FAIL_CODE));
            messageWatch.stop(MessageWatch.STATE_BUSINESS);
            if (listException != null) {
                listException.add(e);
            }
        } catch (Exception e) {
            replyDto = errorMsg(actionValue, replyDto);
            logger.error("BaseJsonProcessor.process Exception", e);
            messageWatch.setResponseCode(Integer.parseInt(Constants.FAIL_CODE));
            messageWatch.stop(MessageWatch.STATE_BUSINESS);
            if (listException != null) {
                listException.add(e);
            }
        }
        return replyDto;
    }

    /**
     * 错误信息
     * @param value
     * @param replyDto
     * @return
     */
    public ReplyDto errorMsg(Integer value, ReplyDto replyDto){
        if (replyDto == null){
            replyDto = new ReplyDto();
        }
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setCode(500);
        actionInfo.setValue(value);
        replyDto.setActionInfo(actionInfo);
        return replyDto;
    }
}
