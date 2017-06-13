package com.jason.controller.scan;

import com.jason.business.BusinessException;
import com.jason.controller.BaseController;
import com.jason.dto.ActionInfo;
import com.jason.dto.ReplyDto;
import com.jason.dto.RequestDto;
//import com.jason.dto.actioninfo.CommonActionData;
import com.jason.netty.service.snapshot.MessageWatch;
import com.jason.netty.service.snapshot.SubPointEnum;
import com.jason.service.UserService;
//import com.jason.util.JSONTool;
import com.jason.vo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 14-1-16
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CloudScanController implements BaseController {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CloudScanController.class);

    @Autowired
    private UserService userService;

    @Override
    public ReplyDto excute(RequestDto requestDto,
                           MessageWatch messageWatch) throws BusinessException {

        // cloudscan
        messageWatch.setSubBusiness(SubPointEnum.CLOUDSCAN.getName(),
                SubPointEnum.CLOUDSCAN.getIndex());

        ReplyDto replyDto = new ReplyDto();
        try{
            Map<String, String> requestData = requestDto.getData();
            String requestDataString = requestData.get("requestData");
            logger.info(requestDataString);

            long id = 1;
            User user = this.userService.getUser(id);
            List list = new ArrayList();
            list.add(user.getId());
            list.add(user.getName());
            list.add(user.getAddress());
            logger.info("{}", list);
            logger.info("cloud scan:" + user.getName());

            //TODO instance replyDto                                                                             s
            ActionInfo actionInfo = requestDto.getActionInfo();
            actionInfo.setValue(actionInfo.getValue());
            actionInfo.setCode(1);
            replyDto.setActionInfo(actionInfo);
//            CommonActionData replyCommonActionData = new CommonActionData();
//            replyCommonActionData.setAddress("reply cloud scan address");
//            replyCommonActionData.setBody("reply cloud scan data body");
//            replyCommonActionData.setDate("2013-12-24");
//            replyCommonActionData.setSmstype("2");
            // init map
            Map<String, String> replyData = new HashMap<String, String>();
//            replyData.put("replyData", JSONTool.toJSONString(replyCommonActionData));
            replyDto.setData(replyData);

        } catch (Exception ex){
            throw new BusinessException("CloudScanController_Exception", ex);
        } finally {
            messageWatch.stop(MessageWatch.STATE_BUSINESS);
        }
        return replyDto;
    }
}
