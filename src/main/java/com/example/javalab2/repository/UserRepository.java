package com.example.javalab2.repository;

import com.example.javalab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByNickName(String nickName);

    User findUserByPasswordAndEmail(String password, String email);

    User findUserByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM users WHERE nickname LIKE :nickName", nativeQuery = true)
    void deleteUserByNickName(@Param("nickName") String nickName);

    @Modifying
    @Query(value = "DELETE FROM users WHERE email LIKE :email", nativeQuery = true)
    void deleteUserByEmail(@Param("email") String email);
}
