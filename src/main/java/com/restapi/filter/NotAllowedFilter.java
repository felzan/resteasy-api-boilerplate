package com.restapi.filter;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Provider
public class NotAllowedFilter implements ExceptionMapper<NotAllowedException> {

	@Override
	public Response toResponse(NotAllowedException exception) {
		return Response.serverError()
		.status(Status.NOT_FOUND)
		.build();
	}
}