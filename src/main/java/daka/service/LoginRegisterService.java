package daka.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sun.activation.registries.MailcapParseException;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import daka.model.RegisterEmailVerificationCode;
import daka.model.User;
import daka.utils.GetVerificationCode;
import daka.utils.HibernateUtil;
import daka.utils.SendMail;

@Service
public class LoginRegisterService {
	/**
	 * 用于注册
	 * 
	 * @param userInfo
	 */
	public void register(String userInfo) {
		System.out.println("register:" + userInfo);
		JSONObject jsonobject = JSONObject.parseObject(userInfo);
		User user = (User) JSONObject.parseObject(userInfo, User.class);
		System.out.println("register:"+user);
		String code = queryCodeByEmail(user.getEmail());
		// 判断是否已经存在账户  发邮箱的时候已经判断了
		// 判断验证码是否正确
		if (code.equals(jsonobject.getString("code"))) {
			Session s1 = HibernateUtil.openSession();
			Transaction tx1 = s1.beginTransaction();
			s1.save(user);
			tx1.commit();
			throw new MyException(ResultEnum.SUCCESS);
		} else {
			throw new MyException(ResultEnum.REGISTER_WRONG_FOR_CODE);
		}
	}
	/**
	 * 根据邮箱发邮件
	 * 
	 * @param Email
	 */
	public void getEmail(String Email) {
		List list = findUserByEmail(Email);
		// 判断是否已经存在账户
		if (list.size() == 0) {
			String verificationCode = GetVerificationCode.getCode();
			Session s1 = HibernateUtil.openSession();
			Transaction tx1 = s1.beginTransaction();
			RegisterEmailVerificationCode registerEmailVerificationCode = new RegisterEmailVerificationCode();
			registerEmailVerificationCode.setEmail(Email);
			registerEmailVerificationCode.setVerificationCode(verificationCode);
			Query q1 = s1.createQuery("from RegisterEmailVerificationCode where Email = ?");
			q1.setString(0, Email);
			List list1 = q1.list();
			//判断是否之前发过验证码
			if (list1.size() != 0) {
				registerEmailVerificationCode = (RegisterEmailVerificationCode) list1.get(0);
				registerEmailVerificationCode.setVerificationCode(verificationCode);
			} else {
				s1.save(registerEmailVerificationCode);
			}
			tx1.commit();
			SendMail.send(Email, "打卡系统注册邮箱验证", "尊敬的用户：您正在进行账户注册，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
		}else {
			throw new MyException(ResultEnum.REGISTER_WRONG_FOR_EXIST);
		}

	}
	private List findUserByEmail(String Email) {
		Session s = HibernateUtil.openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, Email);
		tx.commit();
		List list = q.list();
		System.out.println(list.size());
		return list;
	}
	//修改密码的时候发送验证码
	public void getEmailWhereFindPassword(String Email) {
		//判断是否有该用户进行过注册
		List list = findUserByEmail(Email);
		// 判断是否已经存在账户
		System.out.println("getEmailWhereFindPassword "+list.size());
		System.out.println("getEmailWhereFindPassword "+Email);
		if (list.size() == 1) {
			//获取验证码
			String verificationCode = GetVerificationCode.getCode();
			RegisterEmailVerificationCode registerEmailVerificationCode = new RegisterEmailVerificationCode();
			registerEmailVerificationCode.setEmail(Email);
			registerEmailVerificationCode.setVerificationCode(verificationCode);
			Session s1 = HibernateUtil.openSession();
			Transaction tx1 = s1.beginTransaction();
			Query q1 = s1.createQuery("from RegisterEmailVerificationCode where Email = ?");
			q1.setString(0, Email);
			List list1 = q1.list();
			//判断之前是否发送过验证码
			if (list1.size() != 0) {
				registerEmailVerificationCode = (RegisterEmailVerificationCode) list1.get(0);
				registerEmailVerificationCode.setVerificationCode(verificationCode);
			} else {
				s1.save(registerEmailVerificationCode);
			}
			tx1.commit();
			SendMail.send(Email, "打卡系统找回密码邮箱验证", "尊敬的用户：您正在进行密码找回，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
		}else {
			throw new MyException(ResultEnum.GET_EMAIL_WRONG_FOR_NOT_EXIST);
		}
		//有的话给他发邮件
		//没有的话抛异常
	}
	public void getEmailWhereModifyPassword(String Email) {
		// TODO Auto-generated method stub
		String verificationCode = GetVerificationCode.getCode();
		RegisterEmailVerificationCode registerEmailVerificationCode = new RegisterEmailVerificationCode();
		registerEmailVerificationCode.setEmail(Email);
		registerEmailVerificationCode.setVerificationCode(verificationCode);
		Session s = HibernateUtil.openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from RegisterEmailVerificationCode where Email = ?");
		q.setString(0, Email);
		registerEmailVerificationCode = (RegisterEmailVerificationCode)q.uniqueResult();
		registerEmailVerificationCode.setVerificationCode(verificationCode);
		s.save(registerEmailVerificationCode);
		tx.commit();
		SendMail.send(Email, "打卡系统修改密码邮箱验证", "尊敬的用户：您正在进行修改密码，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
	}
	/**
	 * 注册的时候需要根据用户输入的邮箱查询是否是对应的验证码存在
	 * 
	 * @param Email
	 * @return
	 */
	private String queryCodeByEmail(String Email) {
		Session s = HibernateUtil.getCurrentSession();
		Transaction t = s.beginTransaction();
		// 1.获取Query对象
		Query q = s.createQuery("from RegisterEmailVerificationCode where Email = ?");
		q.setString(0, Email);
		// 1.执行获取结果集
		List list = q.list();
		RegisterEmailVerificationCode registerEmailVerificationCode = null;
		if (list.size() != 0) {
			registerEmailVerificationCode = (RegisterEmailVerificationCode) list.get(0);
		}
		System.out.println("queryCodeByEmail" + registerEmailVerificationCode.getVerificationCode());

		t.commit();
		return registerEmailVerificationCode.getVerificationCode();
	}

	public void login(String userInfo) {
		System.out.println("login-serive:" + userInfo);
		User user = (User) JSONObject.parseObject(userInfo, User.class);
		System.out.println(user);
		Session s = HibernateUtil.openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ? and password = ?");
		q.setString(0, user.getEmail());
		q.setString(1, user.getPassword());
		tx.commit();
		if (q.uniqueResult() == null) {
			throw new MyException(ResultEnum.LOGIN_WRONG);
		} else {
			user.setPassword("baomi");
			throw new MyException(ResultEnum.SUCCESS, user);
		}
	}
	public void modifyPassword(String userInfo) {
		System.out.println("modifyPassword:" + userInfo);
		JSONObject jsonobject = JSONObject.parseObject(userInfo);
		User user = (User) JSONObject.parseObject(userInfo, User.class);
		System.out.println("modifyPassword:"+user);
		String code = queryCodeByEmail(user.getEmail());
		// 判断是否已经存在账户  发邮箱的时候已经判断了
		// 判断验证码是否正确
		if (code.equals(jsonobject.getString("code"))) {
			Session s1 = HibernateUtil.openSession();
			Transaction tx1 = s1.beginTransaction();
			Query q = s1.createQuery("from User where Email = ?");
			q.setString(0, user.getEmail());
			User user1 = (User) q.uniqueResult();
			user1.setPassword(user.getPassword());
			tx1.commit();
			throw new MyException(ResultEnum.SUCCESS);
		} else {
			throw new MyException(ResultEnum.MODIFY_PASSWORD_WRONG_FOR_CODE);
		}
	}
	public void getBaseInfo(String Email) {
		JSONObject jsonobject = JSONObject.parseObject(Email);
		String EmailInfo = jsonobject.getString("Email");
		System.out.println("getBaseInfo "+ EmailInfo);
		Session s = HibernateUtil.openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, EmailInfo);
		User user = (User) q.uniqueResult();
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS,user);
	}
	public void saveBaseInfo(String userInfo) {
		// TODO Auto-generated method stub
		System.out.println("saveBaseInfo:" + userInfo);
		JSONObject jsonobject = JSONObject.parseObject(userInfo);
		User newUser = (User) JSONObject.parseObject(userInfo, User.class);
		
		Session s = HibernateUtil.openSession();
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
