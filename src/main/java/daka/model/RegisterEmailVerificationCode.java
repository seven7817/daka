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

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月22日 上午11:09:10
 *
 */
@Component
@Scope(value="prototype")
@Entity   						//表名该类是一个实体类
@Table(name="register_email_verification_code")   			//建立当前类和数据库表的对应关系
public class RegisterEmailVerificationCode {
	@Id  //表名当前字段时主键
	@Column(name="id") //表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer Id;
	@Column(name="email") 
	private String Email;
	@Column(name="verification_code") 
	private String verificationCode;
	@Column(name="last_date")
	private Date lastDate;	
	
	
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	@Override
	public String toString() {
		return "RegisterEmailVerificationCode [Id=" + Id + ", Email=" + Email + ", verificationCode=" + verificationCode
				+ ", lastDate=" + lastDate + "]";
	}
	
}
