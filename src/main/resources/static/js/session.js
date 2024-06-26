$(document).ready(function () {
    console.log("Document is ready.");
    $.ajax({
      url: "/session",
      method: "GET",
      success: function (sessionModel) {
        console.log("Session data : ", sessionModel);
        if (sessionModel) {
          $("#userImage").attr(
            "src",
            "data:image/jpeg;base64," + sessionModel.pic
          );
          $("#userName").text(sessionModel.name_kor);
          $("#userAuthority").text(sessionModel.authority);
        } else {
          // 로그인 페이지로 리다이렉트
          window.location.href = "/login";
        }
      },
      error: function () {
        console.error("Failed to fetch session data.");
        // 에러 처리
      },
    });
  });
  