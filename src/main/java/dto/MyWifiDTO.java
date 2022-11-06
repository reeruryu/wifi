package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyWifiDTO {
	private int ID;
	private String LAT;
	private String LNT;
	private String INQUIRY_DATE; 

}
