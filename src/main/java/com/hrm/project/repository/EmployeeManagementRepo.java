package com.hrm.project.repository;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.attribute.standard.Fidelity;
import javax.sql.DataSource;
import java.io.File;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
//import org.apache.poi.hpsf.Array;
import org.flywaydb.core.internal.jdbc.Results;
import org.h2.result.ResultTarget;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import com.hrm.project.model.CareerModel;
import com.hrm.project.model.CertificateModel;
import com.hrm.project.model.E_C_Model;
import com.hrm.project.model.EmployeeDataModel;
import com.hrm.project.model.EmployeeManagementModel;
import com.hrm.project.model.FamilyModel;
import com.hrm.project.model.MaintableModel;
import com.hrm.project.model.R_P_Model;
import com.hrm.project.model.R_S_Model;
import com.hrm.project.model.School_educationModel;
import com.hrm.project.model.SessionModel;
import com.hrm.project.model.TechnicalModel;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ch.qos.logback.core.net.SyslogOutputStream;
import io.swagger.models.Path;
import io.swagger.models.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.hrm.project.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Blob;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

// --------------------------------------------------
@RestController
@CrossOrigin(origins = "http://localhost:8080")
// --------------------------------------------------
@Repository("employeeManagementRepo")
public class EmployeeManagementRepo implements EmployeeManagementRepoI{
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private EmployeeManagementRepo employeeManagementRepo;
    EmployeeManagementModel employeeManagementModel;

