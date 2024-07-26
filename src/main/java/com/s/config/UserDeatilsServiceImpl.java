package com.s.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.s.Entity.User;
import com.s.repository.UserRepository;

public class UserDeatilsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// fetching user from database

		User user = userRepo.getuserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not found user!!");
		}
		CustomUserDeatils cud = new CustomUserDeatils(user);

		return cud;
	}

}
