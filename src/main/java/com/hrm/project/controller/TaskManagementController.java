package com.hrm.project.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hrm.project.model.EmployeeManagementModel;
import com.hrm.project.model.Pm_histModel;
import com.hrm.project.model.TaskManagementModel;
import com.hrm.project.repository.EmployeeManagementRepo;
import com.hrm.project.repository.LoginRepo;
import com.hrm.project.repository.TaskManagementRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
@RequiredArgsConstructor
public class TaskManagementController {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private TaskManagementRepo taskManagementRepo;
    private EmployeeManagementRepo employeeManagementRepo;
    Pm_histModel pm_histModel;
    TaskManagementModel taskManagementModel;
    EmployeeManagementModel employeeManagementModel;
    HttpSession httpSession;
    private LoginRepo loginRepo;
    
    @Autowired
    public TaskManagementController(DataSource dataSource, TaskManagementRepo taskManagementRepo, EmployeeManagementRepo employeeManagementRepo){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.taskManagementRepo = taskManagementRepo;
        this.employeeManagementRepo = employeeManagementRepo;
    }

    //과제관리 조회 테이블
    @GetMapping("/assign")
    public String tasktable(HttpSession session, Model model, HttpServletRequest request,
                            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize){
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 로그인 화면으로 리다이렉트
        if(userId == null){
            return "redirect:/login.html";
        }
    
        try{
            // 전체 과제 수 가져오기
            int total = taskManagementRepo.getTaskCount();

            // 과제 정보 조회
            List<TaskManagementModel> tasks = taskManagementRepo.getTask(pageNumber, pageSize);

            // 과제 수 계산 - 페이지당 10개씩
            int totalpage = (int) Math.ceil((double) total / pageSize);

            // 페이지 번호들의 리스트 생성
            List<Integer> pages = new ArrayList<>();
            for(int i = 1; i <= totalpage; i++){
                pages.add(i);
            }

            // 현재 페이지와 페이지 수, 페이지 번호 리스트를 모델에 추가
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", totalpage);
            model.addAttribute("pages", pages);
            // 모델에 데이터 추가
            model.addAttribute("tasks", tasks);

            System.out.println(">> TaskManagementController : tasktable(과제 조회 return)");

            return "assign";
    
        } catch (Exception e){
            e.printStackTrace();
            return null; // 오류 페이지를 반환하도록 수정
        }
    }
    // 페이징
    @GetMapping("/paging_task") 
    public String handlePagingRequest(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        // 페이지 처리 코드 작성
        return "redirect:/assign?pageNumber=" + page;
    }

