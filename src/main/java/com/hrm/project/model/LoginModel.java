package com.hrm.project.model;

import org.springframework.stereotype.Component;

@Component
public class LoginModel {
    private String Id;
    private String passwd;
    private String authority;
    private String emp_num;

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    public String getEmp_num() {
        return this.emp_num;
    }

    public void setEmp_num(String emp_num) {
        this.emp_num = emp_num;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
