package daka.service;

import org.springframework.stereotype.Service;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import net.sf.json.JSONObject;

@Service
public class LoginRegisterService {
	public void Register(String userInfo) {
		JSONObject jsonobject = JSONObject.fromObject(userInfo);
		String code = queryCodeByEmail(jsonobject.getString("Email"));
		if(code.equals(jsonobject.getString("code"))){
			throw new MyException(ResultEnum.SUCCESS);
		}else {
			throw new MyException(ResultEnum.REGISTER_WRONG);
		}
		
	}

	private  String  queryCodeByEmail(String string) {
		return string;
		
		
	}
}
