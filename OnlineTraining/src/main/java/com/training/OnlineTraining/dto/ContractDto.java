package com.training.OnlineTraining.dto;

import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDto {

	private UUID id;
	private Coach coach;
	private Client client;
	private Date startDate;
	private Date endDate;
	private Double monthlyPrice;

}

