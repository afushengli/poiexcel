package com.fsl.poiexcel.controller;

import com.alibaba.fastjson.JSONObject;
import com.fsl.poiexcel.bean.Operatelock;
import com.fsl.poiexcel.bean.Student;
import com.fsl.poiexcel.common.Constant;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.OperateLockService;
import com.fsl.poiexcel.util.JedisUtil;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenrj
 * @Date: 2019/7/19 17:33
 * @Description:
 */
@RequestMapping("/opreate")
@Controller
public class OperatelockController {

    private  static Logger logger = LoggerFactory.getLogger(OperatelockController.class);
    @Autowired
    private OperateLockService operateLockService;

    @Autowired
    private JedisUtil jedisUtil;


    //发送心跳
    @RequestMapping("/sendHearteat")
    @ResponseBody
    public ServerResponse  sendHearteat(@RequestParam String fileName) {

        logger.info("心跳接受文件名称:"+ fileName);
        String files = fileName.substring(0,fileName.indexOf("."));
        fileName = files +".xlsx" ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        logger.info("心跳接受成功-----------");
        return operateLockService.updateTime(fileName,date);

     /*   try {

        } catch (ParseException e) {
            e.printStackTrace();
            return  ServerResponse.error("更新失败");
        }*/
    }



    //编辑
/*    @RequestMapping("/deleteLock")
    @ResponseBody
    public ServerResponse deleteLock(@RequestParam String fileName,@RequestParam String sheetName) {
        return operateLockService.deleteLock(fileName,sheetName);
    }*/



    @RequestMapping(value="/saveData",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveData(HttpServletRequest request) throws IOException, ServletException {

        logger.info("自动保存---------");
        int result= 0;
        Map<String,Object> resultMap = new HashMap<String,Object>();

        String user = (String) request.getSession().getAttribute("loginUser");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;


      /*  MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);*/
        //https://blog.csdn.net/WU5229485/article/details/84400315
        //https://blog.csdn.net/weixin_44594056/article/details/88393683
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile file = fileMap.get("file");
        String fileName = file.getOriginalFilename();

        logger.info("自动保存文件名称:"+ fileName);

        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();
        File newfile  = new File(realPath, fileName);




        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(fileName);

        if (jedisUtil.exists(key.toString())) {

            String userName = jedisUtil.get(key.toString());
            if(user.equals(userName)){
                try {
                    file.transferTo(newfile);
                    logger.info("自动保存成功---------");
                    return ServerResponse.success("保存成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    return ServerResponse.error("保存失败");

                }
            }else{
                return  ServerResponse.error("保存失败,不是同一个人进行的保存");
            }
        }else{
            return ServerResponse.error("没有拿到锁，不能保存1111");
        }

    }

    @RequestMapping(value="/saveExport",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveExport(HttpServletRequest request) throws IOException, ServletException {




        String user = (String) request.getSession().getAttribute("loginUser");
        logger.info("手动或者长时间不操作的保存---------");
        int result= 0;
        Map<String,Object> resultMap = new HashMap<String,Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;


        //https://blog.csdn.net/WU5229485/article/details/84400315
        //https://blog.csdn.net/weixin_44594056/article/details/88393683
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile file = fileMap.get("file");
        String fileName = file.getOriginalFilename();
        logger.info("手动或者长时间不操作的保存:"+ fileName);

        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();
        File newfile  = new File(realPath, fileName);




        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(fileName);

        if (jedisUtil.exists(key.toString())) {

            String userName = jedisUtil.get(key.toString());
            if(user.equals(userName)){
                try {
                    //删除缓存和数据库中的数据
                    ServerResponse  serverResponse = operateLockService.saveFile(fileName,null);
                    file.transferTo(newfile);
                    logger.info("保存成功---------");
                    return ServerResponse.success("保存成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    return ServerResponse.error("保存失败");

                }
            }else{
                return  ServerResponse.error("保存失败,不是同一个人进行的保存");
            }
        }else{
            return ServerResponse.error("没有拿到锁，不能保存22222222");
        }
    }




    //编辑
    @RequestMapping("/editFile")
    @ResponseBody
    public ServerResponse edit(@RequestParam String fileName,@RequestParam String sheetName,HttpSession session) {
        String user = (String)session.getAttribute("loginUser");

         String files = fileName.substring(0,fileName.indexOf("."));
        fileName = files +".xlsx" ;


        StrBuilder key = new StrBuilder();
        key.append(Constant.Redis.TOKEN_PREFIX).append(fileName);


        logger.info("redis中的key:"+ key.toString());


        if (jedisUtil.exists(key.toString())) {


            String userName = jedisUtil.get(key.toString());

            if(user.equals(userName)){
                return  ServerResponse.error("同一个人不能在不同的窗口修改",userName);
            }else{
                return  ServerResponse.error("已经有人在修改，不能修改",userName);
            }
        }else{
            logger.info("r的key:"+ key.toString());
            logger.info("user:"+ user);
            Operatelock op = new Operatelock(fileName,sheetName,new Date());
            op.setUserName(user);
            return operateLockService.editFile(op);
        }

    }


    //编辑
    @RequestMapping("/edit")
    @ResponseBody
    public ServerResponse edit(@RequestParam String sheetName,HttpSession session) {
                String user = (String)session.getAttribute("loginUser");
                Operatelock op = new Operatelock(sheetName);
                 op.setUserName(user);
        return operateLockService.edit(op);
    }


    //保存
    @RequestMapping("/save")
    @ResponseBody
    public ServerResponse save(String sheetName , String stuList) {

        List<Student> stus = JSONObject.parseArray(stuList, Student.class);
        return  operateLockService.save(sheetName,stus);
    }
}
