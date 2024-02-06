package com.training.OnlineTraining.dto;

import com.training.OnlineTraining.model.enums.Role;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;


@Data
@ToString
public class UserDto {

	private UUID id;
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String street = "";
	private String city = "";
	private String country = "";
	private String phoneNumber = "";
	private String gender = "";
	private Integer age = 1;
	private String password = "";
	private Role role = Role.USER;

	public UserDto() {

	}

	public UserDto(String email, String password) {

		this.email = email;
		this.password = password;
	}

}
