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
@Table(name = "friend_apply")
public class FriendApply {
	@Id // 表名当前字段时主键
	@Column(name = "id") // 表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "from_email")
	private String fromEmail; // 用户邮箱
	@Column(name = "to_email")
	private String toEmail; // 打卡类型
	@Column(name = "apply_date")
	private Date applyDate; // 申请的日期
	@Column(name = "is_passed")
	private String isPassed = "0"; // 申请的日期
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}
	@Override
	public String toString() {
		return "FrendApply [id=" + id + ", fromEmail=" + fromEmail + ", toEmail=" + toEmail + ", applyDate=" + applyDate
				+ ", isPassed=" + isPassed + "]";
	}
}
