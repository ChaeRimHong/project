package com.hrm.project.controller;

import org.h2.engine.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrm.project.model.SessionModel;
import com.hrm.project.repository.SessionRepo;
import com.mysql.cj.jdbc.Blob;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {

    private final SessionRepo sessionRepo;

    @Autowired
    public SessionController(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }
    
    @GetMapping("/session")
    public SessionModel getSession(HttpSession session) {
        String userId = (String) session.getAttribute("userid");
        if(userId == null) {
            return null; // 세션에 사용자 정보가 없으면 null 반환
        }
/*
        SessionModel sessionModel = new SessionModel();
        sessionModel.setPic((Blob) session.getAttribute("pic"));
        sessionModel.setName_kor((String) session.getAttribute("name_kor"));
        sessionModel.setAuthority((String) session.getAttribute("authority"));
*/
        System.out.println("sessionController" + userId);
        return sessionRepo.getSession(userId, session);
    }
}
