package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.CoachDto;
import com.training.OnlineTraining.dto.UpdateCoachDTO;
import com.training.OnlineTraining.model.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = CoachUserMapper.class)
public interface CoachMapper {

	CoachMapper INSTANCE = Mappers.getMapper(CoachMapper.class);

	@Mapping(target = "coachUserDTO", source = "user")
	@Mapping(source = "yearsOfExperience", target = "yearsOfExperience")
	@Mapping(source = "education", target = "education")
	@Mapping(source = "id", target = "id")
	@Mapping(source = "monthlyPrice", target = "monthlyPrice")
	CoachDto coachToCoachDto(Coach coach);

	@Mapping(target = "user", source = "coachUserDTO")
	@Mapping(source = "yearsOfExperience", target = "yearsOfExperience")
	@Mapping(source = "education", target = "education")
	@Mapping(source = "id", target = "id")
	@Mapping(source = "monthlyPrice", target = "monthlyPrice")
	Coach coachDtoToCoach(CoachDto coachDto);

	@Mapping(target = "user.firstName", source = "updateCoachDTO.firstName")
	@Mapping(target = "user.lastName", source = "updateCoachDTO.lastName")
	@Mapping(target = "user.street", source = "updateCoachDTO.street")
	@Mapping(target = "user.city", source = "updateCoachDTO.city")
	@Mapping(target = "user.country", source = "updateCoachDTO.country")
	@Mapping(target = "user.phoneNumber", source = "updateCoachDTO.phoneNumber")
	@Mapping(target = "user.gender", source = "updateCoachDTO.gender")
	@Mapping(target = "user.age", source = "updateCoachDTO.age")
	void updateCoachFromDTO(UpdateCoachDTO updateCoachDTO, @MappingTarget Coach coach);

}