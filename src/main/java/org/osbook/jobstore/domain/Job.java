package org.osbook.jobstore.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String title;

	@NotNull
	@Size(max = 4000)
	private String description;

	@Temporal(TemporalType.DATE)
	@NotNull
	private final Date submissionDate = new Date();

	private boolean filled = false;

	@ElementCollection
	private Set<String> skills = new HashSet<>();
	
	@ManyToOne
	@NotNull
	private Company company;

	public Job() {
	}

	public Job(String title, String description, boolean filled) {
		this.title = title;
		this.description = description;
		this.filled = filled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

}
