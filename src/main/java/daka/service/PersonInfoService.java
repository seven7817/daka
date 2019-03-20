package daka.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import daka.model.User;
import daka.utils.HibernateUtil;

@Service
public class PersonInfoService {
	public void getBaseInfo(String Email) {
		JSONObject jsonobject = JSONObject.parseObject(Email);
		String EmailInfo = jsonobject.getString("Email");
		System.out.println("getBaseInfo " + EmailInfo);
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, EmailInfo);
		User user = (User) q.uniqueResult();
		tx.commit();
		System.out.println("3213123213123");
		throw new MyException(ResultEnum.SUCCESS, user);
	}
	public void saveBaseInfo(String userInfo) {
		// TODO Auto-generated method stub
		System.out.println("saveBaseInfo:" + userInfo);
//		JSONObject jsonobject = JSONObject.parseObject(userInfo);
		User newUser = JSONObject.parseObject(userInfo, User.class);

		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, newUser.getEmail());
		User user = (User) q.uniqueResult();
		user.setAge(newUser.getAge());
		user.setGender(newUser.getGender());
		user.setNickname(newUser.getNickname());
		user.setPhone(newUser.getPhone());
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}
}
