package by.tms.service;

import by.tms.entity.Address;
import by.tms.entity.Telephone;
import by.tms.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> getUserById(long id);
    Optional<User> getUserByLogin(String login);
    boolean existUserById(long id);
    boolean existUserByLogin(String login);

    Optional<User> setAddress(Address address, long userId);
    Optional<User> updateAddress(Address address, long userId);
    Optional<User> deleteAddress(long addressId, long userId);

    Optional<User> setTelephone(Telephone telephone, long userId);
    Optional<User> updateTelephone(Telephone telephone, long userId);
    Optional<User> deleteTelephone(long userId);

    void deleteUserAccount(long userId);
}
