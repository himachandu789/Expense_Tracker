package com.chandu.repository;

import com.chandu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


public interface UserRepository extends JpaRepository<User,Integer> {


    @Query(value = "select u from user u where user.email =:email", nativeQuery = true)
    public User findByEmailAddress(String email);

    @Query(value = "select u from User u where u.email =:email")
    public User findByEmailAddressJPQL(String email);


    public User findByEmail(String email);
    public User findByNameLike(String pattern);
    public  User findByCreatedAtGreaterThanAndNameLike(Date CreateAt, String name);


}
