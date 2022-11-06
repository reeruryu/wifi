<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.WifiDTO" %>
<%@ page import="dao.WifiDAO" %>
<%@ page import="java.util.*" %>
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
</style>
<script>
	function getLocation() {
		if(navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(
					function successCallback(position) {
                        var lat = position.coords.latitude;
                        var lng = position.coords.longitude;
                        document.getElementById("lat").value=lat;
                        document.getElementById("lnt").value=lng;
                    }, function errorCallback() {
                    	alert("위도 경도 불러오기 실패");
                    }, options = {
                        enableHighAccuracy: true,
                        timeout: 1500,
                        maximumAge: 5000
                    }
                    )
            } else {
                alert("Geolocation API를 지원하지 않습니다.");
            }
        }
</script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
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
    	<form action="/" method="get">
    		LAT : <input type="text" id="lat" name="lat" value="0.0"> , 
    		LNT : <input type="text" id="lnt" name="lnt" value="0.0">
    		<input type="button" id="location" onclick="getLocation()" value="내 위치 가져오기"/>
    		<input type="submit" value="근처 WIFI 정보 보기" />
    		
    		<table id="info">
    			<thead>
		    		<tr>
			    		<th>거리(Km)</th>
				   		<th>관리번호</th>
				    	<th>자치구</th>
				    	<th>와이파이명</th>
				   		<th>도로명주소</th>
				   		<th>상세주소</th>
				   		<th>설치위치(층)</th>
				   		<th>설치유형</th>
				   		<th>설치기관</th>
				   		<th>서비스구분</th>
				   		<th>망종류</th>
				   		<th>설치년도</th>
				   		<th>실내외구분</th>
				   		<th>WIFI접속환경</th>
				   		<th>X좌표</th>
				   		<th>Y좌표</th>
				   		<th>작업일자</th>
		    		</tr>
	    		</thead>
	    		<tbody>
	    		<%!
	    			String lat;
	    			String lnt;
	    		%>
	    		<%
	    			WifiDAO wifiDAO = new WifiDAO();
	    			List<WifiDTO> wifiList = null;
	    			
	    			lat = request.getParameter("lat");
    				lnt = request.getParameter("lnt");
    				Map<String, String> map = new HashMap();
    				map.put("LAT", lat);
    				map.put("LNT", lnt);
	    			if (lat == null && lnt == null) {
	    		 %>
	    			<td colspan="17" style="text-align: center">위치 정보를 입력한 후에 조회해 주세요.</td>

	    		<% } else { 
	    			wifiList = wifiDAO.selectWifi(map);
    				
    				for (int i = 0 ; i < wifiList.size(); i++) {
    					if (i == 20) {
    						break;
    					}
    			%>
    				<tr>
    					<td><%= wifiList.get(i).getKM()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_MGR_NO()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_WRDOFC()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_MAIN_NM()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_ADRES1()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_ADRES2()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_INSTL_FLOOR()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_INSTL_TY()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_INSTL_MBY()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_SVC_SE()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_CMCWR()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_CNSTC_YEAR()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_INOUT_DOOR()%></td>
    					<td><%= wifiList.get(i).getX_SWIFI_REMARS3()%></td>
    					<td><%= wifiList.get(i).getLAT()%></td>
    					<td><%= wifiList.get(i).getLNT()%></td>
    					<td><%= wifiList.get(i).getWORK_DTTM()%></td>
    			<% }}%>    		
    			</tr>	
	    		</tbody>
    		</table>
    	</form>
	</div>
</body>
</html>