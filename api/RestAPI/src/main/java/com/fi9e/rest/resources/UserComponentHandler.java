package com.fi9e.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fi9e.rest.dto.UserDTO;
import com.fi9e.rest.filters.Authorized;
import com.fi9e.rest.helper.ApiResponse;
import com.fi9e.rest.services.UserService;
import com.fi9e.rest.services.UserServiceInterface;

/**
 * 
 * @author Christopher
 *
 */
@Path("/user")
public class UserComponentHandler {

	private UserServiceInterface userService;
	private ApiResponse api;
	
	@Inject
	public UserComponentHandler(UserServiceInterface users) {
		this.userService = users;
		this.api = new ApiResponse();
	}
	
	@GET
	@Path("/{id}")
	@Authorized
	public Response getUserById(@PathParam("id") int id) {
		UserDTO user = this.userService.getUserById(id);

		return this.api.success(user, "");
	}

}
