package com.jobbies.jobbies.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jobbies.jobbies.dto.UserDTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User implements UserDetails{
	@Id
	private String id;
	private String fullname;
	@Email
	@Indexed(unique = true)
	private String email;
	private String password;
	private Boolean isEnabled;
	Set<Role> roles = new HashSet<>();
	Set<JobOffer> jobs = new HashSet<>();

	public User(UserDTO userDTO) {
		this.fullname = userDTO.fullname();
		this.email = userDTO.email();
		this.password = userDTO.password();
		this.isEnabled = true;
	}

	public void userUpdate(UserDTO userDTO) {
		if (userDTO.fullname() != null) {
			this.fullname = userDTO.fullname();
		}

		if (userDTO.email() != null) {
			this.email = userDTO.email();
		}

		if (userDTO.password() != null) {
			this.password = userDTO.password();
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		this.getRoles().forEach(role -> {
			authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName())));
			
			role.getPermissions().forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission)));
		});
		System.out.println(authorityList);
		return authorityList;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	

}
