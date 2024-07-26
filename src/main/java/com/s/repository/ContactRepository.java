package com.s.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.s.Entity.Contact;
import com.s.Entity.User;

public interface ContactRepository  extends JpaRepository<Contact, Integer>{
	
	//return a list of contacts associated with that user ID
	
	@Query("from Contact as c where c.user.id=:userid")
	public List<Contact> findContactsByUser( @Param("userid") int userid);

	//search-->
	
	@Query("SELECT c FROM Contact c WHERE c.cname LIKE %:cname% AND c.user = :user")
	List<Contact> findByNameContainingAndUser(@Param("cname") String cname, @Param("user") User user);


	
}

