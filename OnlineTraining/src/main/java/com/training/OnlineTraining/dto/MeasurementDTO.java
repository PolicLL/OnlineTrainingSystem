package com.training.OnlineTraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.UUID;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class MeasurementDTO {

	private UUID id;

	private UUID contractId;

	private Date measurementDate;

	@Min(value = 1, message = "Body Weight must be greater than or equal to 1")
	private Double bodyWeight;

	@Min(value = 1, message = "Body Fat must be greater than or equal to 1")
	private Double bodyFat;

	@Min(value = 10, message = "Waist Circumference must be greater than or equal to 10")
	private Double waistCircumference;

	@Min(value = 10, message = "Chest Circumference must be greater than or equal to 10")
	private Double chestCircumference;

	@Min(value = 10, message = "Arm Circumference must be greater than or equal to 10")
	private Double armCircumference;

	@Min(value = 10, message = "Leg Circumference must be greater than or equal to 10")
	private Double legCircumference;

}
