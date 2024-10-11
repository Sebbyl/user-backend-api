package com.boot.User.API;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.io.IOException;
import java.io.File;


@Service
public class UserService {
	private final List<User> users = new ArrayList<>();
	private final ObjectMapper mapper = new ObjectMapper();
	private final File file = new File("users.json");
	
	public UserService() {
		loadUsers();
	}
	
	
	//save new updates to the user list
	public void saveUsers() {
		try {
			mapper.writeValue(file, users);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//load the user list
	private void loadUsers() {
		if(file.exists()) {
			try {
				List<User> loadedUsers = mapper.readValue(file,  new TypeReference<>() {}); 
				users.addAll(loadedUsers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//create a new user
	public User createUser(User user) {
		users.add(user);
		saveUsers();
		return user;
		
	}
	
	//get all existing users
	public List<User> getAllUsers(){
		return users;
	}
	
	
	//get a singular user by id
	public User getUserbyId(int id){
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	
	//update current user information
	public User updateUser(int id, User userDetails) {
		User userFound = getUserbyId(id);
		if(userFound != null) {
			userFound.setUsername(userDetails.getUsername());
			userFound.setFirstName(userDetails.getFirstName());
			userFound.setLastName(userDetails.getLastName());
			userFound.setEmail(userDetails.getEmail());
			userFound.setPhoneNumber(userDetails.getPhoneNumber());
			saveUsers();
			return userFound;
		}
			return null;
		
	}
	
	//delete existing user
	public boolean deleteUser(int id) {
		User userFound = getUserbyId(id);
		if(userFound != null) {
			users.remove(userFound);
			saveUsers();
			return true;
		}
		return false;
	}
	
}
