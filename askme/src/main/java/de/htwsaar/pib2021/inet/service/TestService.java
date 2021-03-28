package de.htwsaar.pib2021.inet.service;

import java.util.List;
import java.util.Optional;

import de.htwsaar.pib2021.inet.model.Test;

public interface TestService {
	List<Test> findAllByCreaterId(Long id);

	Test save(Test test);

	void addTest(Test test);

	Optional<Test> findById(Long id);

	Boolean isAccessableByUser(Long testId, Long userId);

	void deleteById(Long id);

	void setTestAccess(Long testId, Long userId, int access);

	void setTotalMarkForTest(double totalMark, Long testId);
}
