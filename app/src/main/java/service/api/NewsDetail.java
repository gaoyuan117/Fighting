package service.api;

import com.google.gson.annotations.SerializedName;


public class NewsDetail extends BaseEntity
{
	@SerializedName("data")
	public NewsItem Data;
	
}
