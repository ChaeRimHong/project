////////////////////메일+도메인 주소////////////////////
        // 이메일 주소 업데이트 함수
        function updateEmail() {
            // 이메일 아이디 입력값 가져오기
            var emailId = document.getElementById("mail-head").value;
            
            // 이메일 도메인 선택값 가져오기
            var emailDomain = document.getElementById("email-domain-select").value;
            
            // 이메일 주소 조합
            var combinedEmail = emailId + "@" + emailDomain;
            
            // 조합된 이메일 주소를 숨겨진 input 요소에 설정
            document.getElementById("mail").value = combinedEmail;
            
            // 테스트용으로 콘솔에 출력
            console.log("조합된 이메일 주소:", combinedEmail);
        }

        // DOM이 로드되면 이벤트 리스너 설정
        document.addEventListener("DOMContentLoaded", function() {
            // 이메일 아이디 입력 필드와 도메인 선택 박스에 이벤트 리스너 추가
            document.getElementById("mail-head").addEventListener("input", updateEmail);
            document.getElementById("email-domain-select").addEventListener("change", updateEmail);
        });
////////////////////도로명 주소+상세주소////////////////////
        // 주소 결합 함수
        function combineAddresses() {
            // 주소 입력 input 요소들의 값을 가져옴
            var address1 = document.getElementById("address_1").value;
            var address2 = document.getElementById("address_2").value;

            // 두 주소를 합침
            var combinedAddress = address1 + "," + address2;

            // 합친 주소를 콘솔에 출력 (테스트용)
            console.log("합쳐진 주소:", combinedAddress);

            // 합쳐진 주소를 id가 address인 input 요소에 설정
            document.getElementById("address").value = combinedAddress;
        }

////////////////////도로명 주소////////////////////
        // Daum 우편번호 서비스 실행 함수
        function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('address_num').value = data.zonecode;
                    document.getElementById('address_1').value = data.address;
                    combineAddresses(); // 주소가 선택되면 주소 결합 함수 호출
                }
            }).open();
        }

        // DOM이 로드되면 이벤트 리스너 설정
        document.addEventListener("DOMContentLoaded", function() {
            // 도로명 주소와 상세 주소 입력 필드에 이벤트 리스너 추가
            document.getElementById("address_1").addEventListener("input", combineAddresses);
            document.getElementById("address_2").addEventListener("input", combineAddresses);
        });

// select 박스 선택시 미리보기 안보이게
$(document).ready(function() {
    $('select').on('mousedown', function() {
        $(this).siblings('.placeholder').css('display', 'none');
    });
});

// 사진 미리보기
    function previewImage(event) {
        const input_img = event.target;
        const file_img = input_img.files[0];
        const preview_img = document.getElementById('preview_img');

        const reader_img = new FileReader();
        reader_img.onload = function() {
            preview_img.src = reader_img.result;
        }
        reader_img.readAsDataURL(file_img);
    }

// 저장 버튼에 이벤트 처리 함수 연결
$("#updateButton").click(function() {
    updateDataToServer();
});

// 저장
function updateDataToServer() {
    // 각 테이블의 데이터를 수집하여 JavaScript 객체로 만듭니다.
    var tableData = {};

    // main_table 데이터 수집
    tableData.main = {};
    $('#myInput_main .row').each(function(){
        var rowData = {
            emp_num: $(this).find('input[name="emp_num"]').val(),
            name_kor: $(this).find('input[name="name_kor"]').val(),
            name_eng: $(this).find('input[name="name_eng"]').val(),
            reg_num: $(this).find('input[name="reg_num"]').val(),
            // pic: $(this).find('input[name="pic"]').val(),
            age: $(this).find('input[name="age"]').val(),
            phone: $(this).find('input[name="phone"]').val(),
            mail: $(this).find('#email-domain-select option:selected').val(),
            address: $(this).find('input[name="address"]').val(),
            address_num: $(this).find('input[name="address_num"]').val(),
            dept_biz: $(this).find('#businessUnit option:selected').val(),
            dept_group: $(this).find('#department option:selected').val(),
            date_join: $(this).find('input[name="date_join"]').val(),
            emp_rank: $(this).find('#emp_rank_select option:selected').val(),
            work_pos: $(this).find('#emp_position_select option:selected').val(),
            position: $(this).find('#position option:selected').val(),
            salary: $(this).find('input[name="salary"]').val(),
            last_edu: $(this).find('#last_edu option:selected').val(),
            military: $(this).find('#military option:selected').val(),
            merry: $(this).find('#marry option:selected').val(),
            height: $(this).find('input[name="height"]').val(),
            weight: $(this).find('input[name="weight"]').val(),
            gender: $(this).find('input[name="gender"]').val()
        };
        tableData.main = rowData;
    })
    console.log("dept_biz = "+tableData.main.dept_biz);
    console.log("dept_group = "+tableData.main.dept_group);
    console.log("emp_rank = "+tableData.main.emp_rank);
    console.log("work_pos = "+tableData.main.work_pos);
    console.log("position = "+tableData.main.position);

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

        // POST 요청 보내기
        $.ajax({
            url: '/employee/update',
            method: 'POST',
            data: JSON.stringify(requestData), // 수집한 데이터를 JSON 형식으로 변환하여 전송
            contentType: 'application/json', // 전송하는 데이터의 형식을 JSON으로 지정
            success: function(response) {
                // 성공적으로 요청이 완료되었을 때 처리하는 로직
                console.log(response);
            },
            error: function(xhr, status, error) {
                // 요청이 실패했을 때 처리하는 로직
                console.error(error);
            }
        });

    // 서버로 데이터를 전송합니다.
    $.ajax({
        url: '/employee/update', // 서버의 엔드포인트 URL을 입력합니다.
        method: 'POST',
        data: tableData,
        contentType: 'application/json',
        success: function(response) {
            // 서버로부터의 응답을 처리합니다.
            console.log(response);
            window.location.href = '/main';
        },
        error: function(xhr, status, error) {
            // 오류가 발생한 경우 처리합니다.
            console.error(error);
        }
    });
    
}