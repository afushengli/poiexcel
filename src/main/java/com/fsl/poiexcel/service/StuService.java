package com.fsl.poiexcel.service;
import com.fsl.poiexcel.bean.Student;
import com.fsl.poiexcel.util.Page;

import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/4/17 14:46
 * @Description:
 */

public interface StuService {

    public List<Student> getAllStu();


    public int update (List<Student> stus);

    public Student getStu (Long id);

    public Page<Student> queryStudentByPage(int pageIndex, int pageSize);

 }
