package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.CoachDto;
import com.training.OnlineTraining.dto.CoachFilterParams;
import com.training.OnlineTraining.dto.UpdateCoachDTO;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;


public interface CoachService {

	Coach registerCoach(CoachDto coachDto, UUID userId);

	boolean isCoach(User user);

	List<CoachDto> getAllCoaches();

	List<CoachDto> filterCoaches(CoachFilterParams filterParams);

	Page<CoachDto> coachesWithPagination(CoachFilterParams filterParams, Pageable pageable);

	Specification<Coach> buildSpecification(CoachFilterParams filterParams);

	Double getMonthlyPriceById(UUID coachId);

	Coach findByUserId(UUID userId);

	void updateCoach(UUID coachId, UpdateCoachDTO updateCoachDTO);

	Coach getCoachById(UUID coachId);

	boolean areInputsInvalid(UpdateCoachDTO request);

}
