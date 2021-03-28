package de.htwsaar.pib2021.inet.service;

import java.util.List;

import de.htwsaar.pib2021.inet.model.ConfirmationToken;
import de.htwsaar.pib2021.inet.model.User;

public interface ConfirmationTokenService {
	List<Long> findUsersIdsWithUnusedToken(Long adminId);

	List<User> findUsersWithUnusedToken(Long adminId);

	ConfirmationToken save(ConfirmationToken confirmationToken);

	void removeUserOfConfirmationToken(Long userId);

	void deleteByUserId(Long userId);
}
