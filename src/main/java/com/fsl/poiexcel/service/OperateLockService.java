package com.fsl.poiexcel.service;

import com.fsl.poiexcel.bean.Operatelock;
import com.fsl.poiexcel.bean.Student;
import com.fsl.poiexcel.common.ServerResponse;

import java.util.Date;
import java.util.List;

/**
 * @Auther: chenrj
 * @Date: 2019/7/19 17:16
 * @Description:
 */
public interface OperateLockService {

   ServerResponse updateTime(String fileName, Date dateTime);

   ServerResponse editFile(Operatelock o);

   ServerResponse saveFile(String fileName,String sheetName);

  /* ServerResponse deleteLock(String fileName, String sheetName);*/

   ServerResponse edit(Operatelock o);

   ServerResponse  save( String sheetName,List<Student> stus );

}
