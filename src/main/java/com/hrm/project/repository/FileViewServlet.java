package com.hrm.project.repository;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hrm.project.common;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class FileViewServlet {

    @PostMapping("/view")
    public ResponseEntity<String> viewImage(@RequestParam("empNum") String empNum) {
        System.out.println("사원 Image View 요청 처리 : " + empNum);

        String sql = "SELECT pic FROM main_table WHERE emp_num = ?";
        try (Connection conn = common.dbcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 데이터베이스 선택
            stmt.execute("USE pms");

            stmt.setInt(1, Integer.parseInt(empNum));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Blob blob = rs.getBlob("pic");
                    if (blob != null) {
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        return ResponseEntity.ok().body(base64Image);
                } else {
                    //blob이 null일 경우 404 상태코드 반환
                    // return ResponseEntity.notFound().build();
                    System.out.println("이미지 없음 기본 이미지 반환 : " + empNum);
                    return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
                }
            } else {
                //레코드가 없을 경우 404 상태코드 반환
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
            }
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
    }
}
