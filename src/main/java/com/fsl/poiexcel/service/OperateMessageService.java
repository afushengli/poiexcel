package com.fsl.poiexcel.service;

import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.common.ServerResponse;

public interface OperateMessageService {

    int DeleteById(OperateMessage operateMessage);

    public int DeleteOM(OperateMessage operateMessage);

    public  int  addOperateMessage(OperateMessage operateMessage);

    ServerResponse findOPerateByUserId(String userId);

    OperateMessage findOPerateById(Integer id);
}
