package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Pm_histModel {
    private String pm_code;
    private int idx;
    private int emp_num;
    private String pm_gbn;
    private String tech_gbn;
    private String tech_comment;
    private String user_gbn;
    private String user_comment;
    private Date pm_date;

    public String getPm_code() {
        return this.pm_code;
    }

    public void setPm_code(String pm_code) {
        this.pm_code = pm_code;
    }

    public int getIdx() {
        return this.idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getPm_gbn() {
        return this.pm_gbn;
    }

    public void setPm_gbn(String pm_gbn) {
        this.pm_gbn = pm_gbn;
    }

    public String getTech_gbn() {
        return this.tech_gbn;
    }

    public void setTech_gbn(String tech_gbn) {
        this.tech_gbn = tech_gbn;
    }

    public String getTech_comment() {
        return this.tech_comment;
    }

    public void setTech_comment(String tech_comment) {
        this.tech_comment = tech_comment;
    }

    public String getUser_gbn() {
        return this.user_gbn;
    }

    public void setUser_gbn(String user_gbn) {
        this.user_gbn = user_gbn;
    }

    public String getUser_comment() {
        return this.user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public Date getPm_date() {
        return this.pm_date;
    }

    public void setPm_date(Date pm_date) {
        this.pm_date = pm_date;
    }

}
