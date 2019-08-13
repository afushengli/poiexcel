package com.fsl.poiexcel.controller;

import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.OperateMessageService;
import com.fsl.poiexcel.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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



    @RequestMapping(value = "/downFileFromBack")
    @ResponseBody
    public void downFileFromBack(HttpServletRequest request,
                                 HttpServletResponse response, Model model) throws IOException {
        int size = 4096;
        OutputStream os = null;
        String fileName = request.getParameter("fileName");
        File importFile = new File(fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(importFile);
            int len = 0;
            byte[] buf = new byte[size];
            os = response.getOutputStream();
            while((len=fis.read(buf))!=-1){
                os.write(buf, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(fis !=null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



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
        if(operateMessage ==null){
            return  ServerResponse.error("已在别处操作");
        }

        operateMessage.setInnerId(operateStatus);


        //发送操作
        return  senderService.send(operateMessage,session);
    }






}
