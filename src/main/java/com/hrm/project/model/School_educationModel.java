package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class School_educationModel {
    private int emp_num;
    private Date date_admition;
    private Date date_graduate;
    private String name_school;
    private String location;
    private String major;
    private String sub_major;
    private String comment;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public Date getDate_admition() {
        return this.date_admition;
    }

    public void setDate_admition(Date date_admition) {
        this.date_admition = date_admition;
    }

    public Date getDate_graduate() {
        return this.date_graduate;
    }

    public void setDate_graduate(Date date_graduate) {
        this.date_graduate = date_graduate;
    }

    public String getName_school() {
        return this.name_school;
    }

    public void setName_school(String name_school) {
        this.name_school = name_school;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSub_major() {
        return this.sub_major;
    }

    public void setSub_major(String sub_major) {
        this.sub_major = sub_major;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
