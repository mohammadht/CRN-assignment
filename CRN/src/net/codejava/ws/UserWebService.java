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

@Path("/users")
public class UserWebService {

	UserDAOImpl userDao = UserDAOImpl.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listAll(){
		return userDao.listAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{unitId}/{time}")
	public List<User> listValid(@PathParam ("unitId") int unitId, @PathParam ("time") String time) throws ParseException{
		return userDao.listValid(unitId, time);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{unitId}")
	public List<User> listAll(@PathParam ("unitId") int unitId) throws ParseException{
		return userDao.listAll(unitId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(User user) throws URISyntaxException {
		int newUserId = userDao.create(user);
		URI uri = new URI("/CRN/rest/users/" + newUserId);
		return Response.created(uri).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/{version}")
	public Response update(@PathParam ("id") int id, @PathParam ("version") int version, User user) {
		user.setId(id);
		user.setVersion(version);
		if(userDao.update(user)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect user or a version mismatch!").build();
		}
	}
	
	@DELETE
	@Path("/{id}/{version}")
	public Response delete(@PathParam ("id") int id, @PathParam ("version") int version) {
		if(userDao.delete(id, version)) {
			return Response.ok().build();
		}else {
			return Response.notModified("Incorrect user or a version mismatch!").build();
		}
	}
}
