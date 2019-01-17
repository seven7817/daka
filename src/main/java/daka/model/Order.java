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
@Scope(value="prototype")
@Entity   
@Table(name = "recharge_order")   //表名不能是order，他是保留字
public class Order {
	@Id // 表名当前字段时主键
	@Column(name = "id") // 表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "email") 
	private String Email;
	@Column(name = "money") 
	private String money;
	@Column(name = "description") 
	private String description;
	@Column(name = "order_date") 
	private Date orderDate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", Email=" + Email + ", money=" + money + ", description=" + description
				+ ", orderDate=" + orderDate + "]";
	}
	
	
	
	
}
