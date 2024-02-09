package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.WorkoutDTO;
import com.training.OnlineTraining.utils.TestDTOUtils;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class WorkoutTest {

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private ContractService contractService;

	private WorkoutDTO workoutDTO;

	private UUID contractID;

	private int numberOfWorkoutsInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp(){
		numberOfWorkoutsInDatabaseBeforeTest = workoutService.getAllWorkouts().size();

		contractID = contractService.getAllContracts().get(0).getId();

		workoutDTO = TestDTOUtils.getWorkoutDTO(contractID);
	}

	@Test
	public void testCreateWorkout() {

		workoutDTO.setSelfRating(1000);

		WorkoutDTO newWorkout = workoutService.createEmptyWorkout(workoutDTO, contractID);

		assertNotNull(newWorkout);
		assertEquals(1000, newWorkout.getSelfRating());

		List<WorkoutDTO> workouts = workoutService.getAllWorkouts();
		assertEquals(numberOfWorkoutsInDatabaseBeforeTest + 1, workouts.size());
	}

	@Test
	public void testGetAllExercises() {
		workoutService.createEmptyWorkout(workoutDTO, contractID);

		WorkoutDTO workoutDTO1 = TestDTOUtils.getWorkoutDTO(contractID);
		workoutService.createEmptyWorkout(workoutDTO1, contractID);

		WorkoutDTO workoutDTO2 = TestDTOUtils.getWorkoutDTO(contractID);
		workoutService.createEmptyWorkout(workoutDTO2, contractID);

		List<WorkoutDTO> workouts = workoutService.getAllWorkouts();
		assertEquals(numberOfWorkoutsInDatabaseBeforeTest + 3, workouts.size());
	}

	@Test
	public void testGetExerciseById() {
		workoutDTO.setSelfRating(1000);

		WorkoutDTO newWorkout = workoutService.createEmptyWorkout(workoutDTO, contractID);

		WorkoutDTO retrievedWorkout = workoutService.getWorkoutById(newWorkout.getId());

		assertNotNull(retrievedWorkout);
		assertEquals(1000, newWorkout.getSelfRating());
	}

	@Test
	public void testUpdateExercise() {
		workoutDTO.setSelfRating(1000);

		WorkoutDTO newWorkout = workoutService.createEmptyWorkout(workoutDTO, contractID);

		WorkoutDTO updatedWorkoutDTO = TestDTOUtils.getWorkoutDTO(contractID);
		updatedWorkoutDTO.setSelfRating(5500);

		WorkoutDTO updateWorkout = workoutService.updateWorkout(newWorkout.getId(), updatedWorkoutDTO);

		assertNotNull(updateWorkout);
		assertEquals(5500, updateWorkout.getSelfRating());
	}

	@Test
	public void testDeleteExercise() {
		WorkoutDTO newWorkout = workoutService.createEmptyWorkout(workoutDTO, contractID);
		workoutService.deleteWorkout(newWorkout.getId());
		assertEquals(numberOfWorkoutsInDatabaseBeforeTest, workoutService.getAllWorkouts().size());
	}


}
