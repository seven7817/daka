package daka.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	@GetMapping(value="/index",produces = "application/json;charset=utf-8")
	public ModelAndView test2(@RequestParam("Email") String Email,@RequestParam("money") String money,
			@RequestParam("description") String description) {
		System.out.println("aaaa");
		System.out.println(Email);
		System.out.println(money);
		System.out.println(description);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		modelAndView.addObject("Email",Email);
		modelAndView.addObject("money",money);
		modelAndView.addObject("description",description);
		return modelAndView;
	}
	@GetMapping(value="/coding",produces = "application/json;charset=utf-8")
	public String test3() {
		
		return "coding";
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
	//手机充值调用的方法
	@PostMapping(value="/recharge",produces = "application/json;charset=utf-8")
	public void recharge(String Email,String money,String description) {
		System.out.println("充值成功,要充值的账户是:"+Email+"金额是："+money+"描述是："+description);
		loginRegisterService.recharge(Email, money, description);
	}
	//出现验证码的时候轮询访问判断是否扫码
	@PostMapping(value="/isRecharge",produces = "application/json;charset=utf-8")
	public void isRecharge(@RequestBody String Email) {
		System.out.println("isRecharge"+Email);
		loginRegisterService.isRecharge(Email);
	}
	//如果前端判断已经支付了后，就保存用户的申请信息
	@PostMapping(value="/saveDakaInfo",produces = "application/json;charset=utf-8")
	public void saveDakaInfo(@RequestBody String dakaInfo) {
		System.out.println("saveDakaInfo"+dakaInfo);
		loginRegisterService.saveDakaInfo(dakaInfo);
	}
	//用户想查看自己正在进行的所有打卡任务的时候，调用该方法
	@PostMapping(value="/getFinishing",produces = "application/json;charset=utf-8")
	public void getFinishing(@RequestBody String Email) {
		System.out.println("getFinishing"+Email);
		loginRegisterService.getFinishing(Email);
	}
	//通过打卡的id和要显示的年月得到具体的详细打卡任务的状态，就是一个数组，可以用来显示在日历了
	@PostMapping(value="/getDakaTasksStateByDakaIdAndStartDateAndTimeInterval",produces = "application/json;charset=utf-8")
	public void getDakaTasksStateByDakaIdAndDate(@RequestBody String info) {
		System.out.println("getDakaTasksStateByDakaIdAndStartDateAndTimeInterval"+info);
		loginRegisterService.getDakaTasksStateByDakaIdAndDate(info);
	}
	
	
}
