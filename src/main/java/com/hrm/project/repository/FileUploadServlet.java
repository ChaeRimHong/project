package com.hrm.project.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import java.util.List;
//import java.util.ArrayList;

//import org.slf4j.Logger;


@RestController
@CrossOrigin(origins = "http://localhost:8080") // 특정 출처에서의 요청을 허용
public class FileUploadServlet {

//    private static final Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);
        @PostMapping("/upload")
        public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("emp_num") String empNum) {
            try {
                //사번 정보 확인
                if (empNum == null || empNum.isEmpty()) {
                    return ResponseEntity.badRequest().body("사번 정보를 입력해주세요.");
                }
                
                // 파일 저장 경로 설정
                String uploadDir = "C:\\HRIS\\project\\project\\Upload\\hr\\" + empNum + "\\";
        
                // 디렉토리가 존재하지 않으면 생성
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs(); // 경로가 존재하지 않으면 생성
                }
                System.out.println("FileUploadServlet 로직 타는중");

                // 파일 이름 추출
                String originalFilename = file.getOriginalFilename();

                // 확장자 추출
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

                // 파일 저장 경로 설정
                String filePath = uploadDir + empNum + extension;
                file.transferTo(new File(filePath));
        
                // 업로드된 파일 이름을 반환
                String uploadedFile = file.getOriginalFilename();

            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다." + uploadedFile);
        } catch (Exception e) {
            //logger.error("파일 업로드 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드에 실패했습니다.");
        }
    }
}