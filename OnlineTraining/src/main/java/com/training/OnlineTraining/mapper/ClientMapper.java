package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.UpdateClientDTO;
import com.training.OnlineTraining.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface ClientMapper {

	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

	@Mapping(target = "user.firstName", source = "updateClientDTO.firstName")
	@Mapping(target = "user.lastName", source = "updateClientDTO.lastName")
	@Mapping(target = "user.street", source = "updateClientDTO.street")
	@Mapping(target = "user.city", source = "updateClientDTO.city")
	@Mapping(target = "user.country", source = "updateClientDTO.country")
	@Mapping(target = "user.phoneNumber", source = "updateClientDTO.phoneNumber")
	@Mapping(target = "user.gender", source = "updateClientDTO.gender")
	@Mapping(target = "user.age", source = "updateClientDTO.age")
	@Mapping(target = "medicalCondition", source = "updateClientDTO.medicalCondition")
	@Mapping(target = "injuries", source = "updateClientDTO.injuries")
	void updateClientFromDTO(UpdateClientDTO updateClientDTO, @MappingTarget Client client);


}
