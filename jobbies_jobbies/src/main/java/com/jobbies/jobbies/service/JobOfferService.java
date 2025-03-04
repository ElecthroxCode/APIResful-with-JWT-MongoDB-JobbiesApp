package com.jobbies.jobbies.service;

import java.util.List;

import com.jobbies.jobbies.dto.JobOfferDTO;
import com.jobbies.jobbies.dto.JobOfferResponseDTO;

public interface JobOfferService {
	JobOfferResponseDTO createJobOffer(JobOfferDTO jobOfferDTO);
	List<JobOfferResponseDTO> getJobOfferAllPage();
	void deleteJobOffer(String idUser, String idJobOffer);
	JobOfferResponseDTO updateJobOffer(String idUser, String idJobOffer);

}
