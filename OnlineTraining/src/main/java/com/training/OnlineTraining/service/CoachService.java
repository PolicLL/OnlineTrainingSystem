package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.CoachDTO;
import com.training.OnlineTraining.dto.CoachFilterParamsDTO;
import com.training.OnlineTraining.dto.UpdateCoachDTO;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;


public interface CoachService {

	Coach registerCoach(CoachDTO coachDto, UUID userId);

	boolean isCoach(User user);

	List<CoachDTO> getAllCoaches();

	List<CoachDTO> filterCoaches(CoachFilterParamsDTO filterParams);

	Page<CoachDTO> coachesWithPagination(CoachFilterParamsDTO filterParams, Pageable pageable);

	Specification<Coach> buildSpecification(CoachFilterParamsDTO filterParams);

	Double getMonthlyPriceById(UUID coachId);

	Coach findByUserId(UUID userId);

	void updateCoach(UUID coachId, UpdateCoachDTO updateCoachDTO);

	Coach getCoachById(UUID coachId);

	boolean areInputsInvalid(UpdateCoachDTO request);

}
