package com.hrm.project.model;

public class EmployeeDataModel {
    private MaintableModel mainTable;
    private CareerModel career;
    private School_educationModel schoolEducation;
    private TechnicalModel technical;
    private FamilyModel family;
    private CertificateModel certificate;
    private E_C_Model educationCompletion;
    private R_P_Model rewardPunishment;
    private R_S_Model rankSalary;

    public MaintableModel getMainTable() {
        return this.mainTable;
    }

    public void setMainTable(MaintableModel mainTable) {
        this.mainTable = mainTable;
    }

    public CareerModel getCareer() {
        return this.career;
    }

    public void setCareer(CareerModel career) {
        this.career = career;
    }

    public School_educationModel getSchoolEducation() {
        return this.schoolEducation;
    }

    public void setSchoolEducation(School_educationModel schoolEducation) {
        this.schoolEducation = schoolEducation;
    }

    public TechnicalModel getTechnical() {
        return this.technical;
    }

    public void setTechnical(TechnicalModel technical) {
        this.technical = technical;
    }

    public FamilyModel getFamily() {
        return this.family;
    }

    public void setFamily(FamilyModel family) {
        this.family = family;
    }

    public CertificateModel getCertificate() {
        return this.certificate;
    }

    public void setCertificate(CertificateModel certificate) {
        this.certificate = certificate;
    }

    public E_C_Model getEducationCompletion() {
        return this.educationCompletion;
    }

    public void setEducationCompletion(E_C_Model educationCompletion) {
        this.educationCompletion = educationCompletion;
    }

    public R_P_Model getRewardPunishment() {
        return this.rewardPunishment;
    }

    public void setRewardPunishment(R_P_Model rewardPunishment) {
        this.rewardPunishment = rewardPunishment;
    }

    public R_S_Model getRankSalary() {
        return this.rankSalary;
    }

    public void setRankSalary(R_S_Model rankSalary) {
        this.rankSalary = rankSalary;
    }

}
