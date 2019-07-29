package com.fsl.poiexcel.controller;

import com.alibaba.fastjson.JSONObject;
import com.fsl.poiexcel.bean.Student;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.StuService;
import com.fsl.poiexcel.service.TokenService;
import com.fsl.poiexcel.util.DocUtil;
import com.fsl.poiexcel.util.ExcelUtil;
import com.fsl.poiexcel.util.Page;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenrj
 * @Date: 2019/4/17 14:08
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class ExcelController1 {

    Logger logger = LoggerFactory.getLogger(ExcelController1.class);

    @Autowired
    private StuService stuService;


    @Autowired
    private TokenService tokenService;


    @PostMapping(value = "/login")
    @ResponseBody
    public ServerResponse login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            //登陆成功，防止表单重复提交，可以重定向到主页
            session.setAttribute("loginUser",username);
            return  ServerResponse.success("登陆成功");
            //return "redirect:/main.html";
        }else{
            //登陆失败

            map.put("msg","用户名密码错误");
            return  ServerResponse.error("登录失败");

           // return  "login";
        }

    }






 /*   @RequestMapping("/queryPage")
    public PageInfo<City> getAll(City city) {
        List<City> countryList = cityService.getAll(city);
        return new PageInfo<City>(countryList);
    }*/

   //编辑
    @RequestMapping("/edit")
    @ResponseBody
    public ServerResponse edit(@RequestParam int id) {
        return  tokenService.edit(id);
    }


    //保存
    @RequestMapping("/save")
    @ResponseBody
    public ServerResponse save(@RequestParam int id) {
        return  tokenService.save(id);
    }




    @RequestMapping("/queryPage")
    @ResponseBody
    public  Page<Student>  queryPage(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10")int pageSize) {
        System.out.println("-------");
       Page<Student> page  = stuService.queryStudentByPage (pageIndex,pageSize);
        return page;
    }



    @ResponseBody
    @RequestMapping(value = "/updateStu")
    public  ResponseEntity<Integer>    updateStu(String stuList){

        logger.info(stuList   + "   --" );
        List<Student> stus = JSONObject.parseArray(stuList, Student.class);
        int a =stuService.update(stus);
        return ResponseEntity.ok().body(a);
    }


    @ResponseBody
    @RequestMapping(value = "/getStudents")
    public   List<Student> getStudents(){
        List<Student>   students =stuService.getAllStu();
        return students;
    }

    @ResponseBody
    @RequestMapping(value = "/getStu")
    public  ResponseEntity<Student>   getStudent(@RequestParam Long id){
        Student  student =stuService.getStu(id);
        return ResponseEntity.ok().body(student);
    }




    //后台的导入
    @RequestMapping(value = "/downLoadWork", method = RequestMethod.GET)
    @ResponseBody
    public void downLoadWork(HttpServletRequest request,
                         HttpServletResponse response, Model model) throws IOException {
        int size = 4096;
        OutputStream os = null;
        //  File importFile = new File("d:\\test.xlsx");

        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();
        File importFile = new File(realPath, "work.xlsx");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(importFile);
            int len = 0;
            byte[] buf = new byte[size];
            os = response.getOutputStream();
            while((len=fis.read(buf))!=-1){
                os.write(buf, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //后台的导入的带文件名的excel
    @RequestMapping(value = "/downFileFromBack")
    @ResponseBody
    public void downFileFromBack(HttpServletRequest request,
                         HttpServletResponse response, Model model) throws IOException {
        int size = 4096;
        OutputStream os = null;
        //  File importFile = new File("d:\\test.xlsx");
           String fileName = request.getParameter("fileName");
        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();
        File importFile = new File(realPath, fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(importFile);
            int len = 0;
            byte[] buf = new byte[size];
            os = response.getOutputStream();
            while((len=fis.read(buf))!=-1){
                os.write(buf, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //后台的导入
    @RequestMapping(value = "/downLoad", method = RequestMethod.GET)
    @ResponseBody
    public void downLoad(HttpServletRequest request,
                         HttpServletResponse response, Model model) throws IOException {
        int size = 4096;
        OutputStream os = null;
        //  File importFile = new File("d:\\test.xlsx");

        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();
        File importFile = new File(realPath, "bd110.xlsx");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(importFile);
            int len = 0;
            byte[] buf = new byte[size];
            os = response.getOutputStream();
            while((len=fis.read(buf))!=-1){
                os.write(buf, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @RequestMapping(value="/saveExport",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> saveExport(HttpServletRequest request) throws IOException, ServletException {
        int result= 0;
        Map<String,Object> resultMap = new HashMap<String,Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile file = fileMap.get("test.xlsx");

        //File newfile = new File("d:\\testupload.xlsx");


        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();
        File newfile  = new File(realPath, "上传-30000.xlsx");


        try {
            file.transferTo(newfile);
            resultMap.put("isSuccess", 1);
        } catch (Exception e) {
            resultMap.put("isSuccess", result);
            resultMap.put("errorMessage", e.getMessage());
            e.printStackTrace();
        }
        return resultMap;

    }





    @ResponseBody
    @RequestMapping(value = "/getAllStu")
    public   ResponseEntity<List<Student>> getAllStu(){
        List<Student>   students =stuService.getAllStu();
        return ResponseEntity.ok().body(students);
    }










    @RequestMapping("getDoc")
    public void getDoc( HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("title", "标题12121");
        dataMap.put("nian", "2016");
        dataMap.put("yue", "3");
        dataMap.put("ri", "6");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("xuehao", i);
            map.put("neirong", "内容"+i);
            list.add(map);
        }


        dataMap.put("list", list);


        String newWordName = "信息.doc";


        DocUtil  docUtil = new DocUtil();
        //调用打印word的函数
        docUtil.download(request, response, newWordName, dataMap);
    }



    /**
     * 导出报表，使用现有模板
     * @return
     */
    @RequestMapping(value = "/exporTemplet")
    @ResponseBody
    public void exportTemplet(HttpServletRequest request, HttpServletResponse response) throws Exception {

            String templatePath = "public/学生信息表1555500632033.xlsx";


            boolean is2003  = ExcelUtil.readExcel2003(templatePath);


            String[] properties = {"id","stuName","stuSex","stuAge","stuSchoolName","stuClassName"};

            List<Student>   list  =stuService.getAllStu();

            //创建HSSFWorkbook
            ExcelUtil.getHSSFWorkbookFromFile(templatePath,properties,list, is2003);

           /*
           logger.info("*****************");

           String testPath=request.getSession().getServletContext().getRealPath("/");

            //testPath:/private/var/folders/hq/xxzr6b4j3t776tksnb8rskj80000gn/T/tomcat-docbase.2334986243471507666.9000/
            logger.info("testPath:"  + testPath);

            String realPath1 = ClassUtils.getDefaultClassLoader().getResource("").getPath();

            //realPath1:/Users/work/workspace/bitman/poiexcel/target/classes/
            logger.info("realPath1:"  + realPath1);

            logger.info("*****************");*/



           //下边的 ResourceUtils.getFile() ，这个方法在linux系统下没用，希望大家注意一下。
      /*    File file1 = org.springframework.util.ResourceUtils.getFile("classpath:public");
            String realPath = file1.getAbsolutePath();
            logger.info("realPath:"  +  file1.getAbsolutePath());
           */

    }





    /**
     * 导出报表，创建一个全新的
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {


         //excel标题
          String[] title = {"id","姓名","性别","年龄","学校","班级"};

          String[] properties = {"id","stuName","stuSex","stuAge","stuSchoolName","stuClassName"};

           //excel文件名
         String fileName = "学生信息表"+System.currentTimeMillis()+".xls";

          //sheet名
          String sheetName = "学生信息表";

        List<Student>   list  =stuService.getAllStu();

          //创建HSSFWorkbook
         HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, properties,list,null);

        // 响应到客户端
         try {
             this.setResponseHeader(response, fileName);
             OutputStream os = response.getOutputStream();
             wb.write(os);
             os.flush();
             os.close();
      } catch (Exception e) {
          e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


