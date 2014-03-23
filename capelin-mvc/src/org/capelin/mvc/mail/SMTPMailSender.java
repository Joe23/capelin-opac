package org.capelin.mvc.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class SMTPMailSender {
	protected String sender;
	protected String serverName;
	protected String subject;
	protected static final Log log = LogFactory.getLog(SMTPMailSender.class);

	public boolean sendText(String recipient, String body) {
		return send( recipient,  body,  false);
	}
	
	public boolean sendHTML(String recipient, String body) {
		return send( recipient,  body,  true);
	}
	
	protected boolean send(String recipient, String body, boolean html) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", serverName);
		Session session = Session.getInstance(props, null);
		props.put("mail.from", sender);
		Message msg = new MimeMessage(session);
		try {
			msg.setSubject(subject);
			msg.setFrom();
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient, false));
			if(html){
				msg.setContent(new String(body.getBytes(), "iso-8859-1"), "text/html; charset=iso-8859-1");
			}else{
				msg.setText(body);
			}			
			// Send the message:
			Transport.send(msg);
			log.debug("Email send to: " + sender);
			return true;
		} catch (MessagingException e) {
			log.info("Email Sending failed due to " + e);			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
