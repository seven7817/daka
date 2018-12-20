package daka.controller.exception;

import daka.controller.enums.ResultEnum;

public class WrongMailException extends RuntimeException {
	private Integer code ;
	public WrongMailException(ResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
