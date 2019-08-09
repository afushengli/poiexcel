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
    private String userId;
    private String stepId; //步骤Id
    private String docPath;
    private String innerId; //操作标识


    public OperateMessage(String userId,String stepId,String docPath,String innerId){
        this.userId=userId;
        this.stepId=stepId;
        this.docPath=docPath;
        this.innerId =innerId;
    }

}
