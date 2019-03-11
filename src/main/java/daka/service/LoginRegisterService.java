package daka.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import daka.model.Daka;
import daka.model.DakaTask;
import daka.model.Order;
import daka.model.RegisterEmailVerificationCode;
import daka.model.User;
import daka.utils.GetDateByString;
import daka.utils.GetToken4qiniu;
import daka.utils.GetVerificationCode;
import daka.utils.HibernateUtil;
import daka.utils.SendMail;
import daka.utils.SendMail2;

@Service
public class LoginRegisterService {
	/**
	 * 用于注册
	 * 
	 * @param userInfo
	 */
	public void register(String userInfo) {
//		System.out.println("register:" + userInfo);
		JSONObject jsonobject = JSONObject.parseObject(userInfo);
		User user = (User) JSONObject.parseObject(userInfo, User.class);
//		System.out.println("register:"+user);
		String code = queryCodeByEmail(user.getEmail());
		// 判断是否已经存在账户 发邮箱的时候已经判断了
		// 判断验证码是否正确
		if (code.equals(jsonobject.getString("code"))) {
			Session s1 = HibernateUtil.getCurrentSession();
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
		@SuppressWarnings("rawtypes")
		List list = findUserByEmail(Email);
		// 判断是否已经存在账户
		if (list.size() == 0) {
			String verificationCode = GetVerificationCode.getCode();
			RegisterEmailVerificationCode registerEmailVerificationCode = new RegisterEmailVerificationCode();
			Session s1 = HibernateUtil.getCurrentSession();
			Transaction tx1 = s1.beginTransaction();
			registerEmailVerificationCode.setEmail(Email);
			registerEmailVerificationCode.setVerificationCode(verificationCode);
			registerEmailVerificationCode.setLastDate(new Date());
			Query q1 = s1.createQuery("from RegisterEmailVerificationCode where Email = ?");
			q1.setString(0, Email);
			@SuppressWarnings("rawtypes")
			List list1 = q1.list();
			// 判断是否之前发过验证码
			if (list1.size() != 0) {
				registerEmailVerificationCode = (RegisterEmailVerificationCode) list1.get(0);
				registerEmailVerificationCode.setVerificationCode(verificationCode);
				registerEmailVerificationCode.setLastDate(new Date());
			} else {
				s1.save(registerEmailVerificationCode);
			}
			tx1.commit();
//			SendMail.send(Email, "打卡系统注册邮箱验证", "尊敬的用户：您正在进行账户注册，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
			SendMail2.send(Email, "打卡系统注册邮箱验证", "尊敬的用户：您正在进行账户注册，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
		} else {
			throw new MyException(ResultEnum.REGISTER_WRONG_FOR_EXIST);
		}

	}

	@SuppressWarnings("rawtypes")
	private List findUserByEmail(String Email) {
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, Email);
		List list = q.list();
		tx.commit();
		System.out.println(list.size());
		return list;
	}

	// 修改密码的时候发送验证码
	public void getEmailWhereFindPassword(String Email) {
		// 判断是否有该用户进行过注册
		@SuppressWarnings("rawtypes")
		List list = findUserByEmail(Email);
		// 判断是否已经存在账户
		System.out.println("getEmailWhereFindPassword " + list.size());
		System.out.println("getEmailWhereFindPassword " + Email);
		if (list.size() == 1) {
			// 获取验证码
			String verificationCode = GetVerificationCode.getCode();
			RegisterEmailVerificationCode registerEmailVerificationCode = new RegisterEmailVerificationCode();
			registerEmailVerificationCode.setEmail(Email);
			registerEmailVerificationCode.setVerificationCode(verificationCode);
			registerEmailVerificationCode.setLastDate(new Date());
			Session s1 = HibernateUtil.getCurrentSession();
			Transaction tx1 = s1.beginTransaction();
			Query q1 = s1.createQuery("from RegisterEmailVerificationCode where Email = ?");
			q1.setString(0, Email);
			@SuppressWarnings("rawtypes")
			List list1 = q1.list();
			// 判断之前是否发送过验证码
			if (list1.size() != 0) {
				registerEmailVerificationCode = (RegisterEmailVerificationCode) list1.get(0);
				registerEmailVerificationCode.setVerificationCode(verificationCode);
				registerEmailVerificationCode.setLastDate(new Date());
			} else {
				s1.save(registerEmailVerificationCode);
			}
			tx1.commit();
			SendMail2.send(Email, "打卡系统找回密码邮箱验证",
					"尊敬的用户：您正在进行密码找回，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
		} else {
			throw new MyException(ResultEnum.GET_EMAIL_WRONG_FOR_NOT_EXIST);
		}
		// 有的话给他发邮件
		// 没有的话抛异常
	}

	public void getEmailWhereModifyPassword(String Email) {
		// TODO Auto-generated method stub
		String verificationCode = GetVerificationCode.getCode();
		RegisterEmailVerificationCode registerEmailVerificationCode = new RegisterEmailVerificationCode();
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from RegisterEmailVerificationCode where Email = ?");
		q.setString(0, Email);
		registerEmailVerificationCode = (RegisterEmailVerificationCode) q.uniqueResult();
		registerEmailVerificationCode.setVerificationCode(verificationCode);
		registerEmailVerificationCode.setLastDate(new Date());
		s.save(registerEmailVerificationCode);
		tx.commit();
		SendMail2.send(Email, "打卡系统修改密码邮箱验证", "尊敬的用户：您正在进行修改密码，验证码：" + verificationCode + "，请及时输入验证码。若非本人操作，请忽视此邮件。");
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
		@SuppressWarnings("rawtypes")
		List list = q.list();
		RegisterEmailVerificationCode registerEmailVerificationCode = null;
		if (list.size() != 0) {
			registerEmailVerificationCode = (RegisterEmailVerificationCode) list.get(0);
			// 判断验证码是否失效
			if (new Date().getTime() - registerEmailVerificationCode.getLastDate().getTime() > 1000 * 60 * 4) {
//				System.out.println(new Date().getTime() - registerEmailVerificationCode.getLastDate().getTime());
				throw new MyException(ResultEnum.CODE_INVALID);
			}
		}
		System.out.println("queryCodeByEmail" + registerEmailVerificationCode.getVerificationCode());

		t.commit();
		return registerEmailVerificationCode.getVerificationCode();
	}

	public void login(String userInfo) {
		System.out.println("login-serive:" + userInfo);
		User user = (User) JSONObject.parseObject(userInfo, User.class);
//		System.out.println(user);
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ? and password = ?");
		q.setString(0, user.getEmail());
		q.setString(1, user.getPassword());
		if (q.uniqueResult() == null) {
			tx.commit();
			throw new MyException(ResultEnum.LOGIN_WRONG);
		} else {
			user.setPassword("baomi");
			tx.commit();
			throw new MyException(ResultEnum.SUCCESS, user);
		}
	}

	public void modifyPassword(String userInfo) {
		System.out.println("modifyPassword:" + userInfo);
		JSONObject jsonobject = JSONObject.parseObject(userInfo);
		User user = (User) JSONObject.parseObject(userInfo, User.class);
		System.out.println("modifyPassword:" + user);
		String code = queryCodeByEmail(user.getEmail());
		// 判断是否已经存在账户 发邮箱的时候已经判断了
		// 判断验证码是否正确
		if (code.equals(jsonobject.getString("code"))) {
			Session s1 = HibernateUtil.getCurrentSession();
			Transaction tx1 = s1.beginTransaction();
			Query q = s1.createQuery("from User where Email = ?");
			q.setString(0, user.getEmail());
			User user1 = (User) q.uniqueResult();
			// 判断新密码和旧密码是否一样
			if (user.getPassword().equals(user1.getPassword())) {
				throw new MyException(ResultEnum.PASSWORD_REPETITION);
			}
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

	public void recharge(String Email, String money, String description) {
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		// 把付款状态设置为1 表示已付款
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, Email);
		User user = (User) q.uniqueResult();
		user.setIsRecharge("1");
		// 记录付款的订单
		Order order = new Order();
		order.setOrderDate(new Date());
		System.out.println(new Date());
		order.setDescription(description);
		order.setEmail(Email);
		order.setMoney(money);
		s.save(order);
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}

	public void isRecharge(String Email) {
		// TODO Auto-generated method stub

		JSONObject jsonobject = JSONObject.parseObject(Email);
		String EmailInfo = jsonobject.getString("Email");
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from User where Email = ?");
		q.setString(0, EmailInfo);
		User user = (User) q.uniqueResult();
//		System.out.println("user:"+user);

		if (user.getIsRecharge().equals("1")) {
			user.setIsRecharge("0");

			tx.commit();
			throw new MyException(ResultEnum.SUCCESS);
		} else {
			tx.commit();
			throw new MyException(ResultEnum.NOT_RECHARGE);
		}
	}

	public void saveDakaInfo(String dakaInfo) {
		JSONObject jsonDaka = JSONObject.parseObject(dakaInfo);
		System.out.println("接受到的打卡信息是" + jsonDaka);
		Daka daka = new Daka();
		daka.setApplyDate(new Date());
		daka.setDakaType(jsonDaka.getString("dakaType"));
		daka.setEmail(jsonDaka.getString("Email"));
		daka.setMoneyVeryTime(jsonDaka.getString("moneyVeryTime"));
		daka.setStartDate(GetDateByString.GetDateByH5String(jsonDaka.getString("startDate")));
		daka.setTimeInterval(jsonDaka.getString("timeInterval"));
		daka.setTimes(jsonDaka.getString("times"));
		daka.setTitle(jsonDaka.getString("title"));
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		s.save(daka);
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}

	@SuppressWarnings("null")
	public void getFinishing(String email) {
		// TODO Auto-generated method stub
		JSONObject jsonEmail = JSONObject.parseObject(email);
		String Email = jsonEmail.getString("Email");
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from Daka where Email = ? ");
		q.setString(0, Email);
		@SuppressWarnings({ "unchecked" })
		List<Daka> dakaList = q.list();
		List<Daka> dakaList1 = new ArrayList<Daka>();
		// 判断打卡是否已经结束了
		System.out.println(dakaList.size());
		if(dakaList.size()>0) {			
			for (Daka daka : dakaList) {
				Date now = new Date();
//			System.out.println("now:"+now.getTime());
//			System.out.println("daka:"+daka.getStartDate().getTime());
//			System.out.println(1000 * 60 * 60 * Long.parseLong(daka.getTimeInterval()) * Long.parseLong(daka.getTimes()));
//			System.out.println(daka.getTimeInterval());
//			System.out.println(daka.getTimes());
				// 毫秒值最好用long类型来存储，不然要爆炸
				if (now.getTime() - daka.getStartDate().getTime() < 1000 * 60 * 60 * Long.parseLong(daka.getTimeInterval())
						* Long.parseLong(daka.getTimes())) {
					dakaList1.add(daka);
				}
			}
		}
		tx.commit();
		if (dakaList1.size() != 0) {
			throw new MyException(ResultEnum.SUCCESS, dakaList1);
		} else {
			throw new MyException(ResultEnum.SUCCESS_BUT_NO_INFO);
		}
	}

	public void getDakaTasksStateByDakaIdAndDate(String info) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.parseObject(info);
		// 毫秒数太大要用big类型来存贮，且big类型的除法跟int不一样，不能直接用/来除，且big类型只能和big类型的数据进行运算
		int dakaId = json.getIntValue("dakaId");
		BigDecimal startDate = json.getBigDecimal("startDate");
		System.out.println("startDate" + startDate);
		int timeInterval = json.getIntValue("timeInterval");
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		// 2019-02-27 00:00:00
		// UNIX_TIMESTAMP 得到的时秒数不是毫秒数
		Query q = s.createSQLQuery("select CEILING((UNIX_TIMESTAMP(commit_date) - ?)/?/60/60) as numorder ,is_passed "
				+ "from daka_task where daka_id = ? "
				+ "group by CEILING((UNIX_TIMESTAMP(commit_date) - ?)/?/60/60) ,is_passed");
		q.setBigDecimal(0, startDate);
		q.setInteger(1, timeInterval);
		q.setInteger(2, dakaId);
		q.setBigDecimal(3, startDate);
		q.setInteger(4, timeInterval);
		@SuppressWarnings("unchecked")
		List<Object[]> tasksStateList = q.list();
//		System.out.println("tasksStateList"+tasksStateList);
		tx.commit();
//		for(Object [] objArray :tasksStateList) {
//			System.out.print(objArray[0]);
//			System.out.print(":");
//			System.out.println(objArray[1]);
//		}
		if (tasksStateList != null) {
			throw new MyException(ResultEnum.SUCCESS, tasksStateList);
		} else {
			throw new MyException(ResultEnum.SUCCESS_BUT_NO_INFO);
		}
	}

	// 返回上传图片到七牛云的简单凭证
	public void getSimpleToken() {
		throw new MyException(ResultEnum.SUCCESS, GetToken4qiniu.getSimpleToken());
	}

	public void saveDakaTaskInfo(String dakaTaskInfo) {
		JSONObject jsonDakaTask = JSONObject.parseObject(dakaTaskInfo);
		System.out.println("接受到的打卡任务信息是" + jsonDakaTask);

		DakaTask dakaTask = new DakaTask();
		dakaTask.setCycle(jsonDakaTask.getString("cycle"));
		dakaTask.setDakaId(jsonDakaTask.getInteger("dakaId"));
		dakaTask.setSummary(jsonDakaTask.getString("summary"));
		dakaTask.setResponse(jsonDakaTask.getString("response"));

		JSONArray jsonArray = jsonDakaTask.getJSONArray("filesAddress");
		if (jsonArray.size() == 0) {

		} else if (jsonArray.size() == 1) {
			dakaTask.setImg1(jsonArray.getString(0));
		} else if (jsonArray.size() == 2) {
			dakaTask.setImg1(jsonArray.getString(0));
			dakaTask.setImg2(jsonArray.getString(1));
		} else if (jsonArray.size() == 3) {
			dakaTask.setImg1(jsonArray.getString(0));
			dakaTask.setImg2(jsonArray.getString(1));
			dakaTask.setImg3(jsonArray.getString(2));
		} else if (jsonArray.size() == 4) {
			dakaTask.setImg1(jsonArray.getString(0));
			dakaTask.setImg2(jsonArray.getString(1));
			dakaTask.setImg3(jsonArray.getString(2));
			dakaTask.setImg4(jsonArray.getString(3));
		} else if (jsonArray.size() == 5) {
			dakaTask.setImg1(jsonArray.getString(0));
			dakaTask.setImg2(jsonArray.getString(1));
			dakaTask.setImg3(jsonArray.getString(2));
			dakaTask.setImg4(jsonArray.getString(3));
			dakaTask.setImg5(jsonArray.getString(4));
		}
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		s.save(dakaTask);
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}

	public void getDakaTaskInfo(String dakaTaskInfo) {
		// TODO Auto-generated method stub
		JSONObject jsonDakaTask = JSONObject.parseObject(dakaTaskInfo);
		System.out.println("接受到的打卡任务信息是" + jsonDakaTask);
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		
		Query q = s.createQuery("from DakaTask where dakaId = ? and cycle = ?");
		q.setInteger(0,jsonDakaTask.getInteger("dakaId"));
		q.setString(1, jsonDakaTask.getString("cycle"));
		DakaTask dakaTask = (DakaTask) q.uniqueResult();
		if(dakaTask!=null) {
			throw new MyException(ResultEnum.SUCCESS,dakaTask);
		}
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS);
	}

	public void getAllDakaInfo() {
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from Daka order by applyDate desc");
		List<Daka> dakaList = q.list();
		Object [] result = new Object[2];
		
//		for(Daka daka : dakaList) {
//			System.out.println(daka);
//		}
		tx.commit();
		result[1] = dakaList.size();
		if(dakaList.size()>7) {
			//表示从0开始到第8个，包含0不包含8
			dakaList = dakaList.subList(0, 7);
		}
		result[0] = dakaList;
		throw new MyException(ResultEnum.SUCCESS,result);
	}

	public void getUserNumsOfDaka(String email) {
		// TODO Auto-generated method stub
		JSONObject jsonEmail = JSONObject.parseObject(email);
		String Email = jsonEmail.getString("Email");
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from Daka where Email = ? ");
		q.setString(0, Email);
		@SuppressWarnings({ "unchecked" })
		List<Daka> dakaList = q.list();
		// 判断打卡是否已经结束了
		System.out.println(dakaList.size());
		int [] nums = {0,0};
		if(dakaList.size()>0) {			
			for (Daka daka : dakaList) {
				Date now = new Date();
				
				if (now.getTime() - daka.getStartDate().getTime() < 1000 * 60 * 60 * Long.parseLong(daka.getTimeInterval())
						* Long.parseLong(daka.getTimes())) {
					nums[0] +=1; 
				}else {
					nums[1] +=1;
				}
			}
		}
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS,nums);
	}

	public void getDakaInfoOfPage(String pageNum) {
		// TODO Auto-generated method stub
		JSONObject jsonEmail = JSONObject.parseObject(pageNum);
		int pageNumInt = jsonEmail.getIntValue("pageNum");
		Session s = HibernateUtil.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from Daka order by applyDate desc");
		q.setFirstResult((pageNumInt-1)*7);
		q.setMaxResults(7);
		List<Daka> dakaList = q.list();
		
		for(Daka daka : dakaList) {
			System.out.println(daka);
		}
		tx.commit();
		throw new MyException(ResultEnum.SUCCESS,dakaList);
	}

}
