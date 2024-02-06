package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.input.WorkoutSessionInputDTO;
import com.training.OnlineTraining.dto.output.WorkoutSessionOutputDTO;
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
public class WorkoutSessionTest {

	@Autowired
	private WorkoutSessionService workoutSessionService;

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private ExerciseService exerciseService;

	private WorkoutSessionInputDTO workoutSessionInputDTO;

	private UUID workoutID = null, exerciseID = null;

	private int numberOfWorkoutSessionsInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp() {
		numberOfWorkoutSessionsInDatabaseBeforeTest = workoutSessionService.getAllWorkoutSessions().size();

		workoutID = workoutService.getAllWorkouts().get(0).getId();
		exerciseID = exerciseService.getAllExercises().get(0).getId();

		workoutSessionInputDTO = TestDTOUtils.getWorkoutSessionDTO(workoutID, exerciseID); // Implement TestDTOUtils for WorkoutSessionDTO if not existing
	}

	@Test
	public void testCreateWorkoutSession() {
		WorkoutSessionOutputDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionInputDTO);

		assertNotNull(newWorkoutSession);

		List<WorkoutSessionOutputDTO> workoutSessions = workoutSessionService.getAllWorkoutSessions();
		assertEquals(numberOfWorkoutSessionsInDatabaseBeforeTest + 1, workoutSessions.size());
	}

	@Test
	public void testGetWorkoutSessionById() {
		workoutSessionInputDTO.setNumberOfReps(1000);

		WorkoutSessionOutputDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionInputDTO);

		WorkoutSessionOutputDTO retrievedWorkoutSession = workoutSessionService.getWorkoutSessionById(newWorkoutSession.getId());

		assertNotNull(retrievedWorkoutSession);
		assertEquals(1000, retrievedWorkoutSession.getNumberOfReps());
	}

	@Test
	public void testGetAllWorkoutSessions() {
		workoutSessionService.createWorkoutSession(workoutSessionInputDTO);
		workoutSessionService.createWorkoutSession(workoutSessionInputDTO);
		workoutSessionService.createWorkoutSession(workoutSessionInputDTO);

		List<WorkoutSessionOutputDTO> workoutSessionList = workoutSessionService.getAllWorkoutSessions();

		assertNotNull(workoutSessionList);
		assertEquals(numberOfWorkoutSessionsInDatabaseBeforeTest + 3, workoutSessionList.size());
	}



	@Test
	public void testUpdateWorkoutSession() {
		WorkoutSessionOutputDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionInputDTO);

		WorkoutSessionInputDTO updatedWorkoutSessionInputDTO = TestDTOUtils.getWorkoutSessionDTO(workoutID, exerciseID);

		updatedWorkoutSessionInputDTO.setNumberOfReps(1500);

		WorkoutSessionOutputDTO updatedWorkoutSession = workoutSessionService.updateWorkoutSession(newWorkoutSession.getId(), updatedWorkoutSessionInputDTO);

		assertNotNull(updatedWorkoutSession);
		assertEquals(1500, updatedWorkoutSession.getNumberOfReps());
	}

	@Test
	public void testDeleteWorkoutSession() {
		WorkoutSessionOutputDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionInputDTO);

		workoutSessionService.deleteWorkoutSession(newWorkoutSession.getId());

		assertEquals(numberOfWorkoutSessionsInDatabaseBeforeTest, workoutSessionService.getAllWorkoutSessions().size());
	}

}
