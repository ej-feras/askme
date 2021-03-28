package de.htwsaar.pib2021.inet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.htwsaar.pib2021.inet.model.FormView;
import de.htwsaar.pib2021.inet.model.User;
import de.htwsaar.pib2021.inet.security.UserDetailsImpl;
import de.htwsaar.pib2021.inet.service.ConfirmationTokenService;

import de.htwsaar.pib2021.inet.service.ResultService;
import de.htwsaar.pib2021.inet.service.TestService;
import de.htwsaar.pib2021.inet.service.UserService;
import de.htwsaar.pib2021.inet.utils.DateTimeUtils;

@Controller
public class RegistrationController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("testServiceImpl")
	private TestService testService;

	@Autowired
	@Qualifier("confirmationTokenServiceImpl")
	private ConfirmationTokenService confirmationTokenService;

	@Autowired
	@Qualifier("resultServiceImpl")
	private ResultService resultService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 *
	 *
	 * @param model
	 * @param error
	 * @param logout
	 * @param emailConfirmation
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout, String emailConfirmation) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		model.addAttribute("user", new User());
		return "sign-in-sign-up";
	}

	/**
	 *
	 *
	 * @param model
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "sign-in-sign-up";
	}

	/**
	 *
	 *
	 * @param model
	 * @param user
	 * @param role
	 * @param adminEmail
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String registration(Model model, User user, @Param("role") String role,
			@Param("adminEmail") String adminEmail) {
		User existingUser = userService.findByEmailIgnoreCase(user.getEmail());

		if (existingUser != null) {
			model.addAttribute("message", "This email " + user.getEmail() + " already exists!");
			return "sign-in-sign-up";
		}
		existingUser = userService.findByUsernameIgnoreCase(user.getUsername());
		if (existingUser != null) {
			model.addAttribute("message", "This User " + user.getUsername() + " already exists!");
			return "sign-in-sign-up";
		}

		if (role != null && !role.isEmpty() && role.equalsIgnoreCase("admin")) {
			user = createUserWithRole(user, role);
			user.setEnabled(true);
			userService.save(user);
		} else if (role != null && !role.isEmpty() && role.equalsIgnoreCase("user")) {
			User admin = userService.findByEmailIgnoreCase(adminEmail);
			if (admin == null) {
				model.addAttribute("message", "An Admin with the given email " + adminEmail + " dosn't exist!");
				return "sign-in-sign-up";
			}

			return createNormalUser(model, user, role, admin);
		}

		return "redirect:/login";
	}

	/**
	 *
	 *
	 * @param model
	 * @param user
	 * @param role
	 * @param admin
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private String createNormalUser(Model model, User user, String role, User admin) {
		user = createUserWithRole(user, role);
		user = userService.save(user);
		userService.assignUser(admin.getId(), user.getId());
		model.addAttribute("emailConfirmation", user.getEmail());
		return "sign-in-sign-up";
	}

	/**
	 *
	 *
	 * @param user
	 * @param role
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private User createUserWithRole(User user, String role) {
		user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
		user.setUsername(user.getUsername().trim().toLowerCase());
		user.setRole("ROLE_USER");
		if (role.equalsIgnoreCase("admin")) {
			user.setRole("ROLE_ADMIN");
		}
		return user;
	}

	/**
	 *
	 *
	 * @param userDetails
	 * @param model
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/")
	public String viewHomePage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		boolean hasUserRole = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
		if (hasUserRole)
			return "redirect:/tests";

		Long adminId = userDetails.getId();
		int testsNumber = testService.findAllByCreaterId(adminId).size();
		int usersNumber = userService.findAllActiveUsersByAdminId(adminId).size();
		int requestsNumber = findRequestsNumber(adminId);
		List<FormView> results  = findResultsFormViewByAdminId(adminId);
		int resultsNumber = results.size();
		
		Map<String, Long> restultsInMonths = DateTimeUtils.getResultsInMonths(results);
		Collection<Long> numberOfResultsInMonths = restultsInMonths.values();
		
		
		model.addAttribute("user", userDetails.getUser());
		model.addAttribute("testsNumber", testsNumber);
		model.addAttribute("usersNumber", usersNumber);
		model.addAttribute("requestsNumber", requestsNumber);
		model.addAttribute("resultsNumber", resultsNumber);
		model.addAttribute("numberOfResultsInMonths", numberOfResultsInMonths);
		
		return "adminHome";
	}

	/**
	 *
	 *
	 * @param adminId
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	private int findRequestsNumber(Long adminId) {
		List<User> usersRequests = userService.findAllUsersByAdminId(adminId).stream()
				.filter(user -> user.isEnabled() == false).collect(Collectors.toList());

		List<User> usersRecievedAConfirmMessage = confirmationTokenService.findUsersWithUnusedToken(adminId).stream()
				.filter(user -> user.isEnabled() == false).collect(Collectors.toList());

		usersRequests.removeAll(usersRecievedAConfirmMessage);

		return usersRequests.size();
	}

	/**
	 *
	 *
	 * @param adminId
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	public List<FormView> findResultsFormViewByAdminId(Long adminId) {
		List<Object[]> allResults = resultService.findAllByAdminId(adminId);

		List<FormView> resultsFormView = new ArrayList<FormView>();
		for (int i = 0; i < allResults.size(); i++) {
			String username = (String) allResults.get(i)[0];
			String testName = (String) allResults.get(i)[1];
			Date date = (Date) allResults.get(i)[2];
			Double totalMark = (Double) allResults.get(i)[3];
			Double grade = (Double) allResults.get(i)[4];
			Boolean passed = (Boolean) allResults.get(i)[5];
			FormView formView = new FormView(username, testName, date, totalMark, grade, passed);
			resultsFormView.add(formView);
		}
		return resultsFormView;
	}
}
