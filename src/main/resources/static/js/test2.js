
// 저장 버튼에 이벤트 처리 함수 연결
$("#saveButton").click(function() {
    saveDataToServer();
});

// 데이터 저장 함수
function saveDataToServer() {
    var tableData = {};
    // main_table 데이터 수집
    tableData.main = {};

    // 경력 테이블 데이터 수집
    tableData.career = [];
    $('#myTable_car tbody tr').each(function() {
        var rowData = {
            company: $(this).find('input:eq(1)').val(),
            department: $(this).find('input:eq(2)').val(),
            date_join: $(this).find('input:eq(3)').val(),
            date_leave: $(this).find('input:eq(4)').val(),
            work_period: $(this).find('input:eq(5)').val(),
            final_rank: $(this).find('input:eq(6)').val(),
            work_info: $(this).find('input:eq(7)').val()
        };
        tableData.career.push(rowData);
    });
    // 학력 테이블 데이터 수집
    tableData.education = [];
    $('#myTable_edu tbody tr').each(function() {
        var rowData = {
            name_school: $(this).find('input:eq(1)').val(),
            date_admition: $(this).find('input:eq(2)').val(),
            date_graduate: $(this).find('input:eq(3)').val(),
            location: $(this).find('input:eq(4)').val(),
            major: $(this).find('input:eq(5)').val(),
            comment: $(this).find('input:eq(6)').val()
        };
        tableData.education.push(rowData);
    });
    // 기술사항 테이블 데이터 수집
    tableData.technical = [];
    $('#myTable_tec tbody tr').each(function() {
        var rowData = {
            tec_detail: $(this).find('input:eq(1)').val(),
            proficiency: $(this).find('input:eq(2)').val(),
            tec_note: $(this).find('input:eq(3)').val()
        };
        tableData.technical.push(rowData);
    });
    // 가족관계 테이블 데이터 수집
    tableData.family = [];
    $('#myTable_fam tbody tr').each(function() {
        var rowData = {
            name_family: $(this).find('input:eq(1)').val(),
            birth: $(this).find('input:eq(2)').val(),
            rel: $(this).find('input:eq(3)').val(),
            live: $(this).find('input:eq(4)').val()
        };
        tableData.family.push(rowData);
    });
    // 자격증 테이블 데이터 수집
    tableData.certificate = [];
    $('#myTable_lic tbody tr').each(function() {
        var rowData = {
            name_license: $(this).find('input:eq(1)').val(),
            date_acqui: $(this).find('input:eq(2)').val()
        };
        tableData.certificate.push(rowData);
    });
    // 교육이수 테이블 데이터 수집
    tableData.educationCompletion = [];
    $('#myTable_ec tbody tr').each(function() {
        var rowData = {
            name_edu: $(this).find('input:eq(1)').val(),
            date_start: $(this).find('input:eq(2)').val(),
            date_end: $(this).find('input:eq(3)').val(),
            edu_insti: $(this).find('input:eq(4)').val()
        };
        tableData.educationCompletion.push(rowData);
    });
    // 상벌 테이블 데이터 수집
    tableData.rewardPunishment = [];
    $('#myTable_rp tbody tr').each(function() {
        var rowData = {
            name_rew_gbn: $(this).find('input:eq(1)').val(),
            name_rew_puni: $(this).find('input:eq(2)').val(),
            score: $(this).find('input:eq(3)').val(),
            date_rew_puni: $(this).find('input:eq(4)').val(),
            note: $(this).find('input:eq(5)').val()
        };
        tableData.rewardPunishment.push(rowData);
    });
    // 직급/호봉 테이블 데이터 수집
    tableData.rankSalary = [];
    $('#myTable_rs tbody tr').each(function() {
        var rowData = {
            rs_emp_rank: $(this).find('input:eq(1)').val(),
            rs_salary: $(this).find('input:eq(2)').val(),
            date_join: $(this).find('input:eq(3)').val()
        };
        tableData.rankSalary.push(rowData);
    });
    // 과제현황 테이블 데이터 수집
    tableData.projectStatus = [];
    $('#myTable_pm tbody tr').each(function() {
        var rowData = {
            pm_code: $(this).find('input:eq(1)').val(),
            pm_name: $(this).find('input:eq(2)').val(),
            input_period: $(this).find('input:eq(3)').val(),
            start_date: $(this).find('input:eq(4)').val(),
            end_date: $(this).find('input:eq(5)').val()
        };
        tableData.projectStatus.push(rowData);
    });
    // 서버로 데이터를 전송합니다.
    $.ajax({
        url: '/employee/add', // 서버의 엔드포인트 URL을 입력합니다.
        method: 'POST',
        data: JSON.stringify(tableData),
        contentType: 'application/json',
        success: function(response) {
            // 서버로부터의 응답을 처리합니다.
            console.log(response);
        },
        error: function(xhr, status, error) {
            // 오류가 발생한 경우 처리합니다.
            console.error(error);
        }
    });
}