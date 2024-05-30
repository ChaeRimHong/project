package com.hrm.project.repository;

import java.util.List;

import com.hrm.project.model.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeManagementRepoI {
    List<EmployeeManagementModel> getEmployees_main(int pageNumber, int pageSize);
    List<EmployeeManagementModel> searchEmployees(String keyword);
    boolean deleteUser(int empNum);
    public boolean addUser(MaintableModel maintableModel, CareerModel careerModel, School_educationModel school_educationModel
                , TechnicalModel technicalModel, FamilyModel familyModel, CertificateModel certificateModel
                , E_C_Model e_C_Model, R_P_Model r_P_Model, R_S_Model r_S_Model, HttpServletRequest request);
}
