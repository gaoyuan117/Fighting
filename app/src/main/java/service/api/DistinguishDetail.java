
package service.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DistinguishDetail extends BaseEntity implements Serializable
{
	@SerializedName("data")
    public DistinguishItem Data;
}
