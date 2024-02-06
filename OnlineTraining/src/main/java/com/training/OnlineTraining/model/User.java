package com.training.OnlineTraining.model;

import com.training.OnlineTraining.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "User_table", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@Column(name = "first_name")
	private String firstName = "";

	@Column(name = "last_name")
	private String lastName = "";

	@Column
	private String email = "";

	@Column
	private String street = "";

	@Column
	private String city = "";

	@Column
	private String country = "";

	@Column(name = "phone_number")
	private String phoneNumber = "";

	@Column
	private String gender = "";

	@Column
	private Integer age = 1;

	@Column
	private String password = "";

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	public User(UUID id) {

		this.id = id;
	}

	public User(UUID id, String email, String password) {

		this.id = id;
		this.email = email;
		this.password = password;
	}

}