package com.cldiaz.springreact.projmanagetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cldiaz.springreact.projmanagetool.domain.User;
import com.cldiaz.springreact.projmanagetool.exception.UniqueUsernameException;
import com.cldiaz.springreact.projmanagetool.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		
		try {
			//encode password
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setConfirmPassword("");
			
			//check if username is unique
			newUser.setUsername(newUser.getUsername());
			//confirm that both passwords match
			
			//don't persist or show confirmPassword
			
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UniqueUsernameException("User " + newUser.getUsername() + " already exists");
		}
		
	}
	
}
