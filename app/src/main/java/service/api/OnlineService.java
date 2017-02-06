package service.api;


public class OnlineService extends BaseEntity
{
	public Data data;
	
	public static class Data
	{
		public String phone;
		
		public String[] hx_accoun;
	}
}
