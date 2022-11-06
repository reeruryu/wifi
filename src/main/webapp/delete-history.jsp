<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.MyWifiDAO" %>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<%!
	    String id;
	%>
	<%		
		id = request.getParameter("id");
		
		MyWifiDAO myWifiDAO = new MyWifiDAO();
		myWifiDAO.deleteMyWifi(Integer.parseInt(id));
		
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href='history.jsp'");
		script.println("</script>");
	%>

</body>
</html>