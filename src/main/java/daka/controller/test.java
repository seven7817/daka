package daka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import daka.User;
import net.sf.json.JSONArray;

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月18日 上午5:23:31
 *
 */
@Controller
public class test {
	@GetMapping(value="/",produces = "application/json;charset=utf-8")
	public @ResponseBody String test1() {
		System.out.println("bbbbbbbb");
		User user = new User();
		user.setName("孙政");
		user.setPassword("123");
		JSONArray json = JSONArray.fromObject(user);
		return json.toString();
	}
}
