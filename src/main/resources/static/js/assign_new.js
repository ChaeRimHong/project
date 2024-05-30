var addedEmployees = [];
/////////////////////////////////////pm_code 시간///////////////////////////////////////////////
$(document).ready(function(){
   document.getElementById("pm_code_date").readOnly = true;
    // 해당 input 필드에 현재 날짜와 시간을 채우는 함수
    function setCurrentDateTime() {
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');
        const dateTimeString = `${year}${month}${day}${hours}${minutes}`;

        // 해당 input 필드에 현재 날짜와 시간을 설정
        $("#pm_code_date").val(dateTimeString);
        console.log("pm_code_date = " + $("#pm_code_date").val());
    }

    // 스크립트가 실행될 때 setCurrentDateTime 함수 실행
    setCurrentDateTime();
});
/////////////////////////////////////공수///////////////////////////////////////////////
    $(document).ready(function(){
        var pmFullInput = document.getElementById("pm_full");
        var pmPartInput = document.getElementById("pm_part");
        var pmWorkloadInput = document.getElementById("pm_workload");

        function calculateWorkload() {
            var pmFullValue = parseFloat(pmFullInput.value) || 0;
            var pmPartValue = parseFloat(pmPartInput.value) || 0;
            var workload = pmFullValue + pmPartValue;
            pmWorkloadInput.value = workload;
        }

        if (pmFullInput && pmPartInput && pmWorkloadInput) {
            // 초기값 설정
            calculateWorkload();

            // 값 변경 이벤트에 대한 리스너 추가
            pmFullInput.addEventListener("input", calculateWorkload);
            pmPartInput.addEventListener("input", calculateWorkload);
        }
    });
/////////////////////////////////////담당자 모달창///////////////////////////////////////////////
$(document).ready(function(){
    var employees_pmmanager = /*[[${employees}]]*/ [];
    var selectedEmployeeData_pmmanager = {}; // 선택된 사원 데이터를 저장할 객체

    $("#searchInput_pmamanager").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#employeeList_pmmanager tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    $("#pm_nori_manager_Modal").on("show.bs.modal", function(){
        // 사원 목록을 순회하면서 클릭 이벤트를 바인딩합니다.
        $("#employeeList_pmmanager tr").each(function(){
            var row_pmmanager = $(this);

            row_pmmanager.off("click"); // 기존 이벤트 핸들러 제거
            row_pmmanager.on("click", function(){
                // 현재 선택된 행을 강조 표시하기 위해 이전에 선택된 행의 강조 표시를 제거합니다.
                $("#employeeList_pmmanager tr.selected").removeClass("selected");
                row_pmmanager.addClass("selected"); // 현재 행에 선택 클래스 추가

                // 클릭한 행에서 각 셀의 정보 가져오기
                var columns_pm = row_pmmanager.find("td");
                var name_pm = columns_pm.eq(2).text(); // 담당자 이름
                var dept_pm = columns_pm.eq(3).text(); // 부서

                // 선택된 사원 데이터를 selectedEmployeeData_pmmanager 변수에 저장
                selectedEmployeeData_pmmanager.name = name_pm; // 담당자 이름 저장
                selectedEmployeeData_pmmanager.dept = dept_pm; // 부서 저장
                
                // 가져온 사원 정보를 입력란에 표시
               $("#pm_nori_manager").val(selectedEmployeeData_pmmanager.name); // 담당자 이름
               $("#pm_nori_bu").val(selectedEmployeeData_pmmanager.dept); // 부서
               
               // 모달을 닫음
               $("#pm_nori_manager_Modal").modal("hide");
            });
        });
    });

    // 모달이 닫힐 때 선택된 행의 강조 표시를 제거하지만 입력된 데이터는 초기화하지 않습니다.
    $("#pm_nori_manager_Modal").on("hidden.bs.modal", function(){
        $("#employeeList_pmmanager tr.selected").removeClass("selected");
        // 입력된 데이터 초기화를 하지 않습니다.
    });
});

