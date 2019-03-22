package daka.exception;

import daka.enums.ResultEnum;

public class MyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code ;
	private Object object;
	public MyException(ResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}
	public MyException(ResultEnum resultEnum,Object object) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
		this.object = object;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
