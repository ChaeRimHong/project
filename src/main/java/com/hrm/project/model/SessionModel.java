package com.hrm.project.model;

import java.sql.Blob;

public class SessionModel {
    private Blob pic;
    private String name_kor;
    private String authority;

    public Blob getPic() {
        return this.pic;
    }

    public void setPic(Blob pic) {
        this.pic = pic;
    }

    public String getName_kor() {
        return this.name_kor;
    }

    public void setName_kor(String name_kor) {
        this.name_kor = name_kor;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}