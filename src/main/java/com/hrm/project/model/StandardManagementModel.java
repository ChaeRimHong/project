package com.hrm.project.model;

import org.springframework.stereotype.Component;

@Component
public class StandardManagementModel {
    private String gbn_cd;
    private String gbn_detail;
    private String gbn_name;
    private String comment;

    public String getGbn_cd() {
        return this.gbn_cd;
    }

    public void setGbn_cd(String gbn_cd) {
        this.gbn_cd = gbn_cd;
    }

    public String getGbn_detail() {
        return this.gbn_detail;
    }

    public void setGbn_detail(String gbn_detail) {
        this.gbn_detail = gbn_detail;
    }

    public String getGbn_name() {
        return this.gbn_name;
    }

    public void setGbn_name(String gbn_name) {
        this.gbn_name = gbn_name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
