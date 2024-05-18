package com.example.javalab2.repositories;

import com.example.javalab2.entities.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByNickName(String nickName);

    User findUserByPasswordAndEmail(String password, String email);

    User findUserByEmail(String email);

    @Modifying
    @Query("DELETE FROM users WHERE nickname LIKE :nickName")
    void deleteUserByNickName(@Param("nickName") String nickName);

    @Modifying
    @Query("DELETE FROM users WHERE email LIKE :email")
    void deleteUserByEmail(@Param("email") String email);
}
