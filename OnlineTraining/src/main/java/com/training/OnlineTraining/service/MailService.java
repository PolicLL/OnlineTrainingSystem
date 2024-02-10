package com.training.OnlineTraining.service;


import jakarta.mail.MessagingException;


public interface MailService {

	void sendEmailAfterRegistration(String email) throws MessagingException;

}
