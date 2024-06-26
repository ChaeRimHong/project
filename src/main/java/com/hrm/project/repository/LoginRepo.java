package com.hrm.project.repository;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hrm.project.model.LoginModel;
import com.hrm.project.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@Repository("loginRepo")
public class LoginRepo implements LoginRepoI{
    private final JdbcTemplate jdbcTemplate;
    LoginModel loginModel;

    @Autowired
    public LoginRepo(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 로그인
    public LoginModel getUser(String id, String passwd) {
        try {
            // MySQL 데이터베이스에 연결
            Connection conn = common.dbcon();

            // SQL 쿼리 작성
            String query = "SELECT id, passwd, emp_num, authority FROM pms.login_data WHERE id = ? AND passwd = ?";

            // PreparedStatement 객체 생성
            PreparedStatement stmt = conn.prepareStatement(query);
    
            // 파라미터 설정
            stmt.setString(1, id);
            stmt.setString(2, passwd);

            // 쿼리 실행 및 결과 가져오기
            ResultSet resultSet = stmt.executeQuery();
            
            // 결과 출력
            if (resultSet.next()) {
                // 로그인 정보 처리
                String login_id = resultSet.getString("id");
                String login_pass = resultSet.getString("passwd");
                String login_emp = resultSet.getString("emp_num");
                String login_auth = resultSet.getString("authority");

                //LoginModel 객체 생성 및 값 설정
                LoginModel loginModel = new LoginModel();
                loginModel.setId(login_id);
                loginModel.setPasswd(login_pass);

                System.out.println("Login ID: " + loginModel.getId());
                System.out.println("Password: " + loginModel.getPasswd());
                System.out.println("Employee Number: " + login_emp);
                System.out.println("Authority: " + login_auth);

                conn.close();

                return loginModel;

            } else{
                System.out.println("사용자 정보 없음");
                conn.close();
                return null;// 로그인 성공 시 리다이렉트 페이지 반환
            }
        } catch (SQLException e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
    }
}
