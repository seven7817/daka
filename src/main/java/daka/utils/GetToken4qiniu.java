package daka.utils;

import com.qiniu.util.Auth;

public class GetToken4qiniu {
	private static String accessKey = "WOWaqkdgA2lxktWjNggyfuOrqHqxIaxBzNTZH-NI";
	private static String secretKey = "jObttaw2DehzCKLES6kjtUCcomgP-WbpAP1jT3gA";
	private static String bucket = "daka";
	
	public static String getSimpleToken() {
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		System.out.println(upToken);
		return upToken;
	}
}
