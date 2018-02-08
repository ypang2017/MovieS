package com.movies.spider.view;

import com.movies.spider.utils.LoadPropertyUtil;
import com.movies.spider.view.impl.IMailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Use QQ mailbox to send an e-mail 
 */
public class QQEmailSender implements IMailSender{
  @Override
  public void send() {
    // Create Properties to storage some mailbox properties
    Properties props = new Properties();
    // Use SMTP to send e-mail,must do authenticate
    props.put("mail.smtp.auth", LoadPropertyUtil.getQQmailbox("mail.smtp.auth"));
    //SMTP Server info
    props.put("mail.smtp.host", LoadPropertyUtil.getQQmailbox("mail.smtp.host"));
    //Server port 
    props.put("mail.smtp.port", LoadPropertyUtil.getQQmailbox("mail.smtp.port"));
    // My qqmailbox info
    props.put("mail.user", LoadPropertyUtil.getQQmailbox("mail.user"));
    //16 bit STMP password
    props.put("mail.password", LoadPropertyUtil.getQQmailbox("mail.password"));

    //Construct authorization information for SMTP authentication
    Authenticator authenticator = new Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {
        // Username and password
        String userName = LoadPropertyUtil.getQQmailbox("mail.user");
        String password = LoadPropertyUtil.getQQmailbox("mail.password");
        return new PasswordAuthentication(userName, password);
      }
    };
    //Use environmental attributes and authorization information, create mail session
    Session mailSession = Session.getInstance(props, authenticator);
    //Create mail information 
    MimeMessage message = new MimeMessage(mailSession);
    
    try {
      //Set the mail sender
      InternetAddress form = new InternetAddress(
        props.getProperty("mail.user"));
      message.setFrom(form);

      //Set the mail reciver
      InternetAddress to = new InternetAddress(LoadPropertyUtil.getQQmailbox("mail.reciver"));
      message.setRecipient(Message.RecipientType.TO, to);

      //Set the mail title
      message.setSubject("Test email");

      //Set the detail content
      message.setContent("This is a test email", "text/html;charset=UTF-8");
      
      //Send the email
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    QQEmailSender sender = new QQEmailSender();
    sender.send();
  }
}