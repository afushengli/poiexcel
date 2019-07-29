package com.fsl.poiexcel.service.impl;


import com.fsl.poiexcel.common.Constant;
import com.fsl.poiexcel.common.ResponseCode;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.excepetion.ServiceException;
import com.fsl.poiexcel.service.TokenService;
import com.fsl.poiexcel.util.JedisUtil;
import com.fsl.poiexcel.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.UUID32();
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

        return ServerResponse.success(token.toString());
    }

    public  ServerResponse edit(int id){

        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(id);



        if (!jedisUtil.exists(key.toString())) {
            jedisUtil.set(key.toString(), key.toString());

        }else{
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        return ServerResponse.success(key.toString());

    }


    public ServerResponse save(int id){

        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(id);



        Long del = jedisUtil.del(key.toString());
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        return ServerResponse.success("保存成功:"+del );


    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
       if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }


    }

}
