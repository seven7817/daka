package daka.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import daka.model.Result;
import daka.model.User;
import daka.utils.GetVerificationCode;
import daka.utils.SendMail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月18日 上午5:23:31
 *
 */
@Controller
public class test {
	String verificationCode = "";
	Result result = new Result();
	@GetMapping(value="/",produces = "application/json;charset=utf-8")
	public @ResponseBody String test1() {
		System.out.println("bbbbbbbb");
		
		User user = new User();
		user.setName("孙政");
		user.setPassword("123");
		JSONArray json = JSONArray.fromObject(user);
		return json.toString();
	}
	@PostMapping(value="/getEmail",produces = "application/json;charset=utf-8")
	public @ResponseBody String getEmail(@RequestBody String Email) {
		System.out.println(Email);	
		JSONObject jsonobject = JSONObject.fromObject(Email);
		verificationCode = GetVerificationCode.getCode();
		if(SendMail.send( jsonobject.getString("Email"),"打卡系统注册邮箱验证","尊敬的用户：您正在进行账户注册，验证码："+verificationCode+"，请及时输入验证码。若非本人操作，请忽视此邮件。"))
		{
			result.setCode("success");
		}
		else {
			result.setCode("mail is wrong");
		}
		JSONArray json = JSONArray.fromObject(result);
		return json.toString();
	}
	
}
