package service.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ShopFavoritePageResult extends BaseEntity implements Serializable
{
	public Data data;
	
	public static class Data
	{
		@SerializedName("total")
		public int Total;
		
		@SerializedName("result")
		public ShopFavorite[] Result;
	}
}
