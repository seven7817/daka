package daka.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import daka.model.FriendApply;
import daka.model.Friends;
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
	/**
	 * 向自己添加好友和重复添加好友问题没有解决
	 * @param eamilInfo
	 */
	public void applyFriend(String eamilInfo) {
		JSONObject jsonobject = JSONObject.parseObject(eamilInfo);
		String fromEmail = jsonobject.getString("fromEmail");
		String toEmail = jsonobject.getString("toEmail");
		FriendApply friendApply = new FriendApply();
		friendApply.setApplyDate(new Date());
		friendApply.setFromEmail(fromEmail);
		friendApply.setToEmail(toEmail);
		
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		s.save(friendApply);
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}
	public void getMsgAboutApplyFriend(String email) {
		JSONObject jsonobject = JSONObject.parseObject(email);
		String Email = jsonobject.getString("email");
		
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from FriendApply where toEmail = ? order by applyDate desc");
		q.setString(0, Email);
		System.out.println(Email);
		@SuppressWarnings({ "rawtypes" })
		List msgs = new ArrayList<FriendApply>();
		msgs = q.list();
		
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS,msgs);
	}
	public void choosePass(String info) {
		// TODO Auto-generated method stub
		JSONObject jsonobject = JSONObject.parseObject(info);
		String id = jsonobject.getString("id");
		String num = jsonobject.getString("num");
		FriendApply friendApply = null;
		
		
		
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from FriendApply where id = ?");
		q.setString(0, id);
		friendApply = (FriendApply) q.uniqueResult();
		friendApply.setIsPassed(num);
		
		if(num.equals("1")) {
			Friends friends1 = new Friends();
			friends1.setFromEmail(friendApply.getFromEmail());
			friends1.setToEmail(friendApply.getToEmail());
			friends1.setApplyDate(new Date());
			s.save(friends1);
			
			Friends friends2 = new Friends();
			friends2.setToEmail(friendApply.getFromEmail());
			friends2.setFromEmail(friendApply.getToEmail());
			friends2.setApplyDate(new Date());
			s.save(friends2);
		}
		
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}
	public void getFriends(String email) {
		JSONObject jsonobject = JSONObject.parseObject(email);
		String Email = jsonobject.getString("email");
		
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from Friends where fromEmail = ? order by applyDate desc");
		q.setString(0, Email);
		System.out.println(Email);
		@SuppressWarnings({ "rawtypes" })
		List msgs = new ArrayList<Friends>();
		msgs = q.list();
		
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS,msgs);
	}
}
