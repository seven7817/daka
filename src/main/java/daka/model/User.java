package daka.model;


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
 * @date: 2018年12月19日 上午9:58:10
 *
 */
@Component
@Scope(value="prototype")
@Entity   						//表名该类是一个实体类
@Table(name="my_user")  
public class User {
	@Id  //表名当前字段时主键
	@Column(name="id") //表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer Id;
	@Column(name="email")
	private String Email;
	@Column(name="password")
	private String password;
	@Column(name="age")
	private String age;
	@Column(name="phone")
	private String phone;
	@Column(name="nickname")
	private String nickname;
	@Column(name="gender")
	private String gender;
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@Override
	public String toString() {
		return "User [Id=" + Id + ", Email=" + Email + ", password=" + password + ", age=" + age + ", phone=" + phone
				+ ", nickname=" + nickname + ", gender=" + gender + "]";
	}
	
	
	
	
}
