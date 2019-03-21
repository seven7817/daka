package daka.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import daka.enums.ResultEnum;
import daka.exception.MyException;


public class SendMail {
  
    private static String smtpHost="smtp.qq.com"; // 邮件服务器地址  
    private static String sendUserName = "1162573719@qq.com"; // 发件人的用户名  
    private static String sendUserPass = "pjtkhjhjlcydfdji"; // 发件人密码  
  
    private static MimeMessage mimeMsg; // 邮件对象  
    private static Session session;  
    private static Properties props;  
   
    private static void init() {  
        if (props == null) {  
            props = System.getProperties();  
        }  
        props.put("mail.smtp.host", smtpHost);  
        props.put("mail.smtp.auth", "true"); // 需要身份验证  
        session = Session.getDefaultInstance(props, null);  
        // 置true可以在控制台（console)上看到发送邮件的过程  
        session.setDebug(true);  
        // 用session对象来创建并初始化邮件对象  
        mimeMsg = new MimeMessage(session);  
        // 生成附件组件的实例  
        
    } 
    public static void send(String to, String mailSubject, String mailBody)  {  
    	init();  
        setFrom(sendUserName);  
        setTo(to);  
        setBody(mailBody);  
        setSubject(mailSubject);  
    	try {
			mimeMsg.saveChanges();
			System.out.println("正在发送邮件....");  
	        Transport transport = session.getTransport("smtp");  
	        // 连接邮件服务器并进行身份验证  
	        transport.connect(smtpHost, sendUserName, sendUserPass);  
	        // 发送邮件  
	        transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));  
	        System.out.println("发送邮件成功！");  
	        transport.close();  
		} catch (MessagingException e) {
			System.out.println("发送邮件失败");
			throw new MyException(ResultEnum.MAIL_WRONG);
		}  
    	System.out.println("发送邮件成功");
        throw new MyException(ResultEnum.SUCCESS);
    } 
    /** 
     * 设置邮件主题 
     *  
     * @param mailSubject 
     * @return 
     */  
    private static boolean setSubject(String mailSubject) {  
        try {  
            mimeMsg.setSubject(mailSubject);  
        } catch (Exception e) {  
            return false;  
        }  
        return true;  
    }  
  
    /** 
     * 设置邮件内容,并设置其为文本格式或HTML文件格式，编码方式为UTF-8 
     *  
     * @param mailBody 
     * @return 
     */  
    private static boolean setBody(String mailBody) {  
        try {  
        	
        	mimeMsg.setText(mailBody);
//            BodyPart bp = new MimeBodyPart();  
//            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>","text/html;charset=UTF-8");  
            // 在组件上添加邮件文本  
        } catch (Exception e) {  
            System.err.println("设置邮件正文时发生错误！" + e);  
            return false;  
        }  
        return true;  
    }  
  
    /** 
     
  
    /** 
     * 设置发件人地址 
     *  
     * @param from 
     *            发件人地址 
     * @return 
     */  
    private static boolean setFrom(String from) {  
    	String nick="";  
        try {  
            nick=javax.mail.internet.MimeUtility.encodeText("打卡系统");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }   
        try {  
            mimeMsg.setFrom(new InternetAddress(nick+" <"+from+">"));  
        } catch (Exception e) {  
            return false;  
        }  
        return true;  
    }  
  
    /** 
     * 设置收件人地址 
     *  
     * @param to收件人的地址 
     * @return 
     */  
    private static boolean setTo(String to) {  
        if (to == null)  
            return false;  
        try {  
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  
        } catch (Exception e) {  
            return false;  
        }  
        return true;  
    }   
}
