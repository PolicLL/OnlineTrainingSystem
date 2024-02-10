package com.training.OnlineTraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
	private String body;
	private String subject;
	private String receiverEmail;

}
