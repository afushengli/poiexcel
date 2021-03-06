package com.fsl.poiexcel.service.impl;

import com.alibaba.fastjson.JSON;
import com.fsl.poiexcel.bean.*;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.mapper.ProjectProcessMapper;
import com.fsl.poiexcel.service.OperateMessageService;
import com.fsl.poiexcel.service.SenderService;
import com.fsl.poiexcel.util.RPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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


    @Autowired
    private ProjectProcessMapper projectProcessMapper;



    //发送消息，不需要实现任何接口，供外部调用。
    public ServerResponse send(OperateMessage operateMessage,HttpSession session){


        log.info("开始发送消息");


        Message message1 =  new Message();
        message1.setDocPath(operateMessage.getDocPath());
        message1.setStepID(operateMessage.getStepId());
        message1.setUserID(String.valueOf(session.getAttribute("userId")));
        message1.setInnerID(operateMessage.getInnerId());



        return sendMQMessage(operateMessage.getProjectProcessId(),operateMessage.getDocPath(),operateMessage,message1);


    }

    //使用方法锁，保证发送、发送，接收、接收  改为发送、接收，发送、接收
    public    ServerResponse sendMQMessage(Integer projectProcessId,String path,OperateMessage operateMessage,Message message) {

        String aa = new String(path);
        synchronized (path) {

        RPCClient fibonacciRpc = null;
        String response = null;

        log.info("------发送消息-------");

        boolean flag = false;
        try {

            fibonacciRpc = RPCClient.getInstance();

            //message 是要发送的消息
            String json = JSON.toJSONString(message);


                //接收消息改为同步
                log.info("发送mq消息:" + json);

                  response = fibonacciRpc.call(json);
                  log.info("接收到临时队列的返回的消息:" + response);


            Object repJson = JSON.parseObject(response, OperateMessageJson.class);
            OperateMessageJson repResJSON = (OperateMessageJson) repJson;

            if (repResJSON.getCode() == 1) {

                //删除已经操作数据
                operateMessageService.DeleteById(operateMessage);



                List<Message> listM = repResJSON.getData();


                if (!CollectionUtils.isEmpty(listM)) {  //处理并发操作
                    for (Message message1 : listM) {
                        OperateMessage add = new OperateMessage();

                        if("REJECT" .equals(message1.getInnerID()) || "CLOSE".equals(message1.getInnerID())){
                            log.info("删除驳回的路径是："+ operateMessage.getDocPath()  );
                            int result =  operateMessageService.deleteByDocPath(operateMessage.getDocPath());
                            log.info("删除几条数据："+ result);
                        }


                        if ("ADUIT".equals(message1.getInnerID())  || "CREATE".equals(message1.getInnerID())  || "SUBMIT".equals(message1.getInnerID()) ||  "FINAL".equals(message1.getInnerID())) {
                            add.setUserId(message1.getUserID());
                            add.setDocPath(operateMessage.getDocPath());  // node不给传输了
                            add.setInnerId(message1.getInnerID());
                            add.setStepId(message1.getStepID());
                            add.setProjectProcessId(projectProcessId);
                            operateMessageService.addOperateMessage(add);
                        } else if ("REJECT".equals(message1.getInnerID())) {
                            add.setUserId(message1.getUserID());
                            add.setDocPath(operateMessage.getDocPath());  // node不给传输了
                            add.setInnerId(message1.getInnerID());
                            add.setStepId(message1.getStepID());
                            add.setProjectProcessId(projectProcessId);

                            operateMessageService.addOperateMessage(add);
                        } else if ("COPY".equals(message1.getInnerID())) {

                            Message copy = listM.get(0);
                            copy.setDocPath(operateMessage.getDocPath());
                            sendMQMessage(projectProcessId,path,operateMessage, copy);
                            //现在  close  不做特殊处理

                        }else if("CLOSE".equals(message1.getInnerID())){

                            add.setUserId(message1.getUserID());
                            add.setDocPath(operateMessage.getDocPath());  // node不给传输了
                            add.setInnerId(message1.getInnerID());
                            add.setStepId(message1.getStepID());
                            add.setProjectProcessId(projectProcessId);
                            operateMessageService.addOperateMessage(add);

                            ProjectProcess projectProcess = new ProjectProcess();
                            projectProcess.setProcessStatus(ProcessStatusEnum.FINISH.getStatus());
                            Example example = new Example(ProjectProcess.class);
                            example.createCriteria().andEqualTo("id",projectProcessId);

                            //根据id更新 状态
                            projectProcessMapper.updateByExampleSelective(projectProcess,example);
                        }


                     }
                  }
                return ServerResponse.success("操作成功");
            } else {
                flag =true;
                return ServerResponse.error(repResJSON.getMessage());
            }
        } catch(IOException | InterruptedException e){
            flag =true;
            log.info("发送消息异常:"+ e.getMessage());
            return ServerResponse.error("操作失败");
        }/*finally {

            if(flag){
                if(fibonacciRpc !=null ){
                    try {
                        log.info("------连接关闭----");
                        fibonacciRpc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
*/
        }

    }

}
