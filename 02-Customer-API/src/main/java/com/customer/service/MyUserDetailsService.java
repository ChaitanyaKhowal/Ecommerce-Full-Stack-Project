package com.customer.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customer.entities.CustomerEntity;
import com.customer.repository.CustomerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomerEntity c = customerRepo.findByCustomerEmail(email);
		return new User(c.getCustomerEmail(), c.getPassword(), Collections.emptyList());

	}

}
