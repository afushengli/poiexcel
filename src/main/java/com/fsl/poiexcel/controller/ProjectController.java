package com.fsl.poiexcel.controller;

import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 12:01
 * @Description:
 */

@Controller
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @PostMapping("/findAllProject")
    @ResponseBody
    public ServerResponse findAllProject() {

        return projectService.findAllProject();
    }

}
