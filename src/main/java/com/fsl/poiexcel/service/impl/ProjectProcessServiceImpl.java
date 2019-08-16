package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.bean.Process;
import com.fsl.poiexcel.bean.ProcessStatusEnum;
import com.fsl.poiexcel.bean.Project;
import com.fsl.poiexcel.bean.ProjectProcess;
import com.fsl.poiexcel.common.Constant;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.mapper.ProjectProcessMapper;
import com.fsl.poiexcel.service.ProjectProcessService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 14:01
 * @Description:
 */
@Service
public class ProjectProcessServiceImpl implements ProjectProcessService {


    @Autowired
    private ProjectProcessMapper projectProcessMapper;


    @Override
    public ServerResponse deleteById(Integer id) {
        ProjectProcess projectProcess = new ProjectProcess();
        projectProcess.setUserId(id);
        projectProcessMapper.deleteByPrimaryKey(projectProcess);
        return  ServerResponse.success("删除流程成功");
    }

    @Override
    public ServerResponse createProjectProcess(Integer userId, Project project, Process process) {

        ProjectProcess projectProcess = new ProjectProcess();


        projectProcess.setProjectId(project.getId());
        projectProcess.setProcessId(process.getId());
        projectProcess.setProcessName(process.getProcessName());

        //生成一个新的文件


        String sourceFilePath = Constant.PROCESS_TEMP_FILE_PATH +  process.getFilePath();
        File sourceFile = new File(sourceFilePath,process.getFileName());

        String destFilePath = Constant.PROCESS_FILE_PATH +  process.getFilePath();
        String [] sp =process.getFileName().split("\\.");
        String destFileName= process.getProcessType() + "_" + sp[0] +"_"+ dataStr()+ "." +sp[1];

        File destFile = new File(destFilePath,destFileName);


        try {
            FileUtils.copyFile(sourceFile, destFile);

        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error("流程文件创建失败");
        }

        projectProcess.setProcessPath(destFile.getPath());
        projectProcess.setUserId(userId);
        projectProcess.setProcessStatus(ProcessStatusEnum.DRAFT.getStatus());



        projectProcessMapper.insert(projectProcess);

        //向操作表插入一条数据,此处有争议
        return ServerResponse.success("创建流程成功",projectProcess);

    }

    @Override
    public ServerResponse findFinishProcess(Integer userId) {

        Example example = new Example(ProjectProcess.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("processStatus", ProcessStatusEnum.FINISH.getStatus());

        List<ProjectProcess> list = projectProcessMapper.selectByExample(example);
        return ServerResponse.success(list);
    }

    @Override
    public ServerResponse findNotFinishProcess(Integer userId) {

        List<String> statusList = new ArrayList();
        statusList.add(ProcessStatusEnum.DRAFT.getStatus());
        statusList.add(ProcessStatusEnum.PROCESSSING.getStatus());

        Example example = new Example(ProjectProcess.class);
        example.createCriteria().andEqualTo("userId",userId).andIn("processStatus",statusList);

        List<ProjectProcess> list = projectProcessMapper.selectByExample(example);
        return ServerResponse.success(list);
    }


    private String  dataStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }




}
