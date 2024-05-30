package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class E_C_Model {
    private int emp_num;
    private String name_edu;
    private Date date_start;
    private Date date_end;
    private String edu_insti;
    private String comp_yn;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getName_edu() {
        return this.name_edu;
    }

    public void setName_edu(String name_edu) {
        this.name_edu = name_edu;
    }

    public Date getDate_start() {
        return this.date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return this.date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public String getEdu_insti() {
        return this.edu_insti;
    }

    public void setEdu_insti(String edu_insti) {
        this.edu_insti = edu_insti;
    }

    public String getComp_yn() {
        return this.comp_yn;
    }

    public void setComp_yn(String comp_yn) {
        this.comp_yn = comp_yn;
    }

}
