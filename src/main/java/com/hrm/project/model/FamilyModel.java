package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class FamilyModel {
    private int emp_num;
    private String name_family;
    private Date birth;
    private int age;
    private String rel;
    private String live;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getName_family() {
        return this.name_family;
    }

    public void setName_family(String name_family) {
        this.name_family = name_family;
    }

    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRel() {
        return this.rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getLive() {
        return this.live;
    }

    public void setLive(String live) {
        this.live = live;
    }

}
