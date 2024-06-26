package com.hrm.project.repository;

import com.hrm.project.common;
import com.hrm.project.model.SessionModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Base64;

import org.springframework.stereotype.Repository;

@Repository
public class SessionRepo {
    // 로그인 시 로그아웃 왼쪽에 사진, 이름, 권한 출력하는 레포
    public SessionModel getSession(String userId, HttpSession session) {
        System.out.println("로그인 시 로그아웃 왼쪽에 사진, 이름, 권한 출력하는 레포");
        /*
        String userId = (String) session.getAttribute("userid");
        System.out.println("userId = " + userId);
        if (userId == null) {
            return null; // 세션에서 사용자 ID를 찾을 수 없음
        }
        */

        try {        
        Connection conn = common.dbcon();
        // int empNum = Integer.parseInt(userId);
        
        try (PreparedStatement useDStatement = conn.prepareStatement("USE pms")) {
            useDStatement.execute();
        }
            
        String sql = "SELECT mt.pic, mt.name_kor, ld.authority FROM main_table mt JOIN login_data ld ON mt.emp_num = ld.emp_num WHERE ld.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob("pic");
                    String base64Image = null;
                    if (blob != null) {
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    }
                        // SessionModel sessionModel = new SessionModel();
                        // sessionModel.setPic(base64Image);
                        // sessionModel.setName_kor(resultSet.getString("name_kor"));
                        // sessionModel.setAuthority(resultSet.getString("authority"));
                        // System.out.println("pic = " + sessionModel.getPic());
                        // System.out.println("name_kor = " + sessionModel.getName_kor());
                        // System.out.println("authority = " + sessionModel.getAuthority());
                        // return sessionModel;
                    // }
                    session.setAttribute("pic", base64Image);
                    session.setAttribute("name_kor", resultSet.getString("name_kor"));
                    session.setAttribute("authority", resultSet.getString("authority"));
                    
                    SessionModel sessionModel = new SessionModel();
                    sessionModel.setPic(base64Image);
                    sessionModel.setName_kor(resultSet.getString("name_kor"));
                    sessionModel.setAuthority(resultSet.getString("authority"));
                    // System.out.println("SessionRepo pic = " + sessionModel.getPic());
                    System.out.println("SessionRepo name_kor = " + sessionModel.getName_kor());
                    System.out.println("SessionRepo authority = " + sessionModel.getAuthority());
                    return sessionModel;
                }
                } 
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("User session error : " + e.getMessage());
        }
        return null;
    }
}
