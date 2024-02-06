package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.CoachDto;
import com.training.OnlineTraining.dto.UpdateCoachDTO;
import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.utils.TestDTOUtils;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class CoachServiceTest {

	@Autowired
	private CoachService coachService;

	@Autowired
	private UserService userService;

	private CoachDto coachDto;

	private UserDto userDto;

	private int numberOfCoachesInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp() {
		numberOfCoachesInDatabaseBeforeTest = coachService.getAllCoaches().size();
		userDto = TestDTOUtils.getUserDTO();
		coachDto = TestDTOUtils.getCoachDTO();
	}

	@Test
	public void testCreateCoach() {
		User newUser = userService.registerUser(userDto);

		coachDto.setYearsOfExperience(5.0);
		coachService.registerCoach(coachDto, newUser.getId());

		List<CoachDto> coachesAfterTest = coachService.getAllCoaches();
		assertEquals(numberOfCoachesInDatabaseBeforeTest + 1, coachesAfterTest.size());
	}

	@Test
	public void testGetCoachByCoachId() {
		User newUser = userService.registerUser(userDto);

		coachDto.setYearsOfExperience(5.0);

		Coach createdCoach = coachService.registerCoach(coachDto, newUser.getId());

		Coach retrievedCoach = coachService.getCoachById(createdCoach.getId());

		assertNotNull(retrievedCoach);
		assertEquals(5.0, retrievedCoach.getYearsOfExperience());
	}

	@Test
	public void testUpdateCoach() {
		User newUser = userService.registerUser(userDto);

		coachDto.setYearsOfExperience(5.0);

		//

		Coach createdCoach = coachService.registerCoach(coachDto, newUser.getId());

		UpdateCoachDTO updateCoachDto = TestDTOUtils.getUpdateCoachDTO();
		updateCoachDto.setMonthlyPrice(BigDecimal.valueOf(1000.0)); // Set any specific update value

		coachService.updateCoach(createdCoach.getId(), updateCoachDto);

		Coach retrievedCoach = coachService.getCoachById(createdCoach.getId());

		//

		assertNotNull(retrievedCoach);
		assertEquals(1000.0, retrievedCoach.getMonthlyPrice());
	}
}
