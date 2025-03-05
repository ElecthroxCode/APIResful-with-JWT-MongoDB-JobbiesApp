package com.jobbies.jobbies.service;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jobbies.jobbies.dto.JobOfferDTO;
import com.jobbies.jobbies.dto.JobOfferResponseDTO;

public interface JobOfferService {
	JobOfferResponseDTO createJobOffer(String idUser, JobOfferDTO jobOfferDTO);
	void deleteJobOffer(String idUser, String idJobOffer);
	JobOfferResponseDTO updateJobOffer(String idUser, JobOfferDTO JobOfferDTO, String idJobOffer);
	Page<JobOfferResponseDTO> getJobOffersForUserPage(String idUser, Pageable pageable);


}
