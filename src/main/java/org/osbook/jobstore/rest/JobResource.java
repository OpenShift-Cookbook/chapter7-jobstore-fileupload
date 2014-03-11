package org.osbook.jobstore.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.osbook.jobstore.domain.Job;
import org.osbook.jobstore.services.JobService;

@Path("/companies/{companyId}/jobs")
public class JobResource {

	@Inject
	private JobService jobService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewJob(@PathParam("companyId") Long companyId,
			@Valid Job job) {
		job = jobService.save(companyId, job);
		return Response.status(Status.CREATED).entity(jobService.findById(job.getId())).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Job> showAll(@PathParam("companyId") Long companyId) {
		return jobService.findAllByCompany(companyId);
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Job showJob(@PathParam("companyId") Long companyId,@PathParam("id") Long id) {
		return jobService.findById(id);
	}

	@Path("/{id}")
	@DELETE
	public Response deleteJob(@PathParam("companyId") Long companyId,@PathParam("id") Long id) {
		boolean deleted = jobService.delete(id);
		if (deleted) {
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@Path("/{id}")
	@PUT
	public Response updateJobInformation(@PathParam("companyId") Long companyId,@PathParam("id") Long id,
			@Valid Job job) {
		Job existingJob = jobService.findById(id);
		if (existingJob == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(String.format("No job exists with id: %d", id))
					.build();
		}
		job.setId(id);
		jobService.update(job);
		return Response.ok().build();
	}

}
