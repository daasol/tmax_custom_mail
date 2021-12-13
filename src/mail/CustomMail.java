package mail;



import javax.mail.* ;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import config.Config;




public class CustomMail {
	/** Loggner **/
	private final static Logger logger = Logger.getLogger("");
	
	
	/** Sender **/
	public static boolean SendMail() {
		
		logger.info("[CustomMail.SendMail] [Sender] Start to send mail ");
			
		/**Sender Properties **/	
		Properties p = new Properties();  
		
		p.put("mail.smtp.ssl.protocols", Config.TLSVERSION); //TLSv1.2
		p.put("mail.smtp.auth",  "true");
		p.put("mail.smtp.starttls.enable", "true");
	    p.put("mail.smtp.host", Config.HOST);
	    p.put("mail.smtp.port", Config.PORT);
		p.put("mail.smtp.ssl.trust",Config.HOST);
		
		Authenticator auth = new MyAuthentication();
		Session session = Session.getDefaultInstance(p, auth);		
		MimeMessage msg = new MimeMessage(session);
		InternetAddress sender  = new InternetAddress();
		
		
		/** Set Body **/
		SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy.MM.dd-H:mm:ss.SSS");
		String date=logDateFormat.format(new Date());	
		String body="["+date +"]"+Config.BODY;
		
		
		try {			
			msg.setSentDate(new Date());
			
			sender = new InternetAddress(Config.SENDERID);
			logger.info("[CustomMail.SendMail] Sender : "+sender);
			msg.setFrom(sender);
			InternetAddress receiver = new InternetAddress(Config.RECEIVER);
			msg.setRecipient(Message.RecipientType.TO, receiver);			
			msg.setSubject(Config.TITLE, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setHeader("context-Type", "text/html");
			
			
			/****/
			Transport trns =session.getTransport("smtp");
			
			try{
				trns.connect();
				trns.sendMessage(msg, InternetAddress.parse(Config.RECEIVER));
				
				logger.info("[CustomMail.SendMail] Message sent "+sender);
				return true;
				
			} catch(javax.mail.internet.AddressException ae) {
			      ae.printStackTrace();
			      return false;
			      
		     } catch(javax.mail.MessagingException me) {
		    	 
		    	 me.printStackTrace();
		    	 return false;
		    	 
		     }			
		
		}catch (AddressException addr_e) {
			addr_e.printStackTrace();
			logger.warning("[CustomMail.SendMail] [SendMail] Mail Transfer Failed ");
			return false;
			
		}catch (MessagingException msg_e) {
			msg_e.printStackTrace();
			logger.warning("[CustomMail.SendMail] [SendMail] Mail Transfer Failed ");
			return false;
		}
		
	}
}



/**Authentication Properties **/
class MyAuthentication extends Authenticator{
	PasswordAuthentication account;
	
	public MyAuthentication() {
		String id = Config.SENDERID;
		String pw = Config.SENDERPW;
		account = new PasswordAuthentication(id, pw);
	}
	public PasswordAuthentication getPasswordAuthentication() {
		return account;
	}
}



