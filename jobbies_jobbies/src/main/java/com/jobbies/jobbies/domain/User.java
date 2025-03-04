package com.jobbies.jobbies.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jobbies.jobbies.dto.UserDTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {
	@Id
	private String id;
	private String fullname;
	@Email
	@Indexed(unique = true)
	private String email;
	private String password;
	private Boolean isEnabled;
	Set<Role>roles = new HashSet<>();
	Set<JobOffer> jobs = new HashSet<>();
	
	public User(UserDTO userDTO) {
		this.fullname = userDTO.fullname();
		this.email = userDTO.email();
		this.password = userDTO.password();
		this.isEnabled = true;
	}
	
}
