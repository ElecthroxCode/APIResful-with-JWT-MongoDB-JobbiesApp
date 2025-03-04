package com.jobbies.jobbies.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobbies.jobbies.domain.User;

public interface UserRepository extends MongoRepository<User, String>{
	User findByEmail(String email);
}
