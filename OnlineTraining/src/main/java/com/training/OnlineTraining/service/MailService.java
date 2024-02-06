package com.training.OnlineTraining.service;


import jakarta.mail.MessagingException;


public interface MailService {

	void sendEmail(String toEmail, String body, String subject) throws MessagingException;

	void sendEmailAsync(String toEmail, String body, String subject) throws MessagingException;

}
