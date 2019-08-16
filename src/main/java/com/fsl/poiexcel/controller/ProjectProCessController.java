package com.fsl.poiexcel.controller;

import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.bean.Process;
import com.fsl.poiexcel.bean.Project;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.ProcessService;
import com.fsl.poiexcel.service.ProjectProcessService;
import com.fsl.poiexcel.service.ProjectService;
import com.fsl.poiexcel.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 12:12
 * @Description:
 */

@Controller
@RequestMapping("/projectProCess")
public class ProjectProCessController {




    @Autowired
    private ProjectProcessService projectProCessService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private SenderService senderService;


    @PostMapping ("/deleteFailurePP")
    @ResponseBody
    public ServerResponse deleteFailurePP(String id) {
        Integer id1 = Integer.valueOf(id);
        return projectProCessService.deleteById(id1);
    }


    @PostMapping ("/createOperateMessage")
    @ResponseBody
    public ServerResponse createOperateMessage(String stepId,String docPath,String innerId,String projectProcessId ,HttpSession session) {

        OperateMessage operateMessage =  new OperateMessage();
        operateMessage.setStepId(stepId);
        operateMessage.setDocPath(docPath);
        operateMessage.setInnerId(innerId);
        operateMessage.setProjectProcessId(Integer.valueOf(projectProcessId));

        //发送操作
        return  senderService.send(operateMessage,session);
    }




    @PostMapping("/createProjectProcess")
    @ResponseBody
    public ServerResponse createProjectProcess( String projectId, String processId, HttpSession session) {


        Integer userId = getUserId(session);
        //Integer userId = 3;
        Integer projectIdI = Integer.valueOf(projectId);
        Integer processIdI = Integer.valueOf(processId);




        Project project = projectService.findByPrimaryKey(projectIdI);
        Process process = processService.findByPrimaryKey(processIdI);


        return projectProCessService.createProjectProcess(userId,project,process);
    }



    @PostMapping("/findFinishProcess")
    @ResponseBody
    public ServerResponse findFinishProcess(HttpSession session) {
        return projectProCessService.findFinishProcess(getUserId(session));
    }


    @PostMapping("/findNotFinishProcess")
    @ResponseBody
    public ServerResponse findNotFinishProcess(HttpSession session) {
        return projectProCessService.findNotFinishProcess(getUserId(session));
    }




    private Integer getUserId(HttpSession session){
        Integer userId = (Integer)session.getAttribute("userId");

        //return 3;
        return userId;
    }








}
