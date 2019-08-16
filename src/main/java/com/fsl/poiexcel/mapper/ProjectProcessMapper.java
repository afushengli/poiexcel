package com.fsl.poiexcel.mapper;

import com.fsl.poiexcel.bean.ProjectProcess;
import tk.mybatis.mapper.common.Mapper;


public interface ProjectProcessMapper extends Mapper<ProjectProcess> {

    public int insert12(ProjectProcess projectProcess);
}
