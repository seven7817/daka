package daka.enums;

public enum ResultEnum {
	UNKONW_ERROR(-1,"未知错误"),
	SUCCESS(0,"操作成功"),
	MAIL_WRONG(1,"邮箱错误"),
	REGISTER_WRONG_FOR_CODE(2,"注册失败，请检查验证码是否正确"),
	REGISTER_WRONG_FOR_EXIST(3,"注册失败，该账户已存在"),
	LOGIN_WRONG(4,"登录失败，请检查账户密码是否正确"),
	GET_EMAIL_WRONG_FOR_NOT_EXIST(5,"找回密码失败，该邮箱不存在"),
	MODIFY_PASSWORD_WRONG_FOR_CODE(6,"修改密码失败，请检查验证码是否正确"),
	CODE_INVALID(7,"验证码以失效，请重新发送验证码"),
	PASSWORD_REPETITION(8,"不能改与旧密码一样的密码"),
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
