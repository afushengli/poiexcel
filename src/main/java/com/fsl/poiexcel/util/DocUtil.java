package com.fsl.poiexcel.util;

/**
 * @Auther: chenrj
 * @Date: 2019/4/19 10:06
 * @Description:
 */
/**
 * @文件名称: DocUtil.java
 * @描述: TODO
 * @作者：  wuwh
 * @时间：2018年4月10日 上午10:36:08
 * @版本：V1.0
 */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @类功能说明：    1.该代码在web项目中调用使用，需要在项目中的webroot目录下新建一个template文件夹，然后将预定义word的模板转成word.xml文件放入template文件下即可
 * 			2.将要输出的数据放到map集合中，作为参数传入即可
依赖jar包:
freemarker-2.3.13.jar
 * @作者： wuwh
 * @创建时间：2018年4月10日 上午10:36:08
 * @版本：V1.0
 */
public class DocUtil {

   private static final Logger log = LoggerFactory.getLogger(DocUtil.class);



    public  void download(HttpServletRequest request,HttpServletResponse response,String newWordName,Map dataMap) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");

        //dataMap 要填入模本的数据文件
        //设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        //这里我们的模板是放在template包下面
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        Template t=null;

        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate("Title.ftl","utf-8");                  // 文件名 还有这里要设置编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        File outFile = null;
        Writer out = null;
        String filename = newWordName;
        try {
            log.info("path:" + request.getSession()
                    .getServletContext());

            outFile = new File(newWordName);


            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile),"utf-8"));                 //还有这里要设置编码

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream fis = null;
        OutputStream toClient = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(outFile));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            filename = URLEncoder.encode(filename, "utf-8");                                  //这里要用URLEncoder转下才能正确显示中文名称
            response.addHeader("Content-Disposition", "attachment;filename=" + filename+"");
            response.addHeader("Content-Length", "" + outFile.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(toClient!=null){
                    toClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
