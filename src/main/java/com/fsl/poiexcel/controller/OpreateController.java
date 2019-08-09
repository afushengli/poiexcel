package com.fsl.poiexcel.controller;

import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.OperateMessageService;
import com.fsl.poiexcel.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Auther: chenrj
 * @Date: 2019/8/5 16:44
 * @Description:
 */
@Controller
@RequestMapping("/message")
public class OpreateController {



    @Autowired
    private SenderService senderService;

    @Autowired
    private OperateMessageService operateMessageService;



    @RequestMapping("/findUserOpreate")
    @ResponseBody
    public ServerResponse findUserOpreate(HttpSession session) {

        Integer userId = (Integer)session.getAttribute("userId");

        return operateMessageService.findOPerateByUserId(String.valueOf(userId));
    }






    @RequestMapping("/opreate")
    @ResponseBody
    public ServerResponse opreate(String id,String operateStatus,HttpSession session) {


        OperateMessage operateMessage =  operateMessageService.findOPerateById(Integer.valueOf(id));
        operateMessage.setInnerId(operateStatus);


        //发送操作
        return  senderService.send(operateMessage,session);
    }

}
