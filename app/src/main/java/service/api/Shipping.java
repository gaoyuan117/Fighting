
package service.api;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Shipping extends BaseEntity
{

	@SerializedName("data")
	public Data[] Data;

	public static class Data
	{
		public String id;
		
		public String code;
		
		public String title;
		
		public String website;

		public String mobile;
		
		public BigDecimal price;

		public String sort;

		public String create_time;

		public String update_time;

		public String status;
	}
}
