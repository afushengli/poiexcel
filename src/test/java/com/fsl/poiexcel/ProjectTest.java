package com.fsl.poiexcel;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Auther: chenrj
 * @Date: 2019/8/15 15:55
 * @Description:
 */
public class ProjectTest {




    public static void main(String[] args) {
       String aa ="asasasa.xls";
       String [] sp =aa.split("\\.");

        System.out.println(sp[0]   + "    "+  sp[1] );


        File sorceFile = new File("/Users/a123/Desktop","work.xlsx");
        File destFile = new File("/Users/a123/Desktop","fsl.xlsx");
        try {
            FileUtils.copyFile(sorceFile,destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
