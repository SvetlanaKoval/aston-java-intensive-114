package ru.aston.intensive.hibernate.dao;

import ru.aston.intensive.hibernate.entity.User;

public interface UserDAO {

    Long save(User user);

    User findById(Long id);

    void update(User user);

    void delete(Long id);

}
