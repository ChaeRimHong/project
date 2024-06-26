package com.hrm.project.controller;

import javax.sql.DataSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hrm.project.model.*;
import com.hrm.project.repository.EmployeeManagementRepo;
import com.hrm.project.repository.LoginRepo;
import com.hrm.project.repository.StandardManagementRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class EmployeeManagementController {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private EmployeeManagementRepo employeeManagementRepo;
    EmployeeManagementModel employeeManagementModel;
    private StandardManagementRepo standardManagementRepo;
    StandardManagementModel standardManagementModel;
    Pm_histModel pm_histModel;
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
    	System.out.println("\n"+">> EmployeeManagementController : maintable // 사원 조회");
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
            List<CareerModel> career = employeeManagementRepo.getcareer_main();
            List<FamilyModel> family = employeeManagementRepo.getfamily_main();
            List<CertificateModel> certificate = employeeManagementRepo.getcertificate_main();
            List<School_educationModel> school_education = employeeManagementRepo.getschool_education_main();
            List<TechnicalModel> technical = employeeManagementRepo.gettechnical_main();
            List<R_S_Model> r_s = employeeManagementRepo.getr_s_main();
            List<R_P_Model> r_p = employeeManagementRepo.getr_p_main();
            
            // 사업부 목록에서 중복 제거
            Set<String> uniqueDeptBiz = employees.stream()
                    .map(EmployeeManagementModel::getDept_biz)
                    .filter(dept -> dept != null && !dept.isEmpty() && !dept.equals("uniqueDeptBiz"))  // 조건을 추가
                    .collect(Collectors.toSet());
            
            Set<String> uniqueDeptGroup = employees.stream()
                    .map(EmployeeManagementModel::getDept_group)
                    .filter(group -> group != null && !group.isEmpty() && !group.equals("uniqueDeptGroup"))  // 조건을 추가
                    .collect(Collectors.toSet());
            
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
            model.addAttribute("career", career);
            model.addAttribute("family", family);
            model.addAttribute("certificate", certificate);
            model.addAttribute("school_education", school_education);
            model.addAttribute("technical", technical);
            model.addAttribute("r_s", r_s);
            model.addAttribute("r_p", r_p);
            model.addAttribute("uniqueDeptBiz", uniqueDeptBiz);
            model.addAttribute("uniqueDeptGroup", uniqueDeptGroup);
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
    
    // 사원 신규 페이지
    @GetMapping("/board")
    public String board(HttpSession session, Model model, HttpServletRequest request,
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
            System.out.println("\n"+"==============================사원 신규 페이지 return==============================");
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
    
    @GetMapping("/test2")
    public String board_test2(HttpSession session, Model model, HttpServletRequest request,
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
    	return "test2";
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
            System.out.println(">> EmployeeManagementController : deleteSelectedUsers | 사원 삭제");

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
    //	@PostMapping("/employee/add")
    //	public String updateUser(@ModelAttribute MaintableModel maintableModel, @ModelAttribute CareerModel careerModel,
    //	                         @ModelAttribute School_educationModel school_educationModel, @ModelAttribute TechnicalModel technicalModel,
    //	                         @ModelAttribute FamilyModel familyModel, @ModelAttribute CertificateModel certificateModel,
    //	                         @ModelAttribute E_C_Model e_C_Model, @ModelAttribute R_P_Model r_P_Model,
    //	                         @ModelAttribute R_S_Model r_S_Model, Model model, HttpServletRequest request) {
    //	    try {
    //	        // 디버깅 로그 추가
    //	        System.out.println("Received maintableModel: " + maintableModel.toString());
    //	        
    //	        boolean updated = employeeManagementRepo.addUser(maintableModel, careerModel, school_educationModel,
    //	                                                            technicalModel, familyModel, certificateModel,
    //	                                                            e_C_Model, r_P_Model, r_S_Model, request);
    //	        if (updated) {
    //	            model.addAttribute("successMessage", "사용자 정보 업데이트 성공");
    //	        } else {
    //	            model.addAttribute("errorMessage", "사용자 정보 업데이트 실패");
    //	        }
    //	    } catch (Exception e) {
    //	        model.addAttribute("errorMessage", "사용자 정보 업데이트 중 오류 발생");
    //	        e.printStackTrace();
    //	    }
    //	
    //	    // 사용자를 다시 유저 관리 페이지로 리다이렉트
    //	    return "redirect:/main";
    //	}
        
        // 신규 유저 추가
    @PostMapping("/employee/add")
    public String updateUser(@ModelAttribute MaintableModel maintableModel, @ModelAttribute CareerModel careerModel,
                             @ModelAttribute School_educationModel school_educationModel, @ModelAttribute TechnicalModel technicalModel,
                             @ModelAttribute FamilyModel familyModel, @ModelAttribute CertificateModel certificateModel,
                             @ModelAttribute E_C_Model e_C_Model, @ModelAttribute R_P_Model r_P_Model,
                             @ModelAttribute R_S_Model r_S_Model, Model model, HttpServletRequest request) {
        System.out.println(">> EmployeeManagementController : updateUser // 사원 수정");
        try {
            // 디버깅 로그 추가
            System.out.println("	ㄴ Received maintableMode 값 : "+maintableModel.toString());
                                                                
            boolean update_main = employeeManagementRepo.addUser_Main(maintableModel, request);
            boolean update_career = employeeManagementRepo.addUser_Career(maintableModel, careerModel, request);
            boolean update_school_edu = employeeManagementRepo.addUser_school_edu(maintableModel, school_educationModel, request);
            boolean update_technical = employeeManagementRepo.addUser_Technical(maintableModel, technicalModel, request);
            boolean update_family = employeeManagementRepo.addUser_Family(maintableModel, familyModel, request);
            boolean update_certificate = employeeManagementRepo.addUser_Certificate(maintableModel, certificateModel, request);
            boolean update_e_c = employeeManagementRepo.addUser_E_C(maintableModel, e_C_Model, request);
            boolean update_r_p = employeeManagementRepo.addUser_R_P(maintableModel, r_P_Model, request);
            boolean update_r_s = employeeManagementRepo.addUser_R_S(maintableModel, r_S_Model, request);

            // 결과 종합 - 값이 들어 있는 코드만 저장하기
            StringBuilder resultMessage = new StringBuilder();
            if (update_main) {
                resultMessage.append("메인 정보 업데이트 성공. ");
            } else {
                resultMessage.append("메인 정보 업데이트 실패. ");
            }
            if (update_career) {
                resultMessage.append("경력 정보 업데이트 성공. ");
            } else {
                resultMessage.append("경력 정보 업데이트 실패. ");
            }
            if (update_school_edu) {
                resultMessage.append("학력 정보 업데이트 성공. ");
            } else {
                resultMessage.append("학력 정보 업데이트 실패. ");
            }
            if (update_technical) {
                resultMessage.append("기술 정보 업데이트 성공. ");
            } else {
                resultMessage.append("기술 정보 업데이트 실패. ");
            }
            if (update_family) {
                resultMessage.append("가족 정보 업데이트 성공. ");
            } else {
                resultMessage.append("가족 정보 업데이트 실패. ");
            }
            if (update_certificate) {
                resultMessage.append("자격증 정보 업데이트 성공. ");
            } else {
                resultMessage.append("자격증 정보 업데이트 실패. ");
            }
            if (update_e_c) {
                resultMessage.append("EC 정보 업데이트 성공. ");
            } else {
                resultMessage.append("EC 정보 업데이트 실패. ");
            }
            if (update_r_p) {
                resultMessage.append("RP 정보 업데이트 성공. ");
            } else {
                resultMessage.append("RP 정보 업데이트 실패. ");
            }
            if (update_r_s) {
                resultMessage.append("RS 정보 업데이트 성공. ");
            } else {
                resultMessage.append("RS 정보 업데이트 실패. ");
            }
            model.addAttribute("resultMessage", resultMessage.toString());
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
    public String getEmpInfo_update(@PathVariable("emp_num") String emp_num, Model model, HttpSession session, HttpServletRequest request,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize) {
    	System.out.println(">> EmployeeManagementController : getEmpInfo/{emp_num} // 사원 수정 페이지로");
        System.out.println("	ㄴ emp_num 값 : "+emp_num);
        String userId = (String) session.getAttribute("userid");

        if(userId == null){
            return "redirect:/login.html";
        }
            try {
                EmployeeDataModel employeeData = employeeManagementRepo.updateEmployees(emp_num);
                List<TaskManagementModel> emp_hist = employeeManagementRepo.getEmpFromPm_hist(emp_num);
                if (emp_hist != null) {
                    for (TaskManagementModel task : emp_hist) {
                        // TaskManagementModel 객체의 값을 출력
                    	System.out.println("\n");
                        System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | Pm_code 값 : "+ task.getPm_code());
                        System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | Pm_name 값 : "+ task.getPm_name());
                        System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | Pm_work_date 값 : "+ task.getPm_work_date());
                        System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | Pm_start_date 값 : "+ task.getPm_start_date());
                        System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | Pm_end_date 값 : "+ task.getPm_end_date());
                        
                        // 나머지 필요한 속성들도 동일한 방식으로 출력할 수 있음
                    }
                } else {
                    System.out.println("emp_hist 값이 null입니다.");
                }
                
                // mail 데이터 분리
                String mail = employeeData.getMainTable().getMail();
                String[] parts = mail.split("@");
                String mail_head = parts[0];
                String mail_domain = "";
                if (parts.length > 1) {
                    mail_domain = parts[1];
                }
                
                //address 데이터 분리
                String address = employeeData.getMainTable().getAddress();
                String[] addressParts = address.split(",");
                String address_1 = addressParts[0];
                String address_2 = "";
                if (addressParts.length > 1) {
                    address_2 = addressParts[1];
                }
                
                // 지금은 하드코딩 되어있는데 EmployeeManagementRepo.java의 getCode를 사용하거나 응용해도 무관
                
                //사업부 (main_table select 박스 보여주는 용)
                String dept_Biz = "";
                if(employeeData.getMainTable().getDept_biz().equals("s")) {
                	dept_Biz = "사업지원본부";
                } else if (employeeData.getMainTable().getDept_biz().equals("f")) {
                	dept_Biz = "FAB사업부";
                } else if (employeeData.getMainTable().getDept_biz().equals("b")) {
                	dept_Biz = "경영지원실";
                } else if (employeeData.getMainTable().getDept_biz().equals("i")) {
                	dept_Biz = "혁신사업부";
                } else if (employeeData.getMainTable().getDept_biz().equals("t")) {
                	dept_Biz = "TSP사업부";
                }
                System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | dept_Biz : "+ dept_Biz);
                
                //부서 (main_table select 박스 보여주는 용)
                String dept_Group = "";
                if(employeeData.getMainTable().getDept_group().equals("b01")) {
                	dept_Group = "FAB/혁신사업부지원";
                } else if (employeeData.getMainTable().getDept_group().equals("b02")) {
                	dept_Group = "TSP지원";
                } else if (employeeData.getMainTable().getDept_group().equals("f01")) {
                	dept_Group = "빅데이터";
                } else if (employeeData.getMainTable().getDept_group().equals("f02")) {
                	dept_Group = "인프라";
                } else if (employeeData.getMainTable().getDept_group().equals("f03")) {
                	dept_Group = "스마트팩토리";
                } else if (employeeData.getMainTable().getDept_group().equals("i01")) {
                	dept_Group = "TC사업팀";
                } else if (employeeData.getMainTable().getDept_group().equals("i02")) {
                	dept_Group = "MIS사업팀";
                } else if (employeeData.getMainTable().getDept_group().equals("i03")) {
                	dept_Group = "삼성전기 운영사업팀";
                } else if (employeeData.getMainTable().getDept_group().equals("s01")) {
                	dept_Group = "사업기획팀";
                } else if (employeeData.getMainTable().getDept_group().equals("s02")) {
                	dept_Group = "사업지원팀";
                } else if (employeeData.getMainTable().getDept_group().equals("t01")) {
                	dept_Group = "TSP운영1";
                } else if (employeeData.getMainTable().getDept_group().equals("t02")) {
                	dept_Group = "TSP운영2";
                } else if (employeeData.getMainTable().getDept_group().equals("t03")) {
                	dept_Group = "생산실행IT";
                } else if (employeeData.getMainTable().getDept_group().equals("t04")) {
                	dept_Group = "생산스케쥴IT";
                } else if (employeeData.getMainTable().getDept_group().equals("t05")) {
                	dept_Group = "생산품질IT";
                }
                
                
                //직급 (main_table select 박스 보여주는 용)
                String emp_Rank = "";
                if(employeeData.getMainTable().getEmp_rank().equals("ad")) {
                	emp_Rank = "이사";
                } else if (employeeData.getMainTable().getEmp_rank().equals("b")) {
                	emp_Rank = "부장";
                } else if (employeeData.getMainTable().getEmp_rank().equals("c")) {
                	emp_Rank = "차장";
                } else if (employeeData.getMainTable().getEmp_rank().equals("d")) {
                	emp_Rank = "대리";
                } else if (employeeData.getMainTable().getEmp_rank().equals("ed")) {
                	emp_Rank = "상무";
                } else if (employeeData.getMainTable().getEmp_rank().equals("evp")) {
                	emp_Rank = "전무";
                } else if (employeeData.getMainTable().getEmp_rank().equals("fl")) {
                	emp_Rank = "fellow";
                } else if (employeeData.getMainTable().getEmp_rank().equals("gl")) {
                	emp_Rank = "명장";
                } else if (employeeData.getMainTable().getEmp_rank().equals("j")) {
                	emp_Rank = "주임";
                } else if (employeeData.getMainTable().getEmp_rank().equals("k")) {
                	emp_Rank = "과장";
                } else if (employeeData.getMainTable().getEmp_rank().equals("ms")) {
                	emp_Rank = "master";
                } else if (employeeData.getMainTable().getEmp_rank().equals("s")) {
                	emp_Rank = "사원";
                } else if (employeeData.getMainTable().getEmp_rank().equals("svp")) {
                	emp_Rank = "부사장";
                }
                
                //직책 (main_table select 박스 보여주는 용)
                String work_Pos = "";
                if(employeeData.getMainTable().getWork_pos().equals("gl")) {
                	work_Pos = "그룹장";
                } else if (employeeData.getMainTable().getWork_pos().equals("gm")) {
                	work_Pos = "실장";
                } else if (employeeData.getMainTable().getWork_pos().equals("hc")) {
                	work_Pos = "사업부장";
                } else if (employeeData.getMainTable().getWork_pos().equals("hm")) {
                	work_Pos = "본부장";
                } else if (employeeData.getMainTable().getWork_pos().equals("pl")) {
                	work_Pos = "파트장";
                } else if (employeeData.getMainTable().getWork_pos().equals("tl")) {
                	work_Pos = "팀장";
                } else if (employeeData.getMainTable().getWork_pos().equals("tm")) {
                	work_Pos = "팀원";
                }
                
                //직무 (main_table select 박스 보여주는 용)
                String Position = "";
                if(employeeData.getMainTable().getPosition().equals("de")) {
                	Position = "개발";
                } else if (employeeData.getMainTable().getPosition().equals("et")) {
                	Position = "FAB기타";
                } else if (employeeData.getMainTable().getPosition().equals("og")) {
                	Position = "지원";
                } else if (employeeData.getMainTable().getPosition().equals("om")) {
                	Position = "운영(오피스)";
                } else if (employeeData.getMainTable().getPosition().equals("sm")) {
                	Position = "운영(교대)";
                }
                if(employeeData == null) {
                    return "redirect:/main";
                }
                else {
                	//select 박스 값을 가져오기 위함`
                    List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main(pageNumber, pageSize);
                    List<StandardManagementModel> jobstandard = standardManagementRepo.getStandard("job");
                    List<StandardManagementModel> busstandard = standardManagementRepo.getStandard("bus");
                    List<StandardManagementModel> grostandard = standardManagementRepo.getStandard("gro");
                    List<StandardManagementModel> posstandard = standardManagementRepo.getStandard("pos");
                    List<StandardManagementModel> rankstandard = standardManagementRepo.getStandard("rank");
                    model.addAttribute("emp_hist", emp_hist);
                    model.addAttribute("employees", employees);
                    model.addAttribute("jobstandard", jobstandard);
                    model.addAttribute("busstandard", busstandard);
                    model.addAttribute("grostandard", grostandard);
                    model.addAttribute("posstandard", posstandard);
                    model.addAttribute("rankstandard", rankstandard);
    
                	//DB에 저장된 값을 가져오기 위함(main_table)
                    model.addAttribute("mail_head", mail_head);
                    model.addAttribute("mail_domain", mail_domain);
                    model.addAttribute("address_1", address_1);
                    model.addAttribute("address_2", address_2);
                    model.addAttribute("dept_Biz", dept_Biz);
                    model.addAttribute("dept_Group", dept_Group);
                    model.addAttribute("emp_Rank", emp_Rank);
                    model.addAttribute("work_Pos", work_Pos);
                    model.addAttribute("Position", Position);
                    
                    //DB에 저장된 값을 가져오기 위함(경력,학력,기술사항,가족관계,자격증,교육이수,상벌,직급/호봉)
                	model.addAttribute("emp", employeeData.getMainTable());
                	model.addAttribute("career", employeeData.getCareer());
                	model.addAttribute("schoolEducation", employeeData.getSchoolEducation());
                	model.addAttribute("technical", employeeData.getTechnical());
                	model.addAttribute("family", employeeData.getFamily());
//                	System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | name_family : "+employeeData.getFamily().getName_family());
                	model.addAttribute("certificate", employeeData.getCertificate());
                	model.addAttribute("educationCompletion", employeeData.getEducationCompletion());
                	model.addAttribute("rewardPunishment", employeeData.getRewardPunishment());
//                    System.out.println("	ㄴ Cont / getEmpInfo/{emp_num} | name_rew_gbn : "+employeeData.getRewardPunishment().getName_rew_gbn());   
                	model.addAttribute("rankSalary", employeeData.getRankSalary());
                    // model.addAttribute("emp", emp);
                }
                return "board_update";
    
            } catch (Exception e) {
                model.addAttribute("errorMessage", "사원 수정 실패");
                e.printStackTrace();
                return "main";
            }
        }

    // 사원 정보 수정 기능
    @PostMapping("/employee/update")
    public String UpdateEmp(@RequestParam(value = "emp_num", required = false) Integer emp_num,
                                MaintableModel maintableModel, 
                                CareerModel careerModel,
                                School_educationModel school_educationModel, 
                                TechnicalModel technicalModel,
                                FamilyModel familyModel, 
                                CertificateModel certificateModel,
                                E_C_Model e_C_Model, 
                                R_P_Model r_P_Model,
                                R_S_Model r_S_Model,
                                Model model, HttpSession session, 
                                HttpServletRequest request) {
        String userId = (String) session.getAttribute("userId");
        System.out.println("/employee/update 로직 타는중~");
        // if(userId == null){
        //     System.out.println("세션문제");
        //     return "redirect:/login.html";
        // }
        
        try {
            if (maintableModel != null) {
                boolean main = employeeManagementRepo.updateEmp_main(maintableModel, emp_num);
                System.out.println("main rank : " + maintableModel.getEmp_rank());
                System.out.println("main sal : " + maintableModel.getSalary());
                System.out.println("tech note : " + technicalModel.getNote());
            }
            if (careerModel != null) {
                boolean car = employeeManagementRepo.updateEmp_career(maintableModel, careerModel, emp_num);
            }
            if (school_educationModel != null) {
                boolean sch_edu = employeeManagementRepo.updateEmp_school_education(maintableModel, school_educationModel, emp_num);
            }
            if (technicalModel != null) {
                boolean tech = employeeManagementRepo.updateEmp_Technical(maintableModel, technicalModel, emp_num);
            }
            if (familyModel != null) {
                boolean fam = employeeManagementRepo.updateEmp_Family(maintableModel, familyModel, emp_num);
            }
            if (certificateModel != null) {
                boolean cer = employeeManagementRepo.updateEmp_Certificate(maintableModel, certificateModel, emp_num);
            }
            if (e_C_Model != null) {
                boolean ec = employeeManagementRepo.updateEmp_E_C(maintableModel, e_C_Model, emp_num);
            }
            if (r_P_Model != null) {
                boolean rp = employeeManagementRepo.updateEmp_R_P(maintableModel, r_P_Model, emp_num);
            }
            if (r_S_Model != null) {
                boolean rs = employeeManagementRepo.updateEmp_R_S(maintableModel, r_S_Model, emp_num);
            }
        } catch (Exception e) {
            System.out.println("사원 정보 수정 중 에러 발생");
            e.printStackTrace();
            return "main.html";
        }
    
        return "redirect:/main";
    }

    // SessionController로 옮김 - 삭제해도 무관
    /*
    // 로그인 시 로그아웃 왼쪽에 사진, 이름, 권한 출력하는 컨트롤러
    @GetMapping("/employees/session")
    public String getSessionInfo(HttpSession session, Model model, HttpServletRequest request) {
        System.out.println(">> EmployeeManagementController : getSessionInfo // 세션 정보 가져오기");
        String userId = (String) session.getAttribute("userid");
        
        if(userId == null){
            return "redirect:/login.html";
        }
        
        try {
            SessionModel sessionModel = employeeManagementRepo.getSession(session, request);
            if (sessionModel != null) {
                // 이미지 Blob을 Base64로 변환
                // Blob blob = sessionModel.getPic();
                // String base64Image = null;
                // if (blob != null) {
                //     byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                //     base64Image = Base64.getEncoder().encodeToString(imageBytes);
                //     System.out.println("Base64 Image: " + base64Image); // 디버깅 로그 추가
                // }

            // model.addAttribute("pic", base64Image);
            model.addAttribute("name_kor", sessionModel.getName_kor());
            model.addAttribute("authority", sessionModel.getAuthority());
            System.out.println("Name: " + sessionModel.getName_kor()); // 디버깅 로그 추가
            System.out.println("Authority: " + sessionModel.getAuthority()); // 디버깅 로그 추가
            } else {
                return "redirect:/login.html"; // 세션 정보 없음
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "세션 불러오기 실패");
            e.printStackTrace();
            return "redirect:/login.html";
        }
        return "redirect:/navgation.html";
    }

    */
    //과제저장하고 투입인력추가한거 가져오기
    // 과제 인력 투입 시 main_table 에 있는 사원에 대한 pm_code, input_gbn 값 update
    @PostMapping("/updateEmp_pm_data")
    public String updateEmp_pm_data(@RequestParam("emp_num") int emp_num,
                                    @RequestParam("pm_code") int pm_code,
                                    Model model, HttpSession session, HttpServletRequest request) {
        String userId = (String) session.getAttribute("userId");

        if(userId == null){
            return "redirect:/login.html";
        }
        
        try {
            boolean emp = employeeManagementRepo.updateEmp_pm_data(pm_code, emp_num);

            if(emp){
                model.addAttribute("update emp", "사원 pm 정보 수정 완료");
            } else {
                model.addAttribute("errorMessage", "사원 정보 수정 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "과제 수정 중 exception 발생");
            e.printStackTrace();
            return "";
        }
        return "redirect:/assign";
    }

    // 과제에서 인원 철수 시 main_table에 있는 사원에 대한 pm_code, input_gbn 값 삭제
    @PostMapping("/deleteEmp_pm_data")
    public String deleteEmp_pm_data(@RequestParam("emp_num") int emp_num,
                                    @ModelAttribute EmployeeManagementModel employeeManagementModel,
                                    @ModelAttribute TaskManagementModel taskManagementModel,
                                    Model model, HttpSession session, HttpServletRequest request) {
        String userId = (String) session.getAttribute("userId");

        if(userId == null){
            return "redirect:/login.html";
        }

        try {
            boolean emp = employeeManagementRepo.deleteEmp_pm_data(employeeManagementModel, emp_num);

            if(emp){
                model.addAttribute("delete emp", "사원 pm 정보 삭제 완료");
            } else {
                model.addAttribute("errorMessage", "사원 정보 삭제 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "과제 삭제 중 exception 발생");
            e.printStackTrace();
            return "";
        }
        return "redirect:/assign";
    }    

    // 체크한 사원 이력서 엑셀(excel)로 출력 - 이력서 아닌 리스트형식으로 출력되고 있는 상태
    // 이력서 양식이 따로 존재하니 참고하여 만들면 됨
    @PostMapping("/employee/resume")
    public ResponseEntity<byte[]> downloadExcel(@RequestBody List<Integer> empNum) throws IOException {
        // 직원 정보를 가져오는 로직 (예: DB 조회)
        List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main_for_excel(empNum);

        // Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        // Header row 생성
        Row headerRow = sheet.createRow(0);

        // Header Cell Style 생성
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 글꼴 설정
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        // 테두리 설정
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);

        String[] columns = {"사번", "생년월일", "성별", "전화번호", "Email", "기혼여부", "주소"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

	    // 직원 정보 채우기
	    int rowNum = 1;
	    for (EmployeeManagementModel employee : employees) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue(employee.getEmp_num());
	        
	        // reg_num의 앞 6자리만 가져오기
	        String regNum = employee.getReg_num();
	        if (regNum != null && regNum.length() >= 6) {
	            regNum = regNum.substring(0, 6);
	        }
	        row.createCell(1).setCellValue(regNum);
	        
	        row.createCell(2).setCellValue(employee.getGender());
	        row.createCell(3).setCellValue(employee.getPhone());
	        row.createCell(4).setCellValue(employee.getMail());
	        row.createCell(5).setCellValue(employee.getMarry());
	        row.createCell(6).setCellValue(employee.getAddress());
	    }

        // 열 크기 자동 조정
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 엑셀 파일을 바이트 배열로 변환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        byte[] excelBytes = outputStream.toByteArray();

        // 응답 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "employees.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }
}
