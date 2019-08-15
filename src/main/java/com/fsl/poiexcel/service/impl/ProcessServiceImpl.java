package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.bean.Process;
import com.fsl.poiexcel.mapper.ProcessMapper;
import com.fsl.poiexcel.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 11:53
 * @Description:
 */
@Service
public class ProcessServiceImpl implements ProcessService {

   @Autowired
   private ProcessMapper processMapper;

    @Override
    public ServerResponse findAllProcess() {
        List <Process> list  = processMapper.selectAll();
        return ServerResponse.success(list);
    }

    @Override
    public Process findByPrimaryKey(Integer processIdI) {
        Process process = new Process();
        process.setId(processIdI);
        return processMapper.selectByPrimaryKey(process);
    }


}
