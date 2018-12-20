package daka.enums;

public enum ResultEnum {
	UNKONW_ERROR(-1,"未知错误"),
	SUCCESS(0,"操作成功"),
	MAIL_WRONG(1,"邮箱错误"),
	
	
	
	
	;
	private Integer code ;
	private String msg;
	
	
	ResultEnum(Integer code ,String msg){
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
}
