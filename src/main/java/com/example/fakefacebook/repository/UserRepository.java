package com.example.fakefacebook.repository;

import com.example.fakefacebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


   User findByName(String name);



}



//    @Query(value = "SELECT user FROM User user WHERE user.name = ?1 ")
//    User getTheUserWithName(@Param("name") String name);

