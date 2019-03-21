package daka.utils;

import daka.model.Result;

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月20日 下午8:34:44
 *
 */
public class ResultUtil {
	public static  <T> Result<T> success(T data) {
		Result<T> result = new Result<T>();
		result.setCode(0);
		result.setMsg("操作成功");
		result.setData(data);
		return result;
	}
	public static Result success() {
		return success(null);
	}
	public static Result error(Integer code,String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
	
}
