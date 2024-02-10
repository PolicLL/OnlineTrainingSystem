package com.training.OnlineTraining.service.implementation;


import com.training.OnlineTraining.dto.MailDTO;
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

	private final String ONLINE_TRAINING_SYSTEM_MAIL = "onlinetrainingsystemteam@gmail.com";
	// TODO -> Add this email to some configuration file

	@Async("mailThreadPoolTaskExecutor")
	@Override
	public void sendEmailAfterRegistration(String receiverEmail) throws MessagingException {

		MailDTO mailDTO = new MailDTO("Welcome to OnlineTrainingSystem!", "Registration Confirmation", receiverEmail);

		sendEmail(mailDTO);
	}

	private void sendEmail(MailDTO mailDTO) throws MessagingException {

		var mimeMessage = javaMailSender.createMimeMessage();
		var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom(ONLINE_TRAINING_SYSTEM_MAIL);
		mimeMessageHelper.setTo(mailDTO.getReceiverEmail());
		mimeMessageHelper.setText(mailDTO.getBody());
		mimeMessageHelper.setSubject(mailDTO.getSubject());

		javaMailSender.send(mimeMessage);
	}

}
