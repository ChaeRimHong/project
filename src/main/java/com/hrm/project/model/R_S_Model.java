package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class R_S_Model {
    private int emp_num;
    private String emp_rank;
    private String salary;
    private Date date_join;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getEmp_rank() {
        return this.emp_rank;
    }

    public void setEmp_rank(String emp_rank) {
        this.emp_rank = emp_rank;
    }

    public String getSalary() {
        return this.salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getDate_join() {
        return this.date_join;
    }

    public void setDate_join(Date date_join) {
        this.date_join = date_join;
    }

}
