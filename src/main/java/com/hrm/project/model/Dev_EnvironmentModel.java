package com.hrm.project.model;

import org.springframework.stereotype.Component;

@Component
public class Dev_EnvironmentModel {
    private String pm_code;
    private String dev_model;
    private String os;
    private String language;
    private String dbms;
    private String tool;
    private String WAS;
    private String other;

    public String getPm_code() {
        return this.pm_code;
    }

    public void setPm_code(String pm_code) {
        this.pm_code = pm_code;
    }

    public String getDev_model() {
        return this.dev_model;
    }

    public void setDev_model(String dev_model) {
        this.dev_model = dev_model;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDbms() {
        return this.dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getTool() {
        return this.tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public String getWAS() {
        return this.WAS;
    }

    public void setWAS(String WAS) {
        this.WAS = WAS;
    }

    public String getOther() {
        return this.other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