/////////////////////////////////////사업부/부서 모달창///////////////////////////////////////////////
$(document).ready(function(){
// 부서 정보 추출
var deptOptions = {};
$("#employeeList tr").each(function(){
    var dept = $(this).find("td:eq(3)").text(); // 부서 정보 추출
    deptOptions[dept] = true; // 중복 제거를 위해 객체의 속성으로 저장
});

// 부서 필터 select 요소에 옵션 추가
var deptFilter = $("#deptFilter");
for (var dept in deptOptions) {
    if (deptOptions.hasOwnProperty(dept)) {
        $("<option>").val(dept).text(dept).appendTo(deptFilter);
    }
}


// 필터링 옵션 변경
$('#deptFilter').change(function() {
    var selectedDept = $(this).val();
    if (selectedDept !== '') {
        $("#employeeList tr").each(function() {
            var dept = $(this).find("td:eq(3)").text(); // 각 행의 부서 정보 추출
            if (dept !== selectedDept) {
                $(this).hide(); // 선택된 부서가 아닌 행 숨기기
            } else {
                $(this).show(); // 선택된 부서와 일치하는 행 표시
            }
        });
    } else {
        $("#employeeList tr").show(); // 전체를 선택한 경우 모든 행 표시
    }
});

/////////////////////////시작일 완료일 개월수계산
// 시작일과 완료일 입력란의 변경 이벤트를 감지하여 기간을 자동으로 계산 (월 단위)
$("#pm_start_date, #pm_end_date").on("change", function() {
    var startDate = new Date($("#pm_start_date").val()); // 시작일
    var endDate = new Date($("#pm_end_date").val()); // 완료일

    // 시작일과 완료일이 모두 유효한 경우에만 기간을 계산하고 입력란에 표시
    if (!isNaN(startDate.getTime()) && !isNaN(endDate.getTime())) {
        var diffMonths = (endDate.getFullYear() - startDate.getFullYear()) * 12;
        diffMonths -= startDate.getMonth();
        diffMonths += endDate.getMonth();

        // 기간 입력란에 계산된 기간을 표시
        $("#pm_work_date").val(diffMonths);
    } else {
        // 시작일 또는 완료일이 유효하지 않은 경우 기간 입력란을 비움
        $("#pm_work_date").val("");
    }
});
});
/////////////////////////////////////엑셀파일 추가///////////////////////////////////////////////////
$(document).ready(function(){
    // + 버튼에 이벤트 처리 함수 연결
    $("#addExcelRowButton").click(function() {
        addTableRowExel('myTable_excel', 'tableSelect_excel');
    });

    // - 버튼에 이벤트 처리 함수 연결
    $("#deleteExcelRowButton").click(function() {
        deleteTableRowExel('myTable_excel');
    });

//엑셀파일 +/-
function addTableRowExel(tableID, radioGroupName) {
   console.log("addTableRowExel 실행됨")
    var table_excel = document.getElementById(tableID);

    // 새로운 행 생성
    var newRow_excel = table_excel.insertRow();

    // 첫 번째 셀에 라디오 버튼 추가
    var cell1_excel = newRow_excel.insertCell(0);
    var radioInput = document.createElement("input");
    radioInput.type = "radio";
    radioInput.name = radioGroupName;
    cell1_excel.appendChild(radioInput);

    // 두 번째 셀에 파일 선택 input 추가
    var cell2_excel = newRow_excel.insertCell(1);
    var fileInput_excel = document.createElement("input");
    fileInput_excel.type = "file";
    fileInput_excel.className = "form-control-file";
    cell2_excel.appendChild(fileInput_excel);
}

// 엑셀 테이블 행 삭제 함수
function deleteTableRowExel(tableID) {
    var table_excel = document.getElementById(tableID);
    var rowCount_excel = table_excel.rows.length;

    // 테이블의 각 행을 확인하여 체크된 라디오 버튼이 있는지 검사하고 삭제
    for (var i = 0; i < rowCount_excel; i++) {
        var row_excel = table_excel.rows[i];
        var radioInput = row_excel.cells[0].querySelector("input[type='radio']");
        if (radioInput.checked) {
            table_excel.deleteRow(i);
            rowCount_excel--;
            i--;
        }
    }
}
});
var addedEmployees = [];
/////////////////////////////////////pm_code 시간///////////////////////////////////////////////
$(document).ready(function(){
   document.getElementById("pm_code_date").readOnly = true;
    // 해당 input 필드에 현재 날짜와 시간을 채우는 함수
    function setCurrentDateTime() {
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');
        const dateTimeString = `${year}${month}${day}${hours}${minutes}`;

        // 해당 input 필드에 현재 날짜와 시간을 설정
        $("#pm_code_date").val(dateTimeString);
        console.log("pm_code_date = " + $("#pm_code_date").val());
    }

    // 스크립트가 실행될 때 setCurrentDateTime 함수 실행
    setCurrentDateTime();
});
/////////////////////////////////////공수///////////////////////////////////////////////
    $(document).ready(function(){
        var pmFullInput = document.getElementById("pm_full");
        var pmPartInput = document.getElementById("pm_part");
        var pmWorkloadInput = document.getElementById("pm_workload");

        function calculateWorkload() {
            var pmFullValue = parseFloat(pmFullInput.value) || 0;
            var pmPartValue = parseFloat(pmPartInput.value) || 0;
            var workload = pmFullValue + pmPartValue;
            pmWorkloadInput.value = workload;
        }

        if (pmFullInput && pmPartInput && pmWorkloadInput) {
            // 초기값 설정
            calculateWorkload();

            // 값 변경 이벤트에 대한 리스너 추가
            pmFullInput.addEventListener("input", calculateWorkload);
            pmPartInput.addEventListener("input", calculateWorkload);
        }
    });
