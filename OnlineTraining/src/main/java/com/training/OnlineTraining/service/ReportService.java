package com.training.OnlineTraining.service;


import com.training.OnlineTraining.dto.WorkoutDTO;

import java.io.OutputStream;


public interface ReportService {

	void generateReportFromHtml(WorkoutDTO workout, OutputStream outputStream);

}
