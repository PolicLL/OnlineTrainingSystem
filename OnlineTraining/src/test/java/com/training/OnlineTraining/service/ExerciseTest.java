package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.ExerciseDTO;
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

	private ExerciseDTO exerciseDTO;

	private int numberOfExercisesInDatabaseBeforeTest;

	@BeforeEach
	public void setUp(){
		numberOfExercisesInDatabaseBeforeTest = exerciseService.getAllExercises().size();

		exerciseDTO = new ExerciseDTO("Exercise 1", "Description 1", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner, WorkoutType.LEGS);
	}

	@Test
	public void testCreateExercise() {
		ExerciseDTO newExercise = exerciseService.createExercise(exerciseDTO);

		assertNotNull(newExercise);
		assertEquals("Exercise 1", newExercise.getName());

		List<ExerciseDTO> exercises = exerciseService.getAllExercises();
		assertEquals(numberOfExercisesInDatabaseBeforeTest + 1, exercises.size());
	}

	@Test
	public void testGetAllExercises() {
		exerciseService.createExercise(exerciseDTO);

		ExerciseDTO exerciseDTO2 = new ExerciseDTO("Exercise 2", "Description 2", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner, WorkoutType.PUSH);
		exerciseService.createExercise(exerciseDTO2);

		ExerciseDTO exerciseDTO3 = new ExerciseDTO("Exercise 3", "Description 3", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner,  WorkoutType.PUSH);
		exerciseService.createExercise(exerciseDTO3);

		List<ExerciseDTO> exercises = exerciseService.getAllExercises();
		assertEquals(numberOfExercisesInDatabaseBeforeTest + 3, exercises.size());
	}

	@Test
	public void testGetExerciseById() {
		ExerciseDTO newExercise = exerciseService.createExercise(exerciseDTO);

		ExerciseDTO gotExercise = exerciseService.getExerciseById(newExercise.getId());

		assertNotNull(gotExercise);
		assertEquals("Exercise 1", gotExercise.getName());
	}

	@Test
	public void testUpdateExercise() {
		ExerciseDTO newExercise = exerciseService.createExercise(exerciseDTO);

		ExerciseDTO updatedExerciseDTO = new ExerciseDTO("Exercise UPDATE", "Description UPDATE", ExerciseEquipment.Barbell, ExerciseDifficultyLevel.Beginner, WorkoutType.PUSH);

		ExerciseDTO updateExercise = exerciseService.updateExercise(newExercise.getId(), updatedExerciseDTO);

		assertNotNull(updateExercise);
		assertEquals("Exercise UPDATE", updateExercise.getName());
		assertEquals("Description UPDATE", updateExercise.getDescription());
	}

	@Test
	public void testDeleteExercise() {
		ExerciseDTO newExercise = exerciseService.createExercise(exerciseDTO);
		exerciseService.deleteExercise(newExercise.getId());
		assertEquals(numberOfExercisesInDatabaseBeforeTest, exerciseService.getAllExercises().size());
	}

	@Test
	public void testDeleteAllExercises() {
		exerciseService.deleteAll();
		assertEquals(0, exerciseService.getAllExercises().size());
	}


}
