
package service.api;

import com.google.gson.annotations.SerializedName;

public class Product
{

	public int code;

	public String message;

	@SerializedName("data")
	public ProductItem Data;
}
