package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.MeasurementDTO;
import com.training.OnlineTraining.exceptions.MeasurementNotFoundException;
import com.training.OnlineTraining.mapper.MeasurementMapper;
import com.training.OnlineTraining.model.Measurement;
import com.training.OnlineTraining.repository.MeasurementRepository;
import com.training.OnlineTraining.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class MeasurementServiceImpl implements MeasurementService {

	private final MeasurementRepository measurementRepository;
	private final MeasurementMapper measurementMapper;

	@Override
	public Measurement createMeasurement(MeasurementDTO measurementDto) {

		var measurement = measurementMapper.toMeasurement(measurementDto);
		return measurementRepository.save(measurement);
	}

	@Override
	public Page<Measurement> getMeasurementsByContractIdSortedByDatePageable(UUID contractId, PageRequest pageRequest) {

		return measurementRepository.findByContractIdOrderByMeasurementDateDesc(contractId, pageRequest);
	}

	@Override
	public List<Measurement> getMeasurementsByContractIdSortedByDateAsc(UUID contractId) {

		return measurementRepository.findByContractIdOrderByMeasurementDateAsc(contractId);
	}

	@Override
	public MeasurementDTO getMeasurementById(UUID measurementId) {

		var measurement = measurementRepository.findById(measurementId)
				.orElseThrow(() -> new MeasurementNotFoundException(measurementId));
		return measurementMapper.toMeasurementDTO(measurement);
	}

	public List<Measurement> getAllMeasurements() {

		return measurementRepository.findAll();
	}

	public int countMeasurements() {

		return measurementRepository.countMeasurements();
	}

	@Override
	public MeasurementDTO updateMeasurement(UUID measurementId, MeasurementDTO measurementDto) {

		var existingMeasurement = measurementRepository.findById(measurementId)
				.orElseThrow(() -> new MeasurementNotFoundException(measurementId));

		var updatedMeasurement = measurementMapper.toMeasurement(measurementDto);
		updatedMeasurement.setId(existingMeasurement.getId());

		return measurementMapper.toMeasurementDTO(measurementRepository.save(updatedMeasurement));
	}

	public void deleteMeasurement(UUID measurementId) {

		measurementRepository.deleteById(measurementId);
	}


}