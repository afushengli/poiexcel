package com.fsl.poiexcel.mapper;

import com.fsl.poiexcel.bean.Student;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Auther: chenrj
 * @Date: 2019/4/17 14:52
 * @Description:
 */

public interface StuMapper extends Mapper<Student> {

    public  Integer getCount();


    public List<Student> getStudentByPage(Map<String,Object> map);


    public  int updatebyVer(Student stu);

}
