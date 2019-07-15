package com.fsl.poiexcel.util;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Auther: fsl
 * @Date: 2019/4/17 14:07
 * @Description:
 */
public class ExcelUtil <E>{


    public static  boolean  readExcel2003(String importFilePath) throws IOException {
        File file=new File(importFilePath);
        String fileName = file.getName();

        //根据其名称获取后缀
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            return true;
           // read2003Excel(new FileInputStream(file),exportFilePath);
        } else if ("xlsx".equals(extension) || "xlsm".equals(extension)) {
            return false;
            //read2007Excel(new FileInputStream(file),exportFilePath);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }


    public static <E> void getHSSFWorkbookFromFile(String templatePath , String []properties, List<E> list,  boolean is2003) throws IOException {

        //获取target下的class文件




        Resource resource = new ClassPathResource("public");
        String realPath=  resource.getFile().getAbsolutePath();



        if(is2003){

            ClassPathResource classPathResource = new ClassPathResource(templatePath);
            InputStream inputStream =classPathResource.getInputStream();
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);

            //2. 创建新的文件
            File newFile = new File(realPath, "学生信息表1555500632034.xls");
            FileOutputStream out = new FileOutputStream(newFile);
            HSSFWorkbook  wb= get2003Wb(fs,properties,list);
            wb.write(out);

            out.flush();
            out.close();

        }else{
            File newFile = new File(realPath, "学生信息表1555500632034.xlsx");
            XSSFWorkbook  wb= get2007Wb(templatePath,properties,list);

            FileOutputStream out = new FileOutputStream(newFile);
            wb.write(out);
            out.flush();
            out.close();
        }



    }


    public static <E> XSSFWorkbook   get2007Wb (String templatePath, String []properties, List<E> list) throws IOException {

        //获取target下的class文件
        ClassPathResource classPathResource = new ClassPathResource(templatePath);
        InputStream inputStream =classPathResource.getInputStream();
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        //读取了模板内所有sheet内容
        XSSFSheet sheet = wb.getSheetAt(0);

        //如果这行没有了，整个公式都不会有自动计算的效果的
        sheet.setForceFormulaRecalculation(true);

        XSSFRow row =null;
        for(int i=0;i<list.size();i++){
            row = sheet.getRow(i + 1);
            if (row == null) {
                row = sheet.createRow(i + 1);
            }

            E stu = list.get(i);

            for (int j = 0; j < properties.length; j++) {
                Object propertyValue = null;
                try {
                    String name = properties[j];
                    propertyValue = BeanUtils.getProperty(stu, name);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                XSSFCell cell = row.getCell(j);
                if(cell ==null){
                    cell = row.createCell(j);
                }

                cell.setCellValue("" + propertyValue);
            }
        }
        return wb;
    }

    public  static <E>  HSSFWorkbook   get2003Wb (POIFSFileSystem fs, String []properties, List<E> list) throws IOException {
        //读取excel模板
        HSSFWorkbook  wb = new HSSFWorkbook(fs);
        //读取了模板内所有sheet内容
        HSSFSheet sheet = wb.getSheetAt(0);

        //如果这行没有了，整个公式都不会有自动计算的效果的
        sheet.setForceFormulaRecalculation(true);

        HSSFRow row =null;
        for(int i=0;i<list.size();i++){
            row = sheet.getRow(i + 1);
            if (row == null) {
                row = sheet.createRow(i + 1);
            }

            E stu = list.get(i);

            for (int j = 0; j < properties.length; j++) {
                Object propertyValue = null;
                try {
                    String name = properties[j];
                    propertyValue = BeanUtils.getProperty(stu, name);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                HSSFCell cell = row.getCell(j);
                if(cell ==null){
                    cell = row.createCell(j);
                }

                cell.setCellValue("" + propertyValue);
            }
        }
        return wb;
    }

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param properties  获取的属性
     * @param list 获取list的内容
     *
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static <E> HSSFWorkbook getHSSFWorkbook(String sheetName, String []title, String []properties, List<E> list, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<list.size();i++){
            row = sheet.createRow(i + 1);
            E stu = list.get(i);

            for (int j = 0; j < properties.length; j++) {
                Object propertyValue = null;
                try {
                    String name = properties[j] ;
                    propertyValue = BeanUtils.getProperty(stu,
                            name );
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                cell = row.createCell(j);
                cell.setCellValue("" + propertyValue);
            }

        }
        return wb;
    }
}
