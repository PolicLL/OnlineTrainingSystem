package com.training.OnlineTraining.specification;

import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.enums.Education;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;


public class CoachSpecifications {

	public static Specification<Coach> filterByGender(String gender) {

		return (root, query, criteriaBuilder) ->
				Optional.ofNullable(gender)
						.filter(s -> !s.isEmpty())
						.map(s -> criteriaBuilder.equal(root.get("user").get("gender"), s))
						.orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
	}

	public static Specification<Coach> filterByExperience(Double experience) {

		return (root, query, criteriaBuilder) ->
				Optional.ofNullable(experience)
						.map(d -> criteriaBuilder.greaterThanOrEqualTo(root.get("yearsOfExperience"), d))
						.orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
	}

	public static Specification<Coach> filterByAge(Integer age) {

		return (root, query, criteriaBuilder) ->
				Optional.ofNullable(age)
						.map(a -> criteriaBuilder.greaterThanOrEqualTo(root.get("user").get("age"), a))
						.orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
	}

	public static Specification<Coach> filterByEducation(String education) {

		return (root, query, criteriaBuilder) ->
				Optional.ofNullable(education)
						.filter(s -> !s.isEmpty())
						.map(s -> criteriaBuilder.equal(root.get("education"), Education.valueOf(s)))
						.orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
	}

	public static Specification<Coach> filterByMonthlyPrice(Double monthlyPrice) {

		return (root, query, criteriaBuilder) ->
				Optional.ofNullable(monthlyPrice)
						.map(d -> criteriaBuilder.lessThanOrEqualTo(root.get("monthlyPrice"), d))
						.orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
	}

}
