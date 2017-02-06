package service.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("serial")
public class WalletRecordPageResult extends BaseEntity implements Serializable
{
	@SerializedName("data")
	public Data Data;
	
	public static class Data
	{
		@SerializedName("total")
		public int Total;
		
		@SerializedName("result")
		public WalletRecordItem[] Result;
	}
}
