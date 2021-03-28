package de.htwsaar.pib2021.inet.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.htwsaar.pib2021.inet.model.User;
import de.htwsaar.pib2021.inet.security.UserDetailsImpl;
import de.htwsaar.pib2021.inet.service.UserService;

@Controller
public class ProfileController {
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 *
	 *
	 * @param userDetails
	 * @param model
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		User user = userDetails.getUser();
		model.addAttribute("user", user);
		return "profile";
	}

	/**
	 *
	 *
	 * @param userDetails
	 * @param user
	 * @param redirectAttributes
	 * @return
	 *
	 * @author Feras Ejneid
	 */
	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	public String updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute("user") User user,
			RedirectAttributes redirectAttributes) {
		User userToBeEdited = userDetails.getUser();
		if(!user.getFirstName().trim().isEmpty())
			userToBeEdited.setFirstName(user.getFirstName());
		if(!user.getLastName().trim().isEmpty())
			userToBeEdited.setLastName(user.getLastName());
		if(!user.getEmail().trim().isEmpty())
			userToBeEdited.setEmail(user.getEmail());
		if(!user.getPassword().isEmpty())
			userToBeEdited.setPassword(passwordEncoder.encode(user.getPassword()));
		if(!user.getInstitution().trim().isEmpty())
			userToBeEdited.setInstitution(user.getInstitution());
		if(!user.getAddress().getCity().trim().isEmpty())
			userToBeEdited.getAddress().setCity(user.getAddress().getCity());
		if(!user.getAddress().getStreet().trim().isEmpty())
			userToBeEdited.getAddress().setStreet(user.getAddress().getStreet());
		if(!user.getAddress().getCountry().trim().isEmpty())
			userToBeEdited.getAddress().setCountry(user.getAddress().getCountry());
		if(user.getAddress().getPostalCode() > 0)
			userToBeEdited.getAddress().setPostalCode(user.getAddress().getPostalCode());
		
		userService.update(userToBeEdited);
		redirectAttributes.addFlashAttribute("action", "updateProfile");

		return "redirect:/profile";
	}
}
