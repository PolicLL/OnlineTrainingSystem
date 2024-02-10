package com.training.OnlineTraining.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "workout_plan")
public class WorkoutPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;


	@Column(name = "start_date")
	private Date startDate;


	@Column(name = "end_date")
	private Date endDate;


	@Column(name = "number_of_workouts")
	private int numberOfWorkouts;

	@OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Workout> workoutList = new ArrayList<>();

}
