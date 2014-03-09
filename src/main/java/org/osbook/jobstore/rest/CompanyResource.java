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

import org.osbook.jobstore.domain.Company;
import org.osbook.jobstore.services.CompanyService;
import org.osbook.jobstore.utils.NumberUtils;

@Path("/companies")
public class CompanyResource {

	@Inject
	private CompanyService companyService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewCompany(@Valid Company company) {
		Company existingCompanyWithName = companyService.findByName(company
				.getName());
		if (existingCompanyWithName != null) {
			return Response
					.status(Status.NOT_ACCEPTABLE)
					.entity(String.format(
							"Company already exists with name: %s",
							company.getName())).build();
		}
		company = companyService.save(company);
		return Response.status(Status.CREATED).entity(company).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> showAll() {
		return companyService.findAll();
	}

	@Path("/{idOrName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Company showCompany(@PathParam("idOrName") String idOrName) {
		if (NumberUtils.isNumber(idOrName)) {
			return companyService.findById(Long.valueOf(idOrName));
		}
		return companyService.findByName(idOrName);
	}

	@Path("/{id}")
	@DELETE
	public Response deleteCompany(@PathParam("id") Long id) {
		boolean deleted = companyService.delete(id);
		if (deleted) {
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@Path("/{id}")
	@PUT
	public Response updateCompanyInformation(@PathParam("id") Long id,
			@Valid Company company) {
		Company existingCompany = companyService.findById(id);
		if (existingCompany == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(String.format("No company exists with id: %d", id))
					.build();
		}
		company.setId(id);
		companyService.update(company);
		return Response.ok().build();
	}

}
