package com.training.OnlineTraining.service.implementation;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;
import com.training.OnlineTraining.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

	private static final TemplateEngine templateEngine = new SpringTemplateEngine();

	public void generateReportFromHtml(WorkoutOutputDTO workout, OutputStream outputStream) {

		final String TEMPLATE_LOCATION = "report/workout_report_template.html";

		String template = loadTemplate(TEMPLATE_LOCATION);


		Context context = new Context();
		context.setVariable("workout", workout);


		String processedHtml = templateEngine.process(template, context);

		ConverterProperties converterProperties = new ConverterProperties();
		HtmlConverter.convertToPdf(processedHtml, outputStream, converterProperties);
	}

	private static String loadTemplate(String templateName) {

		try (InputStream inputStream = com.training.OnlineTraining.service.ReportService.class.getResourceAsStream("/templates/" + templateName);
		     InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

			StringBuilder template = new StringBuilder();
			char[] buffer = new char[1024];
			int bytesRead;
			while ((bytesRead = reader.read(buffer)) != -1) {
				template.append(buffer, 0, bytesRead);
			}

			return template.toString();

		} catch (IOException e) {
			throw new RuntimeException("Error loading Thymeleaf template", e);
		}
	}

}
