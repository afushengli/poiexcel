package com.fsl.poiexcel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: chenrj
 * @Date: 2019/7/19 17:08
 * @Description:
 */
@Data
@AllArgsConstructor
public class Operatelock implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String sheetName;
    private String userName;
    private Date createTime;
    private Date updateTime;
    private Long expiredTime;
    private String sessionId;


    public Operatelock(String sheetName){
        this.sheetName =sheetName;
    }
    public Operatelock(String fileName,String sheetName,Date createTime){
        this.fileName = fileName;
        this.sheetName =sheetName;
        this.createTime = createTime;
    }







}
