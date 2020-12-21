package net.codejava.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
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

@Path("/user-roles")
public class UserRoleWebService {

	UserRoleDAOImpl userRoleDao = UserRoleDAOImpl.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserRole> listAll(){
		return userRoleDao.listAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userId}/{unitId}")
	public List<UserRole> listAll(@PathParam ("userId") int userId, @PathParam ("unitId") int unitId){
		return userRoleDao.listAll(userId, unitId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userId}/{unitId}/{time}")
	public List<UserRole> listValid(@PathParam ("userId") int userId, @PathParam ("unitId") int unitId, @PathParam ("time") String time) throws ParseException{
		return userRoleDao.listValid(userId, unitId, time);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(UserRole userRole) throws URISyntaxException {
		int newUserRoleId = userRoleDao.create(userRole);
		URI uri = new URI("/CRN/rest/user-roles/" + newUserRoleId);
		return Response.created(uri).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/{version}")
	public Response update(@PathParam ("id") int id, @PathParam ("version") int version, UserRole userRole) {
		userRole.setId(id);
		userRole.setVersion(version);
		if(userRoleDao.update(userRole)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Invalid operation!").build();
		}
	}
	
	@DELETE
	@Path("/{id}/{version}")
	public Response delete(@PathParam ("id") int id, @PathParam ("version") int version) {
		if(userRoleDao.delete(id, version)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect user role or a version mismatch!").build();
		}
	}
}
