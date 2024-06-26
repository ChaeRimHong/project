package com.hrm.project.model;

import org.springframework.stereotype.Component;

@Component
public class UpdateTaskModel {
    private TaskManagementModel taskManagementModel;
    private EmployeeManagementModel employeeManagementModel;

    public TaskManagementModel getTaskManagementModel() {
        return this.taskManagementModel;
    }

    public void setTaskManagementModel(TaskManagementModel taskManagementModel) {
        this.taskManagementModel = taskManagementModel;
    }

    public EmployeeManagementModel getEmployeeManagementModel() {
        return this.employeeManagementModel;
    }

    public void setEmployeeManagementModel(EmployeeManagementModel employeeManagementModel) {
        this.employeeManagementModel = employeeManagementModel;
    }

}
