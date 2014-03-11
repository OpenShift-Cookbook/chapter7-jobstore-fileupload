package org.osbook.jobstore.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@NamedQueries({
	@NamedQuery(name = "Company.findAll", query = "SELECT NEW Company(c.id,c.name,c.description) FROM Company c"),
	@NamedQuery(name = "Company.findByName", query = "SELECT NEW Company(c.id,c.name,c.description) FROM Company c WHERE c.name =:name"),
	@NamedQuery(name = "Company.findById", query = "SELECT NEW Company(c.id,c.name,c.description) FROM Company c WHERE c.id =:id"),
})
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	@NotNull
	@Size(max = 100)
	private String name;

	@Column(length = 4000)
	@NotNull
	@Size(max = 4000)
	private String description;

	@NotNull
	@Email
	private String contactEmail;

	@OneToMany(mappedBy = "company")
	private final List<Job> jobs = new ArrayList<>();

	@Column(updatable = false)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date registeredOn = new Date();

	public Company() {
	}

	public Company(String name, String description, String contactEmail) {
		this.name = name;
		this.description = description;
		this.contactEmail = contactEmail;
	}

	public Company(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public List<Job> getJobs() {
		return jobs;
	}

}
