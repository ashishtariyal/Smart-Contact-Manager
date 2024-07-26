package com.s.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.s.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email=:email")
	public User getuserByUsername(@Param("email") String email);
    
    
    
}
