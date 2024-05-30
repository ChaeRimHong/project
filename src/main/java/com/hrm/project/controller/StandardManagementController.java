package com.hrm.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.hrm.project.model.StandardManagementModel;
import com.hrm.project.repository.LoginRepo;
import com.hrm.project.repository.StandardManagementRepo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class StandardManagementController {
    private final JdbcTemplate jdbcTemplate;
    private DataSource datasource;
    private StandardManagementRepo standardManagementRepo;
    StandardManagementModel standardManagementModel;
    HttpSession httpSession;
    private LoginRepo loginRepo;

    @Autowired
    public StandardManagementController(DataSource dataSource, StandardManagementRepo standardManagementRepo, LoginRepo loginRepo){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.standardManagementRepo = standardManagementRepo;
        this.loginRepo = loginRepo;
    }

    @GetMapping("/code") // 임의의 값
    public String standardtable(HttpSession session, Model model) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");

        // 로그인 안된 상태면 로그인 화면으로 리다이렉트
        if(userId == null){
            return "redirect:/login.html";
        }

        try {
            //기준 정보 조회
            List<StandardManagementModel> jobstandard = standardManagementRepo.getStandard("job");
            model.addAttribute("jobstandard", jobstandard);

            List<StandardManagementModel> busstandard = standardManagementRepo.getStandard("bus");
            model.addAttribute("busstandard", busstandard);

            List<StandardManagementModel> emailstandard = standardManagementRepo.getStandard("email");
            model.addAttribute("emailstandard", emailstandard);

            List<StandardManagementModel> grostandard = standardManagementRepo.getStandard("gro");
            model.addAttribute("grostandard", grostandard);

            List<StandardManagementModel> posstandard = standardManagementRepo.getStandard("pos");
            model.addAttribute("posstandard", posstandard);

            List<StandardManagementModel> rankstandard = standardManagementRepo.getStandard("rank");
            model.addAttribute("rankstandard", rankstandard);

            System.out.println("\n==============기준관리 return===============");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "code"; // 뷰 이름 반환
    }
    
    // 기준 삭제 기능
    @DeleteMapping("/deletestandard")
    public String deletestandard(@RequestParam("emp_num") String gbn_cd, Model model ){
        try{
            boolean deleted = standardManagementRepo.deletestandard(gbn_cd);

            if(deleted){
                model.addAttribute("successMessage", "기준 삭제 완료");
            } else {
                model.addAttribute("errorMessage", "기준 삭제 실패");
            }
        } catch(Exception e){
            model.addAttribute("errorMessage", "기준 삭제 중 오류 발생");
            e.printStackTrace();
        }

        // 사용자를 다시 기준 관리 페이지로 리다이렉트
        return "";
    }

    // 신규 기준 추가 기능
    @PostMapping("/addstandard")
    public String addstandard(StandardManagementModel newstandard, Model model) {
        try{
            boolean added = standardManagementRepo.addstandard(newstandard);
            if(added){
                model.addAttribute("successMessage", "새로운 기준 추가 완료");
            } else {
                model.addAttribute("errorMessage", "새로운 기준 추가 실패");
            }
        } catch(Exception e){
            model.addAttribute("errorMessage", "기준 추가 중 오류 발생");
            e.printStackTrace();
        }
        // 사용자를 다시 기준 관리 페이지로 리다이렉트
        return "redirect:/";
    }
    
}
