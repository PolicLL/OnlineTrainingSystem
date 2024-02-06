package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.util.PasswordUtils;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public class UserMapper {

	public static User mapDtoToEntity(UserDto userDto) {

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setStreet(userDto.getStreet());
		user.setCity(userDto.getCity());
		user.setCountry(userDto.getCountry());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setGender(userDto.getGender());
		user.setAge(userDto.getAge());
		user.setRole(userDto.getRole());
		user.setPassword(PasswordUtils.hashPassword(userDto.getPassword())); // Call hashPassword from PasswordUtils
		return user;
	}

	public UserDto convertToDto(User user) {

		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setStreet(user.getStreet());
		userDto.setCity(user.getCity());
		userDto.setCountry(user.getCountry());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setGender(user.getGender());
		userDto.setAge(user.getAge());
		userDto.setRole(user.getRole());
		return userDto;
	}


}
