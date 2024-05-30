package com.hrm.project.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Blob;

@Component
public class EmployeeManagementModel{
    private int emp_num;
    private String name_kor;
    private String name_eng;
    private String reg_num;
    private Blob pic;
    private int age;
    private String phone;
    private String mail;
    private String address;
    private int address_num;
    private String dept_biz;
    private String dept_group;
    private Date date_join;
    private String emp_rank;
    private String position;
    private String work_pos;
    private String salary;
    private String last_edu;
    private String military;
    private String marry;
    private int height;
    private int weight;
    private String gender;
    private String pm_code;
    private String input_gbn;

    public int getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(int emp_num) {
        this.emp_num = emp_num;
    }

    public String getName_kor() {
        return this.name_kor;
    }

    public void setName_kor(String name_kor) {
        this.name_kor = name_kor;
    }

    public String getName_eng() {
        return this.name_eng;
    }

    public void setName_eng(String name_eng) {
        this.name_eng = name_eng;
    }

    public String getReg_num() {
        return this.reg_num;
    }

    public void setReg_num(String reg_num) {
        this.reg_num = reg_num;
    }

    public Blob getPic() {
        return this.pic;
    }

    public void setPic(Blob pic) {
        this.pic = pic;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAddress_num() {
        return this.address_num;
    }

    public void setAddress_num(int address_num) {
        this.address_num = address_num;
    }

    public String getDept_biz() {
        return this.dept_biz;
    }

    public void setDept_biz(String dept_biz) {
        this.dept_biz = dept_biz;
    }

    public String getDept_group() {
        return this.dept_group;
    }

    public void setDept_group(String dept_group) {
        this.dept_group = dept_group;
    }

    public Date getDate_join() {
        return this.date_join;
    }

    public void setDate_join(Date date_join) {
        this.date_join = date_join;
    }

    public String getEmp_rank() {
        return this.emp_rank;
    }

    public void setEmp_rank(String emp_rank) {
        this.emp_rank = emp_rank;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWork_pos() {
        return this.work_pos;
    }

    public void setWork_pos(String work_pos) {
        this.work_pos = work_pos;
    }

    public String getSalary() {
        return this.salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLast_edu() {
        return this.last_edu;
    }

    public void setLast_edu(String last_edu) {
        this.last_edu = last_edu;
    }

    public String getMilitary() {
        return this.military;
    }

    public void setMilitary(String military) {
        this.military = military;
    }

    public String getMarry() {
        return this.marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPm_code() {
        return this.pm_code;
    }

    public void setPm_code(String pm_code) {
        this.pm_code = pm_code;
    }

    public String getInput_gbn() {
        return this.input_gbn;
    }

    public void setInput_gbn(String input_gbn) {
        this.input_gbn = input_gbn;
    }

}