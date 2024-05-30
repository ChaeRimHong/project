package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class R_P_Model {
    private int emp_num;
    private String name_rew_puni;
    private int score;
    private Date date_rew_puni;
    private String note;
    private String name_rew_gbn;

    public String getName_rew_gbn() {
        return this.name_rew_gbn;
    }

    public void setName_rew_gbn(String name_rew_gbn) {
        this.name_rew_gbn = name_rew_gbn;
    }

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getName_rew_puni() {
        return this.name_rew_puni;
    }

    public void setName_rew_puni(String name_rew_puni) {
        this.name_rew_puni = name_rew_puni;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate_rew_puni() {
        return this.date_rew_puni;
    }

    public void setDate_rew_puni(Date date_rew_puni) {
        this.date_rew_puni = date_rew_puni;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
