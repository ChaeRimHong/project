package com.hrm.project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hrm.project.model.StandardManagementModel;
import com.hrm.project.*;


@Repository("standardManagementRepo")
public class StandardManagementRepo implements StandardManagementRepoI{
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private StandardManagementRepo standardManagementRepo;
    StandardManagementModel standardManagementModel;

    @Autowired
    public StandardManagementRepo(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    List<StandardManagementModel> jobStandard = getStandard("job");
    List<StandardManagementModel> busStandard = getStandard("bus");
    List<StandardManagementModel> emailStandard = getStandard("email");
    List<StandardManagementModel> groStandard = getStandard("gro");
    List<StandardManagementModel> posStandard = getStandard("pos");
    List<StandardManagementModel> rankStandard = getStandard("rank");
    
    public List<StandardManagementModel> getStandard(String gbn_cd) {
        List<StandardManagementModel> standard = new ArrayList<>();
    
        try (Connection conn = common.dbcon()) {
            String useDatabaseQuery = "USE pms";
            Statement stmt = conn.createStatement();
            stmt.execute(useDatabaseQuery);
    
            String sql = "SELECT gbn_cd, gbn_detail, gbn_name, comment FROM MASTER_DATA WHERE gbn_cd = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, gbn_cd);
                
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        String gbn_code = resultSet.getString("gbn_cd");
                        String gbn_detail = resultSet.getString("gbn_detail");
                        String gbn_name = resultSet.getString("gbn_name");
                        String comment = resultSet.getString("comment");
    
                        StandardManagementModel standardManagementModel = new StandardManagementModel();
                        standardManagementModel.setGbn_cd(gbn_code);
                        standardManagementModel.setGbn_detail(gbn_detail);
                        standardManagementModel.setGbn_name(gbn_name);
                        standardManagementModel.setComment(comment);
    
                        standard.add(standardManagementModel);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음 또는 오류 발생");
            e.printStackTrace();
        }
    
        return standard;
    }
    

    

    // 기준 관리 테이블 삭제 기능
    public boolean deletestandard(String gbn_cd){
        try {
            Connection conn = common.dbcon();

            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            String sql = "DELETE FROM MASTER_DATA WHERE gbn_code = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, gbn_cd);

            //쿼리 실행
            int rowsAffected = stmt.executeUpdate();

            // 삭제된 행이 없으면 실패로 간주
            if(rowsAffected == 0){
                return false;
            }
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값이 없음");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 기준 관리 추가 기능
    public boolean addstandard(StandardManagementModel newstandard){
        try {
            Connection conn = common.dbcon();
            
            // 데이터베이스 선택 쿼리
            String useDatabaseQuery = "USE pms";
            PreparedStatement useDatabaseStmt = conn.prepareStatement(useDatabaseQuery);
            useDatabaseStmt.execute();

            //SQL 쿼리 작성
            String sql = "INSERT INTO MASTER_DATA(gbn_cd, gbn_detail, gbn_name, comment) VALUES(?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newstandard.getGbn_cd());
            stmt.setString(2, newstandard.getGbn_detail());
            stmt.setString(3, newstandard.getGbn_name());
            stmt.setString(4, newstandard.getComment());

            // 쿼리 실행
            int rowsAffected = stmt.executeUpdate();

            // 쿼리 실행 되면 ture 반환
            if(rowsAffected > 0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("데이터베이스에서 반환된 값 없음");
            e.printStackTrace();
            return false;
        }
    }
}
