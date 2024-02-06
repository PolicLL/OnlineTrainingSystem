package com.training.OnlineTraining.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;


@Entity
@Data
@Table
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "medical_condition", columnDefinition = "TEXT")
	private String medicalCondition = "";

	@Column(name = "injuries", columnDefinition = "TEXT")
	private String injuries = "";

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Client client = (Client) o;
		return Objects.equals(id, client.id) ||
				(Objects.equals(user, client.user) && Objects.equals(medicalCondition, client.medicalCondition) && Objects.equals(injuries, client.injuries));
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, user, medicalCondition, injuries);
	}

}

