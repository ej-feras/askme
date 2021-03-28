package de.htwsaar.pib2021.inet.service;

import java.util.List;
import java.util.Optional;

import de.htwsaar.pib2021.inet.model.Result;

public interface ResultService {
	Optional<Result> findById(Long id);

	Result findByTestId(Long testId);

	List<Result> findByExamineeId(Long examineeId);

	Result save(Result result);

	List<Object[]> findAllByAdminId(Long admiId);

	void deleteByExamineeId(Long examineeId);
}
