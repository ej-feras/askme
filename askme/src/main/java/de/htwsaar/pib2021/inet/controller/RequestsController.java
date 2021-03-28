package de.htwsaar.pib2021.inet.controller;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.htwsaar.pib2021.inet.model.ConfirmationToken;
import de.htwsaar.pib2021.inet.model.FormView;
import de.htwsaar.pib2021.inet.model.User;
import de.htwsaar.pib2021.inet.repository.ConfirmationTokenRepository;
import de.htwsaar.pib2021.inet.security.UserDetailsImpl;
import de.htwsaar.pib2021.inet.service.ConfirmationTokenService;

import de.htwsaar.pib2021.inet.service.UserService;



@Controller
public class RequestsController {

	private static final String CONFIRMATION_MESSAGE = "To confirm your account by Askme, please click here: http://localhost:8083/confirm-account?token=";

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	@Qualifier("confirmationTokenServiceImpl")
	private ConfirmationTokenService confirmationTokenService;

	/**
	 *
	 *
	 * @param userDetails
	 * @param model
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	public String viewUserRequests(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		List<User> usersRequests = userService.findAllUsersByAdminId(userDetails.getId()).stream()
				.filter(user -> user.isEnabled() == false).collect(Collectors.toList());

		List<User> usersRecievedAConfirmMessage = confirmationTokenService.findUsersWithUnusedToken(userDetails.getId())
				.stream().filter(user -> user.isEnabled() == false).collect(Collectors.toList());

		usersRequests.removeAll(usersRecievedAConfirmMessage);
		int numberOfRequests = usersRequests.size() + usersRecievedAConfirmMessage.size();
		model.addAttribute("numberOfRequests", numberOfRequests);
		model.addAttribute("usersRequests", usersRequests);
		model.addAttribute("usersRecievedAConfirmMessage", usersRecievedAConfirmMessage);
		model.addAttribute("formView", new FormView());
		model.addAttribute("user", userDetails.getUser());

		return "requests";
	}

	/**
	 *
	 *
	 * @param model
	 * @param fromEmail
	 * @param emailPassword
	 * @param toEmail
	 * @param redirectAttributes
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/send-confirm", method = RequestMethod.POST)
	public String sendConfirmation(Model model, @RequestParam("fromEmail") String fromEmail,
			@RequestParam("emailPassword") String emailPassword, @RequestParam("toEmail") String toEmail,
			final RedirectAttributes redirectAttributes) {
		User user = userService.findByEmailIgnoreCase(toEmail);

		try {
			sendConfirmationEmail(user, fromEmail, emailPassword, toEmail);
		} catch (AuthenticationFailedException e) {
			redirectAttributes.addFlashAttribute("invalidEmailCredentials",
					"Please recheck the given email and password.");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("notSent", "try again to send the confirmation.");
			System.out.println("Unable to sent email ! Maybe your antivirus should be disabled ?");
		}

		return "redirect:/requests";
	}

	/**
	 *
	 *
	 * @param user
	 * @param fromEmail
	 * @param emailPassword
	 * @param to
	 * @return
	 * @throws MessagingException
	 *
	 * @author Feras Ejneid
	 */
	private boolean sendConfirmationEmail(User user, String fromEmail, String emailPassword, String to)
			throws MessagingException {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		String emailBody = CONFIRMATION_MESSAGE + confirmationToken.getConfirmationToken();

		sendEmail(fromEmail, emailPassword, to, "Complete Registration!", emailBody);
		confirmationToken.setSent(true);
		confirmationTokenService.save(confirmationToken);

		return true;

	}

	/**
	 *
	 *
	 * @param from
	 * @param password
	 * @param to
	 * @param subject
	 * @param messageText
	 * @throws MessagingException
	 *
	 * @author Feras Ejneid
	 */
	private void sendEmail(String from, String password, String to, String subject, String messageText)
			throws MessagingException {
		Properties prop = setSMTPProperties();
		Session session = checkEmailCredentials(from, password, prop);
		Message message = createMessage(from, to, subject, messageText, session);
		Transport.send(message);
	}

	/**
	 *
	 *
	 * @param from
	 * @param to
	 * @param subject
	 * @param messageText
	 * @param session
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 *
	 * @author Feras Ejneid
	 */
	private Message createMessage(String from, String to, String subject, String messageText, Session session)
			throws MessagingException, AddressException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(messageText, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);
		return message;
	}

	/**
	 *
	 *
	 * @param from
	 * @param password
	 * @param prop
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private Session checkEmailCredentials(String from, String password, Properties prop) {
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		return session;
	}

	/**
	 *
	 *
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private Properties setSMTPProperties() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		return prop;
	}

	/**
	 *
	 *
	 * @param modelAndView
	 * @param confirmationToken
	 * @param redirectAttributes
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public String confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken,
			final RedirectAttributes redirectAttributes) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if (token != null) {
			updateConfirmationToken(token);
			redirectAttributes.addFlashAttribute("confirmed", "Your account has been verfied. You can now sign in.");
			return "redirect:/login";
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			return "error/404";
		}
	}

	/**
	 *
	 *
	 * @param token
	 *
	 * @author Feras Ejneid
	 */
	private void updateConfirmationToken(ConfirmationToken token) {
		User user = userService.findByEmailIgnoreCase(token.getUser().getEmail());
		user.setEnabled(true);
		userService.save(user);
		token.setUsed(true);
		confirmationTokenRepository.save(token);
	}
}
