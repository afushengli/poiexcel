package com.fsl.poiexcel.service.impl;

import com.fsl.poiexcel.bean.Operatelock;
import com.fsl.poiexcel.bean.Student;
import com.fsl.poiexcel.common.Constant;
import com.fsl.poiexcel.common.ResponseCode;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.excepetion.ServiceException;
import com.fsl.poiexcel.mapper.OperatelockMapper;
import com.fsl.poiexcel.service.OperateLockService;
import com.fsl.poiexcel.service.StuService;
import com.fsl.poiexcel.util.JedisUtil;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: chenrj
 * @Date: 2019/7/19 17:24
 * @Description:
 */
@Service
public class OperateLockServiceImpl  implements OperateLockService {

    @Autowired
    private OperatelockMapper operatelockMapper;

    @Autowired
    private JedisUtil jedisUtil;


    @Autowired
    private StuService stuService;

    @Override
    public ServerResponse  updateTime(String fileName, Date dateTime){

        Example example=new Example(Operatelock.class);
        example.createCriteria().andEqualTo("fileName",fileName);//.andEqualTo("id","3");
        List <Operatelock>   list = operatelockMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            Map map =new HashMap();
            List<Long> idList =list.stream().map(op->op.getId()).collect(Collectors.toList());
            map.put("updateTime",dateTime);
            map.put("idList",idList);
            int count = operatelockMapper.batchUpdatebyid(map);
        }

        return ServerResponse.success("更新成功");
    }


  /*  @Override
   public  ServerResponse deleteLock(String fileName, String sheetName){
       StrBuilder key = new StrBuilder();
       key.append(Constant.Redis.TOKEN_PREFIX).append(fileName);


       Long del = jedisUtil.del(key.toString());
       if (del <= 0) {
           throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
       }

       Example example=new Example(Operatelock.class);
       example.createCriteria().andEqualTo("fileName",fileName);//.andEqualTo("id","3");

       operatelockMapper.deleteByExample(example);

       return ServerResponse.success("保存成功:"+del );

   }*/

    @Override
    public ServerResponse editFile(Operatelock o) {

        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(o.getFileName());



        if (!jedisUtil.exists(key.toString())) {

            jedisUtil.set(key.toString(), o.getUserName());
            o.setUpdateTime(new Date());
            //数据库操作
            operatelockMapper.insertSelective(o);

        }else{
            // throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
            return  ServerResponse.error("请勿重复操作",jedisUtil.get(key.toString()));
        }

        return ServerResponse.success(key.toString());
    }


    @Override
    //@Transactional
    public ServerResponse saveFile(String fileName,String sheetName) {

        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(fileName);


        Long del = jedisUtil.del(key.toString());
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Example example=new Example(Operatelock.class);
        example.createCriteria().andEqualTo("fileName",fileName);//.andEqualTo("id","3");


        System.out.println("-------------------");

        operatelockMapper.deleteByExample(example);



        return ServerResponse.success("保存成功:"+del );


    }


    @Override
    public ServerResponse edit(Operatelock o) {

        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(o.getSheetName());



        if (!jedisUtil.exists(key.toString())) {
            jedisUtil.set(key.toString(), o.getUserName());
            //数据库操作
            operatelockMapper.insertSelective(o);
        }else{
           // throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
            return  ServerResponse.error("请勿重复操作",jedisUtil.get(key.toString()));
        }

        return ServerResponse.success(key.toString());
    }

    @Override
    //@Transactional
    public ServerResponse save(String sheetName,List<Student> stus) {

        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(sheetName);


        Long del = jedisUtil.del(key.toString());
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        int i =stuService.update(stus);
        if(i<=0){
            throw new ServiceException(ResponseCode.ERROR.getMsg());
        }

        operatelockMapper.deleteByPrimaryKey(sheetName);

        return ServerResponse.success("保存成功:"+del );


    }
}
