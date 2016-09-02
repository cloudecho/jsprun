package cn.jsprun.utils;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import com.sun.mail.smtp.SMTPTransport;
public class Mail {
	private static final String SSL_FACTORY  =  "javax.net.ssl.SSLSocketFactory";
	private String host=null;
	private int port=25;
	private String auth=null;
	private String username=null;
	private String password=null;
	private boolean mailusername=false;
	private Session session = null;
	ThreadPoolExecutor executor = null;{
		executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
	}
	@SuppressWarnings("unchecked")
	public Mail(Map<String,String> mails) {
		this.host=mails.get("server");
		int tempPort=Common.toDigit(mails.get("port"));
		if(tempPort>0){
			this.port=tempPort;
		}
		this.auth=mails.get("auth");
		this.username=mails.get("auth_username");
		this.password=mails.get("auth_password");
		if("1".equals(mails.get("mailusername"))){
			this.mailusername=true;
		}
	}
	public Mail(String host,int port,String auth,String username,String password,String mailusername) {
		this.host=host;
		if(port>0){
			this.port=port;
		}
		this.auth=auth;
		this.username=username;
		this.password=password;
		if("1".equals(mailusername)){
			this.mailusername=true;
		}
	}
	private synchronized void createSession() {
		  Properties mailProps = new Properties();
		  mailProps.setProperty("mail.transport.protocol", "smtp"); 
		  mailProps.setProperty("mail.smtp.host",  host);
		  mailProps.setProperty("mail.smtp.port", String.valueOf(port));
		  if("smtp.gmail.com".equals(host)){
			   mailProps.setProperty("mail.smtp.socketFactory.class",  SSL_FACTORY);
			   mailProps.setProperty("mail.smtp.socketFactory.fallback",  "false");
			   mailProps.setProperty("mail.smtp.socketFactory.port",String.valueOf(port));
		  }
		   if ("1".equals(auth)) {
				mailProps.put("mail.smtp.auth", "true");
			}
		session = Session.getDefaultInstance(mailProps,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
	}
	private MimeMessage createMimeMessage() {
		if (session == null) {
			createSession();
		}
		return new MimeMessage(session);
	}
	public String sendMessage(String from,String toEmail,String subject, String textBody, String htmlBody) {
		String result=null;
		try {
			String encoding = MimeUtility.mimeCharset(JspRunConfig.CHARSET);
			MimeMessage message = createMimeMessage();
			String toEmails[] = toEmail.split(",");
			Address to[] = new Address[toEmails.length];
			for (int i = 0; i < toEmails.length; i++) {
				String sTo=toEmails[i];
				if(sTo.matches("^.*<.*>$")){
					int index=sTo.indexOf("<");
					to[i] = new InternetAddress(sTo.substring(index+1,sTo.length()-1),mailusername?sTo.substring(0, index):"", encoding);
				}else{
					to[i] = new InternetAddress(sTo, "", encoding);
				}
			}
			String fromName=null;
			String fromEmail;
			if(from.matches("^.*<.*>$")){
				int index=from.indexOf("<");
				if(mailusername){
					fromName=from.substring(0, index);
				}
				fromEmail=from.substring(index+1,from.length()-1);
			}else{
				fromEmail=from;
			}
			Address fromAddress = new InternetAddress(fromEmail,fromName!=null?fromName:"", encoding);
			message.setHeader("Date", Common.gmdate("EEE, dd MMM yyyy HH:mm:ss Z", (int)(System.currentTimeMillis()/1000), "0"));
			message.setHeader("Content-Transfer-Encoding", "8bit");
			message.setRecipients(Message.RecipientType.TO, to);
			message.setFrom(fromAddress);
			message.setSubject(subject, encoding);
			MimeMultipart content = new MimeMultipart("alternative");
			if (textBody != null && htmlBody != null) {
				MimeBodyPart text = new MimeBodyPart();
				text.setText(textBody, encoding);
				text.setDisposition(Part.INLINE);
				content.addBodyPart(text);
				MimeBodyPart html = new MimeBodyPart();
				html.setContent(htmlBody, "text/html;charset="+encoding);
				html.setDisposition(Part.INLINE);
				content.addBodyPart(html);
			} else if (textBody != null) {
				MimeBodyPart text = new MimeBodyPart();
				text.setText(textBody, encoding);
				text.setDisposition(Part.INLINE);
				content.addBodyPart(text);
			} else if (htmlBody != null) {
				MimeBodyPart html = new MimeBodyPart();
				html.setContent(htmlBody, "text/html;charset="+encoding);
				html.setDisposition(Part.INLINE);
				content.addBodyPart(html);
			}
			message.setContent(content);
			message.setDisposition(Part.INLINE);
			addToTask(message);
		} catch (Exception e) {
			result=e.getMessage();
		}
		return result;
	}
	private void addToTask(MimeMessage message) {
		if (message != null) {
			sendMessages(Collections.singletonList(message));
		} else {
			System.out.println("Cannot add null email message to queue.");
		}
	}
	private void sendMessages(Collection<MimeMessage> messages) {
		if (messages.size() == 0) {
			return;
		}
		executor.execute(new EmailTask(messages));
	}
	private class EmailTask implements Runnable {
		private Collection<MimeMessage> messages;
		public EmailTask(Collection<MimeMessage> messages) {
			this.messages = messages;
		}
		public void run() {
			try {
				sendMessages();
			} catch (MessagingException me) {
				int timestamp =(int)(System.currentTimeMillis()/1000);
				Log.writelog("errorlog", timestamp, timestamp+"\tSMTP\t\t("+host+":"+port+") CONNECT - Unable to connect to the SMTP server");
			}
		}
		public boolean sendMessages() throws MessagingException {
			Transport transport = null;
			try {
				URLName url = new URLName("smtp", host, port, "", username,password);
				transport = new SMTPTransport(session, url);
				transport.connect(host, port, username, password);
				for (MimeMessage message : messages) {
					transport.sendMessage(message, message.getRecipients(MimeMessage.RecipientType.TO));
				}
				return true;
			} finally {
				if (transport != null) {
					transport.close();
				}
			}
		}
	}
}