package com.jobbies.jobbies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobbies.jobbies.dto.JobOfferDTO;
import com.jobbies.jobbies.dto.JobOfferResponseDTO;
import com.jobbies.jobbies.service.JobOfferService;

@RestController
@RequestMapping("/api")
public class JobOfferController {
	
	private JobOfferService jobOfferService;
	@Autowired
	public JobOfferController(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
	
	@GetMapping("/jobs-all/{idUser}/pg")
	public ResponseEntity<Page<JobOfferResponseDTO>> getJobOffersForUserPage(@PathVariable String idUser, Pageable pageable){
		return ResponseEntity.ok(jobOfferService.getJobOffersForUserPage(idUser, pageable));
	}
	
	@PostMapping("/{idUser}/jobs")
	public ResponseEntity<JobOfferResponseDTO> createJobOffer(String idUser, JobOfferDTO jobOfferDTO){
		return ResponseEntity.ok(jobOfferService.createJobOffer(idUser, jobOfferDTO));
	}
	
	@PutMapping("/{idUser}/jobs/{idJobOffer}")
	public ResponseEntity<JobOfferResponseDTO> updateJobOffer( String idUser, JobOfferDTO jobOfferDTO, String idJobOffer){
		return ResponseEntity.ok(jobOfferService.updateJobOffer(idUser, jobOfferDTO, idJobOffer));
	}
	
	@DeleteMapping("/{idUser}/jobs/{idJobOffer}")
	public ResponseEntity<Void> deleteJobOffer(String idUser, String idJobOffer){
		jobOfferService.deleteJobOffer(idUser, idJobOffer);
		return ResponseEntity.noContent().build();
	}
	

	
	
	

}
