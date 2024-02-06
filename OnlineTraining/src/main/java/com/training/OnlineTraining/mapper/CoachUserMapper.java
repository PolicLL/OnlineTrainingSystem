package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.CoachUserDTO;
import com.training.OnlineTraining.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CoachUserMapper {

	CoachUserMapper INSTANCE = Mappers.getMapper(CoachUserMapper.class);

	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "email", target = "email")
	@Mapping(source = "street", target = "street")
	@Mapping(source = "city", target = "city")
	@Mapping(source = "country", target = "country")
	@Mapping(source = "phoneNumber", target = "phoneNumber")
	@Mapping(source = "gender", target = "gender")
	@Mapping(source = "age", target = "age")
	@Mapping(source = "id", target = "id")
	CoachUserDTO toCoachUserDTO(User user);

	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "email", target = "email")
	@Mapping(source = "street", target = "street")
	@Mapping(source = "city", target = "city")
	@Mapping(source = "country", target = "country")
	@Mapping(source = "phoneNumber", target = "phoneNumber")
	@Mapping(source = "gender", target = "gender")
	@Mapping(source = "age", target = "age")
	@Mapping(source = "id", target = "id")
	User toUser(CoachUserDTO coachUserDTO);

}