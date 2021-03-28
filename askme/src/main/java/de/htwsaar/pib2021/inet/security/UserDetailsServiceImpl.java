package de.htwsaar.pib2021.inet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htwsaar.pib2021.inet.model.User;
import de.htwsaar.pib2021.inet.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException("Could not find User");
		}

		return new UserDetailsImpl(user);
	}

}