package by.tms.dao;

import by.tms.entity.Address;
import by.tms.entity.Telephone;
import by.tms.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(user);
        currentSession.flush();
        currentSession.clear();
    }

    @Override
    public Optional<User> getUserById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<User> optionalUser = Optional.ofNullable(currentSession.get(User.class, id));
        currentSession.clear();
        return optionalUser;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        String sql = "from User u where u.login = :login";

        User user = currentSession.createQuery(sql, User.class)
                .setParameter("login", login)
                .getSingleResult();
        Optional<User> optionalUser = Optional.of(user);

//        Так же можно воспользоваться методом getResultStream(), тогда проверка на наличие юзера в сервисе
//        будет больше не нужна, т.к. данный метод при отсутствии результата вернет просто пустой стрим (а
//        метод findAny() в таком случае отдаст пустой Optional) , в отличии от метода getSingleResult(),
//        который при отсутствии результата выдаст NoResultException :

//        Optional<User> optionalUser = currentSession.createQuery(sql, User.class)
//                .setParameter("login", login)
//                .getResultStream().findAny();

        currentSession.clear();
        return optionalUser;
    }

    @Override
    public boolean existUserById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        return getUserById(id).isPresent();
    }

    @Override
    public boolean existUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        Long isExistIndicator = (Long) currentSession.createQuery("select count(*) from User u where u.login = :login")
                .setParameter("login", login)
                .uniqueResult();
        return isExistIndicator > 0L;
    }

    @Override
    public void deleteUser(long userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();

//        Такой вариант удалит саму таблицу 'users' и таблицу соотношений 'users_address' но сами вложенные сущности останутся:

//        currentSession.createQuery("delete from User u where u.id = :userId")
//                .setParameter("userId", userId)
//                .executeUpdate();
//        currentSession.clear();

        User user = currentSession.get(User.class, userId);
        currentSession.delete(user);
        currentSession.flush();
        currentSession.clear();

    }




    // Set/update/delete Addresses:.............................................................

    @Override
    public Optional<User> setAddress(Address address, long userId) {
        if (address == null) {
            throw new IllegalArgumentException("Address is null!");
        } else if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();

        User user = currentSession.get(User.class, userId);
        user.getAddresses().add(address);
        currentSession.flush();
        currentSession.clear();

        return Optional.of(user);
    }

    @Override
    public Optional<User> updateAddress(Address address, long userId) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(address);
        currentSession.flush();
        User user = currentSession.get(User.class, userId);
        currentSession.clear();
        return Optional.of(user);
    }

    @Override
    public Optional<User> deleteAddress(long addressId, long userId) {
        if (addressId < 1) {
            throw new IllegalArgumentException("AddressId is not correct!");
        } else if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();

        User user = currentSession.get(User.class, userId);
        List<Address> addresses = user.getAddresses()
                .stream()
                .filter(address -> address.getId() != addressId)
                .collect(Collectors.toList());
        user.setAddresses(addresses);
        currentSession.flush();

        currentSession.createQuery("delete from Address a where a.id = :addressId")
                .setParameter("addressId", addressId)
                .executeUpdate();
        currentSession.clear();

        return Optional.of(user);
    }

    // Set/update/delete Telephone:.............................................................

    @Override
    public Optional<User> setTelephone(Telephone telephone, long userId) {
        if (telephone == null) {
            throw new IllegalArgumentException("Telephone is null!");
        } else if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();

//        Session currentSession = sessionFactory.getCurrentSession();
//        currentSession.persist(telephone);
//        String sql = "update User u set u.telephone = :telephone where u.id = :id";
//        int isUpdatedIndex = currentSession.createQuery(sql)
//                .setParameter("telephone", telephone)
//                .setParameter("id", userId)
//                .executeUpdate();
//        currentSession.clear();

        User user = currentSession.get(User.class, userId);
        user.setTelephone(telephone);
        currentSession.flush();
        currentSession.clear();

        return Optional.of(user);
    }

    @Override
    public Optional<User> updateTelephone(Telephone telephone, long userId) {
        if (telephone == null) {
            throw new IllegalArgumentException("Telephone is null!");
        } else if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(telephone);
        currentSession.flush();

//        currentSession.merge(telephone);
//        String sql = "update User u set u.telephone = :telephone where u.id = :id";
//        currentSession.createQuery(sql)
//                .setParameter("telephone", telephone)
//                .setParameter("id", userId)
//                .executeUpdate();
//        currentSession.flush();

        User user = currentSession.get(User.class, userId);
        currentSession.clear();
        return Optional.of(user);
    }

    @Override
    public Optional<User> deleteTelephone(long userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();

        User user = currentSession.get(User.class, userId);
        long telephoneId = user.getTelephone().getId();
        user.setTelephone(null);
        currentSession.flush();

        String sql = "delete from Telephone t where t.id = :id";
        int isUpdatedIndex = currentSession.createQuery(sql)
                .setParameter("id", telephoneId)
                .executeUpdate();

//        В отличии отличии от delete(user) , update(user) вложеные сущности не тронет:

//        Session currentSession = sessionFactory.getCurrentSession();
//        User user = currentSession.get(User.class, userId);
//        user.setTelephone(null);
//        currentSession.update(user);
//        currentSession.flush();

        currentSession.clear();
        return Optional.of(user);
    }
}
