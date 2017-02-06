package service.api;

import com.google.gson.annotations.SerializedName;


public class MessageDetail extends BaseEntity
{
	@SerializedName("data")
	public MessageItem Data;
	
}
