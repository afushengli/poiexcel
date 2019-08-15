package com.fsl.poiexcel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer id;
    private Integer projectId;
    private Integer processId;
    private String  processName;
    private String processPath;
    private Integer userId;
    private String processStatus;


}
