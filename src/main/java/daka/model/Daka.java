package daka.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
@Entity // 表名该类是一个实体类
@Table(name = "daka")
public class Daka {
	@Id // 表名当前字段时主键
	@Column(name = "id") // 表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "email")
	private String Email; // 用户邮箱
	@Column(name = "daka_type")
	private String dakaType; // 打卡类型
	@Column(name = "title")
	private String title; // 标题
	@Column(name = "start_date")
	private Date startDate; // 打卡开始的时间
	@Column(name = "time_interval")
	private String timeInterval; // 打卡的时间间隔
	@Column(name = "times")
	private String times; // 总次数
	@Column(name = "completion_times")
	private String completionTimes="0"; // 完成的次数
	@Column(name = "money_very_time")
	private String moneyVeryTime; // 总金额
	@Column(name = "apply_date")
	private Date applyDate; // 申请的日期

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDakaType() {
		return dakaType;
	}

	public void setDakaType(String dakaType) {
		this.dakaType = dakaType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getCompletionTimes() {
		return completionTimes;
	}

	public void setCompletionTimes(String completionTimes) {
		this.completionTimes = completionTimes;
	}


	public String getMoneyVeryTime() {
		return moneyVeryTime;
	}

	public void setMoneyVeryTime(String moneyVeryTime) {
		this.moneyVeryTime = moneyVeryTime;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Override
	public String toString() {
		return "Daka [id=" + id + ", Email=" + Email + ", dakaType=" + dakaType + ", title=" + title + ", startDate="
				+ startDate + ", timeInterval=" + timeInterval + ", times=" + times + ", completionTimes="
				+ completionTimes + ", moneyVeryTime=" + moneyVeryTime + ", applyDate=" + applyDate + "]";
	}

	
}
