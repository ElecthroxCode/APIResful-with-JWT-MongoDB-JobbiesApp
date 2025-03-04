package com.jobbies.jobbies.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jobbies.jobbies.dto.JobOfferDTO;
import com.jobbies.jobbies.dto.JobOfferResponseDTO;
import com.jobbies.jobbies.dto.UserDTO;
import com.jobbies.jobbies.dto.UserResponseDTO;
import com.jobbies.jobbies.service.JobOfferService;
import com.jobbies.jobbies.service.UserService;

@Service
public class UserServiceImpl implements UserService, JobOfferService{

	@Override
	public UserResponseDTO createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDTO getUserById(String idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void userDeleteById(String idUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserResponseDTO updateUser(String idUser, UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobOfferResponseDTO createJobOffer(JobOfferDTO jobOfferDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobOfferResponseDTO> getJobOfferAllPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteJobOffer(String idUser, String idJobOffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JobOfferResponseDTO updateJobOffer(String idUser, String idJobOffer) {
		// TODO Auto-generated method stub
		return null;
	}

}
