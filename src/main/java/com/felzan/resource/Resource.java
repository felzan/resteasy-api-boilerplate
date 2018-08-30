package com.felzan.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Resource {

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
