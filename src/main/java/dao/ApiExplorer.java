package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dto.ReturnVo;
import dto.WifiDTO;

public class ApiExplorer {
	public int loadWifi(int start, int end) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		urlBuilder.append("/" + URLEncoder.encode("565256654c68326a31303569436b6a4c", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start), "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end), "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다. */
		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
		String str = sb.toString();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonObject rootob = element.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject();
		
		Gson g = new Gson();
		
		ReturnVo row = g.fromJson(rootob, ReturnVo.class);
		
		return setWifiDTO(row);
		 
	}
	
	public int setWifiDTO(ReturnVo row) {
		WifiDAO wifiDAO = new WifiDAO();
		List<WifiDTO> wifiList = new ArrayList();
		for (int i = 0; i < row.getRow().size(); i++) {
			WifiDTO wifi = new WifiDTO();
			wifi.setX_SWIFI_MGR_NO(row.getRow().get(i).getX_SWIFI_MGR_NO());
            wifi.setX_SWIFI_WRDOFC(row.getRow().get(i).getX_SWIFI_WRDOFC());
            wifi.setX_SWIFI_MAIN_NM(row.getRow().get(i).getX_SWIFI_MAIN_NM());
            wifi.setX_SWIFI_ADRES1(row.getRow().get(i).getX_SWIFI_ADRES1());
            wifi.setX_SWIFI_ADRES2(row.getRow().get(i).getX_SWIFI_ADRES2());
            wifi.setX_SWIFI_INSTL_FLOOR(row.getRow().get(i).getX_SWIFI_INSTL_FLOOR());
            wifi.setX_SWIFI_INSTL_TY(row.getRow().get(i).getX_SWIFI_INSTL_TY());
            wifi.setX_SWIFI_INSTL_MBY(row.getRow().get(i).getX_SWIFI_INSTL_MBY());
            wifi.setX_SWIFI_SVC_SE(row.getRow().get(i).getX_SWIFI_SVC_SE());
            wifi.setX_SWIFI_CMCWR(row.getRow().get(i).getX_SWIFI_CMCWR());
            wifi.setX_SWIFI_CNSTC_YEAR(row.getRow().get(i).getX_SWIFI_CNSTC_YEAR());
            wifi.setX_SWIFI_INOUT_DOOR(row.getRow().get(i).getX_SWIFI_INOUT_DOOR());
            wifi.setX_SWIFI_REMARS3(row.getRow().get(i).getX_SWIFI_REMARS3());
            wifi.setLAT(row.getRow().get(i).getLAT());
            wifi.setLNT(row.getRow().get(i).getLNT());
            wifi.setWORK_DTTM(row.getRow().get(i).getWORK_DTTM());
            
            wifiList.add(wifi);
		}
		wifiDAO.insertWifi(wifiList);
		
		return wifiList.size();
		
	}
	
}