package service.api;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;


public class MyWallet extends BaseEntity
{
	@SerializedName("data")
	public Data Data;
	
	public static class Data
	{
		public String uid;
		
		public BigDecimal amount;

		public BigDecimal freeze_amount;
		
	}
}
