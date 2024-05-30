package com.hrm.project.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hrm.project.model.LoginModel;
import com.hrm.project.repository.LoginRepo;
import com.hrm.project.service.LoginService;
import jakarta.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class MainController {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private LoginRepo loginRepo;
    LoginModel loginModel;
    HttpSession httpSession;

    @Autowired
    public MainController(DataSource dataSource , LoginRepo loginRepo) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.loginRepo = loginRepo;
    }
    
    @GetMapping("/")
    public String loginForm() {
        return "/login.html"; // login.html 파일을 반환
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(LoginModel loginModel, HttpSession session) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 반환 값 없음
        if (userId == null) {
            // 로그인 페이지로 리다이렉트
            return "redirect:/login.html";
        }
        try {
            return "index"; // index.html에 대한 Thymeleaf 템플릿 파일명을 반환합니다.
        } catch (Exception e) {
            e.printStackTrace(); // 오류 발생 시 오류 페이지로 리다이렉트 또는 오류 메시지 반환
            return null;
        }
    }

    // 로그인
    @PostMapping("/login_do")
    public ModelAndView login(LoginModel loginModel, HttpSession session) {
        String id = loginModel.getId();
        String passwd = loginModel.getPasswd();
        try {
            LoginModel result = loginRepo.getUser(id, passwd);
    
            if (result != null && result.getPasswd().equals(passwd)) {
                // login 성공 시 메인 페이지로 리디렉션
                session.setAttribute("userid", loginModel.getId());
                System.out.println("로그인 성공");
                return new ModelAndView("redirect:/main.html");
            } else {
                // login 실패 시 다시 로그인 페이지로 이동
                System.out.println("로그인 실패");
                return new ModelAndView("redirect:/login.html");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            // 로그인 실패 시 다시 로그인 페이지로 이동
            System.out.println("로그인 실패");
            return new ModelAndView("redirect:/login.html");
        }
    }
    
    // 로그아웃
    @GetMapping("/logout_do")
    public String logout(HttpSession session) {
        session.removeAttribute("id");
        System.out.println("로그아웃 성공");
        return "redirect:/login.html";
    }

}
