package com.s.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.s.Entity.Contact;
import com.s.Entity.User;
import com.s.repository.ContactRepository;
import com.s.repository.UserRepository;

@RestController
public class SearchController {

	@Autowired
	private UserRepository repo;
	@Autowired
	private ContactRepository crepo;
	
	@GetMapping("/search/{query}")
	public ResponseEntity search( @PathVariable("query") String query,Principal p){
		
		String name = p.getName();
		  User user = this.repo.getuserByUsername(name);
		
		System.out.println(query);
		List<Contact> contact = this.crepo.findByNameContainingAndUser(query,user);
		return ResponseEntity.ok(contact);
		
	}
}
