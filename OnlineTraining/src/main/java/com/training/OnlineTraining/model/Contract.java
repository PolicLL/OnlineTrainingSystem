package com.training.OnlineTraining.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;


@Entity
@Data
@Table
@Getter
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "coach_id", referencedColumnName = "id")
	private Coach coach;

	@ManyToOne
	@JoinColumn(name = "client_id", referencedColumnName = "id")
	private Client client;


	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "monthly_price")
	private Double monthlyPrice;

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contract contract = (Contract) o;
		return Objects.equals(id, contract.id) ||
				(Objects.equals(coach, contract.coach) && Objects.equals(client, contract.client) && Objects.equals(startDate, contract.startDate) && Objects.equals(endDate, contract.endDate) && Objects.equals(monthlyPrice, contract.monthlyPrice));
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, coach, client, startDate, endDate, monthlyPrice);
	}

}
