package com.fsl.poiexcel.service;

import com.fsl.poiexcel.bean.Process;
import com.fsl.poiexcel.bean.Project;
import com.fsl.poiexcel.common.ServerResponse;


public interface ProjectProcessService {

    //根据用户id查询出来已经完成
    ServerResponse findFinishProcess(Integer userId);


    //根据用户id查询出来未完成的
    ServerResponse findNotFinishProcess(Integer userId);


    ServerResponse createProjectProcess(Integer userId, Project project, Process process);
}
