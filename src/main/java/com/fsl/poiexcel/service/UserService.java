package com.fsl.poiexcel.service;

import com.fsl.poiexcel.bean.User;

import java.util.List;

public interface UserService {

    public List<User> login(String userName, String password);

}
