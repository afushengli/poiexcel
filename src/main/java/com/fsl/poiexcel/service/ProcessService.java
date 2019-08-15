package com.fsl.poiexcel.service;

import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.bean.Process;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 11:52
 * @Description:
 */
public interface ProcessService {

     ServerResponse findAllProcess();


     Process findByPrimaryKey(Integer processIdI);
}
