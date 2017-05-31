<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
//js 에서 서버로부터 데이터를 보내고 받기 위해 작성
// 메일 check 버튼을 누르면 js 변수에 값을 저장시키고
$(function() {
	$("#check-button").click(function(){
		var email = $("#email").val();
		if(email==""){
			return;
		}
		
		//ajax 통신 
		$.ajax( {
		    url : "/mysite03/user/api/checkemail?email="+email, //해당 페이지는 메일 체크하는 페이지에 get방식으로 데이터를 보낸다.
		    type: "get",
		    dataType: "json",
		    data: "",
		    success: function( response ){
		    	if(response.data==true){
		    		alert("이미 존재하는 email 입니다.");
		    	}else{
		    		alert("사용가능한 email입니다.")
		    	}
		    },
		    error: function( jqXHR, status, error ){
		       console.error( status + " : " + error );
		    }
		 });
	});
});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input id="check-button" type="button" value="id 중복체크">
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>