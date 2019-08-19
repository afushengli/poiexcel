package com.fsl.poiexcel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 11:40
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectProcess implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer projectId;
    private String projectName;

    private Integer processId;
    private String  processName;
    private String processPath;
    private Integer userId;
    private String processStatus;


}
