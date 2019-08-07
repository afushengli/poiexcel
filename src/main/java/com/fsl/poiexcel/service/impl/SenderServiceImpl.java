package com.fsl.poiexcel.service.impl;

import com.alibaba.fastjson.JSON;
import com.fsl.poiexcel.bean.Message;
import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.bean.OperateMessageJson;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.OperateMessageService;
import com.fsl.poiexcel.service.SenderService;
import com.fsl.poiexcel.util.RPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: chenrj
 * @Date: 2019/8/5 18:06
 * @Description:
 */
@Service
//https://blog.51cto.com/13877966/2297056
//https://www.jianshu.com/p/e1258c004314
public class SenderServiceImpl implements SenderService{

    private static final Logger log= LoggerFactory.getLogger(SenderServiceImpl.class);


    @Autowired
    private OperateMessageService operateMessageService;

    //发送消息，不需要实现任何接口，供外部调用。
    public ServerResponse send(OperateMessage operateMessage,HttpSession session){


        log.info("开始发送消息");

        OperateMessageJson operateMessageJson = new OperateMessageJson();
        operateMessageJson.setCode(1);
        operateMessageJson.setMessage("操作成功");
        List <Message> list= new ArrayList();

        Message message =  new Message();
        message.setDocPath(operateMessage.getDocPath());
        message.setStepID(operateMessage.getStepID());
        message.setUserID((String)session.getAttribute("userId"));
        message.setInnerID(operateMessage.getInnerID());
        list.add(message);




        RPCClient fibonacciRpc = null;
        String response = null;
        try {
            fibonacciRpc = new RPCClient();
            String json = JSON.toJSONString(operateMessageJson);

            log.info("发送mq消息:"+ json);
            response = fibonacciRpc.call(json);
            System.out.println("接收到的node返回的数据" + response );


            Object repJson = JSON.parseObject(response,OperateMessageJson.class);

            OperateMessageJson   repResJSON  = (OperateMessageJson)repJson;



            if(repResJSON.getCode()==1){
                //删除数据
                operateMessageService.DeleteById(operateMessage);
                List<Message> listM  = repResJSON.getData();

                for(Message message1:listM) {
                    if("ADUIT".equals(message1.getInnerID())){
                        OperateMessage add = new OperateMessage();
                        add.setUserID(message1.getUserID());
                        add.setDocPath(message1.getDocPath());
                        add.setInnerID(message1.getInnerID());
                        add.setStepID(message1.getStepID());
                        operateMessageService.addOperateMessage(add);
                    }
                }
                return ServerResponse.error("操作成功");
            }else{
                return ServerResponse.error("操作失败");
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
            return ServerResponse.error("操作失败");
        } finally {
            if (fibonacciRpc!= null) {
                try {
                    fibonacciRpc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