/////////////////////////////////////담당자 모달창///////////////////////////////////////////////
$(document).ready(function(){
    var employees_pmmanager = /*[[${employees}]]*/ [];
    var selectedEmployeeData_pmmanager = {}; // 선택된 사원 데이터를 저장할 객체

    $("#searchInput_pmamanager").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#employeeList_pmmanager tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    $("#pm_nori_manager_Modal").on("show.bs.modal", function(){
        // 사원 목록을 순회하면서 클릭 이벤트를 바인딩합니다.
        $("#employeeList_pmmanager tr").each(function(){
            var row_pmmanager = $(this);

            row_pmmanager.off("click"); // 기존 이벤트 핸들러 제거
            row_pmmanager.on("click", function(){
                // 현재 선택된 행을 강조 표시하기 위해 이전에 선택된 행의 강조 표시를 제거합니다.
                $("#employeeList_pmmanager tr.selected").removeClass("selected");
                row_pmmanager.addClass("selected"); // 현재 행에 선택 클래스 추가

                // 클릭한 행에서 각 셀의 정보 가져오기
                var columns_pm = row_pmmanager.find("td");
                var name_pm = columns_pm.eq(2).text(); // 담당자 이름
                var dept_pm = columns_pm.eq(3).text(); // 부서

                // 선택된 사원 데이터를 selectedEmployeeData_pmmanager 변수에 저장
                selectedEmployeeData_pmmanager.name = name_pm; // 담당자 이름 저장
                selectedEmployeeData_pmmanager.dept = dept_pm; // 부서 저장
                
                // 가져온 사원 정보를 입력란에 표시
               $("#pm_nori_manager").val(selectedEmployeeData_pmmanager.name); // 담당자 이름
               $("#pm_nori_bu").val(selectedEmployeeData_pmmanager.dept); // 부서
               
               // 모달을 닫음
               $("#pm_nori_manager_Modal").modal("hide");
            });
        });
    });

    // 모달이 닫힐 때 선택된 행의 강조 표시를 제거하지만 입력된 데이터는 초기화하지 않습니다.
    $("#pm_nori_manager_Modal").on("hidden.bs.modal", function(){
        $("#employeeList_pmmanager tr.selected").removeClass("selected");
        // 입력된 데이터 초기화를 하지 않습니다.
    });
});

