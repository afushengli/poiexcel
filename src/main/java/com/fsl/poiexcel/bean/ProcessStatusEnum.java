package com.fsl.poiexcel.bean;

public enum ProcessStatusEnum {

    DRAFT("草稿"),PROCESSSING("审批中"),FINISH("已结束");

    private String status;

    //枚举类型的构造函数默认为private，因为枚举类型的初始化要在当前枚举类中完成。
    ProcessStatusEnum (String status){
        this.status= status;
    }

    public String getStatus(){
        return status;
    }


}
