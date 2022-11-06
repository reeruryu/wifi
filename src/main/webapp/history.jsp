<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.MyWifiDTO" %>
<%@ page import="dao.MyWifiDAO" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
ul {
    list-style: none;
    margin: 0;
    padding : 10;
    
}

li {
    margin-right: 5px;
    border: 0;
    float: left;
}

div {
	clear: both;
}

* {
	padding : 0;
	margin-bottom : 15px;
}

#info {
	font-family: Arial, Helvetica, sans-serif;
  	border-collapse: collapse;
  	width: 100%;
}

#info td, #info th {
  	border: 1px solid #ddd;
  	padding: 8px;
}

#info tr:nth-child(even){background-color: #f2f2f2;}

#info tr:hover {background-color: #ddd;}

#info th {
	padding-top: 12px;
	text-align: center;
  	padding-bottom: 12px;
  	background-color: #04AA6D;
  	color: white;
}

button {
	margin-bottom : 0;
}
</style>
<script type="text/javascript">
	function del(id) {
		location.href="delete-history.jsp?id="+id;
	}
</script>
</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<div>
		<ul>
			<li><a href="../">홈</a></li>
			<li>|</li>
			<li><a href="../history.jsp">위치 히스토리 목록</a></li>
			<li>|</li>
			<li><a href="../load-wifi.jsp">Open API 와이파이 정보 가져오기</a></li>
		</ul>
	</div>
	<div>
    	<table id="info">
    		<thead>
		   		<tr>
			   		<th>ID</th>
			   		<th>X좌표</th>
			   		<th>Y좌표</th>
			   		<th>조회일자</th>
			   		<th>비고</th>
		   		</tr>
	    	</thead>
	    	<tbody>
	   		<%
	   			MyWifiDAO myWifiDAO = new MyWifiDAO();
	   			List<MyWifiDTO> myWifiList = myWifiDAO.selectMyWifi();
    			
	    		int size = myWifiList.size();
    			for (int i = 0 ; i < myWifiList.size(); i++) {
    		%>
    			<tr>
    				<td><%= size--%></td>
    				<td><%= myWifiList.get(i).getLAT()%></td>
    				<td><%= myWifiList.get(i).getLNT()%></td>
    				<%
    					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myWifiList.get(i).getINQUIRY_DATE());
    					String inquiry_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
    				%>
    				<td><%= inquiry_date%></td>
    				<td style="text-align: center">
    					<button type="button" onclick="del(<%= myWifiList.get(i).getID()%>)">삭제</button>
    				</td>
    		<% 
    			}
    		%>    		
    			</tr>
	    	</tbody>
    	</table>
    </div>
</body>
</html>