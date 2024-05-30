package com.hrm.project.repository;

import java.util.List;
import com.hrm.project.model.*;

public interface TaskManagementRepoI {
    List<TaskManagementModel> getTask(int pageNumber, int pageSize);
}
