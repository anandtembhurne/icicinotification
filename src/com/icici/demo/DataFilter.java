package com.icici.demo;

import org.apache.ignite.Ignite;



public class DataFilter {

	public static void printDate(String data) {
		System.out.println("oignl string=" + data);
		String[] split = data.split(",");
		for (String string : split) {
			System.out.println("kafka data =" + string);
		}

	}

	public static void sendData(String data, Ignite ignite) {
		IgniteStoredData instance = IgniteStoredData.getInstance(ignite);
                data=data.replaceAll("'","").trim();
               // System.out.println("user data="+data);
		String[] split = data.split(",");
	//System.out.println("length ="+split.length);
		try{
		if (split.length == 6) {

			String gcmno = split[0];
			long gcm = Long.parseLong(gcmno);

			String appid = split[1].trim();
			long app = Long.parseLong(appid);
			String deptid = split[2].trim();
			long dept = Long.parseLong(deptid);

			String mobileno = split[3].trim();
			long mobile = Long.parseLong(mobileno);

			String userid = split[4].trim();
			String message = split[5];

			instance.insertMessageData(mobile, gcm, app, dept, userid, message);
		}
		}catch(Exception e){
			System.out.println("Filter Exception :"+e);
		}

	}
        
	public static void sendMasterData(String data, Ignite ignite) {
		IgniteStoredData instance = IgniteStoredData.getInstance(ignite);
                data=data.replaceAll("'","").trim();
                System.out.println("master  data="+data);
		String[] split = data.split(",");
	//System.out.println("length ="+split.length);
		try{
		if (split.length == 6) {

			String gcmno = split[0];
			long gcm = Long.parseLong(gcmno);

//			String appid = split[1].trim();
//			long app = Long.parseLong(appid);
//			String deptid = split[2].trim();
//			long dept = Long.parseLong(deptid);

			String mobileno = split[3].trim();
			long mobile = Long.parseLong(mobileno);

			String userid = split[4].trim();
			//String message = split[5];

			instance.insertMasterData(mobile, gcm,userid);
		}
		}catch(Exception e){
			System.out.println("Filter Exception :"+e);
		}

	}

}
