package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.ContractDTO;
import com.training.OnlineTraining.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper(componentModel = "spring")
@Component
public interface ContractMapper {

	ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

	@Mapping(target = "coach.id", source = "coach.id")
	@Mapping(target = "client.id", source = "client.id")
	@Mapping(target = "startDate", source = "startDate")
	@Mapping(target = "endDate", source = "endDate")
	@Mapping(target = "monthlyPrice", source = "monthlyPrice")
	Contract mapDto(ContractDTO contractDto);

	@Mapping(target = "coach.id", source = "coach.id")
	@Mapping(target = "client.id", source = "client.id")
	@Mapping(target = "startDate", source = "startDate")
	@Mapping(target = "endDate", source = "endDate")
	@Mapping(target = "monthlyPrice", source = "monthlyPrice")
	ContractDTO mapToDto(Contract contract);

	List<ContractDTO> mapToDtoList(List<Contract> contracts);

}
