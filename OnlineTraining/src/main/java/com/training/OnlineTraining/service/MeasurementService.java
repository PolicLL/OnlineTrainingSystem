package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.MeasurementDTO;
import com.training.OnlineTraining.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;


public interface MeasurementService {

	Measurement createMeasurement(MeasurementDTO measurementDTO);

	MeasurementDTO getMeasurementById(UUID measurementId);

	MeasurementDTO updateMeasurement(UUID measurementId, MeasurementDTO measurementDto);

	void deleteMeasurement(UUID measurementId);

	public List<Measurement> getMeasurementsByContractIdSortedByDateAsc(UUID contractId);

	public Page<Measurement> getMeasurementsByContractIdSortedByDatePageable(UUID contractId, PageRequest pageRequest);

	List<Measurement> getAllMeasurements();

	int countMeasurements();

}