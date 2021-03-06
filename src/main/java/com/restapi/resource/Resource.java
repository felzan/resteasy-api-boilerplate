package com.restapi.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.restapi.filter.Secured;
import com.restapi.utils.RESTClient;

@Path("/")
@Stateless
public class Resource {

  @Inject
  private RESTClient rc;

  @GET
  @Path("rest")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRestClient(@Encoded @QueryParam("url") String url) throws Exception{
    return Response.status(200).entity(rc.get(url)).build();
  }

  @PUT
  @Path("rest")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response putRestClient(String payload,
    @Encoded @QueryParam("url") String url) throws Exception{
    return Response.status(200).entity(rc.put(url, payload)).build();
  }

  @POST
  @Path("rest")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postRestClient(String payload,
    @Encoded @QueryParam("url") String url) throws Exception{
    return Response.status(200).entity(rc.post(url, payload)).build();
  }

  @DELETE
  @Path("rest")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response deleteRestClient(@Encoded @QueryParam("url") String url) throws Exception{
    return Response.status(200).entity(rc.delete(url)).build();
  }

  @GET
  @Path("{version}/hello")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getHelloWorld(
      @DefaultValue("v1") @PathParam("version") final String version) {
    final String result = "Hello World!" + version;
    return Response.status(200).entity(result).build();
  }

  @GET
  @Path("v2/hello")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getHelloParam(@QueryParam("param") final String param) {
    final String result = "Hello " + param + "!";
    return Response.status(200).entity(result).build();
  }

  @POST
  @Path("/felzan")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response printMessage(@MatrixParam("id") final String id, @MatrixParam("name") final String name, final String payload) {
    final String result = id + ":" + name + "\n" + payload;
    return Response.status(200).entity(result).build();
  }
}
