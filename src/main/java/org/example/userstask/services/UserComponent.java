package org.example.userstask.services;

import org.example.userstask.entity.User;
import org.example.userstask.web.model.request.UserRequest;
import java.util.List;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * service for work with table users
 */
public  interface  UserComponent {

    /**
     * Ищет пользователя по идентификатору и падает по ошибке, если не нашел.
     *
     * @param id идентификатор пользователя.
     * @return пользователь.
     */
    User findByIdOrDie(Long id);

    /**
     * Сохраняет пользователя.
     *
     * @param request пользователь для сохранения.
     * @return сохраненный пользователь.
     */
    User commit(UserRequest request) throws Exception;

    /**
     * Находит всех пользователей.
     *
     * @return список пользователей.
     */
    List<User> findAll();

    /**
     * Удалить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     */
    void deleteUserById(Long id);

    /**
     * Обновить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @param  request пользователь.
     */
    void updateUserById(Long id, UserRequest request);
}
