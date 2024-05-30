package com.hrm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrm.project.model.LoginModel;
import com.hrm.project.repository.LoginRepo;

@Service
public class LoginService {

@Autowired
    private LoginRepo loginRepo;
    
    public boolean authenticate(String id, String passwd){
        LoginModel result = loginRepo.getUser(id, passwd);
        if(result == null || !result.getPasswd().equals(passwd)){
            return false;
        }
        return true;
    }
}
