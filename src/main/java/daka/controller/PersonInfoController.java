package daka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import daka.service.PersonInfoService;

@Controller
public class PersonInfoController {
	@Autowired
	private PersonInfoService personInfoService;
	/**
	 * 用户查看个人信息
	 * 
	 * @param Email
	 */
	@PostMapping(value = "/getBaseInfo", produces = "application/json;charset=utf-8")
	public void getBaseInfo(@RequestBody String Email) {
//		System.out.println("getBaseInfo"+Email);
		personInfoService.getBaseInfo(Email);
	}

	/**
	 * 用户修改并保存个人信息
	 * 
	 * @param Email
	 */
	@PostMapping(value = "/saveBaseInfo", produces = "application/json;charset=utf-8")
	public void saveBaseInfo(@RequestBody String userInfo) {
		System.out.println("saveBaseInfo" + userInfo);
		personInfoService.saveBaseInfo(userInfo);
	}
}
