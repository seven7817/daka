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
		return "User [Id=" + Id + ", Email=" + Email + ", password=" + password + "]";
	}
	
	
}
