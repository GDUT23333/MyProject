package com.mjh.www.util;

import com.mjh.www.po.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发邮件
 */
public class MailUtil {
    private static final String USER = "2316117113@qq.com";//发件人称号，同邮箱地址
    private static final String PASSWORD = "lfpoewkqoirxdjgg";//授权码，如果是QQ邮箱可以使户端授权

    public static boolean sendMail(Mail mail){
        try{
            final Properties props = new Properties();
            props.put("mail.smtp.auth",true);
            props.put("mail.smtp.host","smtp.qq.com");

            //发件人的账号
            props.put("mail.user",USER);
            props.put("mail.password",PASSWORD);
            //构建授权信息，用于进行SMTP进行身份验证
            var authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    //用户名和密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName,password);
                }
            };
            //使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props,authenticator);
            //创建邮件信息
            MimeMessage message = new MimeMessage(mailSession);
            //设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);
            //设置收件人
            InternetAddress toAddress = new InternetAddress(mail.getTo());
            message.setRecipient(Message.RecipientType.TO,toAddress);
            //设置邮件标题和编码
            message.setSubject(mail.getTitle());
            //设置邮件内容体
            message.setContent(mail.getContent(),"text/html;charset=utf-8");
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
