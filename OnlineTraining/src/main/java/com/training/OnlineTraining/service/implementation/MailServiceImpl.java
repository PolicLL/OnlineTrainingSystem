package com.training.OnlineTraining.service.implementation;


import com.training.OnlineTraining.service.MailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender javaMailSender;

	@Async("mailThreadPoolTaskExecutor")
	@Override
	public void sendEmailAsync(String toEmail, String body, String subject) throws MessagingException {

		sendEmail(toEmail, body, subject);
		System.out.println("Sent email asynchronously");
	}

	@Override
	public void sendEmail(String toEmail, String body, String subject) throws MessagingException {

		var mimeMessage = javaMailSender.createMimeMessage();
		var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("onlinetrainingsystemteam@gmail.com");
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);

		javaMailSender.send(mimeMessage);
	}

}
