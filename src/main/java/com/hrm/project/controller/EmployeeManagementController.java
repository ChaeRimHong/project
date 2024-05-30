package com.hrm.project.controller;

import javax.sql.DataSource;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.hrm.project.model.*;
import com.hrm.project.repository.EmployeeManagementRepo;
import com.hrm.project.repository.LoginRepo;
import com.hrm.project.repository.StandardManagementRepo;
import com.hrm.project.service.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Controller
public class EmployeeManagementController {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private EmployeeManagementRepo employeeManagementRepo;
    EmployeeManagementModel employeeManagementModel;
    private StandardManagementRepo standardManagementRepo;
    StandardManagementModel standardManagementModel;
    MaintableModel maintableModel;
    CareerModel careerModel;
    FamilyModel familyModel;
    CertificateModel certificateModel;
    E_C_Model e_C_Model;
    R_P_Model r_P_Model;
    R_S_Model r_S_Model;
    School_educationModel school_educationModel;
    TechnicalModel technicalModel;
    HttpSession httpSession;
    private LoginRepo loginRepo;

    @Autowired
    public EmployeeManagementController(DataSource dataSource, EmployeeManagementRepo employeeManagementRepo, StandardManagementRepo standardManagementRepo, LoginRepo loginRepo){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
        this.employeeManagementRepo = employeeManagementRepo;
        this.standardManagementRepo = standardManagementRepo;
        this.loginRepo = loginRepo;
    }

    //사원조회 테이블
    @GetMapping("/main")
    public String maintable(Model model, HttpSession session, HttpServletRequest request,
                            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize) {
    	System.out.println(">> EmployeeManagementController : maintable // 사원 조회");
    	// 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/login.html";
        }
    
