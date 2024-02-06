package com.training.OnlineTraining.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;


@Entity
@Data
@Table
public class Measurement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	private Contract contract;

	@Column(name = "measurement_date")
	private Date measurementDate;

	@Column(name = "body_weight")
	private Double bodyWeight;

	@Column(name = "body_fat")
	private Double bodyFat;

	@Column(name = "waist_circumference")
	private Double waistCircumference;

	@Column(name = "chest_circumference")
	private Double chestCircumference;

	@Column(name = "arm_circumference")
	private Double armCircumference;

	@Column(name = "leg_circumference")
	private Double legCircumference;

}