    @Autowired
    public EmployeeManagementRepo(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    // 사원관리 테이블 select
    public List<EmployeeManagementModel> getEmployees_main(int pageNumber, int pageSize) {
        List<EmployeeManagementModel> employees = new ArrayList<>();
        try {
            Connection conn = common.dbcon();

            // 데이터베이스 선택 쿼리
            Statement stmt = conn.createStatement();
            stmt.execute("USE pms");

            // SQL 쿼리 작성
            String query = "SELECT "
                        + " m.name_kor, "
                        + " m.name_eng, "
                        + " m.address, "
                        + " m.address_num,"
                        + " (SELECT gbn_name FROM master_data WHERE gbn_detail = m.dept_biz AND gbn_cd = 'bus') AS dept_biz,"
                        + " (SELECT gbn_name FROM master_data WHERE gbn_detail = m.dept_group AND gbn_cd = 'gro') AS dept_group,"
                        + " m.date_join,"
                        + " m.last_edu,"
                        + " m.military,"
                        + " m.marry,"
                        + " m.height,"
                        + " m.weight,"
                        + " m.gender,"
                        + " m.pm_code,"
                        + " m.input_gbn,"
                        + " m.emp_num,"
                        + " (SELECT gbn_name FROM master_data WHERE gbn_detail = m.position AND gbn_cd = 'job') AS job,"
                        + " (SELECT gbn_name FROM master_data WHERE gbn_detail = m.emp_rank AND gbn_cd = 'rank') AS work_pos,"
                        + " (SELECT gbn_name FROM master_data WHERE gbn_detail = m.work_pos AND gbn_cd = 'pos') AS position,"
                        + " m.phone,"
                        + " m.mail "
                        + "FROM main_table m "
                        + "LIMIT ? OFFSET ?";

            // 페이지 번호와 페이지 크기를 기반으로 오프셋(offset) 계산
            int offset = (pageNumber - 1) * pageSize;

            // 쿼리 실행 및 결과 가져오기
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String dept_biz = resultSet.getString("dept_biz");
                String dept_group = resultSet.getString("dept_group");
                String name_kor = resultSet.getString("name_kor");
                String name_eng = resultSet.getString("name_eng");
                int emp_num = resultSet.getInt("emp_num");
                String job = resultSet.getString("job");
                String work_pos = resultSet.getString("work_pos");
                String position = resultSet.getString("position");
                String phone = resultSet.getString("phone");
                String mail = resultSet.getString("mail");
                String address = resultSet.getString("address");
                int address_num = resultSet.getInt("address_num");
                String last_edu = resultSet.getString("last_edu");
                String military = resultSet.getString("military");
                String marry = resultSet.getString("marry");
                int height = resultSet.getInt("height");
                int weight = resultSet.getInt("weight");
                String gender = resultSet.getString("gender");
                String pm_code = resultSet.getString("pm_code");
                String input_gbn = resultSet.getString("input_gbn");

                // EmployeeManagementModel 객체 생성 및 값 설정
                EmployeeManagementModel employeeManagementModel = new EmployeeManagementModel();
                employeeManagementModel.setDept_biz(dept_biz);
                employeeManagementModel.setDept_group(dept_group);
                employeeManagementModel.setName_kor(name_kor);
                employeeManagementModel.setName_eng(name_eng);
                employeeManagementModel.setEmp_num(emp_num);
                employeeManagementModel.setPosition(job);
                employeeManagementModel.setEmp_rank(work_pos);
                employeeManagementModel.setWork_pos(position);
                employeeManagementModel.setPhone(phone);
                employeeManagementModel.setMail(mail);
                employeeManagementModel.setAddress(address);
                employeeManagementModel.setAddress_num(address_num);
                employeeManagementModel.setLast_edu(last_edu);
                employeeManagementModel.setMilitary(military);
                employeeManagementModel.setMarry(marry);
                employeeManagementModel.setHeight(height);
                employeeManagementModel.setWeight(weight);
                employeeManagementModel.setGender(gender);
                employeeManagementModel.setPm_code(pm_code);
                employeeManagementModel.setInput_gbn(input_gbn);

                // 리스트에 추가
                employees.add(employeeManagementModel);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
        return employees;
    }

    // 전체 직원 수를 반환하는 메서드
    public int getEmployeeCount(){
        try {
            Connection conn = common.dbcon();

            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();
            
            //SQL 쿼리 작성
            String countQuery = "SELECT COUNT(*) AS total FROM MAIN_TABLE";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(countQuery);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("total");
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
        }
        return 0;
    }
    
    // 사원관리 테이블 검색기능
    public List<EmployeeManagementModel> searchEmployees(String keyword){
        List<EmployeeManagementModel> employees = new ArrayList<>();
        try{
            Connection conn = common.dbcon();

            //데이터베이스 선택 쿼리
            String useDataBaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDataBaseQuery);
            useDatabaseStmt.execute();

            //SQL 쿼리 작성 - 이름으로 검색
            String sql = "SELECT dept_biz, dept_group, name_kor, emp_num, position, emp_rank, work_pos, phone, mail FROM MAIN_TABLE WHERE name_kor LIKE ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                EmployeeManagementModel employee = new EmployeeManagementModel();
                employee.setDept_biz(resultSet.getString("dept_biz"));
                employee.setDept_group(resultSet.getString("dept_group"));
                employee.setName_kor(resultSet.getString("name_kor"));
                employee.setEmp_num(resultSet.getInt("emp_num"));
                employee.setPosition(resultSet.getString("position"));
                employee.setEmp_rank(resultSet.getString("emp_rank"));
                employee.setWork_pos(resultSet.getString("work_pos"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setMail(resultSet.getString("mail"));
                employees.add(employee);
            }
            conn.close();
            return employees;
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
    }

    // 사원관리 테이블 유저 삭제기능
    public boolean deleteUser(int emp_num){
        try{
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "DELETE FROM MAIN_TABLE WHERE emp_num = ?";

            System.out.println("\n deleteUser: emp_num = "+emp_num);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, emp_num);

            // 삭제 쿼리 실행 
            int rowsAffected = stmt.executeUpdate();

            conn.close();

            // 삭제된 행이 없으면 실패로 간주
            if(rowsAffected > 0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return false;
        }
    }

    // 사원 관리 유저 추가 기능
    public boolean addUser(MaintableModel maintableModel, CareerModel careerModel, School_educationModel school_educationModel
                , TechnicalModel technicalModel, FamilyModel familyModel, CertificateModel certificateModel
                , E_C_Model e_C_Model, R_P_Model r_P_Model, R_S_Model r_S_Model, HttpServletRequest request){
        try{
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // SQL 쿼리 작성
            String sql_main_table = "INSERT INTO main_table("
            + " emp_num, name_kor, name_eng, reg_num, pic, "
            + " age, phone, mail, address, address_num, "
            + " dept_biz, dept_group, date_join, "
            + " emp_rank, work_pos, position, "
            + " salary, last_edu, military, "
            + " marry, height, weight, "
            + " gender "
            + " ) "
            + " SELECT "
            + " ?, ?, ?, ?, ?,"
            + " YEAR(CURRENT_DATE()) - (if(mid(?, 7, 1) = '1' or mid(?, 7, 1) = '2', 1900, 2000) + left(?, 2)) + 1 as age,"
            + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "
            + " FROM dual";
    
            String sql_carrer = "INSERT INTO career(emp_num, company, department, date_join, date_leave, work_period, final_rank, work_info)"
                        + "VALUES (?,?,?,?,?,?,?,?)";
            String sql_school_edu = "INSERT INTO school_education(emp_num, date_admition, date_graduate, name_school, location, major, sub_major, comment)"
                        + "VALUES (?,?,?,?,?,?,?,?)";
            String sql_technical = "INSERT INTO technical(emp_num, tec_detail, proficiency, note)"
                        + "VALUES (?,?,?,?)";
            String sql_family = "INSERT INTO family(emp_num, name_family, birth, age, rel, live) "
                        + "VALUES (?, ?, ?, TIMESTAMPDIFF(YEAR, ?, CURRENT_DATE()), ?, ?)";

            String sql_certificate = "INSERT INTO certificate(emp_num, name_license, date_acqui)"
                        + "VALUES (?,?,?)";
            String sql_e_c = "INSERT INTO e_c(emp_num, name_edu, date_start, date_end, edu_insti, idx)"
                        + "SELECT ?, ?, ?, ?, ?, IFNULL(MAX(idx), 0) + 1 FROM e_c";
            String sql_r_p = "INSERT INTO R_P(emp_num, name_rew_gbn, name_rew_puni, score, date_rew_puni, note)"
                        + "VALUES (?,?,?,?,?,?)";
            String sql_r_s = "INSERT INTO r_s(emp_num, emp_rank, salary, date_join)"
                        + "VALUES (?,?,?,?)";

            // PreparedStatement 객체 생성
            PreparedStatement stmt_main_table = conn.prepareStatement(sql_main_table);
            PreparedStatement stmt_career = conn.prepareStatement(sql_carrer);
            PreparedStatement stmt_school_edu = conn.prepareStatement(sql_school_edu);
            PreparedStatement stmt_technical = conn.prepareStatement(sql_technical);
            PreparedStatement stmt_family = conn.prepareStatement(sql_family);
            PreparedStatement stmt_certificate = conn.prepareStatement(sql_certificate);
            PreparedStatement stmt_e_c = conn.prepareStatement(sql_e_c);
            PreparedStatement stmt_r_p = conn.prepareStatement(sql_r_p);
            PreparedStatement stmt_r_s = conn.prepareStatement(sql_r_s);

            // 성별 계산 코드
            String regNum = maintableModel.getReg_num();
            String gender = "";

            if (regNum != null && regNum.length() >= 8) {
                String genderCode = regNum.substring(6, 7);
                if (genderCode.equals("1") || genderCode.equals("3")) {
                    gender = "남";
                } else if (genderCode.equals("2") || genderCode.equals("4")) {
                    gender = "여";
                } else {
                    gender = null;
                }
            } else {
                // regNum이 null이거나 길이가 8 미만인 경우
                gender = null;
            }

            // // 업로드된 파일을 특정 폴더에 저장
            // Part filePart = request.getPart("pic"); // 폼에서 업로드된 파일을 가져옴
            // if (filePart != null && filePart.getSize() > 0) {
            //     String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            //     String uploadFolder = getServletContext().getRealPath("") + File.separator + "uploads";
            //     File uploadDir = new File(uploadFolder);
            //     if (!uploadDir.exists()) {
            //         uploadDir.mkdir();
            //     }
            //     Path filePath = Paths.get(uploadFolder, fileName);
            //     try (InputStream fileContent = filePart.getInputStream()) {
            //         Files.copy(fileContent, filePath);
            //     }

            //     // 파일을 데이터베이스에 저장
            //     try (InputStream picStream = new FileInputStream(filePath.toFile())) {
            //         stmt_main_table.setBlob(5, picStream);
            //     }
            // } else {
            //     // 파일이 업로드되지 않은 경우
            //     stmt_main_table.setNull(5, java.sql.Types.BLOB);
            // }
            
            //값 설정
            //main_table
            stmt_main_table.setInt(1, maintableModel.getEmp_num());
            stmt_main_table.setString(2, maintableModel.getName_kor());
            stmt_main_table.setString(3, maintableModel.getName_eng());
            stmt_main_table.setString(4, maintableModel.getReg_num());

            // stmt_main_table.setBlob(5, maintableModel.getPic());
            // String nu = FileUploadServlet.handleFileUpload(file.getOriginalFilename(), maintableModel.getEmp_num());
            // String picFilePath = "C:\\HRIS\\project\\project\\Upload\\hr\\" + maintableModel.getEmp_num() + "\\kimJunoh.jpg";
            String picFilePath = "C:\\Users\\김준오\\Desktop\\kimJunoh.jpg";
            
            // Part filePart = request.getPart("image");
            InputStream picStream = new FileInputStream(picFilePath);
            stmt_main_table.setBlob(5, picStream);
            // try (FileInputStream fis = new FileInputStream(new File(picFilePath))) {
            //     stmt_main_table.setBlob(5, fis);
            // }


            // try (InputStream fis = new FileInputStream(new File(picFilePath))) {
            //     stmt_main_table.setBlob(5, fis);
            // } catch (IOException e) {
            //     e.printStackTrace();
            //     stmt_main_table.setNull(5, java.sql.Types.BLOB);
            // }


            stmt_main_table.setString(6, maintableModel.getReg_num());
            stmt_main_table.setString(7, maintableModel.getReg_num());
            stmt_main_table.setString(8, maintableModel.getReg_num());
            stmt_main_table.setString(9, maintableModel.getPhone());
            stmt_main_table.setString(10, maintableModel.getMail());
            stmt_main_table.setString(11, maintableModel.getAddress());
            stmt_main_table.setInt(12, maintableModel.getAddress_num());
            stmt_main_table.setString(13, maintableModel.getDept_biz());
            stmt_main_table.setString(14, maintableModel.getDept_group());
            stmt_main_table.setDate(15, maintableModel.getDate_join());
            stmt_main_table.setString(16, maintableModel.getEmp_rank());
            stmt_main_table.setString(17, maintableModel.getWork_pos());
            stmt_main_table.setString(18, maintableModel.getPosition());
            stmt_main_table.setString(19, maintableModel.getSalary());
            stmt_main_table.setString(20, maintableModel.getLast_edu());
            stmt_main_table.setString(21, maintableModel.getMilitary());
            stmt_main_table.setString(22, maintableModel.getMarry());
            stmt_main_table.setInt(23, maintableModel.getHeight());
            stmt_main_table.setInt(24, maintableModel.getWeight());
            stmt_main_table.setString(25, gender);

            String nameKor = maintableModel.getName_kor();
            int emp = maintableModel.getEmp_num();
            String reg = maintableModel.getReg_num();
            String debiz = maintableModel.getDept_biz();
            Blob pi = maintableModel.getPic();
            String posi = maintableModel.getPosition();
            // name_kor 값을 콘솔에 출력
            System.out.println("emp_num: " + emp);
            System.out.println("name_kor: " + nameKor);
            System.out.println("reg_num: " + reg);
            System.out.println("dept_biz: " + debiz);
            System.out.println("pic: " + pi); // null 값 가져옴
            System.out.println("position: " + posi);
            
            //career
            stmt_career.setInt(1, maintableModel.getEmp_num());
            stmt_career.setString(2, careerModel.getCompany());
            stmt_career.setString(3, careerModel.getDepartment());
            stmt_career.setDate(4, careerModel.getDate_join());
            stmt_career.setDate(5, careerModel.getDate_leave());
            stmt_career.setFloat(6, careerModel.getWork_period());
            stmt_career.setString(7, careerModel.getFinal_rank());
            stmt_career.setString(8, careerModel.getWork_info());

            String comp = careerModel.getCompany();
            String Depart = careerModel.getDepartment();
            System.out.println("company : " + comp);
            System.out.println("Department : " + Depart);
            //school_edu
            stmt_school_edu.setInt(1, maintableModel.getEmp_num());
            stmt_school_edu.setDate(2, school_educationModel.getDate_admition());
            stmt_school_edu.setDate(3, school_educationModel.getDate_graduate());
            stmt_school_edu.setString(4, school_educationModel.getName_school());
            stmt_school_edu.setString(5, school_educationModel.getLocation());
            stmt_school_edu.setString(6, school_educationModel.getMajor());
            stmt_school_edu.setString(7, school_educationModel.getSub_major());
            stmt_school_edu.setString(8, school_educationModel.getComment());

            //technical
            stmt_technical.setInt(1, maintableModel.getEmp_num());
            stmt_technical.setString(2, technicalModel.getTec_detail());
            stmt_technical.setString(3, technicalModel.getProficiency());
            stmt_technical.setString(4, technicalModel.getNote());

            //family
            stmt_family.setInt(1, maintableModel.getEmp_num());
            stmt_family.setString(2, familyModel.getName_family());
            stmt_family.setDate(3, familyModel.getBirth());
            stmt_family.setDate(4, familyModel.getBirth());
            stmt_family.setString(5, familyModel.getRel());
            stmt_family.setString(6, familyModel.getLive());

            String name_fam = familyModel.getName_family();
            Date bir = familyModel.getBirth();
            int age = familyModel.getAge();
            String rel = familyModel.getRel();
            String live = familyModel.getLive();

            System.out.println("name_fam : " + name_fam);
            System.out.println("bir : " + bir);
            System.out.println("age : " + age);
            System.out.println("rel : " + rel);
            System.out.println("live : " + live);
            
            //certificate
            stmt_certificate.setInt(1, maintableModel.getEmp_num());
            stmt_certificate.setString(2, certificateModel.getName_license());
            stmt_certificate.setDate(3, certificateModel.getDate_acqui());

            //E_C
            stmt_e_c.setInt(1, maintableModel.getEmp_num());
            stmt_e_c.setString(2, e_C_Model.getName_edu());
            stmt_e_c.setDate(3, e_C_Model.getDate_start());
            stmt_e_c.setDate(4, e_C_Model.getDate_end());
            stmt_e_c.setString(5, e_C_Model.getEdu_insti());
            // stmt_e_c.setString(6, e_C_Model.getComp_yn());
            
            //R_P
            stmt_r_p.setInt(1, maintableModel.getEmp_num());
            stmt_r_p.setString(2, r_P_Model.getName_rew_gbn());
            stmt_r_p.setString(3, r_P_Model.getName_rew_puni());
            stmt_r_p.setInt(4, r_P_Model.getScore());
            stmt_r_p.setDate(5, r_P_Model.getDate_rew_puni());
            stmt_r_p.setString(6, r_P_Model.getNote());

            //R_S
            stmt_r_s.setInt(1, maintableModel.getEmp_num());
            stmt_r_s.setString(2, r_S_Model.getEmp_rank());
            stmt_r_s.setString(3, r_S_Model.getSalary());
            stmt_r_s.setDate(4, r_S_Model.getDate_join());

            // 쿼리 실행
            int rowsAffected1 = stmt_main_table.executeUpdate();
            int rowsAffected2 = stmt_career.executeUpdate();
            int rowsAffected3 = stmt_school_edu.executeUpdate();
            int rowsAffected4 = stmt_technical.executeUpdate();
            int rowsAffected5 = stmt_family.executeUpdate();
            int rowsAffected6 = stmt_certificate.executeUpdate();
            int rowsAffected7 = stmt_e_c.executeUpdate();
            int rowsAffected8 = stmt_r_p.executeUpdate();
            int rowsAffected9 = stmt_r_s.executeUpdate();

            // 모든 쿼리가 성공적으로 실행 되었는지 확인
            if(rowsAffected1 > 0 && rowsAffected2 > 0 && rowsAffected3 > 0 && rowsAffected4 > 0
                && rowsAffected5 > 0 && rowsAffected6 > 0 && rowsAffected7 > 0
                && rowsAffected8 > 0 && rowsAffected9 > 0) {
                    return true;
                } else{
                    return false;
                }
            // if(rowsAffected1 > 0){
            //     return true;
            // } else {
            //     return true;
            // }
        } catch (Exception e){
            System.out.println("데이터베이스에서 반환된 값 없음");
            e.printStackTrace();
            return false;
        }
    }

    // 유저 정보 저장 시 Maintable 저장하기
    public boolean addUser_Main(MaintableModel maintableModel, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "INSERT INTO main_table("
            + " emp_num, name_kor, name_eng, reg_num, pic, "
            + " age, phone, mail, address, address_num, "
            + " dept_biz, dept_group, date_join, "
            + " emp_rank, work_pos, position, "
            + " salary, last_edu, military, "
            + " marry, height, weight, "
            + " gender "
            + " ) "
            + " SELECT "
            + " ?, ?, ?, ?, ?,"
            + " YEAR(CURRENT_DATE()) - (if(mid(?, 7, 1) = '1' or mid(?, 7, 1) = '2', 1900, 2000) + left(?, 2)) + 1 as age,"
            + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "
            + " FROM dual";

            PreparedStatement stmt = conn.prepareStatement(sql);

            // 성별 계산 코드
            String regNum = maintableModel.getReg_num();
            String gender = "";

            if (regNum != null && regNum.length() >= 8) {
                String genderCode = regNum.substring(6, 7);
                if (genderCode.equals("1") || genderCode.equals("3")) {
                    gender = "남";
                } else if (genderCode.equals("2") || genderCode.equals("4")) {
                    gender = "여";
                } else {
                    gender = null;
                }
            } else {
                // regNum이 null이거나 길이가 8 미만인 경우
                gender = null;
            }

            //main_table
            stmt.setInt(1, maintableModel.getEmp_num());
            stmt.setString(2, maintableModel.getName_kor());
            stmt.setString(3, maintableModel.getName_eng());
            stmt.setString(4, maintableModel.getReg_num());

            // stmt.setBlob(5, maintableModel.getPic());
            // String nu = FileUploadServlet.handleFileUpload(file.getOriginalFilename(), maintableModel.getEmp_num());
            // String picFilePath = "C:\\HRIS\\project\\project\\Upload\\hr\\" + maintableModel.getEmp_num() + "\\kimJunoh.jpg";
            String picFilePath = "C:\\Users\\김준오\\Desktop\\kimJunoh.jpg";
            
            // Part filePart = request.getPart("image");
            InputStream picStream = new FileInputStream(picFilePath);
            stmt.setBlob(5, picStream);
            // try (FileInputStream fis = new FileInputStream(new File(picFilePath))) {
            //     stmt.setBlob(5, fis);
            // }

            // try (InputStream fis = new FileInputStream(new File(picFilePath))) {
            //     stmt.setBlob(5, fis);
            // } catch (IOException e) {
            //     e.printStackTrace();
            //     stmt.setNull(5, java.sql.Types.BLOB);
            // }

            stmt.setString(6, maintableModel.getReg_num());
            stmt.setString(7, maintableModel.getReg_num());
            stmt.setString(8, maintableModel.getReg_num());
            stmt.setString(9, maintableModel.getPhone());
            stmt.setString(10, maintableModel.getMail());
            stmt.setString(11, maintableModel.getAddress());
            stmt.setInt(12, maintableModel.getAddress_num());
            stmt.setString(13, maintableModel.getDept_biz());
            stmt.setString(14, maintableModel.getDept_group());
            stmt.setDate(15, maintableModel.getDate_join());
            stmt.setString(16, maintableModel.getEmp_rank());
            stmt.setString(17, maintableModel.getWork_pos());
            stmt.setString(18, maintableModel.getPosition());
            stmt.setString(19, maintableModel.getSalary());
            stmt.setString(20, maintableModel.getLast_edu());
            stmt.setString(21, maintableModel.getMilitary());
            stmt.setString(22, maintableModel.getMarry());
            stmt.setInt(23, maintableModel.getHeight());
            stmt.setInt(24, maintableModel.getWeight());
            stmt.setString(25, gender);

            int rowsAffected1 = stmt.executeUpdate();

            return rowsAffected1 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    } 

    // 유저 신규 추가 시 경력 테이블 INSERT
    public boolean addUser_Career(MaintableModel maintableModel, CareerModel careerModel, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabasestmt = conn.prepareStatement(useDatabaseQuery);
            useDatabasestmt.execute();

            String sql_carrer = "INSERT INTO career(emp_num, company, department, date_join, date_leave, work_period, final_rank, work_info)"
                        + "VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement stmt_career = conn.prepareStatement(sql_carrer);

            //career
            stmt_career.setInt(1, maintableModel.getEmp_num());
            stmt_career.setString(2, careerModel.getCompany());
            stmt_career.setString(3, careerModel.getDepartment());
            stmt_career.setDate(4, careerModel.getDate_join());
            stmt_career.setDate(5, careerModel.getDate_leave());
            stmt_career.setFloat(6, careerModel.getWork_period());
            stmt_career.setString(7, careerModel.getFinal_rank());
            stmt_career.setString(8, careerModel.getWork_info());

            int rowsAffected2 = stmt_career.executeUpdate();

            return rowsAffected2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 학력 테이블 INSERT
    public boolean addUser_school_edu(MaintableModel maintableModel, School_educationModel school_educationModel, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_school_edu = "INSERT INTO school_education(emp_num, date_admition, date_graduate, name_school, location, major, sub_major, comment)"
                                    + "VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement stmt_school_edu = conn.prepareStatement(sql_school_edu);

            stmt_school_edu.setInt(1, maintableModel.getEmp_num());
            stmt_school_edu.setDate(2, school_educationModel.getDate_admition());
            stmt_school_edu.setDate(3, school_educationModel.getDate_graduate());
            stmt_school_edu.setString(4, school_educationModel.getName_school());
            stmt_school_edu.setString(5, school_educationModel.getLocation());
            stmt_school_edu.setString(6, school_educationModel.getMajor());
            stmt_school_edu.setString(7, school_educationModel.getSub_major());
            stmt_school_edu.setString(8, school_educationModel.getComment());

            int rowsAffected3 = stmt_school_edu.executeUpdate();

            return rowsAffected3 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 기술사항 테이블 INSERT
    public boolean addUser_Technical(MaintableModel maintableModel, TechnicalModel technicalModel, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_technical = "INSERT INTO technical(emp_num, tec_detail, proficiency, note)"
                                + "VALUES (?,?,?,?)";

            PreparedStatement stmt_technical = conn.prepareStatement(sql_technical);

            //technical
            stmt_technical.setInt(1, maintableModel.getEmp_num());
            stmt_technical.setString(2, technicalModel.getTec_detail());
            stmt_technical.setString(3, technicalModel.getProficiency());
            stmt_technical.setString(4, technicalModel.getNote());

            int rowsAffected4 = stmt_technical.executeUpdate();

            return rowsAffected4 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 가족관계 테이블 INSERT
    public boolean addUser_Family(MaintableModel maintableModel, FamilyModel familyModel, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_family = "INSERT INTO family(emp_num, name_family, birth, age, rel, live) "
                                + "VALUES (?, ?, ?, TIMESTAMPDIFF(YEAR, ?, CURRENT_DATE()), ?, ?)";

            PreparedStatement stmt_family = conn.prepareStatement(sql_family);

            //family
            stmt_family.setInt(1, maintableModel.getEmp_num());
            stmt_family.setString(2, familyModel.getName_family());
            stmt_family.setDate(3, familyModel.getBirth());
            stmt_family.setDate(4, familyModel.getBirth());
            stmt_family.setString(5, familyModel.getRel());
            stmt_family.setString(6, familyModel.getLive());

            int rowsAffected5 = stmt_family.executeUpdate();

            return rowsAffected5 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 자격증 테이블 INSERT
    public boolean addUser_Certificate(MaintableModel maintableModel, CertificateModel certificateModel, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_certificate = "INSERT INTO certificate(emp_num, name_license, date_acqui)"
                        + "VALUES (?,?,?)";

            PreparedStatement stmt_certificate = conn.prepareStatement(sql_certificate);

            //certificate
            stmt_certificate.setInt(1, maintableModel.getEmp_num());
            stmt_certificate.setString(2, certificateModel.getName_license());
            stmt_certificate.setDate(3, certificateModel.getDate_acqui());

            int rowsAffected6 = stmt_certificate.executeUpdate();

            return rowsAffected6 > 0 ;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 교육이수 테이블 INSERT
    public boolean addUser_E_C(MaintableModel maintableModel, E_C_Model e_C_Model, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_e_c = "INSERT INTO e_c(emp_num, name_edu, date_start, date_end, edu_insti, idx)"
                        + "SELECT ?, ?, ?, ?, ?, IFNULL(MAX(idx), 0) + 1 FROM e_c";

            PreparedStatement stmt_e_c = conn.prepareStatement(sql_e_c);

            //E_C
            stmt_e_c.setInt(1, maintableModel.getEmp_num());
            stmt_e_c.setString(2, e_C_Model.getName_edu());
            stmt_e_c.setDate(3, e_C_Model.getDate_start());
            stmt_e_c.setDate(4, e_C_Model.getDate_end());
            stmt_e_c.setString(5, e_C_Model.getEdu_insti());
            // stmt_e_c.setString(6, e_C_Model.getComp_yn());

            int rowsAffected7 = stmt_e_c.executeUpdate();

            return rowsAffected7 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 상벌 테이블 INSERT
    public boolean addUser_R_P(MaintableModel maintableModel, R_P_Model r_P_Model, HttpServletRequest request){
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_r_p = "INSERT INTO R_P(emp_num, name_rew_gbn, name_rew_puni, score, date_rew_puni, note)"
                        + "VALUES (?,?,?,?,?,?)";

            PreparedStatement stmt_r_p = conn.prepareStatement(sql_r_p);

            //R_P
            stmt_r_p.setInt(1, maintableModel.getEmp_num());
            stmt_r_p.setString(2, r_P_Model.getName_rew_gbn());
            stmt_r_p.setString(3, r_P_Model.getName_rew_puni());
            stmt_r_p.setInt(4, r_P_Model.getScore());
            stmt_r_p.setDate(5, r_P_Model.getDate_rew_puni());
            stmt_r_p.setString(6, r_P_Model.getNote());

            int rowsAffected8 = stmt_r_p.executeUpdate();

            return rowsAffected8 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 신규 추가 시 직급/호봉 테이블 INSERT
    public boolean addUser_R_S(MaintableModel maintableModel, R_S_Model r_S_Model, HttpServletRequest reqeust) {
        try {
            Connection conn = common.dbcon();
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_r_s = "INSERT INTO r_s(emp_num, emp_rank, salary, date_join)"
                        + "VALUES (?,?,?,?)";

            PreparedStatement stmt_r_s = conn.prepareStatement(sql_r_s);

            //R_S
            stmt_r_s.setInt(1, maintableModel.getEmp_num());
            stmt_r_s.setString(2, r_S_Model.getEmp_rank());
            stmt_r_s.setString(3, r_S_Model.getSalary());
            stmt_r_s.setDate(4, r_S_Model.getDate_join());

            int rowsAffected9 = stmt_r_s.executeUpdate();

            return rowsAffected9 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 유저 정보 수정 시 사번에 대한 정보 가져오기
    public EmployeeManagementModel getMainTableByEmpNum(String emp_num){
        EmployeeManagementModel employeeManagementModel = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql_main_table = "SELECT emp_num, name_kor, name_eng, pic, age, phone, mail, address, address_num, dept_biz, dept_group,"
                        + " date_join, emp_rank, work_pos, position, salary, last_edu, military, marry, height, weight, gender, pm_code, input_gbn"
                        + " FROM main_table WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt_main_table = conn.prepareStatement(sql_main_table);

            ResultSet resultSet_maintable = stmt_main_table.executeQuery();

            if(resultSet_maintable.next()){
                employeeManagementModel = new EmployeeManagementModel();
                employeeManagementModel.setEmp_num(resultSet_maintable.getInt("emp_num"));
                employeeManagementModel.setName_kor(resultSet_maintable.getString("name_kor"));
                employeeManagementModel.setName_eng(resultSet_maintable.getString("name_eng"));
                // main.setReg_num(resultSet_maintable.getString("reg_num"));
                employeeManagementModel.setPic(resultSet_maintable.getBlob("pic"));
                employeeManagementModel.setAge(resultSet_maintable.getInt("age"));
                employeeManagementModel.setPhone(resultSet_maintable.getString("phone"));
                employeeManagementModel.setMail(resultSet_maintable.getString("mail"));
                employeeManagementModel.setAddress(resultSet_maintable.getString("address"));
                employeeManagementModel.setAddress_num(resultSet_maintable.getInt("address_num"));
                employeeManagementModel.setDept_biz(resultSet_maintable.getString("dept_biz"));
                employeeManagementModel.setDept_group(resultSet_maintable.getString("dept_group"));
                employeeManagementModel.setDate_join(resultSet_maintable.getDate("date_join"));
                employeeManagementModel.setEmp_rank(resultSet_maintable.getString("emp_rank"));
                employeeManagementModel.setPosition(resultSet_maintable.getString("position"));
                employeeManagementModel.setWork_pos(resultSet_maintable.getString("work_pos"));
                employeeManagementModel.setSalary(resultSet_maintable.getString("salary"));
                employeeManagementModel.setLast_edu(resultSet_maintable.getString("last_edu"));
                employeeManagementModel.setMilitary(resultSet_maintable.getString("military"));
                employeeManagementModel.setMarry(resultSet_maintable.getString("marry"));
                employeeManagementModel.setHeight(resultSet_maintable.getInt("height"));
                employeeManagementModel.setWeight(resultSet_maintable.getInt("weight"));
                employeeManagementModel.setGender(resultSet_maintable.getString("gender"));
                employeeManagementModel.setPm_code(resultSet_maintable.getString("pm_code"));
                employeeManagementModel.setInput_gbn(resultSet_maintable.getString("input_gbn"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeManagementModel;
    }

    // 유저 정보 수정 시 경력에 대한 정보 가져오기
    public CareerModel getCareerTableByEmpNum(String emp_num){
        CareerModel careerModel = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, company, department, date_join, date_leave, work_period, final_rank, work_info"
                        + " FROM career WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                careerModel = new CareerModel();
                careerModel.setEmp_num(resultSet.getInt("emp_num"));
                careerModel.setCompany(resultSet.getString("company"));
                careerModel.setDepartment(resultSet.getString("department"));
                careerModel.setDate_join(resultSet.getDate("date_join"));
                careerModel.setDate_leave(resultSet.getDate("date_leave"));
                careerModel.setWork_period(resultSet.getFloat("work_period"));
                careerModel.setFinal_rank(resultSet.getString("final_rank"));
                careerModel.setWork_info(resultSet.getString("work_info"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return careerModel;
    }

    // 유저 정보 수정 시 학력에 대한 정보 가져오기
    public School_educationModel getSchoolEducationByEmpNum(String emp_num){
        School_educationModel school_educationModel = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, date_admition, date_graduate, name_school, location, major, sub_major, comment"
                        + " FROM school_education WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                school_educationModel = new School_educationModel();
                school_educationModel.setEmp_num(resultSet.getInt("emp_num"));
                school_educationModel.setDate_admition(resultSet.getDate("date_admition"));
                school_educationModel.setDate_graduate(resultSet.getDate("date_graduate"));
                school_educationModel.setName_school(resultSet.getString("name_school"));
                school_educationModel.setLocation(resultSet.getString("location"));
                school_educationModel.setMajor(resultSet.getString("major"));
                school_educationModel.setSub_major(resultSet.getString("sub_major"));
                school_educationModel.setComment(resultSet.getString("comment"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return school_educationModel;
    }

    // 유저 정보 수정 시 학력에 대한 정보 가져오기
    public TechnicalModel getTechByEmpNum(String emp_num){
        TechnicalModel technicalModel = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, tec_detail, proficiency, note"
                        + " FROM technical WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                technicalModel = new TechnicalModel();
                technicalModel.setEmp_num(resultSet.getInt("emp_num"));
                technicalModel.setTec_detail(resultSet.getString("tec_detail"));
                technicalModel.setProficiency(resultSet.getString("proficiency"));
                technicalModel.setNote(resultSet.getString("note"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return technicalModel;
    }

    // 유저 정보 수정 시 가족관계에 대한 정보 가져오기
    public FamilyModel getFamilyByEmpNum(String emp_num){
        FamilyModel familyModel = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, name_family, birth, age, rel, live"
                        + " FROM family WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                familyModel = new FamilyModel();
                familyModel.setEmp_num(resultSet.getInt("emp_num"));
                familyModel.setName_family(resultSet.getString("name_family"));
                familyModel.setBirth(resultSet.getDate("birth"));
                familyModel.setAge(resultSet.getInt("age"));
                familyModel.setRel(resultSet.getString("rel"));
                familyModel.setLive(resultSet.getString("live"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return familyModel;
    }

    // 유저 정보 수정 시 자격증에 관한 정보 가져오기
    public CertificateModel getCertificateByEmpNum(String emp_num){
        CertificateModel certificateModel = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, name_license, date_acqui"
                        + " FROM certificate WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                certificateModel = new CertificateModel();
                certificateModel.setEmp_num(resultSet.getInt("emp_num"));
                certificateModel.setName_license(resultSet.getString("name_license"));
                certificateModel.setDate_acqui(resultSet.getDate("date_acqui"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificateModel;
    }    

    
    // 유저 정보 수정 시 교육 이수에 관한 정보 가져오기
    public E_C_Model getE_CTableByEmpNum(String emp_num){
        E_C_Model e_C_Model = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, name_edu, edu_insti, comp_yn, date_start, date_end"
                        + " FROM E_C WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                e_C_Model = new E_C_Model();
                e_C_Model.setEmp_num(resultSet.getInt("emp_num"));
                e_C_Model.setName_edu(resultSet.getString("name_edu"));
                e_C_Model.setEdu_insti(resultSet.getString("edu_insti"));
                e_C_Model.setComp_yn(resultSet.getString("comp_yn"));
                e_C_Model.setDate_start(resultSet.getDate("date_start"));
                e_C_Model.setDate_end(resultSet.getDate("date_end"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return e_C_Model;
    }    

    // 유저 정보 수정 시 상벌에 관한 정보 가져오기
    public R_P_Model getR_PTableByEmpNum(String emp_num){
        R_P_Model r_P_Model = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, name_rew_gbn, name_rew_puni, score, date_rew_puni, note"
                        + " FROM R_P WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                r_P_Model = new R_P_Model();
                r_P_Model.setEmp_num(resultSet.getInt("emp_num"));
                r_P_Model.setName_rew_puni(resultSet.getString("name_rew_puni"));
                r_P_Model.setScore(resultSet.getInt("score"));
                r_P_Model.setDate_rew_puni(resultSet.getDate("date_rew_puni"));
                r_P_Model.setNote(resultSet.getString("note"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r_P_Model;
    }    

    // 유저 정보 수정 시 상벌에 관한 정보 가져오기
    public R_S_Model getR_STableByEmpNum(String emp_num){
        R_S_Model r_S_Model = null;

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT emp_num, emp_rank, salary, date_join"
                        + " FROM R_S WHERE emp_num = ?";

            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                r_S_Model = new R_S_Model();
                r_S_Model.setEmp_num(resultSet.getInt("emp_num"));
                r_S_Model.setEmp_rank(resultSet.getString("emp_rank"));
                r_S_Model.setSalary(resultSet.getString("salary"));
                r_S_Model.setDate_join(resultSet.getDate("date_join"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r_S_Model;
    }    

    // 유저 정보 수정 기능
    public EmployeeDataModel updateEmployees(String emp_num){
        // List<MaintableModel> main_table = new ArrayList<>();
        // List<EmployeeManagementModel> employees = new ArrayList<>();
        // List<CareerModel> career = new ArrayList<>();
        // List<School_educationModel> sch_edu = new ArrayList<>();
        // List<TechnicalModel> tech = new ArrayList<>();
        // List<FamilyModel> family = new ArrayList<>();
        // List<CertificateModel> certificate = new ArrayList<>();
        // List<E_C_Model> e_c = new ArrayList<>();
        // List<R_P_Model> r_p = new ArrayList<>();
        // List<R_S_Model> r_s = new ArrayList<>();
        EmployeeDataModel employeeDataModel = new EmployeeDataModel();
        try {
            Connection conn = common.dbcon();

            //데이터베이스 선택 쿼리
            String useDataBaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDataBaseQuery);
            useDatabaseStmt.execute();

            // SQL 쿼리 작성
            // 수정을 누르면 기존에 있던 정보가 출력되기
            String sql_main_table = "SELECT emp_num, name_kor, name_eng, pic, age, phone, mail, address, address_num, dept_biz, dept_group,"
                        + " date_join, emp_rank, work_pos, position, salary, last_edu, military, marry, height, weight, gender, pm_code, input_gbn"
                        + " FROM main_table WHERE emp_num = ?";
            String sql_career = "SELECT emp_num, company, department, date_join, date_leave, work_period, final_rank, work_info"
                        + " FROM career WHERE emp_num = ?";
            String sql_school_edu = "SELECT emp_num, date_admition, date_graduate, name_school, location, major, sub_major, comment"
                        + " FROM school_education WHERE emp_num = ?";
            String sql_technical = "SELECT emp_num, tec_detail, proficiency, note"
                        + " FROM technical WHERE emp_num = ?";
            String sql_family = "SELECT emp_num, name_family, birth, age, rel, live"
                        + " FROM family WHERE emp_num = ?";
            String sql_certificate = "SELECT emp_num, name_license, date_acqui"
                        + " FROM certificate WHERE emp_num = ?";
            String sql_e_c = "SELECT emp_num, name_edu, edu_insti, comp_yn, date_start, date_end"
                        + " FROM E_C WHERE emp_num = ?";
            String sql_r_p = "SELECT emp_num, name_rew_gbn, name_rew_puni, score, date_rew_puni, note"
                        + " FROM R_P WHERE emp_num = ?";
            String sql_r_s = "SELECT emp_num, emp_rank, salary, date_join"
                        + " FROM R_S WHERE emp_num = ?";
            
            //쿼리 실행 및 결과 가져오기
            PreparedStatement stmt_main_table = conn.prepareStatement(sql_main_table);
            PreparedStatement stmt_career = conn.prepareStatement(sql_career);
            PreparedStatement stmt_school_edu = conn.prepareStatement(sql_school_edu);
            PreparedStatement stmt_technical = conn.prepareStatement(sql_technical);
            PreparedStatement stmt_family = conn.prepareStatement(sql_family);
            PreparedStatement stmt_certificate = conn.prepareStatement(sql_certificate);
            PreparedStatement stmt_e_c = conn.prepareStatement(sql_e_c);
            PreparedStatement stmt_r_p = conn.prepareStatement(sql_r_p);
            PreparedStatement stmt_r_s = conn.prepareStatement(sql_r_s);

            stmt_main_table.setString(1, emp_num);
            stmt_career.setString(1, emp_num);
            stmt_school_edu.setString(1, emp_num);
            stmt_technical.setString(1, emp_num);
            stmt_family.setString(1, emp_num);
            stmt_certificate.setString(1, emp_num);
            stmt_e_c.setString(1, emp_num);
            stmt_r_p.setString(1, emp_num);
            stmt_r_s.setString(1, emp_num);

            // 쿼리 실행 및 결과 가져오기
            ResultSet resultSet_maintable = stmt_main_table.executeQuery();
            ResultSet resultSet_career = stmt_career.executeQuery();
            ResultSet resultSet_school_edu = stmt_school_edu.executeQuery();
            ResultSet resultSet_technical = stmt_technical.executeQuery();
            ResultSet resultSet_family = stmt_family.executeQuery();
            ResultSet resultSet_certificate = stmt_certificate.executeQuery();
            ResultSet resultSet_e_c = stmt_e_c.executeQuery();
            ResultSet resultSet_r_p = stmt_r_p.executeQuery();
            ResultSet resultSet_r_s = stmt_r_s.executeQuery();

            // // 메인테이블 정보 가져오기
            // MaintableModel main = new MaintableModel();
            // main.setEmp_num(resultSet_maintable.getInt("emp_num"));
            // main.setName_kor(resultSet_maintable.getString("name_kor"));
            // main.setName_eng(resultSet_maintable.getString("name_eng"));
            // // main.setReg_num(resultSet_maintable.getString("reg_num"));
            // main.setPic(resultSet_maintable.getBlob("pic"));
            // main.setAge(resultSet_maintable.getInt("age"));
            // main.setPhone(resultSet_maintable.getString("phone"));
            // main.setMail(resultSet_maintable.getString("mail"));
            // main.setAddress(resultSet_maintable.getString("address"));
            // main.setAddress_num(resultSet_maintable.getInt("address_num"));
            // main.setDept_biz(resultSet_maintable.getString("dept_biz"));
            // main.setDept_group(resultSet_maintable.getString("dept_group"));
            // main.setDate_join(resultSet_maintable.getDate("date_join"));
            // main.setEmp_rank(resultSet_maintable.getString("emp_rank"));
            // main.setPosition(resultSet_maintable.getString("position"));
            // main.setWork_pos(resultSet_maintable.getString("work_pos"));
            // main.setSalary(resultSet_maintable.getString("salary"));
            // main.setLast_edu(resultSet_maintable.getString("last_edu"));
            // main.setMilitary(resultSet_maintable.getString("military"));
            // main.setMarry(resultSet_maintable.getString("marry"));
            // main.setHeight(resultSet_maintable.getInt("height"));
            // main.setWeight(resultSet_maintable.getInt("weight"));
            // main.setGender(resultSet_maintable.getString("gender"));
            // main.setPm_code(resultSet_maintable.getString("pm_code"));
            // main.setInput_gbn(resultSet_maintable.getString("input_gbn"));
            // main_table.add(main);

            // // career 정보 가져오기
            // CareerModel car = new CareerModel();
            // car.setEmp_num(resultSet_career.getInt("emp_num"));
            // car.setCompany(resultSet_career.getString("company"));
            // car.setDepartment(resultSet_career.getString("department"));
            // car.setDate_join(resultSet_career.getDate("date_join"));
            // car.setDate_leave(resultSet_career.getDate("date_leave"));
            // car.setWork_period(resultSet_career.getFloat("work_period"));
            // car.setFinal_rank(resultSet_career.getString("final_rank"));
            // car.setWork_info(resultSet_career.getString("work_info"));
            // career.add(car);

            // // school_education (학력) 정보 가져오기
            // School_educationModel sch = new School_educationModel();
            // sch.setEmp_num(resultSet_school_edu.getInt("emp_num"));
            // sch.setDate_admition(resultSet_school_edu.getDate("date_admition"));
            // sch.setDate_graduate(resultSet_school_edu.getDate("date_graduate"));
            // sch.setName_school(resultSet_school_edu.getString("name_school"));
            // sch.setLocation(resultSet_school_edu.getString("location"));
            // sch.setMajor(resultSet_school_edu.getString("major"));
            // sch.setSub_major(resultSet_school_edu.getString("sub_major"));
            // sch.setComment(resultSet_school_edu.getString("comment"));
            // sch_edu.add(sch);

            // // Technical (기술사항) 정보 가져오기
            // TechnicalModel tec = new TechnicalModel();
            // tec.setEmp_num(resultSet_technical.getInt("emp_num"));
            // tec.setTec_detail(resultSet_technical.getString("tec_detail"));
            // tec.setProficiency(resultSet_technical.getString("proficiency"));
            // tec.setNote(resultSet_technical.getString("note"));
            // tech.add(tec);

            // // familiy (가족관계) 정보 가져오기
            // FamilyModel fam = new FamilyModel();
            // fam.setEmp_num(resultSet_family.getInt("emp_num"));
            // fam.setName_family(resultSet_family.getString("name_family"));
            // fam.setBirth(resultSet_family.getDate("birth"));
            // fam.setAge(resultSet_family.getInt("age"));
            // fam.setRel(resultSet_family.getString("rel"));
            // fam.setLive(resultSet_family.getString("live"));
            // family.add(fam);

            // // certificate (자격증) 정보 가져오기
            // CertificateModel cer = new CertificateModel();
            // cer.setEmp_num(resultSet_certificate.getInt("emp_num"));
            // cer.setName_license(resultSet_certificate.getString("name_license"));
            // cer.setDate_acqui(resultSet_certificate.getDate("date_acqui"));
            // certificate.add(cer);

            // // E_C (교육 이수) 정보 가져오기
            // E_C_Model ec = new E_C_Model();
            // ec.setEmp_num(resultSet_e_c.getInt("emp_num"));
            // ec.setName_edu(resultSet_e_c.getString("name_edu"));
            // ec.setEdu_insti(resultSet_e_c.getString("edu_insti"));
            // ec.setComp_yn(resultSet_e_c.getString("comp_yn"));
            // ec.setDate_start(resultSet_e_c.getDate("date_start"));
            // ec.setDate_end(resultSet_e_c.getDate("date_end"));
            // e_c.add(ec);

            // // R_P (상벌) 정보 가져오기
            // R_P_Model rp = new R_P_Model();
            // rp.setEmp_num(resultSet_r_p.getInt("emp_num"));
            // rp.setName_rew_puni(resultSet_r_p.getString("name_rew_puni"));
            // rp.setScore(resultSet_r_p.getInt("score"));
            // rp.setDate_rew_puni(resultSet_r_p.getDate("date_rew_puni"));
            // rp.setNote(resultSet_r_p.getString("note"));
            // r_p.add(rp);

            // // R_S (진급 및 호봉) 정보 가져오기
            // R_S_Model rs = new R_S_Model();
            // rs.setEmp_num(resultSet_r_s.getInt("emp_num"));
            // rs.setEmp_rank(resultSet_r_s.getString("emp_rank"));
            // rs.setSalary(resultSet_r_s.getString("salary"));
            // rs.setDate_join(resultSet_r_s.getDate("date_join"));
            // r_s.add(rs);

            // 데이터베이스에서 가져온 정보로 EmployeeData 객체 채우기
            if (resultSet_maintable.next()) {
                employeeDataModel.setMainTable(mapMainTable(resultSet_maintable));
            }
            if (resultSet_career.next()) {
                employeeDataModel.setCareer(mapCareerModel(resultSet_career));
            }
            if (resultSet_school_edu.next()) {
                employeeDataModel.setSchoolEducation(mapSchool_educationModel(resultSet_school_edu));
            }
            if (resultSet_technical.next()) {
                employeeDataModel.setTechnical(mapTechnicalModel(resultSet_technical));
            }
            if (resultSet_family.next()) {
                employeeDataModel.setFamily(mapFamilyModel(resultSet_family));
            }
            if (resultSet_certificate.next()) {
                employeeDataModel.setCertificate(mapCertificateModel(resultSet_certificate));
            }
            if (resultSet_e_c.next()) {
                employeeDataModel.setEducationCompletion(mapE_C_Model(resultSet_e_c));
            }
            if (resultSet_r_p.next()) {
                employeeDataModel.setRewardPunishment(mapR_P_Model(resultSet_r_p));
            }
            if (resultSet_r_s.next()) {
                employeeDataModel.setRankSalary(mapR_S_Model(resultSet_r_s));
            }
            return null;
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
    }

    private MaintableModel mapMainTable(ResultSet rs) throws SQLException{
        MaintableModel main = new MaintableModel();
        main.setEmp_num(rs.getInt("emp_num"));
        main.setName_kor(rs.getString("name_kor"));
        main.setName_eng(rs.getString("name_eng"));
        // main.setReg_num(rs.getString("reg_num"));
        main.setPic(rs.getBlob("pic"));
        main.setAge(rs.getInt("age"));
        main.setPhone(rs.getString("phone"));
        main.setMail(rs.getString("mail"));
        main.setAddress(rs.getString("address"));
        main.setAddress_num(rs.getInt("address_num"));
        main.setDept_biz(rs.getString("dept_biz"));
        main.setDept_group(rs.getString("dept_group"));
        main.setDate_join(rs.getDate("date_join"));
        main.setEmp_rank(rs.getString("emp_rank"));
        main.setPosition(rs.getString("position"));
        main.setWork_pos(rs.getString("work_pos"));
        main.setSalary(rs.getString("salary"));
        main.setLast_edu(rs.getString("last_edu"));
        main.setMilitary(rs.getString("military"));
        main.setMarry(rs.getString("marry"));
        main.setHeight(rs.getInt("height"));
        main.setWeight(rs.getInt("weight"));
        main.setGender(rs.getString("gender"));
        main.setPm_code(rs.getString("pm_code"));
        main.setInput_gbn(rs.getString("input_gbn"));
        return main;
    }

    private CareerModel mapCareerModel(ResultSet resultSet_career) throws SQLException{
        CareerModel car = new CareerModel();
        car.setEmp_num(resultSet_career.getInt("emp_num"));
        car.setCompany(resultSet_career.getString("company"));
        car.setDepartment(resultSet_career.getString("department"));
        car.setDate_join(resultSet_career.getDate("date_join"));
        car.setDate_leave(resultSet_career.getDate("date_leave"));
        car.setWork_period(resultSet_career.getFloat("work_period"));
        car.setFinal_rank(resultSet_career.getString("final_rank"));
        car.setWork_info(resultSet_career.getString("work_info"));
        return car;
    }

    private School_educationModel mapSchool_educationModel(ResultSet resultSet_school_edu) throws SQLException {
        School_educationModel sch = new School_educationModel();
        sch.setEmp_num(resultSet_school_edu.getInt("emp_num"));
        sch.setDate_admition(resultSet_school_edu.getDate("date_admition"));
        sch.setDate_graduate(resultSet_school_edu.getDate("date_graduate"));
        sch.setName_school(resultSet_school_edu.getString("name_school"));
        sch.setLocation(resultSet_school_edu.getString("location"));
        sch.setMajor(resultSet_school_edu.getString("major"));
        sch.setSub_major(resultSet_school_edu.getString("sub_major"));
        sch.setComment(resultSet_school_edu.getString("comment"));
        return sch;
    }

    private TechnicalModel mapTechnicalModel(ResultSet resultSet_technical) throws SQLException {
        TechnicalModel tec = new TechnicalModel();
        tec.setEmp_num(resultSet_technical.getInt("emp_num"));
        tec.setTec_detail(resultSet_technical.getString("tec_detail"));
        tec.setProficiency(resultSet_technical.getString("proficiency"));
        tec.setNote(resultSet_technical.getString("note"));
        return tec;
    }

    private FamilyModel mapFamilyModel(ResultSet resultSet_family) throws SQLException {
        FamilyModel fam = new FamilyModel();
        fam.setEmp_num(resultSet_family.getInt("emp_num"));
        fam.setName_family(resultSet_family.getString("name_family"));
        fam.setBirth(resultSet_family.getDate("birth"));
        fam.setAge(resultSet_family.getInt("age"));
        fam.setRel(resultSet_family.getString("rel"));
        fam.setLive(resultSet_family.getString("live"));
        return fam;
    }

    private CertificateModel mapCertificateModel(ResultSet resultSet_Certificate) throws SQLException{
        CertificateModel cer = new CertificateModel();
        cer.setEmp_num(resultSet_Certificate.getInt("emp_num"));
        cer.setName_license(resultSet_Certificate.getString("name_license"));
        cer.setDate_acqui(resultSet_Certificate.getDate("date_acqui"));
        return cer;
    }

    private E_C_Model mapE_C_Model(ResultSet resultSet_e_c) throws SQLException {
        E_C_Model ec = new E_C_Model();
        ec.setEmp_num(resultSet_e_c.getInt("emp_num"));
        ec.setName_edu(resultSet_e_c.getString("name_edu"));
        ec.setEdu_insti(resultSet_e_c.getString("edu_insti"));
        ec.setComp_yn(resultSet_e_c.getString("comp_yn"));
        ec.setDate_start(resultSet_e_c.getDate("date_start"));
        ec.setDate_end(resultSet_e_c.getDate("date_end"));
        return ec;
    }

    private R_P_Model mapR_P_Model(ResultSet resultSet_r_p) throws SQLException {
        R_P_Model rp = new R_P_Model();
        rp.setEmp_num(resultSet_r_p.getInt("emp_num"));
        rp.setName_rew_puni(resultSet_r_p.getString("name_rew_puni"));
        rp.setScore(resultSet_r_p.getInt("score"));
        rp.setDate_rew_puni(resultSet_r_p.getDate("date_rew_puni"));
        rp.setNote(resultSet_r_p.getString("note"));
        return rp;
    }

    private R_S_Model mapR_S_Model(ResultSet resultSet_r_s) throws SQLException {
        R_S_Model rs = new R_S_Model();
        rs.setEmp_num(resultSet_r_s.getInt("emp_num"));
        rs.setEmp_rank(resultSet_r_s.getString("emp_rank"));
        rs.setSalary(resultSet_r_s.getString("salary"));
        rs.setDate_join(resultSet_r_s.getDate("date_join"));
        return rs;
    }


    // 유저 수정하여 db에 업데이트
    public boolean update_emp(MaintableModel maintableModel, CareerModel careerModel, School_educationModel school_educationModel
    , TechnicalModel technicalModel, FamilyModel familyModel, CertificateModel certificateModel
    , E_C_Model e_C_Model, R_P_Model r_P_Model, R_S_Model r_S_Model){
        try {
            Connection conn = common.dbcon();

            String useDataBaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDataBaseQuery);
            useDatabaseStmt.execute();

            String sql_main = "UPDATE MAIN_TABLE SET emp_num = ?, name_kor = ?, name_eng = ?, reg_num = ?, pic = ?, age = ?, phone = ?, mail = ?, address = ?,"
                                + " address_num = ?, dept_biz = ?, dept_group = ?, date_join = ?, emp_rank = ?, work_pos = ?, position = ?, salary = ?, last_edu = ?,"
                                + " military = ?, marry = ?, height = ?, weight = ?, gender = ?, pm_code = ?, input_gbn = ? WHERE emp_num = ?";
            String sql_car = "UPDATE CARRER SET company = ?, department = ?, date_join = ?, date_leave = ?, work_period = ?, final_rank = ?, work_info = ?"
                                + "WHERE emp_num = ?";
            String sql_sch = "UPDATE school_education SET date_admition = ?, date_graduate = ?, name_school = ?, location = ?, major = ?, sub_major = ?, comment = ?"
                                + " WHERE emp_num = ?";
            String sql_tech = "UPDATE technical SET tec_detail = ?, proficiency = ?, note = ?"
                                + " WHERE emp_num = ?";
            String sql_fam = "UPDATE family SET name_family = ?, birth = ?, age = ?, rel = ?, live = ?"
                                + " WHERE emp_num = ?";
            String sql_cer = "UPDATE certificate SET name_license = ?, date_acqut = ?"
                                + " WHERE emp_num = ?";
            String sql_ec = "UPDATE e_c SET name_edu = ?, edu_insti = ?, comp_yn = ?, date_start = ?,  date_end = ?"
                                + " WHERE emp_num = ?;";
            String sql_rp = "UPDATE r_p SET name_rew_puni = ?, score = ?, date_rew_puni = ?, note = ?, name_rew_gbn = ?"
                                + " WHERE emp_num = ?";
            String sql_rs = "UPDATE r_s SET emp_rank = ?, salary = ?, date_join = ?"
                                + " WHERE emp_num = ?";

            PreparedStatement stmt1 = conn.prepareStatement(sql_main);
            PreparedStatement stmt2 = conn.prepareStatement(sql_car);
            PreparedStatement stmt3 = conn.prepareStatement(sql_sch);
            PreparedStatement stmt4 = conn.prepareStatement(sql_tech);
            PreparedStatement stmt5 = conn.prepareStatement(sql_fam);
            PreparedStatement stmt6 = conn.prepareStatement(sql_cer);
            PreparedStatement stmt7 = conn.prepareStatement(sql_ec);
            PreparedStatement stmt8 = conn.prepareStatement(sql_rp);
            PreparedStatement stmt9 = conn.prepareStatement(sql_rs);

            // 값 설정
            stmt1.setInt(1, maintableModel.getEmp_num());
            stmt1.setString(2, maintableModel.getName_kor());
            stmt1.setString(3, maintableModel.getName_eng());
            stmt1.setString(4, maintableModel.getReg_num());
            stmt1.setBlob(5, maintableModel.getPic());
            stmt1.setInt(6, maintableModel.getAge());
            stmt1.setString(7, maintableModel.getPhone());
            stmt1.setString(8, maintableModel.getMail());
            stmt1.setString(9, maintableModel.getAddress());
            stmt1.setInt(10, maintableModel.getAddress_num());
            stmt1.setString(11, maintableModel.getDept_biz());
            stmt1.setString(12, maintableModel.getDept_group());
            stmt1.setDate(13, maintableModel.getDate_join());
            stmt1.setString(14, maintableModel.getEmp_rank());
            stmt1.setString(15, maintableModel.getWork_pos());
            stmt1.setString(16, maintableModel.getPosition());
            stmt1.setString(17, maintableModel.getSalary());
            stmt1.setString(18, maintableModel.getLast_edu());
            stmt1.setString(19, maintableModel.getMilitary());
            stmt1.setString(20, maintableModel.getMarry());
            stmt1.setInt(21, maintableModel.getHeight());
            stmt1.setInt(22, maintableModel.getWeight());
            stmt1.setString(23, maintableModel.getGender());
            stmt1.setString(24, maintableModel.getPm_code());
            stmt1.setString(25, maintableModel.getInput_gbn());
            stmt1.setInt(26, maintableModel.getEmp_num());

            stmt2.setString(1, careerModel.getCompany());
            stmt2.setString(2, careerModel.getDepartment());
            stmt2.setDate(3, careerModel.getDate_join());
            stmt2.setDate(4, careerModel.getDate_leave());
            stmt2.setFloat(5, careerModel.getWork_period());
            stmt2.setString(6, careerModel.getFinal_rank());
            stmt2.setString(7, careerModel.getWork_info());
            stmt2.setInt(8, maintableModel.getEmp_num());
            
            stmt3.setDate(1, school_educationModel.getDate_admition());
            stmt3.setDate(2, school_educationModel.getDate_graduate());
            stmt3.setString(3, school_educationModel.getName_school());
            stmt3.setString(4, school_educationModel.getLocation());
            stmt3.setString(5, school_educationModel.getMajor());
            stmt3.setString(6, school_educationModel.getSub_major());
            stmt3.setString(7, school_educationModel.getComment());
            stmt3.setInt(8, maintableModel.getEmp_num());

            stmt4.setString(1, technicalModel.getTec_detail());
            stmt4.setString(2, technicalModel.getProficiency());
            stmt4.setString(3, technicalModel.getNote());
            stmt4.setInt(4, maintableModel.getEmp_num());

            stmt5.setString(1, familyModel.getName_family());
            stmt5.setDate(2, familyModel.getBirth());
            stmt5.setInt(3, familyModel.getAge());
            stmt5.setString(4, familyModel.getRel());
            stmt5.setString(5, familyModel.getLive());
            stmt5.setInt(6, maintableModel.getEmp_num());
            
            stmt6.setString(1, certificateModel.getName_license());
            stmt6.setDate(2, certificateModel.getDate_acqui());
            stmt6.setInt(3, maintableModel.getEmp_num());

            stmt7.setString(1, e_C_Model.getName_edu());
            stmt7.setString(2, e_C_Model.getEdu_insti());
            stmt7.setString(3, e_C_Model.getComp_yn());
            stmt7.setDate(4, e_C_Model.getDate_start());
            stmt7.setDate(5, e_C_Model.getDate_end());
            stmt7.setInt(6, maintableModel.getEmp_num());
            
            stmt8.setString(1, r_P_Model.getName_rew_puni());
            stmt8.setInt(2, r_P_Model.getScore());
            stmt8.setDate(3, r_P_Model.getDate_rew_puni());
            stmt8.setString(4, r_P_Model.getNote());
            stmt8.setString(5, r_P_Model.getName_rew_gbn());
            stmt8.setInt(6, maintableModel.getEmp_num());
            
            stmt9.setString(1, r_S_Model.getEmp_rank());
            stmt9.setString(2, r_S_Model.getSalary());
            stmt9.setDate(3, r_S_Model.getDate_join());
            stmt9.setInt(4, maintableModel.getEmp_num());

            // 업데이트 실행
            int rowsAffected1 = stmt1.executeUpdate();
            int rowsAffected2 = stmt2.executeUpdate();
            int rowsAffected3 = stmt3.executeUpdate();
            int rowsAffected4 = stmt4.executeUpdate();
            int rowsAffected5 = stmt5.executeUpdate();
            int rowsAffected6 = stmt6.executeUpdate();
            int rowsAffected7 = stmt7.executeUpdate();
            int rowsAffected8 = stmt8.executeUpdate();
            int rowsAffected9 = stmt9.executeUpdate();

            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    

    // 로그인 시 로그아웃 왼쪽에 사진, 이름, 권한 출력하는 레포
    public List<SessionModel> getSession(HttpSession session, HttpServletRequest request){
        List<SessionModel> sess = new ArrayList<>();
        String userId = (String) session.getAttribute("userid");
        try {
            int empNum = Integer.parseInt(userId);
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT a.pic, a.name_kor, b.authority "
                       + " FROM main_table a, login_data b "
                       + " WHERE a.emp_num = b.emp_num AND a.emp_num = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empNum);
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                SessionModel ses = new SessionModel();
                ses.setPic(resultSet.getBlob("pic"));
                ses.setName_kor(resultSet.getString("name_kor"));
                ses.setAuthority(resultSet.getString("authority"));
                sess.add(ses);
            }
            conn.close();
            return sess;
        } catch (Exception e) {
            System.err.println("EmpRepo getSession exception");
            e.printStackTrace();
            return null;
        }
    }
}
