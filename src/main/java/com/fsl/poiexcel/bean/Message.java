package com.fsl.poiexcel.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: chenrj
 * @Date: 2019/8/7 09:42
 * @Description:
 */
@Data
public class Message implements Serializable {

    private String stepID;
    private String userID;
    private String docPath;
    private String innerID;
}
