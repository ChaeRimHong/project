// package com.hrm.project.service;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;

// import com.hrm.project.model.EmployeeManagementModel;
// import com.hrm.project.repository.EmployeeManagementRepo;

// import lombok.RequiredArgsConstructor;


// @Service
// @RequiredArgsConstructor
// public class EmployeeManagementService {
//     private final EmployeeManagementRepo employeeManagementRepo;

//     public Page<EmployeeManagementModel> paging(int pageNumber, int pageSize) {
//         // 페이지 번호를 0부터 시작하도록 조정
//         int page = pageNumber - 1;
        
//         // 한 페이지당 15개씩 데이터를 가져오고, 정렬은 id 기준으로 내림차순
//         Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));

//         // 페이징된 데이터를 가져오기
//         //Page<EmployeeManagementModel> employeePage = employeeManagementRepo.findAll(pageable);

//         //return employeePage;
//     }
// }

