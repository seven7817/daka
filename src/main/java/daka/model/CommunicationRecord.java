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
@Table(name = "communication_record")
public class CommunicationRecord {
	@Id // 表名当前字段时主键
	@Column(name = "id") // 表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "from_email")
	private String fromEmail; // 用户邮箱
	@Column(name = "to_email")
	private String toEmail; // 打卡类型
	@Column(name = "apply_date")
	private Date applyDate = new Date(); // 申请的日期
	@Column(name = "content")
	private String content; // 申请的日期
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "CommunicationRecord [id=" + id + ", fromEmail=" + fromEmail + ", toEmail=" + toEmail + ", applyDate="
				+ applyDate + ", content=" + content + "]";
	}
	
}
