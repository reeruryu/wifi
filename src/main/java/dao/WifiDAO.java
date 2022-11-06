package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import dto.WifiDTO;

public class WifiDAO {
	private String url;
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
	
	public WifiDAO() {
		url = "jdbc:mariadb://localhost:3306/wifi";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(url, "wifi", "wifi");
			System.out.println("db success");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertWifi(List<WifiDTO> wifiList) {
		try {
			for (WifiDTO wifi : wifiList) {
				String sql = "insert into wifi (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC,"
						+ "X_SWIFI_MAIN_NM, X_SWIFI_ADRES1,X_SWIFI_ADRES2,"
						+ "X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY,"
						+ "X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR,"
						+ " X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)"
						+ "values (?, ?, ?, ?, ?," +
		                " ?, ?, ?, ?, ?," +
		                " ?, ?, ?, ?, ?, ?); ";
				ps = con.prepareStatement(sql);
				ps.setString(1, wifi.getX_SWIFI_MGR_NO());
	            ps.setString(2, wifi.getX_SWIFI_WRDOFC());
	            ps.setString(3, wifi.getX_SWIFI_MAIN_NM());
	            ps.setString(4, wifi.getX_SWIFI_ADRES1());
	            ps.setString(5, wifi.getX_SWIFI_ADRES2());
	            ps.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
	            ps.setString(7, wifi.getX_SWIFI_INSTL_TY());
	            ps.setString(8, wifi.getX_SWIFI_INSTL_MBY());
	            ps.setString(9, wifi.getX_SWIFI_SVC_SE());
	            ps.setString(10, wifi.getX_SWIFI_CMCWR());
	            ps.setString(11, wifi.getX_SWIFI_CNSTC_YEAR());
	            ps.setString(12, wifi.getX_SWIFI_INOUT_DOOR());
	            ps.setString(13, wifi.getX_SWIFI_REMARS3());
	            ps.setString(14, wifi.getLAT());
	            ps.setString(15, wifi.getLNT());
	            ps.setString(16, wifi.getWORK_DTTM());
	            
	            int affected = ps.executeUpdate();
            	if (affected > 0) {
                	System.out.println("insertWifi success");
                } else {
                	System.out.println("insertWifi fail");
                }
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
        }
		
	}
	
	public List<WifiDTO> selectWifi(Map<String, String> map) {
		
		List<WifiDTO> wifiList = new ArrayList<>();
        try {
        	String sql = "select * from wifi";
        	ps = con.prepareStatement(sql);
        	rs = ps.executeQuery();
			
        	while(rs.next()){
        		WifiDTO wifi = new WifiDTO();
        		String lat = rs.getString("LAT");
				String lnt = rs.getString("LNT");
				double km = Double.parseDouble(
	            		distance(Double.parseDouble(map.get("LAT")), 
	            				Double.parseDouble(map.get("LNT")),
	            				Double.parseDouble(lat), Double.parseDouble(lnt),
	            				"kilometer"));
	            wifi.setKM(km);
	            wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
	            wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
	            wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
	            wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
	            wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
	            wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
	            wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
	            wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
	            wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
	            wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
	            wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
	            wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
	            wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
	            wifi.setLAT(lat);
	            wifi.setLNT(lnt);
	            wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));
	            wifiList.add(wifi);
	            }
        	} catch (SQLException e) {
        		e.printStackTrace();
        		}
        
        MyWifiDAO myWifiDAO = new MyWifiDAO();
        myWifiDAO.insertMyWifi(map);
        
        wifiList.sort(Comparator.comparing(WifiDTO::getKM));
        
		return wifiList;
		
	}
	
	public static String distance(double lat1, double lnt1, double lat2, double lnt2, String unit){
        double theta = lnt1 - lnt2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit.equals("kilometer")) {
            dist = dist * 1.609344;
        } else {
        	
        }
        return String.format("%.4f", dist);
    }
	
	public static double deg2rad(double deg){
        return (deg * Math.PI / 180.0);
    }
	
    public static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

}
