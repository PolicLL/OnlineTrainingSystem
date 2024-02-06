package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.MeasurementDTO;
import com.training.OnlineTraining.model.Measurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface MeasurementMapper {

	MeasurementMapper INSTANCE = Mappers.getMapper(MeasurementMapper.class);

	@Mapping(source = "contractId", target = "contract.id")
	@Mapping(source = "bodyWeight", target = "bodyWeight")
	@Mapping(source = "bodyFat", target = "bodyFat")
	@Mapping(source = "waistCircumference", target = "waistCircumference")
	@Mapping(source = "chestCircumference", target = "chestCircumference")
	@Mapping(source = "armCircumference", target = "armCircumference")
	@Mapping(source = "legCircumference", target = "legCircumference")
	@Mapping(source = "measurementDate", target = "measurementDate")
	Measurement toMeasurement(MeasurementDTO measurementDTO);

	MeasurementDTO toMeasurementDTO(Measurement measurement);

}