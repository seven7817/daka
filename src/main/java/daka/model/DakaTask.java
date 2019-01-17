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
@Table(name = "daka_task")
public class DakaTask {
	@Id // 表名当前字段时主键
	@Column(name = "id") // 表名对应数据库的主键字段时cust_id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "daka_id") 
	private Integer dakaId;//打卡的id
	@Column(name = "response") 
	private String response; //回复
	@Column(name = "commit_date") 
	private Date commitDate; //提交时间
	@Column(name = "summary") 
	private String summary;  //总结
	@Column(name = "img1") 
	private String img1;
	@Column(name = "img2") 
	private String img2;
	@Column(name = "img3") 
	private String img3;
	@Column(name = "img4") 
	private String img4;
	@Column(name = "img5") 
	private String img5;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDakaId() {
		return dakaId;
	}
	public void setDakaId(Integer dakaId) {
		this.dakaId = dakaId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	@Override
	public String toString() {
		return "DakaTask [id=" + id + ", dakaId=" + dakaId + ", response=" + response + ", commitDate=" + commitDate
				+ ", summary=" + summary + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3 + ", img4=" + img4
				+ ", img5=" + img5 + "]";
	}
	
	
	
	
	
}
