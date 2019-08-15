package com.fsl.poiexcel.service;

import com.fsl.poiexcel.bean.Project;
import com.fsl.poiexcel.common.ServerResponse;

public interface ProjectService {

    ServerResponse findAllProject();

    Project findByPrimaryKey(Integer projectId);
}
