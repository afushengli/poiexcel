package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.bean.Student;
import com.fsl.poiexcel.mapper.StuMapper;
import com.fsl.poiexcel.service.StuService;
import com.fsl.poiexcel.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenrj
 * @Date: 2019/4/17 14:54
 * @Description:
 */
@Service
public class StuServiceImpl   implements StuService {

    @Autowired
    StuMapper stuMapper;

    public List<Student> getAllStu() {

        return stuMapper.selectAll();
    }


    public  Student   getStu (Long id){
        return stuMapper.selectByPrimaryKey(id);
    }

    public int update (List<Student> stus) {
        int sum = 0;

        for(Student stu : stus){
            int  i=  stuMapper.updatebyVer(stu);
            sum += i;
        }
        return sum;


    }


    public Page<Student> queryStudentByPage(int pageIndex, int pageSize){

        Page<Student>  page = new Page<>();

        //设置当前页码
        page.setPageNumber(pageIndex);
        //设置页面大小
        page.setPageSize(pageSize);

         Integer count=  stuMapper.getCount();
        page.setTotalRecord(count);

        Map<String,Object> map = new HashMap<>();
        map.put("pageIndex",page.getIndex());
        map.put("pageSize",pageSize);


        List <Student>  list = stuMapper.getStudentByPage(map);

        //将list设置进page
        page.setData(list);

        return page;


    }

}
