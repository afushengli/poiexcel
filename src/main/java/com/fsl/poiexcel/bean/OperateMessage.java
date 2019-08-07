package com.fsl.poiexcel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: chenrj
 * @Date: 2019/8/6 10:38
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="operate_message")
public class OperateMessage implements Serializable {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userID;
    private String stepID; //步骤Id
    private String docPath;
    private String innerID; //操作标识


    public OperateMessage(String userID,String stepID,String docPath,String innerID){
        this.userID=userID;
        this.stepID=stepID;
        this.docPath=docPath;
        this.innerID =innerID;
    }

}
