package com.hrm.project.model;

import org.springframework.stereotype.Component;

@Component
public class TechnicalModel {
    private int emp_num;
    private String tec_detail;
    private String proficiency;
    private String note;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getTec_detail() {
        return this.tec_detail;
    }

    public void setTec_detail(String tec_detail) {
        this.tec_detail = tec_detail;
    }

    public String getProficiency() {
        return this.proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
