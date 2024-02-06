package com.training.OnlineTraining.model;

import com.training.OnlineTraining.model.enums.Education;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;


@Entity
@Data
@Table
public class Coach {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "years_of_experience")
	private Double yearsOfExperience;


	@Enumerated(EnumType.STRING)
	@Column(name = "education")
	private Education education;

	@Column(name = "monthly_price")
	private Double monthlyPrice = 0.0;

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coach coach = (Coach) o;
		return Objects.equals(id, coach.id) ||
				(Objects.equals(user, coach.user) && Objects.equals(yearsOfExperience, coach.yearsOfExperience) && education == coach.education && Objects.equals(monthlyPrice, coach.monthlyPrice));
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, user, yearsOfExperience, education, monthlyPrice);
	}

}