package com.hrm.project.repository;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.hibernate.sql.results.jdbc.internal.ResultSetAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.hrm.project.model.EmployeeManagementModel;
import com.hrm.project.model.Pm_histModel;
import com.hrm.project.model.TaskManagementModel;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;
import java.io.*;
import java.sql.*;
import java.util.Iterator;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Decoder.Text;

import com.hrm.project.*;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.sql.ResultSet;

@Repository
public class TaskManagementRepo implements TaskManagementRepoI{
    private HttpSession session;
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private TaskManagementRepo taskManagementRepo;
    TaskManagementModel taskManagementModel;
    Pm_histModel pm_histModel;

    @Autowired
    public TaskManagementRepo(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 과제관리 조회 테이블
    public List<TaskManagementModel> getTask(int pageNumber, int pageSize){
        List<TaskManagementModel> tasks = new ArrayList<>();

        try{
            Connection conn = common.dbcon();
            System.out.println(">  TaskManagementRepo : getTask");
            
            //데이터베이스 선택 쿼리
            Statement stmt = conn.createStatement();
            stmt.execute("USE pms");

            // sql 쿼리 작성
            String query = "SELECT pm_code, pm_name, pm_nori_manager, pm_nori_bu, pm_create_date, pm_manager, pm_bu, pm_business, pm_price,"
            + "pm_workload, pm_full, pm_part, pm_suggest_date, pm_start_date, pm_end_date, pm_work_date, pm_status, pm_outline, pm_bego, pm_file, pm_confirmed,"
            + "pm_save_id, pm_save_sa, pm_save_name, pm_file_path FROM PM_DATA LIMIT ?, ?";

            int offset = (pageNumber - 1) * pageSize;

            // 쿼리 실행 및 결과 가져오기
            PreparedStatement Pstmt = conn.prepareStatement(query);
            Pstmt.setInt(1, offset);
            Pstmt.setInt(2, pageSize);
            ResultSet resultSet = Pstmt.executeQuery();

            while(resultSet.next()){
                String pm_code = resultSet.getString("pm_code");
                String pm_name = resultSet.getString("pm_name");
                String pm_nori_manager = resultSet.getString("pm_nori_manager");
                String pm_nori_bu = resultSet.getString("pm_nori_bu");
                Date pm_create_date = resultSet.getDate("pm_create_date");
                String pm_manager = resultSet.getString("pm_manager");
                String pm_bu = resultSet.getString("pm_bu");
                String pm_business = resultSet.getString("pm_business");
                BigDecimal pm_price = resultSet.getBigDecimal("pm_price");
                int pm_workload = resultSet.getInt("pm_workload");
                int pm_full = resultSet.getInt("pm_full");
                int pm_part = resultSet.getInt("pm_part");
                Date pm_suggest_date = resultSet.getDate("pm_suggest_date");
                Date pm_start_date = resultSet.getDate("pm_start_date");
                Date pm_end_date = resultSet.getDate("pm_end_date");
                int pm_work_date = resultSet.getInt("pm_work_date");
                String pm_status = resultSet.getString("pm_status");
                String pm_outline = resultSet.getString("pm_outline");
                String pm_bego = resultSet.getString("pm_bego");
                String pm_file = resultSet.getString("pm_file");
                String pm_confirmed = resultSet.getString("pm_confirmed");
                String pm_save_id = resultSet.getString("pm_save_id");
                String pm_save_sa = resultSet.getString("pm_save_sa");
                String pm_save_name = resultSet.getString("pm_save_name");
                String pm_file_path = resultSet.getString("pm_file_path");

                // TaskManagementModel 객체 생성 및 값 설정
                TaskManagementModel taskManagementModel = new TaskManagementModel();
                taskManagementModel.setPm_code(pm_code);
                taskManagementModel.setPm_name(pm_name);
                taskManagementModel.setPm_nori_manager(pm_nori_manager);
                taskManagementModel.setPm_nori_bu(pm_nori_bu);
                taskManagementModel.setPm_create_date(pm_create_date);
                taskManagementModel.setPm_manager(pm_manager);
                taskManagementModel.setPm_bu(pm_bu);
                taskManagementModel.setPm_business(pm_business);
                taskManagementModel.setPm_price(pm_price);
                taskManagementModel.setPm_workload(pm_workload);
                taskManagementModel.setPm_full(pm_full);
                taskManagementModel.setPm_part(pm_part);
                taskManagementModel.setPm_suggest_date(pm_suggest_date);
                taskManagementModel.setPm_start_date(pm_start_date);
                taskManagementModel.setPm_end_date(pm_end_date);
                taskManagementModel.setPm_work_date(pm_work_date);
                taskManagementModel.setPm_status(pm_status);
                taskManagementModel.setPm_outline(pm_outline);
                taskManagementModel.setPm_bego(pm_bego);
                taskManagementModel.setPm_file(pm_file);
                taskManagementModel.setPm_confirmed(pm_confirmed);
                taskManagementModel.setPm_save_id(pm_save_id);
                taskManagementModel.setPm_save_sa(pm_save_sa);
                taskManagementModel.setPm_save_name(pm_save_name);
                taskManagementModel.setPm_file_path(pm_file_path);

                tasks.add(taskManagementModel);
            }
            conn.close();
        } catch (Exception e){
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
        return tasks;
    }

    // 전체 과제 수를 반환하는 메서드
    public int getTaskCount(){
        try {
            Connection conn = common.dbcon();

            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // SQL 쿼리
            String sql = "SELECT COUNT(*) AS total FROM PM_DATA";

            // 쿼리 실행 및 결과 가져오기
            PreparedStatement stmt = conn.prepareStatement(sql);
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


    // 과제관리 테이블 검색기능
    public List<TaskManagementModel> searchTasks(String keyword){
        List<TaskManagementModel> tasks = new ArrayList<>();
        try {
            Connection conn = common.dbcon();

            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // SQL 쿼리 작성 - 이름으로 검색
            String sql = "SELECT pm_code, pm_name, pm_nori_manager, pm_nori_bu, pm_create_date, pm_manager, pm_bu, pm_business, pm_price, pm_workload, pm_full,"
            + " pm_part, pm_suggest_date, pm_start_date, pm_end_date, pm_work_date, pm_status, pm_outline, pm_bego, pm_file, pm_confirmed,"
            + " pm_save_id, pm_save_sa, pm_save_name, pm_file_path FROM PM_DATA WHERE pm_name LIKE ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet resultSet = stmt.executeQuery();
            
            while(resultSet.next()){
                TaskManagementModel task = new TaskManagementModel();
                task.setPm_code(resultSet.getString("pm_code"));
                task.setPm_name(resultSet.getString("pm_name"));
                task.setPm_nori_manager(resultSet.getString("pm_nori_manager"));
                task.setPm_nori_bu(resultSet.getString("pm_nori_bu"));
                task.setPm_create_date(resultSet.getDate("pm_create_date"));
                task.setPm_manager(resultSet.getString("pm_manager"));
                task.setPm_bu(resultSet.getString("pm_bu"));
                task.setPm_business(resultSet.getString("pm_business"));
                task.setPm_price(resultSet.getBigDecimal("pm_price"));
                task.setPm_workload(resultSet.getInt("pm_workload"));
                task.setPm_full(resultSet.getInt("pm_full"));
                task.setPm_part(resultSet.getInt("pm_part"));
                task.setPm_suggest_date(resultSet.getDate("pm_suggest_date"));
                task.setPm_start_date(resultSet.getDate("pm_start_date"));
                task.setPm_end_date(resultSet.getDate("pm_end_date"));
                task.setPm_work_date(resultSet.getInt("pm_work_date"));
                task.setPm_status(resultSet.getString("pm_status"));
                task.setPm_outline(resultSet.getString("pm_outline"));
                task.setPm_bego(resultSet.getString("pm_bego"));
                task.setPm_file(resultSet.getString("pm_file"));
                task.setPm_confirmed(resultSet.getString("pm_confirmed"));
                task.setPm_save_id(resultSet.getString("pm_save_id"));
                task.setPm_save_sa(resultSet.getString("pm_save_sa"));
                task.setPm_save_name(resultSet.getString("pm_save_name"));
                task.setPm_file_path(resultSet.getString("pm_file_path"));
                tasks.add(task);
            }
            conn.close();
            return tasks;
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
    }

    // 과제 관리 테이블 유저 삭제 기능
    public boolean deleteTask(String pm_code){
        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "DELETE FROM PM_DATA WHERE pm_code = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pm_code);

            // 삭제 쿼리 실행
            int rowsAffected = stmt.executeUpdate();

            // 삭제된 행이 없으면 실패로 간주
            if(rowsAffected == 0){
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return false;
        }
    }

    // 과제 관리 과제 추가 기능
    public boolean addTask(TaskManagementModel taskManagementModel){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = common.dbcon();
            conn.setAutoCommit(false); // 오토커밋 비활성화

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            //String userId = (String) session.getAttribute("id");

            // SQL 쿼리 작성
            String sql = "INSERT INTO PM_DATA(pm_code, pm_name, pm_nori_manager, pm_nori_bu, pm_create_date, pm_manager, pm_bu, pm_business, pm_price, "
            + "pm_workload, pm_full, pm_part, pm_suggest_date, pm_start_date, pm_end_date, pm_work_date, pm_status, pm_outline, pm_bego) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            // PreparedStatement 객체 생성
            stmt = conn.prepareStatement(sql);

            // 값 설정 - 과제 저장
            stmt.setString(1, taskManagementModel.getPm_code());
            stmt.setString(2, taskManagementModel.getPm_name());
            stmt.setString(3, taskManagementModel.getPm_nori_manager());
            stmt.setString(4, taskManagementModel.getPm_nori_bu());
            stmt.setDate(5, taskManagementModel.getPm_create_date());
            stmt.setString(6, taskManagementModel.getPm_manager());
            stmt.setString(7, taskManagementModel.getPm_bu());
            stmt.setString(8, taskManagementModel.getPm_business());
            stmt.setBigDecimal(9, taskManagementModel.getPm_price());
            stmt.setInt(10, taskManagementModel.getPm_workload());
            stmt.setInt(11, taskManagementModel.getPm_full());
            stmt.setInt(12, taskManagementModel.getPm_part());
            stmt.setDate(13, taskManagementModel.getPm_suggest_date());
            stmt.setDate(14, taskManagementModel.getPm_start_date());
            stmt.setDate(15, taskManagementModel.getPm_end_date());
            stmt.setInt(16, taskManagementModel.getPm_work_date());
            stmt.setString(17, taskManagementModel.getPm_status());
            stmt.setString(18, taskManagementModel.getPm_outline());
            stmt.setString(19, taskManagementModel.getPm_bego());

            // 쿼리 실행
            int rowsAffected = stmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true); // 오토커밋 다시 활성화
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            // 예외 처리
            System.out.println("데이터베이스에서 반환된 값 없음");
            e.printStackTrace();
            try {
                // 롤백 시도
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            // 리소스 정리
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 과제 추가할 때 history 테이블에 값 저장하는 메소드
    public boolean insert_hist(@ModelAttribute EmployeeManagementModel employeeManagementModel,
                                @ModelAttribute TaskManagementModel taskManagementModel){
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = common.dbcon();
            conn.setAutoCommit(false);  // 트랜잭션 시작

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // Max idx 값을 가져오기
            String maxIdxQuery = "SELECT IFNULL(MAX(idx), 0) + 1 AS max_idx FROM pm_hist";
            PreparedStatement maxIdxStmt = conn.prepareStatement(maxIdxQuery);
            ResultSet rs = maxIdxStmt.executeQuery();
            int maxIdx = 1;
            if (rs.next()) {
                maxIdx = rs.getInt("max_idx");
            }
            rs.close();
            maxIdxStmt.close();

            // INSERT 쿼리 준비
            String query = "INSERT INTO pm_hist(pm_code, idx, emp_num, pm_gbn, pm_date)"
                        + " VALUES(?, ?, ?, 'Y', SYSDATE())";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, taskManagementModel.getPm_code());
            stmt.setInt(2, maxIdx);
            stmt.setInt(3, employeeManagementModel.getEmp_num());

            int rowsAffected = stmt.executeUpdate();

            conn.commit();  // 트랜잭션 커밋
            return rowsAffected > 0;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();  // 트랜잭션 롤백
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("데이터베이스에서 반환된 값 없음");
            e.printStackTrace();
            return false;
        } finally {
            // 자원 해제
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    // 파일 업로드 디렉토리
    public static final String upload_directory = "/path/to/upload/directory";

    // 파일 업로드 메서드
    public boolean uploadFile(InputStream fileInputStream, String fileName){
        String filePath = Paths.get(upload_directory, fileName).toString();
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)){
            byte[] buffer = new byte[1024];
            int bytesRead;

            while((bytesRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //사원 신규(수정) 화면 과제 현황 출력하기
    public List<TaskManagementModel> sin(){
        List<TaskManagementModel> tasks = new ArrayList<>();
        try{
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT pm.pm_code, pm.pm_name, pm.pm_work_date, pm.pm_start_date, pm.pm_end_date FROM PM_DATA pm, main_table main "
                    + " WHERE pm.pm_code = main.pm_code and main.emp_num = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                TaskManagementModel taskManagementModel = new TaskManagementModel();
                taskManagementModel.setPm_code(resultSet.getString("pm_code"));
                taskManagementModel.setPm_name(resultSet.getString("pm_name"));
                taskManagementModel.setPm_work_date(resultSet.getInt("pm_work_date"));
                taskManagementModel.setPm_start_date(resultSet.getDate("pm_start_date"));
                taskManagementModel.setPm_end_date(resultSet.getDate("pm_end_date"));
                tasks.add(taskManagementModel);
            }
            conn.close();
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 과제관리에서 확정 유무가 Y라면 삭제가 안되게 하기 위한 확정유무 가져오기
    public List<TaskManagementModel> getConfirmed(){
        List<TaskManagementModel> tasks = new ArrayList<>();
        try {
            Connection conn = common.dbcon();

            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // SQL 쿼리
            String sql = "SELECT pm_confirmed FROM PM_DATA WHERE pm_code = ?";

            // 쿼리 실행 및 결과
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            TaskManagementModel task = new TaskManagementModel();
            task.setPm_code(resultSet.getString("pm_code"));

            tasks.add(task);

            conn.close();
            return tasks;
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return null;
        }
    }

    // 과제 수정 시 과제코드에 대한 정보 가져오기
    public TaskManagementModel getTaskByCode(String pm_code) {
        TaskManagementModel taskManagementModel = null; // 과제 정보를 저장할 변수 선언

        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // 쿼리 - 과제 코드를 눌렀을 때 해당 과제 정보 가져오기
            String sql = "SELECT pm_code, pm_name, pm_nori_manager, pm_nori_bu, pm_create_date, pm_manager, pm_bu, pm_business, pm_price,"
                + "pm_workload, pm_full, pm_part, pm_suggest_date, pm_start_date, pm_end_date, pm_work_date, pm_status, pm_outline, pm_bego, pm_file, pm_confirmed,"
                + "pm_save_id, pm_save_sa, pm_save_name, pm_file_path, pm_save_date FROM PM_DATA WHERE pm_code = ?";

            PreparedStatement pmstmt = conn.prepareStatement(sql);
            pmstmt.setString(1, pm_code); // 파라미터로 전달된 pm_code 값을 사용
            ResultSet resultSet_pmcode = pmstmt.executeQuery();

            if(resultSet_pmcode.next()) {
                // 과제 정보를 생성하여 값을 설정
                taskManagementModel = new TaskManagementModel();
                taskManagementModel.setPm_code(resultSet_pmcode.getString("pm_code"));
                taskManagementModel.setPm_name(resultSet_pmcode.getString("pm_name"));
                taskManagementModel.setPm_nori_manager(resultSet_pmcode.getString("pm_nori_manager"));
                taskManagementModel.setPm_nori_bu(resultSet_pmcode.getString("pm_nori_bu"));
                taskManagementModel.setPm_create_date(resultSet_pmcode.getDate("pm_create_date"));
                taskManagementModel.setPm_manager(resultSet_pmcode.getString("pm_manager"));
                taskManagementModel.setPm_bu(resultSet_pmcode.getString("pm_bu"));
                taskManagementModel.setPm_business(resultSet_pmcode.getString("pm_business"));
                taskManagementModel.setPm_price(resultSet_pmcode.getBigDecimal("pm_price"));
                taskManagementModel.setPm_workload(resultSet_pmcode.getInt("pm_workload"));
                taskManagementModel.setPm_full(resultSet_pmcode.getInt("pm_full"));
                taskManagementModel.setPm_part(resultSet_pmcode.getInt("pm_part"));
                taskManagementModel.setPm_suggest_date(resultSet_pmcode.getDate("pm_suggest_date"));
                taskManagementModel.setPm_start_date(resultSet_pmcode.getDate("pm_start_date"));
                taskManagementModel.setPm_end_date(resultSet_pmcode.getDate("pm_end_date"));
                taskManagementModel.setPm_work_date(resultSet_pmcode.getInt("pm_work_date"));
                taskManagementModel.setPm_status(resultSet_pmcode.getString("pm_status"));
                taskManagementModel.setPm_outline(resultSet_pmcode.getString("pm_outline"));
                taskManagementModel.setPm_bego(resultSet_pmcode.getString("pm_bego"));
                taskManagementModel.setPm_file(resultSet_pmcode.getString("pm_file"));
                taskManagementModel.setPm_confirmed(resultSet_pmcode.getString("pm_confirmed"));
                taskManagementModel.setPm_save_id(resultSet_pmcode.getString("pm_save_id"));
                taskManagementModel.setPm_save_sa(resultSet_pmcode.getString("pm_save_sa"));
                taskManagementModel.setPm_save_name(resultSet_pmcode.getString("pm_save_name"));
                taskManagementModel.setPm_file_path(resultSet_pmcode.getString("pm_file_path"));
                taskManagementModel.setPm_save_date(resultSet_pmcode.getDate("pm_save_date"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskManagementModel;
    }



    // 과제 수정 시 DB데이터 가져오기
    public List<TaskManagementModel> getTask_update(TaskManagementModel taskManagementModel, EmployeeManagementModel employeeManagementModel){
        List<TaskManagementModel> tasks = new ArrayList<>();
        List<EmployeeManagementModel> emp = new ArrayList<>();
        System.out.println(">  TaskManagementRepo : getTask_update return");
        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            // 쿼리 작성 - 과제 정보 가져오기
            String sql = "SELECT pm_code, pm_name, pm_nori_manager, pm_nori_bu, pm_create_date, pm_manager, pm_bu, pm_business, pm_price,"
                + "pm_workload, pm_full, pm_part, pm_suggest_date, pm_start_date, pm_end_date, pm_work_date, pm_status, pm_outline, pm_bego, pm_file, pm_confirmed,"
                + "pm_save_id, pm_save_sa, pm_save_name, pm_file_path, pm_save_date FROM PM_DATA WHERE pm_code = ?";

            // // 쿼리 - 투입인력 가져오기
            // String sql2 = "SELECT a.emp_num, a.name_kor, a.dept_biz, a.dept_group, a.emp_rank, a.phone, a.mail"
            //     + " From main_table a, pm_data b, pm_hist c"
            //     + " WHERE a.emp_num = c.emp_num AND c.pm_code = b.pm_code AND b.pm_code = ?";

            // 과제 정보
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, taskManagementModel.getPm_code());
            ResultSet resultSet = stmt.executeQuery();

            // 투입인력
            // PreparedStatement sstmt = conn.prepareStatement(sql2);
            // sstmt.setString(1, taskManagementModel.getPm_code());
            // ResultSet resultSet2 = sstmt.executeQuery();

            while(resultSet.next()){
                String pm_code = resultSet.getString("pm_code");
                String pm_name = resultSet.getString("pm_name");
                String pm_nori_manager = resultSet.getString("pm_nori_manager");
                String pm_nori_bu = resultSet.getString("pm_nori_bu");
                Date pm_create_date = resultSet.getDate("pm_create_date");
                String pm_manager = resultSet.getString("pm_manager");
                String pm_bu = resultSet.getString("pm_bu");
                String pm_business = resultSet.getString("pm_business");
                BigDecimal pm_price = resultSet.getBigDecimal("pm_price");
                int pm_workload = resultSet.getInt("pm_workload");
                int pm_full = resultSet.getInt("pm_full");
                int pm_part = resultSet.getInt("pm_part");
                Date pm_suggest_date = resultSet.getDate("pm_suggest_date");
                Date pm_start_date = resultSet.getDate("pm_start_date");
                Date pm_end_date = resultSet.getDate("pm_end_date");
                int pm_work_date = resultSet.getInt("pm_work_date");
                String pm_status = resultSet.getString("pm_status");
                String pm_outline = resultSet.getString("pm_outline");
                String pm_bego = resultSet.getString("pm_bego");
                String pm_file = resultSet.getString("pm_file");
                String pm_confirmed = resultSet.getString("pm_confirmed");
                String pm_save_id = resultSet.getString("pm_save_id");
                String pm_save_sa = resultSet.getString("pm_save_sa");
                String pm_save_name = resultSet.getString("pm_save_name");
                String pm_file_path = resultSet.getString("pm_file_path");
                Date pm_save_date = resultSet.getDate("pm_save_date");

                // TaskManagementModel 객체 생성 및 값 설정
                TaskManagementModel task = new TaskManagementModel();
                task.setPm_code(pm_code);
                task.setPm_name(pm_name);
                task.setPm_nori_manager(pm_nori_manager);
                task.setPm_nori_bu(pm_nori_bu);
                task.setPm_create_date(pm_create_date);
                task.setPm_manager(pm_manager);
                task.setPm_bu(pm_bu);
                task.setPm_business(pm_business);
                task.setPm_price(pm_price);
                task.setPm_workload(pm_workload);
                task.setPm_full(pm_full);
                task.setPm_part(pm_part);
                task.setPm_suggest_date(pm_suggest_date);
                task.setPm_start_date(pm_start_date);
                task.setPm_end_date(pm_end_date);
                task.setPm_work_date(pm_work_date);
                task.setPm_status(pm_status);
                task.setPm_outline(pm_outline);
                task.setPm_bego(pm_bego);
                task.setPm_file(pm_file);
                task.setPm_confirmed(pm_confirmed);
                task.setPm_save_id(pm_save_id);
                task.setPm_save_sa(pm_save_sa);
                task.setPm_save_name(pm_save_name);
                task.setPm_file_path(pm_file_path);
                task.setPm_save_date(pm_save_date);

                tasks.add(task);
            }

            // 투입 인력 객체에 값 넣기
            // while(resultSet2.next()){
            //     int emp_num = resultSet2.getInt("emp_num");
            //     String name_kor = resultSet2.getString("name_kor");
            //     String dept_biz = resultSet2.getString("dept_biz");
            //     String dept_group = resultSet2.getString("dept_group");
            //     String emp_rank = resultSet2.getString("emp_rank");
            //     String phone = resultSet2.getString("phone");
            //     String mail = resultSet2.getString("mail");

            //     EmployeeManagementModel emps = new EmployeeManagementModel();
            //     emps.setEmp_num(emp_num);
            //     emps.setName_kor(name_kor);
            //     emps.setDept_biz(dept_biz);
            //     emps.setDept_group(dept_group);
            //     emps.setEmp_rank(emp_rank);
            //     emps.setPhone(phone);
            //     emps.setMail(mail);

            //     emp.add(emps);
            // }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tasks;
    }


    // 과제 수정 시 정보 업데이트(수정 안된 것도 전부다 업데이트)
    public boolean update_task(TaskManagementModel taskManagementModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        System.out.println(">> TaskManagementRepo : update_task return");
    
        try {
            conn = common.dbcon();
            conn.setAutoCommit(false);
    
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();
    
            String sql = "UPDATE PM_DATA SET pm_name=?, pm_nori_manager=?, pm_nori_bu=?, pm_create_date=?, pm_manager=?, pm_bu=?, pm_business=?,"
                       + " pm_price=?, pm_workload=?, pm_full=?, pm_part=?, pm_suggest_date=?, pm_start_date=?, pm_end_date=?, pm_work_date=?, pm_status=?,"
                       + " pm_outline=?, pm_bego=?, pm_file=?, pm_confirmed=?, pm_file_path=? WHERE pm_code=?";
            stmt = conn.prepareStatement(sql);
    
            // 값 설정
            stmt.setString(1, taskManagementModel.getPm_name());
            stmt.setString(2, taskManagementModel.getPm_nori_manager());
            stmt.setString(3, taskManagementModel.getPm_nori_bu());
            stmt.setDate(4, taskManagementModel.getPm_create_date());
            stmt.setString(5, taskManagementModel.getPm_manager());
            stmt.setString(6, taskManagementModel.getPm_bu());
            stmt.setString(7, taskManagementModel.getPm_business());
            stmt.setBigDecimal(8, taskManagementModel.getPm_price());
            stmt.setInt(9, taskManagementModel.getPm_workload());
            stmt.setInt(10, taskManagementModel.getPm_full());
            stmt.setInt(11, taskManagementModel.getPm_part());
            stmt.setDate(12, taskManagementModel.getPm_suggest_date());
            stmt.setDate(13, taskManagementModel.getPm_start_date());
            stmt.setDate(14, taskManagementModel.getPm_end_date());
            stmt.setInt(15, taskManagementModel.getPm_work_date());
            stmt.setString(16, taskManagementModel.getPm_status());
            stmt.setString(17, taskManagementModel.getPm_outline());
            stmt.setString(18, taskManagementModel.getPm_bego());
            stmt.setString(19, taskManagementModel.getPm_file());
            stmt.setString(20, taskManagementModel.getPm_confirmed());
            stmt.setString(21, taskManagementModel.getPm_file_path());
            stmt.setString(22, taskManagementModel.getPm_code());
    
            System.out.println("pm_code : " + taskManagementModel.getPm_code());
            System.out.println("pm_name : " + taskManagementModel.getPm_name());
    
            int rowsAffected = stmt.executeUpdate();
    
            conn.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    // 과제 눌렀을 때 투입 인력 정보 가져오기
    public List<EmployeeManagementModel> getEmpFromPm_hist(String pm_code){
    	System.out.println(">  TaskManagementRepo : getEmpFromPm_hist() //emp 데이터를 가져옴");
        List<EmployeeManagementModel> employees = new ArrayList<>();

        try {
            Connection conn = common.dbcon();

            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "SELECT a.emp_num, a.name_kor, "
            + " (SELECT gbn_name FROM master_data WHERE gbn_detail = a.dept_biz AND gbn_cd = 'bus') AS dept_biz, "
            + " (SELECT gbn_name FROM master_data WHERE gbn_detail = a.dept_group AND gbn_cd = 'gro') AS dept_group, "
            + " (SELECT gbn_name FROM master_data WHERE gbn_detail = a.emp_rank AND gbn_cd = 'rank') AS work_pos, "
            + " a.phone, a.mail FROM main_table a, pm_data b WHERE a.pm_code = b.pm_code AND b.pm_code = ?"; 

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pm_code);
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                int emp_num = resultSet.getInt("pm_code");
                String name_kor = resultSet.getString("name_kor");
                String dept_biz = resultSet.getString("dept_biz");
                String dept_group = resultSet.getString("dept_group");
                String work_pos = resultSet.getString("work_pos");
                String phone = resultSet.getString("phone");
                String mail = resultSet.getString("mail");

                EmployeeManagementModel employeeManagementModel = new EmployeeManagementModel();
                employeeManagementModel.setEmp_num(emp_num);
                employeeManagementModel.setName_kor(name_kor);
                employeeManagementModel.setDept_biz(dept_biz);
                employeeManagementModel.setDept_group(dept_group);
                employeeManagementModel.setWork_pos(work_pos);
                employeeManagementModel.setPhone(phone);
                employeeManagementModel.setMail(mail);
                
                System.out.println("	ㄴ emp_num 값 : "+emp_num);
                System.out.println("	ㄴ name_kor 값 : "+name_kor);
                System.out.println("	ㄴ dept_biz 값 : "+dept_biz);
                System.out.println("	ㄴ dept_group 값 : "+dept_group);
                System.out.println("	ㄴ work_pos 값 : "+work_pos);
                System.out.println("	ㄴ phone 값 : "+phone);
                System.out.println("	ㄴ mail 값 : "+mail);
                
                employees.add(employeeManagementModel);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return employees;
    }

    // pm_hist 테이블에 투입인력 평가 및 기술 기입하는 메소드
    public boolean update_hist(Pm_histModel pm_histModel){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = common.dbcon();
            
            String sql = "UPDATE PM_HIST SET tech_gbn = ?, tech_comment = ?, user_gbn = ?, user_comment = ?"
                        + " WHERE pm_code = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, pm_histModel.getPm_code());

            // 쿼리 실행
            int rowsAffected = stmt.executeUpdate();

            conn.commit();
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return false;
        }
    }
}