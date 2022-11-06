package dao;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.WifiDTO;

public class Test {

	public static void main(String[] args) {
		
		/*
		 * int tot = 0; ApiExplorer ae = new ApiExplorer(); try { tot = ae.loadWifi(1,
		 * 30); } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * System.out.println(tot);
		 */
		 
		WifiDAO dao = new WifiDAO();
		Map<String, String> map = new HashMap();
		map.put("LAT", "37.5544069");
		map.put("LNT", "126.8998666");
		int cnt=0;
		
		List<WifiDTO> list = dao.selectWifi(map);
		for (WifiDTO dto: list) {
			if (cnt == 5)
				break;
			System.out.println(dto.getKM() +" "+ dto.getLAT() + " " + dto.getLNT());
			cnt++;
		}
		
//		MyWifiDAO myWifiDAO = new MyWifiDAO();
//		myWifiDAO.insertMyWifi(map);
//		System.out.println("insert!");
		 
	}

}
