package com.fsl.poiexcel.controller;

import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 11:48
 * @Description:
 */
@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @RequestMapping("/findAllProcess")
    @ResponseBody
    public ServerResponse findAllProcess() {

        return processService.findAllProcess();
    }




}
