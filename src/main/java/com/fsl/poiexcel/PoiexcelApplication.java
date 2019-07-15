package com.fsl.poiexcel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan(basePackages = "com.fsl.poiexcel.mapper")
public class PoiexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoiexcelApplication.class, args);
    }

}
