package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, UUID> {

	List<WorkoutSession> findAllByWorkoutId(UUID workoutId);

	@Modifying
	@Query("DELETE FROM WorkoutSession ws WHERE ws.id = :workoutSessionId")
	void deleteByCustomQuery(@Param("workoutSessionId") UUID workoutSessionId);

}
