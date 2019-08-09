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
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
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


        Message message1 =  new Message();
        message1.setDocPath(operateMessage.getDocPath());
        message1.setStepID(operateMessage.getStepId());
        message1.setUserID(String.valueOf(session.getAttribute("userId")));
        message1.setInnerID(operateMessage.getInnerId());


        return sendMQMessage(operateMessage,message1);


    }


    public ServerResponse sendMQMessage(OperateMessage operateMessage,Message message) {

        RPCClient fibonacciRpc = null;
        String response = null;
        try {



            fibonacciRpc = new RPCClient();
            String json = JSON.toJSONString(message);

            log.info("发送mq消息:" + json);
            response = fibonacciRpc.call(json);
            System.out.println("接收到的node返回的数据" + response);




            Object repJson = JSON.parseObject(response, OperateMessageJson.class);

            OperateMessageJson repResJSON = (OperateMessageJson) repJson;

            if (repResJSON.getCode() == 1) {
                //删除数据
                operateMessageService.DeleteById(operateMessage);
                List<Message> listM = repResJSON.getData();

                if (!CollectionUtils.isEmpty(listM)) {  //处理并发操作
                    for (Message message1 : listM) {
                        OperateMessage add = new OperateMessage();

                        if("REJECT" .equals(message.getInnerID()) || "CLOSE".equals(message.getInnerID())){
                            operateMessageService.deleteByDocPath(message1.getDocPath());
                        }

                        if ("ADUIT".equals(message1.getInnerID()) || "FINAL".equals(message1.getInnerID())) {
                            add.setUserId(message1.getUserID());
                            add.setDocPath(operateMessage.getDocPath());  // node不给传输了
                            add.setInnerId(message1.getInnerID());
                            add.setStepId(message1.getStepID());
                            operateMessageService.addOperateMessage(add);
                        } else if ("REJECT".equals(message1.getInnerID())) {
                            add.setUserId(message1.getUserID());
                            add.setDocPath(operateMessage.getDocPath());  // node不给传输了
                            add.setInnerId(message1.getInnerID());
                            add.setStepId(message1.getStepID());
                            operateMessageService.addOperateMessage(add);
                        } else if ("COPY".equals(message1.getInnerID())) {
                            Message copy = listM.get(0);
                            copy.setDocPath(operateMessage.getDocPath());
                            sendMQMessage(operateMessage, copy);
                            //现在  close  不做特殊处理
                        }
                     }
                  }
                return ServerResponse.success("操作成功");
            } else {
                return ServerResponse.error(repResJSON.getMessage());
            }
        } catch(IOException | TimeoutException | InterruptedException e){
            e.printStackTrace();
            return ServerResponse.error("操作失败");
        }


    }

}
