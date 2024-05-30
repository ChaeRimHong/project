// $(documnt).ready(function(){
//   // 페이지가 로드될 때마다 직원 데이터를 가져오는 함수 호출
//   fetchEmployeeData();
// });

// //직원 데이터를 가져와서 표시하는 함수
// function fetchEmployeeData(){
//   $.get("/select_maintable", function(data){
//     //가져온 데이터를 테이블에 삽입
//     $('#employeeTableBody').html(data);
//   }).fail(function(){
//     console.error('Failed to fetch employee data.');
//   })
// }

var pageLoaded = false;

// 페이지 로드 시에만 한 번 데이터를 가져오도록 설정
$(document).ready(function(){
  if (!pageLoaded && $('#employeeTableBody').children().length === 0) {
    getPage(1); // 페이지 로드 시에 데이터를 가져오는 코드
    //pageLoaded = true;
  }
});

// 검색 기능
$(document).ready(function(){
  $(".search-form").submit(function(event){
      event.preventDefault();
      var searchKeyword = $("input[name='search']").val().trim(); // 검색어를 "search"라는 name 속성을 가진 input에서 가져옴
      if(searchKeyword === ""){
          return;
      }
      $.post("/searchTasks", { keyword: searchKeyword }, function(data){
          // 검색 결과를 받아서 페이지 전체를 업데이트하는 것이 아니라, 과제 목록을 업데이트
          $("body").html(data);
      }).fail(function(){
          console.error('Failed to fetch employee data.');
      });
  });
});

// 과제 목록 업데이트 함수
function updateTaskList(data) {
  // 받은 데이터로 과제 목록을 업데이트
  var taskList = JSON.parse(data); // 데이터를 JSON 형식으로 파싱
  $('#taskTableBody').empty(); // 기존의 과제 목록을 지움
  
  for (var i = 0; i < taskList.length; i++) {
    var task = taskList[i];
    // 과제 목록에 새로운 행 추가
    var row = "<tr>" +
              "<td>" + task.pm_code + "</td>" +
              "<td>" + task.pm_name + "</td>" +
              "<td>" + task.pm_nori_manager + "</td>" +
              "<td>" + task.pm_nori_bu + "</td>" +
              "<td>" + task.pm_create_date + "</td>" +
              "<td>" + task.pm_manager + "</td>" +
              "<td>" + task.pm_bu + "</td>" +
              "<td>" + task.pm_business + "</td>" +
              "<td>" + task.pm_price + "</td>" +
              "<td>" + task.pm_workload + "</td>" +
              "<td>" + task.pm_full + "</td>" +
              "<td>" + task.pm_part + "</td>" +
              "<td>" + task.pm_suggest_date + "</td>" +
              "<td>" + task.pm_start_date + "</td>" +
              "<td>" + task.pm_end_date + "</td>" +
              "<td>" + task.pm_work_date + "</td>" +
              "<td>" + task.pm_status + "</td>" +
              "<td>" + task.pm_outline + "</td>" +
              "<td>" + task.pm_bego + "</td>" +
              "<td>" + task.pm_file + "</td>" +
              "<td>" + task.pm_confirmed + "</td>" +
              "<td>" + task.pm_save_id + "</td>" +
              "<td>" + task.pm_save_sa + "</td>" +
              "<td>" + task.pm_save_name + "</td>" +
              "<td>" + task.pm_file_path + "</td>" +
              "</tr>";
    $('#taskTableBody').append(row);
  }
}


function getPage(pageNumber) {
    // Ajax 요청을 보냄
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/getData?page=' + pageNumber, true);
    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 400) {
            // 응답이 성공적으로 도착하면 테이블 내용을 업데이트
            var response = xhr.responseText;
            // response를 이용하여 테이블 업데이트 등의 작업 수행
            $('tbody').html(response);
        } else {
            console.error('Error: ' + xhr.status);
        }
    };
    xhr.onerror = function() {
        console.error('Request failed');
    };
    xhr.send();
}