/////////////////////////////////////사업부/부서 모달창///////////////////////////////////////////////
$(document).ready(function(){
// 부서 정보 추출
var deptOptions = {};
$("#employeeList tr").each(function(){
    var dept = $(this).find("td:eq(3)").text(); // 부서 정보 추출
    deptOptions[dept] = true; // 중복 제거를 위해 객체의 속성으로 저장
});

// 부서 필터 select 요소에 옵션 추가
var deptFilter = $("#deptFilter");
for (var dept in deptOptions) {
    if (deptOptions.hasOwnProperty(dept)) {
        $("<option>").val(dept).text(dept).appendTo(deptFilter);
    }
}


// 필터링 옵션 변경
$('#deptFilter').change(function() {
    var selectedDept = $(this).val();
    if (selectedDept !== '') {
        $("#employeeList tr").each(function() {
            var dept = $(this).find("td:eq(3)").text(); // 각 행의 부서 정보 추출
            if (dept !== selectedDept) {
                $(this).hide(); // 선택된 부서가 아닌 행 숨기기
            } else {
                $(this).show(); // 선택된 부서와 일치하는 행 표시
            }
        });
    } else {
        $("#employeeList tr").show(); // 전체를 선택한 경우 모든 행 표시
    }
});

/////////////////////////시작일 완료일 개월수계산
// 시작일과 완료일 입력란의 변경 이벤트를 감지하여 기간을 자동으로 계산 (월 단위)
$("#pm_start_date, #pm_end_date").on("change", function() {
    var startDate = new Date($("#pm_start_date").val()); // 시작일
    var endDate = new Date($("#pm_end_date").val()); // 완료일

    // 시작일과 완료일이 모두 유효한 경우에만 기간을 계산하고 입력란에 표시
    if (!isNaN(startDate.getTime()) && !isNaN(endDate.getTime())) {
        var diffMonths = (endDate.getFullYear() - startDate.getFullYear()) * 12;
        diffMonths -= startDate.getMonth();
        diffMonths += endDate.getMonth();

        // 기간 입력란에 계산된 기간을 표시
        $("#pm_work_date").val(diffMonths);
    } else {
        // 시작일 또는 완료일이 유효하지 않은 경우 기간 입력란을 비움
        $("#pm_work_date").val("");
    }
});
});
/////////////////////////////////////엑셀파일 추가///////////////////////////////////////////////////
$(document).ready(function(){
    // + 버튼에 이벤트 처리 함수 연결
    $("#addExcelRowButton").click(function() {
        addTableRowExel('myTable_excel', 'tableSelect_excel');
    });

    // - 버튼에 이벤트 처리 함수 연결
    $("#deleteExcelRowButton").click(function() {
        deleteTableRowExel('myTable_excel');
    });

//엑셀파일 +/-
function addTableRowExel(tableID, radioGroupName) {
   console.log("addTableRowExel 실행됨")
    var table_excel = document.getElementById(tableID);

    // 새로운 행 생성
    var newRow_excel = table_excel.insertRow();

    // 첫 번째 셀에 라디오 버튼 추가
    var cell1_excel = newRow_excel.insertCell(0);
    var radioInput = document.createElement("input");
    radioInput.type = "radio";
    radioInput.name = radioGroupName;
    cell1_excel.appendChild(radioInput);

    // 두 번째 셀에 파일 선택 input 추가
    var cell2_excel = newRow_excel.insertCell(1);
    var fileInput_excel = document.createElement("input");
    fileInput_excel.type = "file";
    fileInput_excel.className = "form-control-file";
    cell2_excel.appendChild(fileInput_excel);
}

// 엑셀 테이블 행 삭제 함수
function deleteTableRowExel(tableID) {
    var table_excel = document.getElementById(tableID);
    var rowCount_excel = table_excel.rows.length;

    // 테이블의 각 행을 확인하여 체크된 라디오 버튼이 있는지 검사하고 삭제
    for (var i = 0; i < rowCount_excel; i++) {
        var row_excel = table_excel.rows[i];
        var radioInput = row_excel.cells[0].querySelector("input[type='radio']");
        if (radioInput.checked) {
            table_excel.deleteRow(i);
            rowCount_excel--;
            i--;
        }
    }
}
});
/////////////////////////////////////투입인력정보 모달창///////////////////////////////////////////////
$(document).ready(function(){
    var employees = /*[[${employees}]]*/ []; // 타임리프를 통해 employees 데이터를 가져옴
    var select = document.getElementById("deptSelect");
    var deptSet = new Set(); // 중복을 제거하기 위한 Set 생성
    
    $("#searchInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#employeeList tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
    // employees 리스트를 순회하며 dept_biz 값 추출하여 Set에 추가
    employees.forEach(function(employee) {
        deptSet.add(employee.dept_biz);
    });

    // Set에서 값을 꺼내어 option 요소 생성
    deptSet.forEach(function(dept) {
        var option = document.createElement("option");
        option.value = dept;
        option.text = dept;
        select.appendChild(option);
    });

    // 이미 추가된 사원들의 사번을 기억하는 배열
    var addedEmployeeIds = [];

    // 선택한 사원을 추가하는 함수
    function addSelectedEmployees() {
        var selectedEmployees = [];
        var duplicateDetected = false;

        // 기존에 추가된 사원들의 사번을 배열에 추가합니다.
        $("#myTable_sawon tbody tr").each(function(){
            var employeeId = $(this).find("td:eq(1)").text(); // 사번 가져오기
            addedEmployeeIds.push(employeeId);
        });

        $("#employeeList input[type='checkbox']:checked").each(function(){
            var row = $(this).closest("tr");
            var columns = row.find("td");
            var employeeData = [];
            columns.each(function(){
                employeeData.push($(this).text());
            });

            var id = employeeData[1]; // 사번을 기준으로 중복 여부 확인
            if (addedEmployeeIds.includes(id)) {
                duplicateDetected = true;
            } else {
                selectedEmployees.push(employeeData);
            }
        });

        // 테이블에 선택한 사원들을 추가합니다.
        var table = $("#myTable_sawon");
        $.each(selectedEmployees, function(index, value){
            var newRow = $("<tr>");
            // 첫 번째 셀에만 체크박스 추가
            $("<td>").html("<input type='checkbox' class='employee-checkbox'>").appendTo(newRow);
            $.each(value.slice(1), function(i, val){
                // 나머지 셀 추가
                $("<td>").html(val).appendTo(newRow);
            });
            newRow.appendTo(table);
            addedEmployeeIds.push(value[1]); // 사번을 addedEmployeeIds 배열에 추가
            console.log("addedEmployeeIds = "+addedEmployeeIds)
        });

        // 모달을 닫습니다.
        $("#myModal").modal("hide");
    }

    // '선택 추가' 버튼 클릭 시 선택된 사원을 테이블에 추가합니다.
    $("#addSelected").click(function(){
        addSelectedEmployees();
    });

    // 필터로 걸러진 사원만 전체 선택
    $('#selectAll').change(function() {
        var isChecked = $(this).prop('checked');
        $('#employeeList tr:visible input[type="checkbox"]').prop('checked', isChecked);
    });
    
    // removeSelectedSawon 버튼 클릭 시 선택된 사원 삭제
    $("#removeSelectedSawon").click(function(){
        $("#myTable_sawon input[type='checkbox']:checked").each(function(){
            var row = $(this).closest("tr");
            var id = row.find("td:eq(1)").text(); // 사번을 기준으로 삭제
            // 삭제 함수 호출
            removeAddedEmployee(id);
            // 행 삭제
            row.remove();
        });
    });

    // 추가된 사원 삭제 함수
    function removeAddedEmployee(employeeId) {
        // addedEmployees 배열에서 해당 사원의 인덱스를 찾습니다.
        var index = addedEmployees.indexOf(employeeId);
        if (index !== -1) {
            // 배열에서 해당 인덱스의 요소를 제거합니다.
            addedEmployees.splice(index, 1);
        }
    }

    // select 박스 선택시 미리보기 안보이게
    $('select').on('mousedown', function() {
        $(this).siblings('.placeholder').css('display', 'none');
    });
    
    // 저장 버튼에 이벤트 처리 함수 연결
    $("#saveButton").click(function() {
        saveDataToServer();
    });

    // 저장 함수 정의
    function saveDataToServer() {
        // FormData 생성
        var formData = new FormData();

        // 과제 정보 수집
        formData.append('pm_code', $('#pm_code_date').val());
        formData.append('pm_name', $('#pm_name').val());
        formData.append('pm_nori_manager', $('#pm_nori_manager').val());
        formData.append('pm_nori_bu', $('#pm_nori_bu').val());
        formData.append('pm_create_date', $('#pm_create_date').val());
        formData.append('pm_manager', $('#pm_manager').val());
        formData.append('pm_bu', $('#pm_bu').val());
        formData.append('pm_business', $('#pm_business').val());
        formData.append('pm_price', $('#pm_price').val());
        formData.append('pm_workload', $('#pm_workload').val());
        formData.append('pm_full', $('#pm_full').val());
        formData.append('pm_part', $('#pm_part').val());
        formData.append('pm_suggest_date', $('#pm_suggest_date').val());
        formData.append('pm_start_date', $('#pm_start_date').val());
        formData.append('pm_end_date', $('#pm_end_date').val());
        formData.append('pm_work_date', $('#pm_work_date').val());
        formData.append('pm_status', $('#pm_status').val());
        formData.append('pm_outline', $('#pm_outline').val());
        formData.append('pm_bego', $('#pm_bego').val());
        formData.append('comment', $('#comment').val());

        // 투입인력 정보 수집
        // 추가한 사원들의 사번을 formData에 추가
        console.log("add 시 addedEmployeeIds : "+addedEmployeeIds)
        addedEmployeeIds.forEach(function(employeeId) {
            formData.append('addedEmployeeIds', employeeId);
        });

        // 파일 업로드 처리
        var fileInputs = document.getElementById('pm_file').files;
        if (fileInputs.length > 0) {
            for (var i = 0; i < fileInputs.length; i++) {
                formData.append('pm_file', fileInputs[i]);
            }
        }
		
        // 콘솔 로그 출력 후 데이터 전송
        console.log("FormData: ", formData); // FormData 내용 확인
        sendData(formData); // 데이터 서버로 전송


        // Ajax를 이용하여 서버로 데이터 전송
        $.ajax({
            url: '/addTask',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                // 성공적으로 저장되었을 때의 동작
                console.log('데이터가 성공적으로 서버에 전송되었습니다.');
            },
            error: function(xhr, status, error) {
                // 에러 발생 시의 동작
                console.error('데이터 전송 중 오류가 발생했습니다:', error);
            }
        });
    }
});