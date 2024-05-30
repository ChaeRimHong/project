package com.hrm.project.repository;

import com.hrm.project.model.*;

public interface LoginRepoI{
    LoginModel getUser(String id, String passwd);

}
