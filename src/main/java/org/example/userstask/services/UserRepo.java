package org.example.userstask.services;

import jakarta.transaction.Transactional;
import org.example.userstask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface UserRepo
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Modifying
    @Transactional // @Modifying annotation should be wrapped up with @Transactional
    @Query(  value = "update users  set username = ?1 , email = ?2 , password_user = ?3,  updated = ?4 where user_id = ?5",
            nativeQuery = true)
    int updateUserById(String username,
                       String email,
                       String password,
                       LocalDateTime updatedAt,
                       Long id);

    @Query(value
            = "select count(*) "
            + "from users u "
            + "where u.username = :username ",
            nativeQuery = true)
    int checkUserExistByName(@Param("username") String username);
}
