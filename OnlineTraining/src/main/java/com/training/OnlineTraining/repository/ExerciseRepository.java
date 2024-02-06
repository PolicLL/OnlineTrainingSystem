package com.training.OnlineTraining.repository;

import com.training.OnlineTraining.model.Exercise;
import com.training.OnlineTraining.model.enums.WorkoutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {

	List<Exercise> findAllByWorkoutType(WorkoutType workoutType);

	int countByWorkoutType(WorkoutType workoutType);

}
