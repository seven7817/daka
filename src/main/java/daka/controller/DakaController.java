package daka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import daka.service.DakaService;
@Controller
public class DakaController {
	@Autowired
	private DakaService dakaService;
	// 手机充值调用的方法
		@PostMapping(value = "/recharge", produces = "application/json;charset=utf-8")
		public void recharge(String Email, String money, String description) {
			System.out.println("充值成功,要充值的账户是:" + Email + "金额是：" + money + "描述是：" + description);
			dakaService.recharge(Email, money, description);
		}

		// 出现验证码的时候轮询访问判断是否扫码
		@PostMapping(value = "/isRecharge", produces = "application/json;charset=utf-8")
		public void isRecharge(@RequestBody String Email) {
			System.out.println("isRecharge" + Email);
			dakaService.isRecharge(Email);
		}

		// 如果前端判断已经支付了后，就保存用户的申请信息
		@PostMapping(value = "/saveDakaInfo", produces = "application/json;charset=utf-8")
		public void saveDakaInfo(@RequestBody String dakaInfo) {
			System.out.println("saveDakaInfo" + dakaInfo);
			dakaService.saveDakaInfo(dakaInfo);
		}

		// 用户想查看自己正在进行的所有打卡任务的时候，调用该方法
		@PostMapping(value = "/getFinishing", produces = "application/json;charset=utf-8")
		public void getFinishing(@RequestBody String Email) {
			System.out.println("getFinishing" + Email);
			dakaService.getFinishing(Email);
		}

		// 通过打卡的id和要显示的年月得到具体的详细打卡任务的状态，就是一个数组，可以用来显示在日历了
		@PostMapping(value = "/getDakaTasksStateByDakaIdAndStartDateAndTimeInterval", produces = "application/json;charset=utf-8")
		public void getDakaTasksStateByDakaIdAndDate(@RequestBody String info) {
			System.out.println("getDakaTasksStateByDakaIdAndStartDateAndTimeInterval" + info);
			dakaService.getDakaTasksStateByDakaIdAndDate(info);
		}

		// 得到七牛云的简单上传凭证
		@PostMapping(value = "/getSimpleToken", produces = "application/json;charset=utf-8")
		public void getSimpleToken() {
			System.out.println("getSimpleToken");
			dakaService.getSimpleToken();
		}
		
		// 如果前端判断已经支付了后，就保存用户的申请信息
		@PostMapping(value = "/saveDakaTaskInfo", produces = "application/json;charset=utf-8")
		public void saveDakaTaskInfo(@RequestBody String dakaTaskInfo) {
			System.out.println("saveDakaTaskInfo" + dakaTaskInfo);
			dakaService.saveDakaTaskInfo(dakaTaskInfo);
		}
		// 根据用户给的打卡id和周期，查询对应的信息
		@PostMapping(value = "/getDakaTaskInfo", produces = "application/json;charset=utf-8")
		public void getDakaTaskInfo(@RequestBody String dakaTaskInfo) {
			System.out.println("getDakaTaskInfo" + dakaTaskInfo);
			dakaService.getDakaTaskInfo(dakaTaskInfo);
		}
		@PostMapping(value = "/getAllDakaInfo", produces = "application/json;charset=utf-8")
		public void getAllDakaInfo() {
			System.out.println("getAllDakaInfo");
			dakaService.getAllDakaInfo();
		}
		//首页分页功能选择页数变更打卡信息
		@PostMapping(value = "/getDakaInfoOfPage", produces = "application/json;charset=utf-8")
		public void getDakaInfoOfPage(@RequestBody String pageNum) {
			System.out.println("getDakaInfoOfPage:"+pageNum);
			dakaService.getDakaInfoOfPage(pageNum);
		}
		
		// 登录用户在web首页查看自己的打卡情况
		@PostMapping(value = "/getUserNumsOfDaka", produces = "application/json;charset=utf-8")
		public void getUserNumsOfDaka(@RequestBody String Email) {
			System.out.println("getUserNumsOfDaka" + Email);
			dakaService.getUserNumsOfDaka(Email);
		}
}
