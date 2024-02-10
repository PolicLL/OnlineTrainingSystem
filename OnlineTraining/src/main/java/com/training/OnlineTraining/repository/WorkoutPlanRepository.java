package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {
	default boolean doesNotExistById(UUID id) {
		return !existsById(id);
	}
}
