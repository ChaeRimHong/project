package com.hrm.project.model;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class TaskManagementModel {
    private String pm_code;
    private String pm_name;
    private String pm_nori_manager;
    private String pm_nori_bu;
    private Date pm_create_date;
    private String pm_manager;
    private String pm_bu;
    private String pm_business;
    private BigDecimal pm_price;
    private int pm_workload;
    private int pm_full;
    private int pm_part;
    private Date pm_suggest_date;
    private Date pm_start_date;
    private Date pm_end_date;
    private int pm_work_date;
    private String pm_status;
    private String pm_outline;
    private String pm_bego;
    private String pm_file;
    private String pm_confirmed;
    private String pm_save_id;
    private String pm_save_sa;
    private String pm_save_name;
    private String pm_file_path;
    private Date pm_save_date;
    private int[] emp_num;

    public Date getPm_save_date() {
        return this.pm_save_date;
    }

    public void setPm_save_date(Date pm_save_date) {
        this.pm_save_date = pm_save_date;
    }

    public String getPm_code() {
        return this.pm_code;
    }

    public void setPm_code(String pm_code) {
        this.pm_code = pm_code;
    }

    public String getPm_name() {
        return this.pm_name;
    }

    public void setPm_name(String pm_name) {
        this.pm_name = pm_name;
    }

    public String getPm_nori_manager() {
        return this.pm_nori_manager;
    }

    public void setPm_nori_manager(String pm_nori_manager) {
        this.pm_nori_manager = pm_nori_manager;
    }

    public String getPm_nori_bu() {
        return this.pm_nori_bu;
    }

    public void setPm_nori_bu(String pm_nori_bu) {
        this.pm_nori_bu = pm_nori_bu;
    }

    public Date getPm_create_date() {
        return this.pm_create_date;
    }

    public void setPm_create_date(Date pm_create_date) {
        this.pm_create_date = pm_create_date;
    }

    public String getPm_manager() {
        return this.pm_manager;
    }

    public void setPm_manager(String pm_manager) {
        this.pm_manager = pm_manager;
    }

    public String getPm_bu() {
        return this.pm_bu;
    }

    public void setPm_bu(String pm_bu) {
        this.pm_bu = pm_bu;
    }

    public String getPm_business() {
        return this.pm_business;
    }

    public void setPm_business(String pm_business) {
        this.pm_business = pm_business;
    }

    public BigDecimal getPm_price() {
        return this.pm_price;
    }

    public void setPm_price(BigDecimal pm_price) {
        this.pm_price = pm_price;
    }

    public int getPm_workload() {
        return this.pm_workload;
    }

    public void setPm_workload(int pm_workload) {
        this.pm_workload = pm_workload;
    }

    public int getPm_full() {
        return this.pm_full;
    }

    public void setPm_full(int pm_full) {
        this.pm_full = pm_full;
    }

    public int getPm_part() {
        return this.pm_part;
    }

    public void setPm_part(int pm_part) {
        this.pm_part = pm_part;
    }

    public Date getPm_suggest_date() {
        return this.pm_suggest_date;
    }

    public void setPm_suggest_date(Date pm_suggest_date) {
        this.pm_suggest_date = pm_suggest_date;
    }

    public Date getPm_start_date() {
        return this.pm_start_date;
    }

    public void setPm_start_date(Date pm_start_date) {
        this.pm_start_date = pm_start_date;
    }

    public Date getPm_end_date() {
        return this.pm_end_date;
    }

    public void setPm_end_date(Date pm_end_date) {
        this.pm_end_date = pm_end_date;
    }

    public int getPm_work_date() {
        return this.pm_work_date;
    }

    public void setPm_work_date(int pm_work_date) {
        this.pm_work_date = pm_work_date;
    }

    public String getPm_status() {
        return this.pm_status;
    }

    public void setPm_status(String pm_status) {
        this.pm_status = pm_status;
    }

    public String getPm_outline() {
        return this.pm_outline;
    }

    public void setPm_outline(String pm_outline) {
        this.pm_outline = pm_outline;
    }

    public String getPm_bego() {
        return this.pm_bego;
    }

    public void setPm_bego(String pm_bego) {
        this.pm_bego = pm_bego;
    }

    public String getPm_file() {
        return this.pm_file;
    }

    public void setPm_file(String pm_file) {
        this.pm_file = pm_file;
    }

    public String getPm_confirmed() {
        return this.pm_confirmed;
    }

    public void setPm_confirmed(String pm_confirmed) {
        this.pm_confirmed = pm_confirmed;
    }

    public String getPm_save_id() {
        return this.pm_save_id;
    }

    public void setPm_save_id(String pm_save_id) {
        this.pm_save_id = pm_save_id;
    }

    public String getPm_save_sa() {
        return this.pm_save_sa;
    }

    public void setPm_save_sa(String pm_save_sa) {
        this.pm_save_sa = pm_save_sa;
    }

    public String getPm_save_name() {
        return this.pm_save_name;
    }

    public void setPm_save_name(String pm_save_name) {
        this.pm_save_name = pm_save_name;
    }

    public String getPm_file_path() {
        return this.pm_file_path;
    }

    public void setPm_file_path(String pm_file_path) {
        this.pm_file_path = pm_file_path;
    }
    
    public int[] getEmp_num() {
        return emp_num;
    }

    public void setEmp_num(int[] emp_num) {
        this.emp_num = emp_num;
    }
}
