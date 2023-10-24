package IFTTT;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;  
import javax.activation.FileDataSource;  
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.Authenticator;  
import javax.mail.BodyPart;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.Multipart;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;  
import javax.mail.internet.MimeMessage;  
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.UnsupportedEncodingException;

public class GmailTask {
	
	public static boolean sendGmail(String id, String pw, String ct, String desId) throws AddressException, MessagingException
	{
		String host = "smtp.gmail.com";
		Properties props = new Properties();                        
		props.setProperty("mail.smtp.host", host);                  //smtp地址
		props.setProperty("mail.smtp.port", "465");                 //smtp端口
		props.setProperty("mail.smtp.auth", "true");                //需要认证
		props.setProperty("mail.smtp.ssl.enable", "true"); 
        props.setProperty("mail.smtp.connectiontimeout", "5000");  
		props.setProperty("mail.transport.protocol", "smtp");       //设置发送协议为smtp

		final String username = id;
		final String password = pw;
		Authenticator auth = new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);			
			}	
		};
		try
		{
			Session session = Session.getDefaultInstance(props,auth); //新建包含认证信息auth的邮件会话对象session
			session.setDebug(true); 
			//由会话对象创建MimeMessage邮件,并设置邮件的发件地址，收件信息(地址和昵称)，主题，内容，发件日期。
			Message message = new MimeMessage(session);          
			message.setFrom(new InternetAddress(id));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(desId,false));
			message.setSubject("来自IFTTT单机版的邮件");
			message.setText(ct);
			message.setSentDate(new Date());
			message.saveChanges();  
			
			Transport transport = session.getTransport();          //由会话对象创建邮件发送对象transport，并获取之前设置的发送协议                    
			transport.connect(host,465,id,pw);                     //创建邮件发送对象transport与smtp服务器的连接
			Transport.send(message);
			transport.close();
		}catch(Exception e){return false;}
		return true;
	}
	
	public static boolean recvGmail(String id, String pw) throws Exception
	{
		Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");          //设置存储协议为imap
        props.setProperty("mail.imap.host", "imap.gmail.com"); 
        //props.setProperty("mail.imap.port", "143"); 
        try
		{
        	Session session = Session.getInstance(props);           // 创建Session会话对象 
        	Store store = session.getStore("imaps");                // 由会话对象创建Store对象，并设定使用imap协议 
        	store.connect("imap.gmail.com",id,pw);                  // 创建对象Store与imap服务器的连接
        	Folder folder = store.getFolder("INBOX");               // 获得收件箱 
        	folder.open(Folder.READ_ONLY);                          // 以读写模式打开收件箱 
        	if(folder.getUnreadMessageCount() > 0)                  // 检测未读邮件数目。。注意测试前确保目的邮箱没有新邮件，或添加代码使任务进行前将邮箱内所有邮件标为已读
        		return true;
        	folder.close(false);
        	store.close();
			}catch(Exception e){}
			return false;
	}
}
