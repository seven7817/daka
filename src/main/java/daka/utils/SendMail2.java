package daka.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
 
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail2 {
	

	public static void send(String to, String mailSubject, String mailBody) {
	 String content = "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<title>"+mailSubject+"</title>"
				+ "<meta name=\"content-type\" content=\"text/html; charset=UTF-8\">"
				+ "</head>"
				+ "<body>"
				+ mailBody
				+ "</body>"
				+ "</html>"; // 可以用HTMl语言写
	 	Properties props = new Properties();
	 
		InputStream inputStream = SendMail2.class.getClassLoader().getResourceAsStream("qqEmail.properties");
		try {
			props.load(inputStream);	//加载properties文件
//			 props.put("mail.smtp.host", smtpHost);  
			inputStream.close();

			// 使用环境属性和授权信息，创建邮件会话
			Session session = Session.getInstance(props);
			// 通过session得到transport对象
			Transport ts = session.getTransport();
			// 连接邮件服务器：邮箱类型，帐号，授权码
			ts.connect("smtp.qq.com",props.getProperty("mail.user"), props.getProperty("mail.password"));
			// 创建邮件消息
			MimeMessage message = new MimeMessage(session);
			// 设置发件人

			String nick="";   
			try {  
				nick=javax.mail.internet.MimeUtility.encodeText("打卡系统");  
			} catch (UnsupportedEncodingException e) {  
				e.printStackTrace();  
			}   
			InternetAddress from = new InternetAddress(nick+" <"+props.getProperty("mail.user")+">");

			
			message.setFrom(from);

			// 设置收件人的邮箱
			InternetAddress to1 = new InternetAddress(to);
			message.setRecipient(RecipientType.TO, to1);

			// 设置邮件标题
			message.setSubject(mailSubject);
		
			// 设置邮件的内容体
			message.setContent(content, "text/html;charset=UTF-8");

			// 发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
			System.out.println("发送邮箱成功");
		} catch (MessagingException e) {
			// 邮件异常
			System.out.println("发送邮箱失败");
			e.printStackTrace();
		} catch (IOException e) {
			// properties文件加载异常
			System.out.println("发送邮箱失败");
			e.printStackTrace();
		}

	}

	
}
