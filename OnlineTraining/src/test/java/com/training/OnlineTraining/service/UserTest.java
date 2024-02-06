package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.UserDto;
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

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class UserTest {
	@Autowired
	private UserService userService;

	private UserDto userDto;

	private int numberOfUsersInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp(){
		numberOfUsersInDatabaseBeforeTest = userService.countUsers();
		userDto = TestDTOUtils.getUserDTO();
	}

	@Test
	public void testCreateUser() {
		userDto.setAge(10);

		System.out.println(userDto);

		User newUser = userService.registerUser(userDto);

		assertNotNull(newUser);
		assertEquals(10, newUser.getAge());

		assertEquals(numberOfUsersInDatabaseBeforeTest + 1, userService.countUsers());
	}


	@Test
	public void testGetUserById() {
		userDto.setAge(10);

		System.out.println(userDto);

		User newUser = userService.registerUser(userDto);

		User retreivedUser = userService.getUserById(newUser.getId());

		assertNotNull(retreivedUser);
		assertEquals(10, retreivedUser.getAge());
	}

	@Test
	public void testAreInputsInvalid() {
		assertFalse(userService.areInputsInvalid(userDto));
	}

	@Test
	public void testAreInputsInvalid_Exception_Email() {
		userDto.setEmail("");

		assertThrows(RuntimeException.class, () -> {
			userService.registerUser(userDto);
		});
	}

	@Test
	public void testAreInputsInvalid_Exception_Age() {
		userDto.setAge(500);

		assertThrows(RuntimeException.class, () -> {
			userService.registerUser(userDto);
		});
	}

	@Test
	public void testDuplicateEmail_Exception() {
		userDto.setEmail("test@test.com");
		userService.registerUser(userDto);

		assertThrows(RuntimeException.class, () -> {
			userService.registerUser(userDto);
		});
	}


}
