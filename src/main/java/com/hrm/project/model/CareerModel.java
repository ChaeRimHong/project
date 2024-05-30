package com.hrm.project.model;

import java.sql.Date;
import org.springframework.stereotype.Component;

@Component
public class CareerModel {
    private int emp_num;
    private String company;
    private String department;
    private Date date_join;
    private Date date_leave;
    private float work_period;
    private String final_rank;
    private String work_info;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDate_join() {
        return this.date_join;
    }

    public void setDate_join(Date date_join) {
        this.date_join = date_join;
    }

    public Date getDate_leave() {
        return this.date_leave;
    }

    public void setDate_leave(Date date_leave) {
        this.date_leave = date_leave;
    }

    public float getWork_period() {
        return this.work_period;
    }

    public void setWork_period(float work_period) {
        this.work_period = work_period;
    }

    public String getFinal_rank() {
        return this.final_rank;
    }

    public void setFinal_rank(String final_rank) {
        this.final_rank = final_rank;
    }

    public String getWork_info() {
        return this.work_info;
    }

    public void setWork_info(String work_info) {
        this.work_info = work_info;
    }

}
