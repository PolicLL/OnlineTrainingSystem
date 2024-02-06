package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.WorkoutTemplate;
import com.training.OnlineTraining.dto.input.WorkoutInputDTO;
import com.training.OnlineTraining.model.enums.WorkoutGoal;
import com.training.OnlineTraining.model.enums.WorkoutType;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;



@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class WorkoutTemplateCreatorServiceTest {

	@Autowired
	private WorkoutTemplateCreatorService workoutTemplateCreatorService;

	@Test
	public void test(){

		WorkoutTemplate workoutTemplate = new WorkoutTemplate(WorkoutType.LEGS, WorkoutGoal.ENDURANCE, 0, WorkoutType.CORE);
		WorkoutInputDTO workout = workoutTemplateCreatorService.createWorkoutInputDTO(workoutTemplate);

		System.out.println(workout);

		Assertions.assertNotNull(workout);
	}

}
