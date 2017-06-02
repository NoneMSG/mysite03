<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%pageContext.setAttribute( "newLine", "\n");%>
<!doctype html>
<html>
<head>
<title>${mainInfo.title }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/include/header.jsp" />
		
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile" src="${pageContext.request.contextPath }${mainInfo.profilepath}">
					<br>
					<h2>${mainInfo.welcome } </h2>
					
					<p>
						${fn:replace(mainInfo.description, newLine, "<br>") }
						<br><br>
						<a href="${pageContext.servletContext.contextPath }/guestbook/list">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" >
			<c:param name="menu" value="main"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" >
		</c:import>
	</div>
</body>
</html>