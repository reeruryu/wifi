package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.MyWifiDTO;

public class MyWifiDAO {
	private String url;
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
	
	public MyWifiDAO() {
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
	
	public void insertMyWifi(Map<String, String> map) {	
		try {
			String sql = "insert into my_wifi (LAT, LNT, INQUIRY_DATE)" +
                    "values (?, ?, now()); ";
			
			String lat = map.get("LAT");
			System.out.println(lat);
			String lnt = map.get("LNT");
			System.out.println(lnt);
			ps = con.prepareStatement(sql);
			ps.setString(1, lat);
            ps.setString(2, lnt);
				
            
	        int affected = ps.executeUpdate();
            if (affected > 0) {
            	System.out.println("insertMyWifi success");
               	} else {
               		System.out.println("insertMyWifi fail");
                }
            System.out.println(affected);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
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
	
	public List<MyWifiDTO> selectMyWifi() {
		List<MyWifiDTO> myWifiList = new ArrayList<>();
        try {
        	String sql = "select * from my_wifi order by ID desc;";
        	ps = con.prepareStatement(sql);
        	rs = ps.executeQuery();
			
        	while(rs.next()){
        		MyWifiDTO myWifi = new MyWifiDTO();
        		myWifi.setID(rs.getInt("ID"));
				myWifi.setLAT(rs.getString("LAT"));
				myWifi.setLNT(rs.getString("LNT"));
				myWifi.setINQUIRY_DATE(rs.getString("INQUIRY_DATE"));
	            myWifiList.add(myWifi);
	            }
        	} catch (SQLException e) {
        		e.printStackTrace();
        		}
         
		return myWifiList;
	}
	
	public void deleteMyWifi(int id) {
		System.out.println(id);
		try {
            String sql = "delete from my_wifi where id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected > 0) {
            	System.out.println("deleteMyWifi success");
            	} else {
               		System.out.println("deleteMyWifi fail");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

}
