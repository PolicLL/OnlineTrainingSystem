package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.CoachDto;
import com.training.OnlineTraining.dto.CoachFilterParams;
import com.training.OnlineTraining.dto.UpdateCoachDTO;
import com.training.OnlineTraining.exceptions.UserNotFoundException;
import com.training.OnlineTraining.mapper.CoachMapper;
import com.training.OnlineTraining.mapper.CoachUserMapper;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.model.enums.Role;
import com.training.OnlineTraining.repository.CoachRepository;
import com.training.OnlineTraining.repository.UserRepository;
import com.training.OnlineTraining.service.CoachService;
import com.training.OnlineTraining.service.UserService;
import com.training.OnlineTraining.specification.CoachSpecifications;
import com.training.OnlineTraining.util.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {

	private final UserService userService;
	private final CoachRepository coachRepository;
	private final CoachMapper coachMapper;
	private final CoachUserMapper coachUserMapper;
	private final UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);


	@Transactional
	@Override
	public Coach registerCoach(CoachDto coachDto, UUID userId) {

		var optionalUser = Optional.ofNullable(userService.getUserById(userId));

		coachDto.setCoachUserDTO(coachUserMapper.toCoachUserDTO(optionalUser.get()));

		var coachForSaving = coachMapper.coachDtoToCoach(coachDto);

		optionalUser.ifPresentOrElse(
				user -> {
					coachRepository.save(coachForSaving);
					user.setRole(Role.COACH);
					userRepository.save(user);

				},
				() -> {
					throw new UserNotFoundException(userId);
				}
		);

		return coachForSaving;
	}

	@Override
	public boolean isCoach(User user) {

		return coachRepository.existsByUser(user);
	}

	@Override
	public List<CoachDto> getAllCoaches() {

		return coachRepository.findAll().stream()
				.map(coachMapper::coachToCoachDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<CoachDto> filterCoaches(CoachFilterParams filterParams) {

		logger.info("Filter Params: {}", filterParams);
		Specification<Coach> spec = buildSpecification(filterParams);

		var filteredCoaches = coachRepository.findAll(spec);

		return filteredCoaches.stream()
				.map(coachMapper::coachToCoachDto)
				.collect(Collectors.toList());
	}

	@Override
	public Page<CoachDto> coachesWithPagination(CoachFilterParams filterParams, Pageable pageable) {

		return coachRepository.findAll(pageable)
				.map(coachMapper::coachToCoachDto);
	}

	@Override
	public Specification<Coach> buildSpecification(CoachFilterParams filterParams) {

		return Specification
				.where(CoachSpecifications.filterByGender(filterParams.getGender()))
				.and(CoachSpecifications.filterByExperience(filterParams.getExperience()))
				.and(CoachSpecifications.filterByAge(filterParams.getAge()))
				.and(CoachSpecifications.filterByEducation(filterParams.getEducation()))
				.and(CoachSpecifications.filterByMonthlyPrice(filterParams.getMonthlyPrice()));
	}

	@Override
	public Double getMonthlyPriceById(UUID coachId) {

		return coachRepository.findMonthlyPriceById(coachId);
	}

	@Override
	public Coach findByUserId(UUID userId) {

		return coachRepository.findByUserId(userId);
	}

	@Override
	public Coach getCoachById(UUID coachId) {

		return coachRepository.findById(coachId).orElse(null);
	}

	@Override
	public boolean areInputsInvalid(UpdateCoachDTO request) {

		return ValidationUtils.isStringNullOrEmpty(request.getFirstName()) ||
				ValidationUtils.isStringNullOrEmpty(request.getLastName()) ||
				ValidationUtils.isStringNullOrEmpty(request.getStreet()) ||
				ValidationUtils.isStringNullOrEmpty(request.getCity()) ||
				ValidationUtils.isStringNullOrEmpty(request.getCountry()) ||
				ValidationUtils.isStringNullOrEmpty(request.getPhoneNumber()) ||
				ValidationUtils.isStringNullOrEmpty(request.getGender()) ||
				ValidationUtils.isAgeInvalid(request.getAge());
	}

	@Transactional
	@Override
	public void updateCoach(UUID coachId, UpdateCoachDTO updateCoachDTO) {

		if (areInputsInvalid(updateCoachDTO)) {
			throw new RuntimeException("Invalid coach input");
		}
		Coach coach = coachRepository.findById(coachId)
				.orElseThrow(() -> new EntityNotFoundException("Coach not found with ID: " + coachId));

		CoachMapper.INSTANCE.updateCoachFromDTO(updateCoachDTO, coach);

		coachRepository.save(coach);
	}

}
