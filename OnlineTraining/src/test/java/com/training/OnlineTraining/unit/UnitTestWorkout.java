package com.training.OnlineTraining.unit;

import com.training.OnlineTraining.model.Workout;
import com.training.OnlineTraining.model.WorkoutSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestWorkout {

	@Test
	public void testGetDuration(){
		Workout workout = new Workout();

		WorkoutSession workoutSession = new WorkoutSession();
		workoutSession.setNumberOfReps(10);
		workoutSession.setPauseAfterExerciseInSeconds(30);
		// 10 * 5 + 30 = 80

		WorkoutSession workoutSession2 = new WorkoutSession();
		workoutSession2.setNumberOfReps(20);
		workoutSession2.setPauseAfterExerciseInSeconds(20);
		// 20 * 5 + 20 = 120

		workout.setNumberOfExercises(2);
		workout.setNumberOfSets(5);
		workout.setPauseBetweenSetsInSeconds(100);
		workout.setWarmingUpTimeInSeconds(200);
		// 5 * (80 + 120) + 5 * 100 + 200 = 1000 + 500 + 200
		// 1600 sec


		workout.getWorkoutSessions().add(workoutSession);
		workout.getWorkoutSessions().add(workoutSession2);

		assertEquals(1600, workout.getDuration().getDurationInSeconds());

	}

	@Test
	public void testGetDurationToString(){
		Workout workout = new Workout();

		WorkoutSession workoutSession = new WorkoutSession();
		workoutSession.setNumberOfReps(10);
		workoutSession.setPauseAfterExerciseInSeconds(30);
		// 10 * 5 + 30 = 80

		WorkoutSession workoutSession2 = new WorkoutSession();
		workoutSession2.setNumberOfReps(20);
		workoutSession2.setPauseAfterExerciseInSeconds(20);
		// 20 * 5 + 20 = 120

		workout.setNumberOfExercises(2);
		workout.setNumberOfSets(5);
		workout.setPauseBetweenSetsInSeconds(100);
		workout.setWarmingUpTimeInSeconds(200);
		// 5 * (80 + 120) + 5 * 100 + 200 = 1000 + 500 + 200
		// 1600 sec


		workout.getWorkoutSessions().add(workoutSession);
		workout.getWorkoutSessions().add(workoutSession2);


		String expected = "00h:26m:40s";
		assertTrue(workout.getDuration().getFormatedDuration().equals(expected));

	}

	@Test
	public void testGetDuration2(){
		Workout workout = new Workout();

		WorkoutSession workoutSession = new WorkoutSession();
		workoutSession.setNumberOfReps(8);
		workoutSession.setPauseAfterExerciseInSeconds(60);
		// 8 * 5 + 60 = 100

		WorkoutSession workoutSession2 = new WorkoutSession();
		workoutSession2.setNumberOfReps(12);
		workoutSession2.setPauseAfterExerciseInSeconds(40);
		// 12 * 5 + 40 = 100

		workout.setNumberOfSets(4);
		workout.setPauseBetweenSetsInSeconds(100);
		workout.setWarmingUpTimeInSeconds(300);
		// 4 * (100 + 100) + 3 * 100 + 300 = 800 + 300 + 300
		// 1400 sec


		workout.getWorkoutSessions().add(workoutSession);
		workout.getWorkoutSessions().add(workoutSession2);

		assertEquals(1400, workout.getDuration().getDurationInSeconds());

		String expected = "00h:23m:20s";
		assertTrue(workout.getDuration().getFormatedDuration().equals(expected));

	}

	@Test
	public void testGetDurationEverythingZero(){
		Workout workout = new Workout();
		assertEquals(0, workout.getDuration().getDurationInSeconds());

		String expected = "00h:00m:00s";
		assertTrue(workout.getDuration().getFormatedDuration().equals(expected));
	}

	@Test
	public void testGetDurationNoWorkoutSessions(){
		Workout workout = new Workout();

		workout.setWarmingUpTimeInSeconds(500);

		assertEquals(500, workout.getDuration().getDurationInSeconds());

		String expected = "00h:08m:20s";
		assertTrue(workout.getDuration().getFormatedDuration().equals(expected));

	}

	@Test
	public void testGetDurationOnlyWorkoutSessions(){
		Workout workout = new Workout();

		WorkoutSession workoutSession = new WorkoutSession();
		workoutSession.setNumberOfReps(10);
		workoutSession.setPauseAfterExerciseInSeconds(100);
		// 150

		WorkoutSession workoutSession2 = new WorkoutSession();
		workoutSession2.setNumberOfReps(20);
		workoutSession2.setPauseAfterExerciseInSeconds(50);
		// 150

		workout.setNumberOfSets(5);
		workout.setPauseBetweenSetsInSeconds(200);
		workout.setWarmingUpTimeInSeconds(200);
		// 5 * (150 + 150) + 4 * 200 + 200 = 1500 + 800 + 200
		// 2500 sec


		workout.getWorkoutSessions().add(workoutSession);
		workout.getWorkoutSessions().add(workoutSession2);

		assertEquals(2500, workout.getDuration().getDurationInSeconds());

		String expected = "00h:41m:40s";
		assertTrue(workout.getDuration().getFormatedDuration().equals(expected));

	}


}
