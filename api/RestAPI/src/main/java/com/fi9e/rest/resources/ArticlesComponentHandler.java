package com.fi9e.rest.resources;

import java.util.List;

import javax.inject.Inject;
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

import com.fi9e.rest.dto.ArticleDTO;
import com.fi9e.rest.exceptions.ApiException;
import com.fi9e.rest.filters.Authorized;
import com.fi9e.rest.helper.ApiResponseInterface;
import com.fi9e.rest.managers.ArticleManager;

/**
 * ENDPOINT: API: ARTICLES
 * 
 * @author Christopher
 *
 */
@Path("/article")
public class ArticlesComponentHandler {
	private ArticleManager mngr;
	private ApiResponseInterface api;
	
	@Inject
	public ArticlesComponentHandler(ApiResponseInterface api) {
		this.api = api;
		this.mngr = new ArticleManager();
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Authorized
	public Response store(ArticleDTO articleDTO) throws ApiException {
		
		this.mngr.validate(articleDTO);
		
		ArticleDTO dto = this.mngr.createArticle(articleDTO);
		
		return this.api.success(dto, "Article Created");
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response show(@PathParam("id") String id) throws ApiException {
		
		ArticleDTO dto = this.mngr.getArticleById(id);
		
		return this.api.success(dto, "");
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Authorized
	public Response delete(@PathParam("id") String id) throws ApiException {

		if(this.mngr.deleteArticleById(id)) {
			return this.api.success(null, "Article removed");
		} else {
			return this.api.error(null, "Can not remove article");
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Authorized
	public Response update(ArticleDTO articleDTO) throws ApiException {
		
		this.mngr.validate(articleDTO);
		
		ArticleDTO dto = this.mngr.updateArticle(articleDTO);

		return this.api.success(dto, "Article updated");
	}

	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response all() throws ApiException {
		
		List<ArticleDTO> dtoList = this.mngr.getAllArticles();

		return this.api.success(dtoList, "");
	}

	@GET
	@Path("/category/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response allCategory(@PathParam("id") int id) throws ApiException {
		
		List<ArticleDTO> dtoList = this.mngr.getAllArticlesByCategoryId( id );

		return this.api.success(dtoList,"");
	}
}
