package by.tms.service.mapper;

import by.tms.dto.RegUserDto;
import by.tms.entity.Address;
import by.tms.entity.Telephone;
import by.tms.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserDtoDoMapperServiceImpl implements UserDtoDoMapperService {

    @Override
    public User mappingUserDoFromUserRegDto(RegUserDto regUserDto) {
        User user = new User();
        user.setName(regUserDto.getUserName());
        user.setLogin(regUserDto.getUserLogin());
        user.setPassword(regUserDto.getUserPassword());
        user.setAge(regUserDto.getUserAge());

        Optional<Telephone> telephoneBox = mappingTelephone(regUserDto);
        if (telephoneBox.isPresent()) {
            user.setTelephone(telephoneBox.get());
        }

        Optional<List<Address>> addressesBox = mappingAddresses(regUserDto);
        if (addressesBox.isPresent()) {
            user.setAddresses(addressesBox.get());
        }

        return user;
    }


    private Optional<Telephone> mappingTelephone(RegUserDto regUserDto) {
        if (regUserDto.getTelephoneNumber().equals("") && regUserDto.getTelephoneProvider().equals("") && regUserDto.getTelephoneCategory().equals("")) {
            return Optional.empty();
        }
        Telephone telephone = new Telephone();
        telephone.setNumber(regUserDto.getTelephoneNumber());
        telephone.setProvider(regUserDto.getTelephoneProvider());
        telephone.setCategory(regUserDto.getTelephoneCategory());
        return Optional.of(telephone);
    }

    private Optional<List<Address>> mappingAddresses(RegUserDto regUserDto) {
        if (regUserDto.getAddressCity().equals("") && regUserDto.getAddressStreet().equals("") &&
                regUserDto.getAddressHouse() == 0 && regUserDto.getAddressFlat() == 0 && regUserDto.getAddressCategory().equals("")) {
            return Optional.of(new ArrayList<>());
        }
        Address address = new Address();
        address.setCity(regUserDto.getAddressCity());
        address.setStreet(regUserDto.getAddressStreet());
        address.setHouse(regUserDto.getAddressHouse());
        address.setFlat(regUserDto.getAddressFlat());
        address.setCategory(regUserDto.getAddressCategory());
        List<Address> addresses = Arrays.asList(address);
        return Optional.of(addresses);
    }
}
