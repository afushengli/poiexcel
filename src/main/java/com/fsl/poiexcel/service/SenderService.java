package com.fsl.poiexcel.service;

import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface SenderService {

    public ServerResponse send(OperateMessage operateMessage, HttpSession session);
}