    @GetMapping("/assign_new")
    public String assign_new(HttpSession session, Model model, HttpServletRequest request,
    @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
    @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 로그인 화면으로 리다이렉트
        if(userId == null){
            return "redirect:/login.html";
        }
        try{
            // 전체 과제 수 가져오기
            int total = taskManagementRepo.getTaskCount();

            // 과제 정보 조회
            List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main(pageNumber, pageSize);
            List<TaskManagementModel> tasks = taskManagementRepo.getTask(pageNumber, pageSize);
           
            // 과제 수 계산 - 페이지당 10개씩
            int totalpage = (int) Math.ceil((double) total / pageSize);

            // 페이지 번호들의 리스트 생성
            List<Integer> pages = new ArrayList<>();
            for(int i = 1; i <= totalpage; i++){
                pages.add(i);
            }
            // 모델에 데이터 추가
            model.addAttribute("employees", employees);
            model.addAttribute("tasks", tasks);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "assign_new";
    }

    @GetMapping("/goBack")
    public String tasktable_goBack(HttpSession session, Model model, HttpServletRequest request,
                            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize){
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
    
        // 로그인 안된 상태면 로그인 화면으로 리다이렉트
        if(userId == null){
            return "redirect:/login.html";
        }
    
        try{
            // 전체 과제 수 가져오기
            int total = taskManagementRepo.getTaskCount();

            // 과제 정보 조회
            List<TaskManagementModel> tasks = taskManagementRepo.getTask(pageNumber, pageSize);

            // 과제 수 계산 - 페이지당 10개씩
            int totalpage = (int) Math.ceil((double) total / pageSize);

            // 페이지 번호들의 리스트 생성
            List<Integer> pages = new ArrayList<>();
            for(int i = 1; i <= totalpage; i++) {
                pages.add(i);
            }

            // 현재 페이지와 페이지 수, 페이지 번호 리스트를 모델에 추가
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", totalpage);
            model.addAttribute("pages", pages);
            // 모델에 데이터 추가
            model.addAttribute("tasks", tasks);
            
            return "assign";
    
        } catch (Exception e){
            e.printStackTrace();
            return "error"; // 오류 페이지를 반환하도록 수정
        }
    }
    
    // 과제 이름 검색 기능 - 과제명으로만 검색 가능
    @PostMapping("/searchTasks")
    public String searchTasks(@RequestParam("keyword") String keyword, Model model) {
        //과제 정보 검색
        List<TaskManagementModel> tasks = taskManagementRepo.searchTasks(keyword);

        model.addAttribute("tasks", tasks);

        return "assign";
    }

    // 과제 삭제 기능
    @DeleteMapping("/deleteTask")
    public String deleteTask(@RequestParam("pm_code") String pm_code, Model model){
        try{
            boolean deleted = taskManagementRepo.deleteTask(pm_code);
            List<TaskManagementModel> confirmed = taskManagementRepo.getConfirmed();

            //확정 유무가 Y인 경우 삭제 불가능
            for(TaskManagementModel tas : confirmed){
                String pcode = tas.getPm_code();
                if(pcode.equals("Y")){
                    System.out.println("삭제가 불가능합니다.");
                    return null;
                }
            }

            if(deleted){
                model.addAttribute("successMessage", "과제 삭제 완료");
            } else {
                model.addAttribute("errorMessage", "과제 삭제 실패");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "과제 삭제 중 오류 발생");
            e.printStackTrace();
        }

        // 사용자를 다시 과제 관리 페이지로 리다이렉트
        return "redirect:/tasks.html";
    }

    // 신규 과제 추가 기능
    @PostMapping("/addTask")
    public String addTasks(@ModelAttribute TaskManagementModel taskManagementModel,
                           @ModelAttribute EmployeeManagementModel employeeManagementModel,
                           @RequestParam(value = "addedEmployeeIds", required = false) String[] addedEmployeeIds,
                           Model model) {
        try {
            // addedEmployeeIds 확인
            if (addedEmployeeIds != null) {
                for (String id : addedEmployeeIds) {
                    System.out.println("Employee ID: " + id);
                }
            } else {
                System.out.println("No Employee IDs provided");
            }
    
            boolean added = taskManagementRepo.addTask(taskManagementModel);
            boolean added_empModal = true; // 히스토리 추가 결과
            if (added && addedEmployeeIds != null) {
                for (String empId : addedEmployeeIds) {
                    EmployeeManagementModel empModel = new EmployeeManagementModel();
                    empModel.setEmp_num(Integer.parseInt(empId));
                    boolean isHistAdded = taskManagementRepo.insert_hist(empModel, taskManagementModel);
                    if (!isHistAdded) {
                        added_empModal = false;
                        break; // 오류 발생 시 반복 중지
                    }
                }
            }
    
            if (added && added_empModal) {
                model.addAttribute("successMessage", "과제 추가 및 히스토리 추가 완료");
            } else {
                model.addAttribute("errorMessage", "과제 추가 또는 히스토리 추가 중 오류 발생");
            }
    
        } catch (Exception e) {
            model.addAttribute("errorMessage", "과제 추가 및 히스토리 추가 중 오류 발생");
            e.printStackTrace();
        }
    
        // 사용자를 다시 과제 관리 페이지로 리다이렉트
        return "redirect:/assign";
    }
    


    // 사원 신규(수정) 화면 과제 현황 출력
    @PostMapping("TaskToEmp")
    public String taskToEmp(HttpSession session, Model model){
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("userid");
        
        // 로그인 안된 상태면 반환 값 없음
        if(userId == null){
            // 로그인 페이지로 리다이렉트
            return "redirect:/login.html";
        }
        try {
            // 과제 정보 조회
            List<TaskManagementModel> tasks = taskManagementRepo.sin();

            // 모델 데이터 추가
            model.addAttribute("tasks", tasks);

            // 템플릿 반환 - 아직 안만들어져서 아무것도 안들어감
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 오류 페이지로 리다이렉트 또는 오류 메시지 반환
            return "error";
        }
    }

    // 과제 수정 시 과제 코드를 가져오는 메소드
    @GetMapping("/getTask/{pm_code}")
    public String getTaskByCode(@PathVariable("pm_code") String pm_code, Model model, HttpServletRequest request, HttpSession session) {
        String userId = (String) session.getAttribute("userid");

        if(userId == null){
            return "redirect:/login.html";
        }

        try {
            TaskManagementModel taskManagementModel = new TaskManagementModel();
            taskManagementModel.setPm_code(pm_code);

            TaskManagementModel pmCode = taskManagementRepo.getTaskByCode(pm_code);

            model.addAttribute("setPmcode", pmCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "assign_update";
    }
    
    // 과제 정보 수정 시 기존 db 데이터 가져오기
    @GetMapping("/getTaskInfo/{pm_code}")
    public String gettask_update(@PathVariable("pm_code") String pm_code, Model model, HttpSession session, HttpServletRequest request,
           @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
           @RequestParam(name = "pageSize", required = false, defaultValue = "2000") int pageSize) {
        String userId = (String) session.getAttribute("userid"); // 세션 속성 이름 수정
    
        System.out.println(">> TaskManagementController : getTaskInfo/{pm_code}");
    
        // 로그인 안된 상태면 로그인 페이지로 리다이렉트
        if(userId == null){
            return "redirect:/login.html";
        }
    
        try{
            // 과제 코드를 사용하여 해당 과제 정보를 가져옴
            TaskManagementModel taskManagementModel = taskManagementRepo.getTaskByCode(pm_code);
            System.out.println("	ㄴ taskManagementModel 값 : "+taskManagementModel.getPm_code());
            List<EmployeeManagementModel> employees = employeeManagementRepo.getEmployees_main(pageNumber, pageSize);
            List<TaskManagementModel> tasks = taskManagementRepo.getTask(pageNumber, pageSize);
            List<EmployeeManagementModel> emp = taskManagementRepo.getEmpFromPm_hist(pm_code);
    
            EmployeeManagementModel employeeManagementModel = new EmployeeManagementModel();
    
            List<TaskManagementModel> tasks_id = taskManagementRepo.getTask_update(taskManagementModel, employeeManagementModel);
    
            model.addAttribute("tasks_id", tasks_id);
//            System.out.println("\n >> tasks_id : "+tasks_id.get);
            model.addAttribute("employees", employees);
            model.addAttribute("tasks", tasks);
            // 여기까지가 pm_code에 대한 과제 정보를 가져오는 부분

            // 과제 투입 인력 가져오기
            model.addAttribute("emp", emp);
            System.out.println("	ㄴ emp 값 : "+emp);
            System.out.println("	ㄴ emp 0번째 값 : "+emp.indexOf(0));
            System.out.println("	ㄴ emp 1번째 값 : "+emp.indexOf(1));
            System.out.println("	ㄴ emp 2번째 값 : "+emp.indexOf(2));
            System.out.println("	ㄴ emp 3번째 값 : "+emp.indexOf(3));

            // List<TaskManagementModel> updateTask = taskManagementRepo.update_task(taskManagementModel);

            // if(updateTask != null && !updateTask.isEmpty()){
            //     // 업데이트된 과제 정보를 가져와서 모델에 추가
            //     TaskManagementModel updatedTask = updateTask.get(0);
            //     model.addAttribute("updatedTask", updatedTask);
            // } else {
            //     // 업데이트 실패 시 에러 메세지 출력
            //     model.addAttribute("errorMessage", "과제 정보를 업데이트 중 오류 발생");
            // }
        } catch(Exception e) {
            // 예외 처리: 사용자에게 적절한 방식으로 표시하거나 로깅하여 추적할 수 있도록 처리
            model.addAttribute("errorMessage", "과제 정보를 가져오는 중에 오류가 발생했습니다.");
            e.printStackTrace();
            return "assign";
        }
        return "assign_update";
    }

    // 과제 수정 시 정보 업데이트
    @PostMapping("/updateTask")
    public String updateTask(@RequestParam("pm_code") String pm_code, 
                        @ModelAttribute TaskManagementModel taskManagementModel, 
                        Model model, HttpSession session, HttpServletRequest request) {
        String userId = (String) session.getAttribute("userId");

        if(userId == null){
            return "redirect:/login.html";
        }
        
        try{
            // 과제 코드를 사용하여 해당 과제 정보를 가져옴
            // taskManagementModel = taskManagementRepo.getTaskByCode(pm_code);
            // System.out.println("/updateTask 로직 ");

            TaskManagementModel tasks = taskManagementRepo.getTaskByCode(pm_code);
            System.out.println("/updateTask 로직 ");

            // 가져온 과제 정보가 없는 경우 처리
            if(tasks == null) {
                // 에러 처리 등 필요한 작업 수행
                return "/assign"; // 예시로 에러 페이지로 리다이렉트하거나 에러 메시지를 표시하는 등의 처리를 수행할 수 있음
            }

            tasks.setPm_code(taskManagementModel.getPm_code());
            tasks.setPm_name(taskManagementModel.getPm_name());
            tasks.setPm_nori_manager(taskManagementModel.getPm_nori_manager());
            tasks.setPm_nori_bu(taskManagementModel.getPm_nori_bu());
            tasks.setPm_create_date(taskManagementModel.getPm_create_date());
            tasks.setPm_manager(taskManagementModel.getPm_manager());
            tasks.setPm_bu(taskManagementModel.getPm_bu());
            tasks.setPm_business(taskManagementModel.getPm_code());
            tasks.setPm_price(taskManagementModel.getPm_price());
            tasks.setPm_workload(taskManagementModel.getPm_workload());
            tasks.setPm_full(taskManagementModel.getPm_full());
            tasks.setPm_part(taskManagementModel.getPm_part());
            tasks.setPm_suggest_date(taskManagementModel.getPm_suggest_date());
            tasks.setPm_start_date(taskManagementModel.getPm_start_date());
            tasks.setPm_end_date(taskManagementModel.getPm_end_date());
            tasks.setPm_work_date(taskManagementModel.getPm_work_date());
            tasks.setPm_status(taskManagementModel.getPm_status());
            tasks.setPm_outline(taskManagementModel.getPm_outline());
            tasks.setPm_bego(taskManagementModel.getPm_bego());
            

            // 과제 정보 업데이트 작업 수행
            boolean updateTask = taskManagementRepo.update_task(tasks);

            if(updateTask){
                // 업데이트된 과제 정보를 가져와서 모델에 추가
                // TaskManagementModel updatedTask = updateTask.get(0);
                model.addAttribute("updatedTask", "과제 수정 완료");
            } else {
                // 업데이트 실패 시 에러 메세지 출력
                model.addAttribute("errorMessage", "과제 정보를 업데이트 중 오류 발생");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "과제 수정 실패");
            e.printStackTrace();
            return "assign.html";
        }
        return "assign";
    }


    // pm_hist 테이블에 투입인력 평가 및 기술 기입
    @PostMapping("/update_hist")
    public String update_hist(Model model) {
        try {
            boolean hist = taskManagementRepo.update_hist(pm_histModel);

            model.addAttribute("hist", hist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/assign";
    }
    
}