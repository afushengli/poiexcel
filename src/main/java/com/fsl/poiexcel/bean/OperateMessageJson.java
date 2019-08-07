package com.fsl.poiexcel.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/8/6 18:17
 * @Description:
 */
@Data
public class OperateMessageJson implements Serializable {

    private Integer code;
    private String message;
    private List<Message> data;



}
