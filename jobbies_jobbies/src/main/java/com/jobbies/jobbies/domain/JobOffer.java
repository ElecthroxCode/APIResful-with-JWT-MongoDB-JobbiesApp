package com.jobbies.jobbies.domain;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.jobbies.jobbies.dto.JobOfferDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "jobs")
public class JobOffer {
	@Id
	private ObjectId id;
	private String nameCompany;
	private String rolJob;
	private String url;
	@Field(targetType = FieldType.STRING)
	private String status;
	private LocalDate date;

	public JobOffer(JobOfferDTO jobOfferDTO) {
		this.id = new ObjectId();
		this.nameCompany = jobOfferDTO.nameCompany();
		this.url = jobOfferDTO.url();
		this.status = jobOfferDTO.status();
		this.date = jobOfferDTO.date();
	}

	public void jobOfferUpdate(JobOfferDTO jobOfferDTO) {
		if (jobOfferDTO.nameCompany() != null) {
			this.nameCompany = jobOfferDTO.nameCompany();
		}

		if (jobOfferDTO.url() != null) {
			this.url = jobOfferDTO.url();
		}

		if (jobOfferDTO.status() != null) {
			this.status = jobOfferDTO.status();
		}
		if (jobOfferDTO.date() != null) {
			this.date = jobOfferDTO.date();
		}

	}

}
