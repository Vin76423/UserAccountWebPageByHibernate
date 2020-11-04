package by.tms.service;

import by.tms.dao.UserDao;
import by.tms.entity.Address;
import by.tms.entity.Telephone;
import by.tms.entity.User;
import by.tms.service.exception.DuplicateUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;



    @Override
    public void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null!");
        } else if (userDao.existUserByLogin(user.getLogin())) {
            throw new DuplicateUserException();
        }
        userDao.saveUser(user);
    }

    @Override
    public Optional<User> getUserById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        return  userDao.getUserById(id);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login is null!");
        } else if (userDao.existUserByLogin(login)) {
            return userDao.getUserByLogin(login);
        }
        return Optional.empty();
    }

    @Override
    public boolean existUserById(long id) {
        return userDao.existUserById(id);
    }

    @Override
    public boolean existUserByLogin(String login) {
        return userDao.existUserByLogin(login);
    }


    // Set/update/delete address:.....................................
    @Override
    public Optional<User> setAddress(Address address, long userId) { return userDao.setAddress(address, userId); }

    @Override
    public Optional<User> updateAddress(Address address, long userId) { return userDao.updateAddress(address, userId); }

    @Override
    public Optional<User> deleteAddress(long addressId, long userId) { return userDao.deleteAddress(addressId, userId); }


    // Set/update/delete telephone:.............................
    @Override
    public Optional<User> setTelephone(Telephone telephone, long userId) { return userDao.setTelephone(telephone, userId); }

    @Override
    public Optional<User> updateTelephone(Telephone telephone, long userId) { return userDao.updateTelephone(telephone, userId); }

    @Override
    public Optional<User> deleteTelephone(long userId) { return userDao.deleteTelephone(userId); }


    // Delete user account:............................................
    @Override
    public void deleteUserAccount(long userId) { userDao.deleteUser(userId); }
}
