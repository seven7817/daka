package daka.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import daka.model.Result;
import daka.utils.ResultUtil;
import net.sf.json.JSONArray;

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月20日 下午8:58:20
 *
 */
@ControllerAdvice
public class ExceptionHandle {
	@ExceptionHandler(value=Exception.class)
	public @ResponseBody String handle(Exception e) {
		if(e instanceof MyException) {
			MyException mailException = (MyException)e;
			System.out.println("捕获到异常了");
			JSONArray json = JSONArray.fromObject(ResultUtil.error(mailException.getCode(), mailException.getMessage()));
			return json.toString();
		}
		else {
			e.printStackTrace();
			return JSONArray.fromObject(ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(),ResultEnum.UNKONW_ERROR.getMsg())).toString();
		}
	}

}
