package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

public interface IUserServices {
	User registerUser(User user);

	List<User> getUsers();

	void deleteUser(String email);

	User getUser(String email);
}
