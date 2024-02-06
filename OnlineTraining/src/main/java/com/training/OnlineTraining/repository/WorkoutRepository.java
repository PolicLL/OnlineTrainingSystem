package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

	List<Workout> findByContractId(UUID contractId);

	Workout findTopByContractIdOrderByOrdinalNumberOfWorkoutDesc(UUID contractId);

}
