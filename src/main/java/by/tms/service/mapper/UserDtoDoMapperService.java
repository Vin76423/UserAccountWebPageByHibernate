package by.tms.service.mapper;

import by.tms.dto.RegUserDto;
import by.tms.entity.User;

public interface UserDtoDoMapperService {
    User mappingUserDoFromUserRegDto(RegUserDto regUserDto);
}
