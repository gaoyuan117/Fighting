
package service.api;

import com.google.gson.annotations.SerializedName;

public class ShippingSellerDetail extends BaseEntity
{

	@SerializedName("data")
	public Data Data;

	public static class Data
	{
		public ShippingItem seller_shipping;
	}
}
