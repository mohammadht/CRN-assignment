package net.codejava.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

@Path("/units")
public class UnitWebService {

	UnitDAOImpl unitDao = UnitDAOImpl.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Unit> listAll(){
		return unitDao.listAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Unit unit) throws URISyntaxException {
		int newUnitId = unitDao.create(unit);
		URI uri = new URI("/CRN/rest/units/" + newUnitId);
		return Response.created(uri).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/{version}")
	public Response update(@PathParam ("id") int id, @PathParam ("version") int version, Unit unit) {
		unit.setId(id);
		unit.setVersion(version);
		if(unitDao.update(unit)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect unit or a version mismatch!").build();
		}
	}
	
	@DELETE
	@Path("/{id}/{version}")
	public Response delete(@PathParam ("id") int id, @PathParam ("version") int version) {
		if(unitDao.delete(id, version)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect unit or a version mismatch!").build();
		}
	}
}
