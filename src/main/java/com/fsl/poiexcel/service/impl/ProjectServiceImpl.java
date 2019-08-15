package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.bean.Project;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.mapper.ProjectMapper;
import com.fsl.poiexcel.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 12:04
 * @Description:
 */

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public ServerResponse findAllProject() {
        List<Project> list = projectMapper.selectAll();
        return ServerResponse.success(list);
    }

    @Override
    public Project findByPrimaryKey(Integer projectId) {
        Project project = new Project();
        project.setId(projectId);
        return projectMapper.selectByPrimaryKey(project);
    }
}
