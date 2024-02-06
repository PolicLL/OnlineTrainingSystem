package com.training.OnlineTraining.service;


import com.training.OnlineTraining.dto.output.WorkoutOutputDTO;

import java.io.OutputStream;


public interface ReportService {

	void generateReportFromHtml(WorkoutOutputDTO workout, OutputStream outputStream);

}
