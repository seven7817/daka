package daka.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import daka.enums.ResultEnum;
import daka.exception.MailException;
import daka.model.Result;
import daka.utils.ResultUtil;

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月20日 下午8:58:20
 *
 */
@ControllerAdvice
public class ExceptionHandle {
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result handle(Exception e) {
		if(e instanceof MailException) {
			MailException mailException = (MailException)e;
			return ResultUtil.error(mailException.getCode(), mailException.getMessage());
		}
		else {
			e.printStackTrace();
			return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMsg());
		}
	}

}
