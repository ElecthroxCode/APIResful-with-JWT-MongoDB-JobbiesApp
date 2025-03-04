package com.jobbies.jobbies.dto;

import java.time.LocalDate;

import com.jobbies.jobbies.domain.JobOffer;



public record JobOfferResponseDTO(
		String id,
		String nameCompany,
		String rolJob,
		String url,
		String status,
		LocalDate date
		) {
	public JobOfferResponseDTO(JobOffer jobOffer) {
		this(jobOffer.getId().toHexString(), jobOffer.getNameCompany(), jobOffer.getRolJob(),
				jobOffer.getUrl(), jobOffer.getStatus(), jobOffer.getDate());
	}

}
