package com.jobbies.jobbies.dto;

import java.time.LocalDate;



public record JobOfferDTO(
		String nameCompany,
		String rolJob,
		String url,
		String status,
		LocalDate date
		) {

}
