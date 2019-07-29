package com.fsl.poiexcel.bean;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: chenrj
 * @Date: 2019/4/17 14:18
 * @Description:
 */


public class Student implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="stu_name")
    private String stuName;
    @Column(name="stu_sex")
    private Integer stuSex;
    @Column(name="stu_age")
    private Integer stuAge;
    @Column(name="stu_school_name")
    private String stuSchoolName;
    @Column(name="stu_class_name")
    private String stuClassName;
    @Column(name="ver")
    private int ver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuSex() {
        return stuSex;
    }

    public void setStuSex(int stuSex) {
        this.stuSex = stuSex;
    }

    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public String getStuSchoolName() {
        return stuSchoolName;
    }

    public void setStuSchoolName(String stuSchoolName) {
        this.stuSchoolName = stuSchoolName;
    }

    public String getStuClassName() {
        return stuClassName;
    }

    public void setStuClassName(String stuClassName) {
        this.stuClassName = stuClassName;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", stuName='" + stuName + '\'' + ", stuSex=" + stuSex + ", stuAge=" + stuAge + ", stuSchoolName='" + stuSchoolName + '\'' + ", stuClassName='" + stuClassName + '\'' + ", ver=" + ver + '}';
    }
}
