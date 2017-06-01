<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="a" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
					<input type="hidden" value="search">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>	
					
					<c:forEach items="${blist }" var="bvo" varStatus="status">		
					<tr>
						<td>[${status.count }]</td>
						<c:choose>
							<c:when test="${bvo.depth > 0 }">
								<td class="left" style="padding-left:${20*bvo.depth }px">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
									<a href="${pageContext.servletContext.contextPath }/board/view/${bvo.no}">${bvo.title }</a>
								</td>	
							</c:when>
							<c:otherwise>
								<td class="left">
								<a href="${pageContext.servletContext.contextPath }/board/view/${bvo.no}">${bvo.title }</a>
								</td>
							</c:otherwise>
						</c:choose>
						<td>${bvo.userName }</td>
						<td>${bvo.hit }</td>
						<td>${bvo.regDate }</td>
						<td>
							<c:choose>
								<c:when test="${not empty authUser && authUser.no == bvo.userNo}">
									<a href="${pageContext.servletContext.contextPath }/board/delete/${bvo.no}" class="del">삭제</a>
								
								</c:when>
								<c:otherwise>
									 &nbsp;
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li class="selected">3</li>
						<li><a href="">4</a></li>
						<li><a href="">5</a></li>
						<li><a href="">▶</a></li>
					</ul>
				</div>				
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>