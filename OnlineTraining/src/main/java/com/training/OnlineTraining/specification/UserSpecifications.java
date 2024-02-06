package com.training.OnlineTraining.specification;

import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.model.enums.Role;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;


public class UserSpecifications {

	public static Specification<User> filterByRole(String role) {

		return (root, query, criteriaBuilder) ->
				Optional.ofNullable(role)
						.filter(s -> !s.isEmpty())
						.map(s -> criteriaBuilder.equal(root.get("role"), Role.valueOf(s)))
						.orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
	}

}
