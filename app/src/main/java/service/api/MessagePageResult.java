package service.api;

import com.google.gson.annotations.SerializedName;


public class MessagePageResult extends BaseEntity
{
	@SerializedName("data")
	public Data Data;
	
	public static class Data
	{
		@SerializedName("total")
		public int Total;
		
		@SerializedName("result")
		public MessageItem[] Result;
	}
}
