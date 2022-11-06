<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.ApiExplorer" %>
<%@ page import="dto.ReturnVo" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
<%
	ApiExplorer apiExplorer = new ApiExplorer();
	int tot = 0;
	for (int i = 0; i < 15; i++) {
		tot += apiExplorer.loadWifi(10 * i + 1, 10 * i + 999);
	}
	System.out.println(tot);
	
%>
<center>
	<h2><%=tot%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h2>
	<a href="../main.jsp">홈으로 가기</a>
</center>
</body>
</html>