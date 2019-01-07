package daka.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;



import daka.model.User;
import daka.service.LoginRegisterService;
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
	@Autowired
	LoginRegisterService loginRegisterService ;
	
	String verificationCode = "";
	@GetMapping(value="/",produces = "application/json;charset=utf-8")
	public @ResponseBody String test1() {
		System.out.println("bbbbbbbb");
		User user = new User();
		user.setEmail("孙政");
		user.setPassword("123");
		JSONArray json = JSONArray.fromObject(user);
		return json.toString();
	}
	@PostMapping(value="/getEmail",produces = "application/json;charset=utf-8")
	public void getEmail(@RequestBody String Email) {
		System.out.println(Email+"controller里面的getEmail");	
		String operation = JSONObject.fromObject(Email).getString("operation");
		//表示注册的时候用验证码
		if(operation.equals("0")) {			
			loginRegisterService.getEmail(JSONObject.fromObject(Email).getString("Email"));
		}else if (operation.equals("1")){ //表示找回密码的时候的验证码
			loginRegisterService.getEmailWhereFindPassword(JSONObject.fromObject(Email).getString("Email"));
		}else if(operation.equals("2")) { //表示修改密码的时候的验证码
			loginRegisterService.getEmailWhereModifyPassword(JSONObject.fromObject(Email).getString("Email"));
		}
	}
	@PostMapping(value="/register",produces = "application/json;charset=utf-8")
	public void register(@RequestBody String userInfo) {
		System.out.println(userInfo);
		loginRegisterService.register(userInfo);
	}
	@PostMapping(value="/login",produces = "application/json;charset=utf-8")
	public void login(@RequestBody String userInfo) {
//		System.out.println("login"+userInfo);
		loginRegisterService.login(userInfo);
	}
	@PostMapping(value="/modifyPassword",produces = "application/json;charset=utf-8")
	public void modifyPassword(@RequestBody String userInfo) {
		System.out.println("modifyPassword"+userInfo);
		loginRegisterService.modifyPassword(userInfo);
	}
	/**
	 * 用户查看个人信息
	 * @param Email
	 */
	@PostMapping(value="/getBaseInfo",produces = "application/json;charset=utf-8")
	public void getBaseInfo(@RequestBody String Email) {
//		System.out.println("getBaseInfo"+Email);
		loginRegisterService.getBaseInfo(Email);
	}
	/**
	 * 用户修改并保存个人信息
	 * @param Email
	 */
	@PostMapping(value="/saveBaseInfo",produces = "application/json;charset=utf-8")
	public void saveBaseInfo(@RequestBody String userInfo) {
		System.out.println("saveBaseInfo"+userInfo);
		loginRegisterService.saveBaseInfo(userInfo);
	}
}
