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

@Path("/roles")
public class RoleWebService {

	RoleDAOImpl roleDao = RoleDAOImpl.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> listAll(){
		return roleDao.listAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Role role) throws URISyntaxException {
		int newRoleId = roleDao.create(role);
		URI uri = new URI("/CRN/rest/roles/" + newRoleId);
		return Response.created(uri).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/{version}")
	public Response update(@PathParam ("id") int id, @PathParam ("version") int version, Role role) {
		role.setId(id);
		role.setVersion(version);
		if(roleDao.update(role)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect role or a version mismatch!").build();
		}
	}
	
	@DELETE
	@Path("/{id}/{version}")
	public Response delete(@PathParam ("id") int id, @PathParam ("version") int version) {
		if(roleDao.delete(id, version)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect role or a version mismatch!").build();
		}
	}
}
