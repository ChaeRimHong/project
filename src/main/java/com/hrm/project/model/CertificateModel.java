package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class CertificateModel {
    private int emp_num;
    private String name_license;
    private Date date_acqui;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getName_license() {
        return this.name_license;
    }

    public void setName_license(String name_license) {
        this.name_license = name_license;
    }

    public Date getDate_acqui() {
        return this.date_acqui;
    }

    public void setDate_acqui(Date date_acqui) {
        this.date_acqui = date_acqui;
    }

}
