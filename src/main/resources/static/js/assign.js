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
          $("#assignTableBody").html(data); // Update only the task table body
      }).fail(function(){
          console.error('Failed to fetch task data.');
      });
  });
});
