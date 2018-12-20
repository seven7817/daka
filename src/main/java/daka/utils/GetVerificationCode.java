package daka.utils;

public class GetVerificationCode {
	public static String getCode() {
		String code = "";
		for( int i = 0 ; i <4; i++) {
			code += (int)(Math.random()*10);
		}
		return code;
	}
}
