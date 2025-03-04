package com.jobbies.jobbies.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.jobbies.jobbies.domain.User;

public record UserResponseDTO(
		String id,
		String fullname,
		String email,
		String password,
		Boolean isEnabled,
		Set<RoleResponseDTO> roles,
		Set<JobOfferResponseDTO> jobs
		) {
	public UserResponseDTO(User user) {
		this(user.getId(), user.getFullname(), user.getEmail(), user.getPassword(),
				user.getIsEnabled(), user.getRoles().stream().map(RoleResponseDTO::new).collect(Collectors.toSet()),
				user.getJobs().stream().map(JobOfferResponseDTO::new).collect(Collectors.toSet()));
	}
}
