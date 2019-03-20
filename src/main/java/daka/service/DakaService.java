package daka.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import daka.enums.ResultEnum;
import daka.exception.MyException;
import daka.model.Daka;
import daka.model.DakaTask;
import daka.model.Order;
import daka.model.User;
import daka.utils.GetDateByString;
import daka.utils.GetToken4qiniu;
import daka.utils.HibernateUtil;

@Service
public class DakaService {
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
			
//			for(Daka daka : dakaList) {
//				System.out.println(daka);
//			}
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
