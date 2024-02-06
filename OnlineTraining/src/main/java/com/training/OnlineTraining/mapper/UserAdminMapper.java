package com.training.OnlineTraining.mapper;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface UserAdminMapper {

	UserAdminMapper INSTANCE = Mappers.getMapper(UserAdminMapper.class);

	@Mapping(target = "password", ignore = true)
	User mapDtoToEntity(UserDto userDto);

	UserDto convertToDto(User user);

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	void updateEntityFromDto(UserDto userDto, @MappingTarget User user);

}
