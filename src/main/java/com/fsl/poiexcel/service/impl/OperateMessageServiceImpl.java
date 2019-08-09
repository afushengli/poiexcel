package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.bean.OperateMessage;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.mapper.OperateMessageMapper;
import com.fsl.poiexcel.service.OperateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/8/6 11:58
 * @Description:
 */
@Service
public class OperateMessageServiceImpl  implements OperateMessageService {


    @Autowired
    OperateMessageMapper operateMessageMapper;

    @Override
    public OperateMessage findOPerateById(Integer id) {
        OperateMessage operateMessage  =  new OperateMessage();
        operateMessage.setId(id);
        return operateMessageMapper.selectByPrimaryKey(operateMessage);
    }

    @Override
    public int deleteByDocPath(String docPath) {
        Example example = new Example(OperateMessage.class);
        example.createCriteria().andEqualTo("docPath",docPath);
        return operateMessageMapper.deleteByExample(example);
    }

    @Override
    public int DeleteById(OperateMessage operateMessage) {
        return operateMessageMapper.deleteByPrimaryKey(operateMessage);
    }

    @Override
    public int DeleteOM(OperateMessage operateMessage) {

        Example example = new Example(OperateMessage.class);
        example.createCriteria().andEqualTo("userId",operateMessage.getUserId()).andEqualTo("docPath",operateMessage.getDocPath());

        return operateMessageMapper.deleteByExample(example);
    }

    @Override
    public int addOperateMessage(OperateMessage operateMessage) {
        return operateMessageMapper.insertSelective(operateMessage);
    }

    @Override
    public ServerResponse findOPerateByUserId(String userId) {
        Example example = new Example(OperateMessage.class);

        example.createCriteria().andEqualTo("userId",userId);
        List<OperateMessage> list  =operateMessageMapper.selectByExample(example);
        return ServerResponse.success("查询成功",list);
    }


}
