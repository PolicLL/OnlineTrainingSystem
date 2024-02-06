package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.input.ExerciseInputDTO;
import com.training.OnlineTraining.dto.output.ExerciseOutputDTO;
import com.training.OnlineTraining.model.enums.ExerciseDifficultyLevel;
import com.training.OnlineTraining.model.enums.ExerciseEquipment;
import com.training.OnlineTraining.model.enums.WorkoutType;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class ExerciseTest {

	@Autowired
	private ExerciseService exerciseService;

	private ExerciseInputDTO exerciseInputDTO;

	private int numberOfExercisesInDatabaseBeforeTest;

	@BeforeEach
	public void setUp(){
		numberOfExercisesInDatabaseBeforeTest = exerciseService.getAllExercises().size();

		exerciseInputDTO = new ExerciseInputDTO("Exercise 1", "Description 1", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner, WorkoutType.LEGS);
	}

	@Test
	public void testCreateExercise() {
		ExerciseOutputDTO newExercise = exerciseService.createExercise(exerciseInputDTO);

		assertNotNull(newExercise);
		assertEquals("Exercise 1", newExercise.getName());

		List<ExerciseOutputDTO> exercises = exerciseService.getAllExercises();
		assertEquals(numberOfExercisesInDatabaseBeforeTest + 1, exercises.size());
	}

	@Test
	public void testGetAllExercises() {
		exerciseService.createExercise(exerciseInputDTO);

		ExerciseInputDTO exerciseInputDTO2 = new ExerciseInputDTO("Exercise 2", "Description 2", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner, WorkoutType.PUSH);
		exerciseService.createExercise(exerciseInputDTO2);

		ExerciseInputDTO exerciseInputDTO3 = new ExerciseInputDTO("Exercise 3", "Description 3", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner,  WorkoutType.PUSH);
		exerciseService.createExercise(exerciseInputDTO3);

		List<ExerciseOutputDTO> exercises = exerciseService.getAllExercises();
		assertEquals(numberOfExercisesInDatabaseBeforeTest + 3, exercises.size());
	}

	@Test
	public void testGetExerciseById() {
		ExerciseOutputDTO newExercise = exerciseService.createExercise(exerciseInputDTO);

		ExerciseOutputDTO gotExercise = exerciseService.getExerciseById(newExercise.getId());

		assertNotNull(gotExercise);
		assertEquals("Exercise 1", gotExercise.getName());
	}

	@Test
	public void testUpdateExercise() {
		ExerciseOutputDTO newExercise = exerciseService.createExercise(exerciseInputDTO);

		ExerciseInputDTO updatedExerciseInputDTO = new ExerciseInputDTO("Exercise UPDATE", "Description UPDATE", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner, WorkoutType.PUSH);

		ExerciseOutputDTO updateExercise = exerciseService.updateExercise(newExercise.getId(), updatedExerciseInputDTO);

		assertNotNull(updateExercise);
		assertEquals("Exercise UPDATE", updateExercise.getName());
		assertEquals("Description UPDATE", updateExercise.getDescription());
	}

	@Test
	public void testDeleteExercise() {
		ExerciseOutputDTO newExercise = exerciseService.createExercise(exerciseInputDTO);
		exerciseService.deleteExercise(newExercise.getId());
		assertEquals(numberOfExercisesInDatabaseBeforeTest, exerciseService.getAllExercises().size());
	}

	@Test
	public void testDeleteAllExercises() {
		exerciseService.deleteAll();
		assertEquals(0, exerciseService.getAllExercises().size());
	}


}
