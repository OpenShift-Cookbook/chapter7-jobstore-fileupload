package org.osbook.jobstore.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.osbook.jobstore.domain.Company;

@Stateless
public class CompanyService {

	@PersistenceContext(unitName = "jobstore")
	private EntityManager entityManager;

	public Company save(Company company) {
		entityManager.persist(company);
		return company;
	}

	public Company findById(Long id) {
		return entityManager.find(Company.class, id);
	}

	public List<Company> findAll() {
		return entityManager.createNamedQuery("Company.findAll", Company.class)
				.getResultList();
	}

	public Company findByName(String name) {
		return entityManager
				.createNamedQuery("Company.findByName", Company.class)
				.setParameter("name", name).getSingleResult();
	}

	public void update(Company company) {
		entityManager.merge(company);
	}

	public boolean delete(Long id) {
		try {
			Company company = entityManager.getReference(Company.class, id);
			entityManager.remove(company);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
}
