package com.opensense.dashboard.server.logic;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;
import com.opensense.dashboard.client.services.AuthenticationService;
import com.opensense.dashboard.server.util.ClientRequestHandler;
import com.opensense.dashboard.server.util.DatabaseManager;
import com.opensense.dashboard.server.util.ServerLanguages;
import com.opensense.dashboard.server.util.SessionUser;
import com.opensense.dashboard.shared.ActionResult;
import com.opensense.dashboard.shared.ActionResultType;

@SuppressWarnings("serial")
public class AuthenticationServlet extends RemoteServiceServlet implements AuthenticationService{

	private static final Logger LOGGER = Logger.getLogger(AuthenticationServlet.class.getName());

	//works like salt
	private static final byte[] key = new byte[] { -110, 121, -65, 22, -60, 61, -22, -60, 21, -122, 41, -89, -89, -68, -8, 41, -119, -51, -12, -36, 19, -8, -17, 47 };

	/**
	 * Returns boolean true if a user logged in, false the user is guest
	 */
	@Override
	public Boolean createUserInSession() {
		if((System.getenv("DEV_MODE") != null) && "true".equalsIgnoreCase(System.getenv("DEV_MODE"))) {
			ActionResult result = this.userLoginRequest(System.getenv("USERNAME"), System.getenv("PASSWORD"));
			if(ActionResultType.SUCCESSFUL.equals(result.getActionResultType())) {
				return true;
			}
		}
		SessionUser.getInstance().removeUser();
		return false;
	}

	@Override
	public ActionResult userLoginRequest(String username, String password) {
		DatabaseManager.initPooling();
		DatabaseManager db = new DatabaseManager();
		String passwordDB = db.getPasswordFromUsername(username);
		if(this.isPasswordCorrect(passwordDB, password)) {
			String body = "{\"username\":\""+System.getenv("USERNAME")+"\",\"password\":\""+System.getenv("PASSWORD")+"\"}";
			String token = ClientRequestHandler.getInstance().sendLoginRequest(body);
			SessionUser.getInstance().createUser(db.getUserIdFromUsername(username), username, token);
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}else {
			SessionUser.getInstance().removeUser();
			DatabaseManager.clearDataSource();
			return new ActionResult(ActionResultType.FAILED);
		}
	}

	@Override
	public ActionResult userLoggedOut() {
		SessionUser.getInstance().removeUser();
		DatabaseManager.clearDataSource();
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}

	@Override
	public ActionResult userRegisterRequest(String username, String password, String email) {
		DatabaseManager.initPooling();
		DatabaseManager db = new DatabaseManager();
		Integer id = null;
		id = db.createUserProfile(email, username, password);
		DatabaseManager.clearDataSource();
		if((id==null) || (id<=0)) {
			ActionResult result = new ActionResult(ActionResultType.FAILED);
			if(id==-1) {
				result.setErrorMessage(ServerLanguages.usernameOrEmailExists());
			}
			return result;
		}
		SessionUser.getInstance().createUser(id, username, null);
		return new ActionResult(ActionResultType.SUCCESSFUL);
	}

	@Override
	public ActionResult forgotPasswordRequest(String email) {
		DatabaseManager.initPooling();
		DatabaseManager db = new DatabaseManager();
		Integer userId = db.getUserIdFromEmail(email);
		if((userId==null) || (userId==0)) {
			DatabaseManager.clearDataSource();
			return new ActionResult(ActionResultType.FAILED);
		}else {
			String password = RandomStringUtils.randomAlphanumeric(10);
			TripleDesCipher cipher = new TripleDesCipher();
			cipher.setKey(key);
			String passwordHash = null;
			try {
				passwordHash = cipher.encrypt(password);
			} catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
			}
			db.setUserPassword(userId, passwordHash);
			this.sendResetPasswordMail(email, password);
			DatabaseManager.clearDataSource();
			return new ActionResult(ActionResultType.SUCCESSFUL);
		}
	}

	public void sendResetPasswordMail(String email, String password) {
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.from", "Opensense-Dashboard");
		mailProps.put("mail.smtp.host", "smtp.gmail.com");
		mailProps.put("mail.smtp.port", "25");
		mailProps.put("mail.smtp.auth", true);
		mailProps.put("mail.smtp.socketFactory.port", "587");
		mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		mailProps.put("mail.smtp.socketFactory.fallback", "true");
		mailProps.put("mail.smtp.starttls.enable", "true");

		Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("opensense.dashboard@gmail.com", System.getenv("PASSWORD"));
			}
		});

		MimeMessage message = new MimeMessage(mailSession);
		try {
			message.setFrom(new InternetAddress("opensense.dashboard@gmail.com"));
			String[] emails = { email };
			InternetAddress dests[] = new InternetAddress[emails.length];
			for (int i = 0; i < emails.length; i++) {
				dests[i] = new InternetAddress(emails[i].trim().toLowerCase());
			}
			message.setRecipients(Message.RecipientType.TO, dests);
			message.setSubject("Password Reset", "UTF-8");
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent("New password: "+password, "text/html;charset=utf-8");
			mp.addBodyPart(mbp);
			message.setContent(mp);
			message.setSentDate(new java.util.Date());

			Transport.send(message);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failure sending email", e);
		}
	}

	@Override
	public ActionResult changePassword(String oldPassword, String newPassword) {
		if(SessionUser.getInstance().isGuest()) {
			return new ActionResult(ActionResultType.FAILED);
		}
		DatabaseManager db = new DatabaseManager();
		String passwordDB = db.getPasswordFromUserId(SessionUser.getInstance().getUserId());
		if(this.isPasswordCorrect(passwordDB, oldPassword)){
			return db.setUserPassword(SessionUser.getInstance().getUserId(), this.encryptPassword(newPassword));
		}else {
			return new ActionResult(ActionResultType.FAILED, ServerLanguages.wrongPassword());
		}
	}

	private String encryptPassword(String password) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(key);
		try {
			return cipher.encrypt(password);
		} catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
			return null;
		}
	}

	private boolean isPasswordCorrect(String password, String typedPassword){
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(key);
		String encryptedPassword = "";
		try {
			encryptedPassword = cipher.encrypt(typedPassword);
		} catch (DataLengthException | IllegalStateException | InvalidCipherTextException e) {
			return false;
		}
		return encryptedPassword.equals(password);
	}
}
