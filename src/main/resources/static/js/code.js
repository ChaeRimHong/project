// 탭 클릭 이벤트 핸들러
document.querySelectorAll('.nav-tabs a').forEach(tab => {
    tab.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작 방지 (페이지 이동)
        // 탭의 href 속성값을 가져와서 해당 내용을 로드
        let target = this.getAttribute('href');
        loadTabContent(target);
    });
});
// 탭 내용 로드 함수
function loadTabContent(target) {
    // AJAX 요청을 보내고 응답을 받아서 해당 탭에 내용을 채움
    let xhr = new XMLHttpRequest();
    xhr.open('GET', target, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // 응답을 받았을 때 탭 내용 갱신
            document.querySelector('.tab-content').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}