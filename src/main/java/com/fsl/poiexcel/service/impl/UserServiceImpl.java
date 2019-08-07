package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.bean.User;
import com.fsl.poiexcel.mapper.UserMapper;
import com.fsl.poiexcel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/8/6 10:03
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> login(String userName, String password) {

        Example example=new Example(User.class);
        example.createCriteria().andEqualTo("userName",userName).andEqualTo("password",password);

        List<User> list=  userMapper.selectByExample(example);
        return list;
    }
}
