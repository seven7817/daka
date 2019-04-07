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
	/*
	 * 用于向指定用户进行好友申请
	 */
	@PostMapping(value = "/applyFriend", produces = "application/json;charset=utf-8")
	public void applyFriend(@RequestBody String eamilInfo) {
		System.out.println("applyFriend" + eamilInfo);
		personInfoService.applyFriend(eamilInfo);
	}
	/*
	 * 用于获取别人对自己的好友申请
	 */
	@PostMapping(value = "/getMsgAboutApplyFriend", produces = "application/json;charset=utf-8")
	public void getMsgAboutApplyFriend(@RequestBody String eamil) {
		System.out.println("getMsgAboutApplyFriend" + eamil);
		personInfoService.getMsgAboutApplyFriend(eamil);
	}
	/**
	 * 选择是否通过其他用户对自身的好友申请
	 * @param eamil
	 */
	@PostMapping(value = "/choosePass", produces = "application/json;charset=utf-8")
	public void choosePass(@RequestBody String info) {
		System.out.println("choosePass" + info);
		personInfoService.choosePass(info);
	}
	/**
	 * 获取好友列表
	 * @param info
	 */
	@PostMapping(value = "/getFriends", produces = "application/json;charset=utf-8")
	public void getFriends(@RequestBody String email) {
		System.out.println("getFriends" + email);
		personInfoService.getFriends(email);
	}
	/**
	 * 根据两个用户的email得到对应的聊天记录
	 * @param emails
	 */
	@PostMapping(value = "/getChatInfoByTwoEmail", produces = "application/json;charset=utf-8")
	public void getChatInfoByTwoEmail(@RequestBody String emails) {
		System.out.println("getChatInfoByTwoEmail" + emails);
		personInfoService.getChatInfoByTwoEmail(emails);
	}
	/**
	 * 给某个好友发送消息
	 * @param emails
	 */
	@PostMapping(value = "/sendMsg", produces = "application/json;charset=utf-8")
	public void sendMsg(@RequestBody String info) {
		System.out.println("sendMsg" + info);
		personInfoService.sendMsg(info);
	}
	
	
	
}
