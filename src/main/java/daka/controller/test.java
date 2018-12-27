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
		if(operation.equals("0")) {			
			loginRegisterService.getEmail(JSONObject.fromObject(Email).getString("Email"));
		}else if (operation.equals("1")){
			loginRegisterService.getEmailWhereFindPassword(JSONObject.fromObject(Email).getString("Email"));
		}
	}
	
	@PostMapping(value="/register",produces = "application/json;charset=utf-8")
	public void register(@RequestBody String userInfo) {
		System.out.println(userInfo);
		loginRegisterService.register(userInfo);
	}
	@PostMapping(value="/login",produces = "application/json;charset=utf-8")
	public void login(@RequestBody String userInfo) {
		System.out.println("login"+userInfo);
		loginRegisterService.login(userInfo);
	}
	@PostMapping(value="/modifyPassword",produces = "application/json;charset=utf-8")
	public void modifyPassword(@RequestBody String userInfo) {
		System.out.println("login"+userInfo);
		loginRegisterService.modifyPassword(userInfo);
	}
	
	
}
