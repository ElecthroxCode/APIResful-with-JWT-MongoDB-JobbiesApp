package com.jobbies.jobbies.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.jobbies.jobbies.domain.JobOffer;
import com.jobbies.jobbies.domain.User;
import com.jobbies.jobbies.dto.JobOfferDTO;
import com.jobbies.jobbies.dto.JobOfferResponseDTO;
import com.jobbies.jobbies.repository.UserRepository;
import com.jobbies.jobbies.service.JobOfferService;

@Service
public class JobOfferServiceImpl implements JobOfferService{
	private UserRepository userRepository;
	private MongoTemplate mongoTemplate;
	
	@Autowired
	public JobOfferServiceImpl(UserRepository userRepository, MongoTemplate mongoTemplate) {
		this.userRepository = userRepository;
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public JobOfferResponseDTO createJobOffer(String idUser, JobOfferDTO jobOfferDTO) {
		JobOffer jobOffer = new JobOffer(jobOfferDTO);
		User user = userRepository.findById(idUser).orElseThrow(() -> new NoSuchElementException("User not found."));
		user.getJobs().add(jobOffer);
		userRepository.save(user);
		return new JobOfferResponseDTO(jobOffer);
	}

	@Override
	public Page<JobOfferResponseDTO> getJobOffersForUserPage(String idUser, Pageable pageable) {
	    // Contar el número total de ofertas de trabajo
	    Aggregation countAgg = Aggregation.newAggregation(
	            Aggregation.match(Criteria.where("_id").is(idUser)),
	            Aggregation.unwind("jobs"),
	            Aggregation.count().as("total")
	    );
	  
		AggregationResults<Document> countResults = mongoTemplate.aggregate(countAgg, User.class, Document.class);
	    long total = countResults.getMappedResults().isEmpty() ? 0 : convertToLong(countResults.getMappedResults().get(0).get("total"));

	    // Agregación para la paginación
	   /* Aggregation agg = Aggregation.newAggregation(
	            Aggregation.match(Criteria.where("_id").is(idUser)),
	            Aggregation.unwind("jobs"),
	            Aggregation.skip(pageable.getOffset()),
	            Aggregation.limit(pageable.getPageSize())
	    );*/
	    
	    Aggregation agg = Aggregation.newAggregation(
		        Aggregation.match(Criteria.where("_id").is(idUser)),
		        Aggregation.unwind("jobs"),
		        Aggregation.project()
		                .and("jobs._id").as("_id")
		                .and("jobs.companyName").as("nameCompany")
		                .and("jobs.roleJob").as("rolJob")
		                .and("jobs.status").as("status")
		                .and("jobs.urlOffer").as("url")
		                .and("jobs.date").as("date"),
		        Aggregation.skip(pageable.getOffset()),
		        Aggregation.limit(pageable.getPageSize())
		);
	    
	    

	    AggregationResults<JobOffer> results = mongoTemplate.aggregate(agg, User.class, JobOffer.class);
	    List<JobOfferResponseDTO> jobOffers = results.getMappedResults().stream()
	            .map(JobOfferResponseDTO::new)
	            .toList();

	    return new PageImpl<>(jobOffers, pageable, total);
	}

	private long convertToLong(Object value) {
	    if (value instanceof Integer) {
	        return ((Integer) value).longValue();
	    } else if (value instanceof Long) {
	        return (Long) value;
	    } else {
	        // Manejar otros tipos o casos de error si es necesario
	        return 0; // Valor predeterminado en caso de error
	    }
	}

	

	@Override
	public void deleteJobOffer(String idUser, String idJobOffer) {
	    User user = userRepository.findById(idUser).orElseThrow(() -> new NoSuchElementException("User not found."));
	    user.getJobs().stream()
	            .filter(jobRemove -> jobRemove.getId().toHexString().equals(idJobOffer))
	            .findFirst().ifPresentOrElse(job -> user.getJobs().remove(job), () -> {
	                throw new NoSuchElementException("Job Offer not found.");
	            });
	    userRepository.save(user);
	}

	@Override
	public JobOfferResponseDTO updateJobOffer(String idUser, JobOfferDTO jobOferDTO, String idJobOffer) {
	    User user = userRepository.findById(idUser).orElseThrow(() -> new NoSuchElementException("User not found."));
	    JobOffer jobOffer = user.getJobs().stream()
	            .filter(jobRemove -> jobRemove.getId().toHexString().equals(idJobOffer))
	            .findFirst().orElseThrow(() -> new NoSuchElementException("Job Offer not found."));

	    jobOffer.jobOfferUpdate(jobOferDTO);
	    userRepository.save(user);
	    return new JobOfferResponseDTO(jobOffer);
	}

	

}