        try {
            // 전체 직원 수를 가져오기
            int total = employeeManagementRepo.getEmployeeCount();
    
            // 직원 정보 조회 (페이지별로 가져오도록 수정)
            List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main(pageNumber, pageSize);
    
            // 페이지 수 계산
            int totalpage = (int) Math.ceil((double) total / pageSize);
    
            // 페이지 번호들의 리스트 생성
            List<Integer> pages = new ArrayList<>();
            for (int i = 1; i <= totalpage; i++) {
                pages.add(i);
            }
    
            // 현재 페이지와 페이지 수, 페이지 번호 리스트를 모델에 추가
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", totalpage);
            model.addAttribute("pages", pages);
            // 모델에 데이터 추가
            model.addAttribute("employees", employees);
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 오류 페이지로 리다이렉트 또는 오류 메시지 반환
            return null;
        }
        return "main";
    }

    // 페이징
    @GetMapping("/paging_emp")
    public String handlePagingRequest(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        // 페이지 처리 코드 작성
        return "redirect:/main?pageNumber=" + page;
    }
    
    @GetMapping("/board")
    public String assign_new(HttpSession session, Model model, HttpServletRequest request,
                            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize) {
    	System.out.println(">> EmployeeManagementController : assign_new // 사원 신규");
    	// 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 로그인 화면으로 리다이렉트
        if(userId == null){
            return "redirect:/login.html";
        }
        try{
            List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main(pageNumber, pageSize);
            List<StandardManagementModel> jobstandard = standardManagementRepo.getStandard("job");
            List<StandardManagementModel> busstandard = standardManagementRepo.getStandard("bus");
            List<StandardManagementModel> grostandard = standardManagementRepo.getStandard("gro");
            List<StandardManagementModel> posstandard = standardManagementRepo.getStandard("pos");
            List<StandardManagementModel> rankstandard = standardManagementRepo.getStandard("rank");
            model.addAttribute("employees", employees);
            model.addAttribute("jobstandard", jobstandard);
            model.addAttribute("busstandard", busstandard);
            model.addAttribute("grostandard", grostandard);
            model.addAttribute("posstandard", posstandard);
            model.addAttribute("rankstandard", rankstandard);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "board";
    }

    // 로그아웃
    @GetMapping("/test")
    public String test(HttpSession session, Model model, HttpServletRequest request,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 반환 값 없음
        if (userId == null) {
            // 로그인 페이지로 리다이렉트
            return "redirect:/login.html";
        }
        try {
            System.out.println("==============================test 페이지 return==============================");
            List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main(pageNumber, pageSize);
            List<StandardManagementModel> jobstandard = standardManagementRepo.getStandard("job");
            List<StandardManagementModel> busstandard = standardManagementRepo.getStandard("bus");
            List<StandardManagementModel> grostandard = standardManagementRepo.getStandard("gro");
            List<StandardManagementModel> posstandard = standardManagementRepo.getStandard("pos");
            List<StandardManagementModel> rankstandard = standardManagementRepo.getStandard("rank");
          
            
            model.addAttribute("employees", employees);
            model.addAttribute("jobstandard", jobstandard);
            model.addAttribute("busstandard", busstandard);
            model.addAttribute("grostandard", grostandard);
            model.addAttribute("posstandard", posstandard);
            model.addAttribute("rankstandard", rankstandard);
            
        return "test";
        } catch (Exception e) {
            e.printStackTrace(); // 오류 발생 시 오류 페이지로 리다이렉트 또는 오류 메시지 반환
            return null;
        }
    }
    // 이름 검색 기능
    @PostMapping("/searchEmployees")
    public String searchEmployees(@RequestParam("keyword") String keyword, Model model) {
    
        // 직원 정보 검색
        List<EmployeeManagementModel> employees = employeeManagementRepo.searchEmployees(keyword);

        if (employees != null && !employees.isEmpty()) {
            model.addAttribute("employees", employees);
        } else {
            model.addAttribute("message", "검색 결과가 없습니다.");
        }
        return "main";
    }

    //유저 삭제 기능
    @PostMapping("/employees/delete")
    public String deleteSelectedUsers(@RequestParam("empNum") int empNum, Model model){
        try {
            System.out.println("\n 유저 삭제 메소드 실행");

            boolean deleted = employeeManagementRepo.deleteUser(empNum);

            if(deleted){
                model.addAttribute("successMessage", "사용자 삭제 완료");
            } else {
                model.addAttribute("errorMessage", "사용자 삭제 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 삭제 중 오류 발생");
            e.printStackTrace();
        }

        // 사용자를 다시 유저 관리 페이지로 리다이렉트
        return "redirect:/main";
    }
    
    // 신규 유저 추가
	@PostMapping("/employee/add")
	public String updateUser(@ModelAttribute MaintableModel maintableModel, @ModelAttribute CareerModel careerModel,
	                         @ModelAttribute School_educationModel school_educationModel, @ModelAttribute TechnicalModel technicalModel,
	                         @ModelAttribute FamilyModel familyModel, @ModelAttribute CertificateModel certificateModel,
	                         @ModelAttribute E_C_Model e_C_Model, @ModelAttribute R_P_Model r_P_Model,
	                         @ModelAttribute R_S_Model r_S_Model, Model model, HttpServletRequest request) {
	    try {
	        // 디버깅 로그 추가
	        System.out.println("Received maintableModel: " + maintableModel.toString());
	        
	        boolean updated = employeeManagementRepo.addUser(maintableModel, careerModel, school_educationModel,
	                                                            technicalModel, familyModel, certificateModel,
	                                                            e_C_Model, r_P_Model, r_S_Model, request);
	        if (updated) {
	            model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
	        } else {
	            model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
	        }
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
	        e.printStackTrace();
	    }
	
	    // 사용자를 다시 유저 관리 페이지로 리다이렉트
	    return "redirect:/main";
	}
    
    // 신규 유저 추가 - 메인테이블
    @PostMapping("/employee/add/main")
    public String addUserMain(@ModelAttribute MaintableModel maintableModel, Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_Main(maintableModel, request);

	        if (updated) {
	            model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
	        } else {
	            model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
	        }
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
	        e.printStackTrace();
	    }
        return "redirect:/main";
    }
    
    // 신규 유저 추가 - 경력테이블
    @PostMapping("/employee/add/career")
    public String addUserCareer(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute CareerModel careerModel, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_Career(maintableModel, careerModel, request);

	        if (updated) {
	            model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
	        } else {
	            model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
	        }
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
	        e.printStackTrace();
	    }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 학력테이블
    @PostMapping("/employee/add/school_edu")
    public String addUserSchoolEdu(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute School_educationModel school_educationModel, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_school_edu(maintableModel, school_educationModel, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 기술사항 테이블
    @PostMapping("/employee/add/technocal")
    public String addUserTechnical(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute TechnicalModel technicalModel, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_Technical(maintableModel, technicalModel, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 가족관계 테이블
    @PostMapping("/employee/add/family")
    public String addUserFamily(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute FamilyModel familyModel, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_Family(maintableModel, familyModel, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 자격증 테이블
    @PostMapping("/employee/add/certificate")
    public String addUserCertificate(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute CertificateModel certificateModel, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_Certificate(maintableModel, certificateModel, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 교육이수 테이블
    @PostMapping("/employee/add/e_c")
    public String addUserE_C(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute E_C_Model e_C_Model, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_E_C(maintableModel, e_C_Model, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 상벌 테이블
    @PostMapping("/employee/add/r_p")
    public String addUserR_P(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute R_P_Model r_P_Model, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_R_P(maintableModel, r_P_Model, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 신규 유저 추가 - 직급/호봉 테이블
    @PostMapping("/employee/add/r_s")
    public String addUserR_S(@ModelAttribute MaintableModel maintableModel, 
                                @ModelAttribute R_S_Model r_S_Model, 
                                Model model, HttpServletRequest request) {
        try{
            boolean updated = employeeManagementRepo.addUser_R_S(maintableModel, r_S_Model, request);

            if (updated) {
                model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
            } else {
                model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    // 사원 수정 시 기존 db 데이터 가져오기
    @GetMapping("/getEmpInfo/{emp_num}")
    public String getEmpInfo_update(@PathVariable("emp_num") String emp_num, Model model, HttpSession session, HttpServletRequest request) {
        String userId = (String) session.getAttribute("userid");

        if(userId == null){
            return "redirect:/login.html";
        }

        try {
            // EmployeeManagementModel employeeManagementModel = employeeManagementRepo.getMainTableByEmpNum(emp_num);
            // CareerModel careerModel = employeeManagementRepo.getCareerTableByEmpNum(emp_num);
            // School_educationModel school_educationModel = employeeManagementRepo.getSchoolEducationByEmpNum(emp_num);
            // TechnicalModel technicalModel = employeeManagementRepo.getTechByEmpNum(emp_num);
            // FamilyModel familyModel = employeeManagementRepo.getFamilyByEmpNum(emp_num);
            // CertificateModel certificateModel = employeeManagementRepo.getCertificateByEmpNum(emp_num);
            // E_C_Model e_C_Model = employeeManagementRepo.getE_CTableByEmpNum(emp_num);
            // R_P_Model r_P_Model = employeeManagementRepo.getR_PTableByEmpNum(emp_num);
            // R_S_Model r_S_Model = employeeManagementRepo.getR_STableByEmpNum(emp_num);

            EmployeeDataModel employeeData = employeeManagementRepo.updateEmployees(emp_num);

            if(employeeManagementModel == null) {
                return "main";
            }

            model.addAttribute("emp", employeeData.getMainTable());
            model.addAttribute("career", employeeData.getCareer());
            model.addAttribute("schoolEducation", employeeData.getSchoolEducation());
            model.addAttribute("technical", employeeData.getTechnical());
            model.addAttribute("family", employeeData.getFamily());
            model.addAttribute("certificate", employeeData.getCertificate());
            model.addAttribute("educationCompletion", employeeData.getEducationCompletion());
            model.addAttribute("rewardPunishment", employeeData.getRewardPunishment());
            model.addAttribute("rankSalary", employeeData.getRankSalary());

            // model.addAttribute("emp", emp);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사원 수정 실패");
            e.printStackTrace();
            return "main";
        }
        return "main";
    }

    // // 로그인 시 로그아웃 왼쪽에 사진, 이름, 권한 출력하는 컨트롤러
    @GetMapping("/employees/session")
    public String getSessionInfo(HttpSession session, Model model, HttpServletRequest request) {
        String userId = (String) session.getAttribute("userid");
        
        if(userId == null){
            return "redirect:/login.html";
        }
        
        try {
            SessionModel sessionModel = (SessionModel) employeeManagementRepo.getSession(session, request);

            model.addAttribute("pic", sessionModel.getPic());
            model.addAttribute("name_kor", sessionModel.getName_kor());
            model.addAttribute("authority", sessionModel.getAuthority());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "세션 불러오기 실패");
            e.printStackTrace();
            return "";
        }
        return "";
    }
    
}
