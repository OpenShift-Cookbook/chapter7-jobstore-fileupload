package org.osbook.jobstore.services;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.osbook.jobstore.domain.Company;
import org.osbook.jobstore.domain.Job;

@Stateless
public class JobService {

	@PersistenceContext(unitName = "jobstore")
	private EntityManager entityManager;
	@Inject
	private CompanyService companyService;

	public Job save(Long companyId, Job job) {
		Company company = companyService.findById(companyId);
		job.setCompany(company);
		company.getJobs().add(job);
		entityManager.persist(job);
		return job;
	}

	public Job findById(Long id) {
		return entityManager.createNamedQuery("Job.findById", Job.class).setParameter("jobId", id).getSingleResult();
	}

	public List<Job> findAllByCompany(Long companyId) {
		try {
			Company company = entityManager.getReference(Company.class, companyId);
			return entityManager
					.createNamedQuery("Job.findAllByCompany", Job.class)
					.setParameter("company", company).getResultList();
		} catch (EntityNotFoundException e) {
			return Collections.emptyList();
		}
	}

	public void update(Job job) {
		entityManager.merge(job);
	}

	public boolean delete(Long id) {
		try {
			Job job = entityManager.getReference(Job.class, id);
			entityManager.remove(job);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
}
