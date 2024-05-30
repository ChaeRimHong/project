package com.hrm.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class common { 

    public static void main(String[] args) {
        // MySQL 서버 접속 정보 설정
        String url = "jdbc:mysql://192.168.2.54:3306"; // 데이터베이스 URL
        String username = "dev"; // 데이터베이스 사용자 이름
        String password = "nori1234"; // 데이터베이스 암호


        // JDBC 연결 변수
        Connection conn = null;
        //Statement stmt = null;
        ResultSet resultSet = null;

        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
//             Class.forName("com.mysql.jdbc.Driver");

            // 데이터베이스에 연결
            conn = DriverManager.getConnection(url, username, password);

            String s_id = "1111";
            String s_pass = "1111";
            
            // SQL 쿼리 작성
            String sql = "SELECT id, passwd, emp_num, authority FROM pms.login_data WHERE id = ? AND `passwd` = ?";
            

            // 쿼리 실행을 위한 Statement 객체 생성
            //statement = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // 쿼리 실행 및 결과 가져오기
            stmt.setString(1, s_id);
            stmt.setString(2, s_pass);
            //resultSet = statement.executeQuery(sql);
            resultSet = stmt.executeQuery();
        
                  // 결과 출력
                  while (resultSet.next()) {
                    //로그인 계정과 암호를 db에서 select 
                         String login_id = resultSet.getString("id");
                         String login_pass = resultSet.getString("passwd");
                         String login_emp = resultSet.getString("emp_num");
                         String login_auth = resultSet.getString("authority");
                         System.out.println(login_id + " / " + login_pass + " / " + login_emp +  " / " + login_auth);
                  }

            if (conn != null) {
//                System.out.println("-------------------------------------------------------------------------------------------> common.java : (main) MySQL 데이터베이스에 성공적으로 접속했습니다.");
                
            } else {
                System.out.println("-------------------------------------------------------------------------------------------> common.java : (main) MySQL 데이터베이스에 접속할 수 없습니다.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("-------------------------------------------------------------------------------------------> common.java : (main) MySQL JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("-------------------------------------------------------------------------------------------> common.java : (main) MySQL 데이터베이스에 접속하는 도중 오류가 발생했습니다.");
            e.printStackTrace();
        } finally {
            // Connection 객체 닫기
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static Connection dbcon() throws SQLException{
        // MySQL 서버 접속 정보 설정
        String url = "jdbc:mysql://192.168.2.54:3306"; // 데이터베이스 URL
        String username = "dev"; // 데이터베이스 사용자 이름
        String password = "nori1234"; // 데이터베이스 암호

        // JDBC 연결 변수
        Connection conn = null;

        try {
            // MySQL JDBC 드라이버 로드
//             Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 데이터베이스에 연결
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
//                System.out.println("-------------------------------------------------------------------------------------------> common.java : (Connection dbcon()) MySQL 데이터베이스에 성공적으로 접속했습니다.");
            } else {
                System.out.println("-------------------------------------------------------------------------------------------> common.java : (Connection dbcon()) MySQL 데이터베이스에 접속할 수 없습니다.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("-------------------------------------------------------------------------------------------> common.java :  (Connection dbcon()) MySQL JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        }

        return (Connection) conn;
    }
}