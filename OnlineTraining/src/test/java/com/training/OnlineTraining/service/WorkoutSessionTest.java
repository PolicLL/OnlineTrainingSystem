package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.WorkoutSessionDTO;
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

	private WorkoutSessionDTO workoutSessionDTO;

	private UUID workoutID = null, exerciseID = null;

	private int numberOfWorkoutSessionsInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp() {
		numberOfWorkoutSessionsInDatabaseBeforeTest = workoutSessionService.getAllWorkoutSessions().size();

		workoutID = workoutService.getAllWorkouts().get(0).getId();
		exerciseID = exerciseService.getAllExercises().get(0).getId();

		workoutSessionDTO = TestDTOUtils.getWorkoutSessionDTO(workoutID, exerciseID); // Implement TestDTOUtils for WorkoutSessionDTO if not existing
	}

	@Test
	public void testCreateWorkoutSession() {
		WorkoutSessionDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionDTO);

		assertNotNull(newWorkoutSession);

		List<WorkoutSessionDTO> workoutSessions = workoutSessionService.getAllWorkoutSessions();
		assertEquals(numberOfWorkoutSessionsInDatabaseBeforeTest + 1, workoutSessions.size());
	}

	@Test
	public void testGetWorkoutSessionById() {
		workoutSessionDTO.setNumberOfReps(1000);

		WorkoutSessionDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionDTO);

		WorkoutSessionDTO retrievedWorkoutSession = workoutSessionService.getWorkoutSessionById(newWorkoutSession.getId());

		assertNotNull(retrievedWorkoutSession);
		assertEquals(1000, retrievedWorkoutSession.getNumberOfReps());
	}

	@Test
	public void testGetAllWorkoutSessions() {
		workoutSessionService.createWorkoutSession(workoutSessionDTO);
		workoutSessionService.createWorkoutSession(workoutSessionDTO);
		workoutSessionService.createWorkoutSession(workoutSessionDTO);

		List<WorkoutSessionDTO> workoutSessionList = workoutSessionService.getAllWorkoutSessions();

		assertNotNull(workoutSessionList);
		assertEquals(numberOfWorkoutSessionsInDatabaseBeforeTest + 3, workoutSessionList.size());
	}



	@Test
	public void testUpdateWorkoutSession() {
		WorkoutSessionDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionDTO);

		WorkoutSessionDTO updatedWorkoutSessionDTO = TestDTOUtils.getWorkoutSessionDTO(workoutID, exerciseID);

		updatedWorkoutSessionDTO.setNumberOfReps(1500);

		WorkoutSessionDTO updatedWorkoutSession = workoutSessionService.updateWorkoutSession(newWorkoutSession.getId(), updatedWorkoutSessionDTO);

		assertNotNull(updatedWorkoutSession);
		assertEquals(1500, updatedWorkoutSession.getNumberOfReps());
	}

	@Test
	public void testDeleteWorkoutSession() {
		WorkoutSessionDTO newWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionDTO);

		workoutSessionService.deleteWorkoutSession(newWorkoutSession.getId());

		assertEquals(numberOfWorkoutSessionsInDatabaseBeforeTest, workoutSessionService.getAllWorkoutSessions().size());
	}

}
