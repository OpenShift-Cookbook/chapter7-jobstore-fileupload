package org.osbook.jobstore.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.osbook.jobstore.domain.Job;

@Stateless
public class JobService {

	@PersistenceContext(unitName = "jobstore")
	private EntityManager entityManager;

	public Job save(Job job) {
		entityManager.persist(job);
		return job;
	}

	public Job findById(Long id) {
		return entityManager.find(Job.class, id);
	}

	public List<Job> findAll() {
		return entityManager.createNamedQuery("Job.findAll", Job.class)
				.getResultList();
	}

	public void update(Job job) {
		entityManager.merge(job);
	}

	public void delete(Long id) {
		try {
			Job job = entityManager.getReference(Job.class, id);
			entityManager.remove(job);
		} catch (EntityNotFoundException e) {
			return;
		}
	}
}
