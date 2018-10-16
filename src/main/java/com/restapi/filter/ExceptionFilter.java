package com.restapi.filter;

import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alibaba.fastjson.JSON;
import com.restapi.exception.GenericException;

@Provider
public class ExceptionFilter implements ExceptionMapper<GenericException> {

	private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public Response toResponse(final GenericException e) {

		log.severe("Exception => " + e.getMessage());

		return Response.serverError()
		.status(e.getHttpStatusCode())
		.type(MediaType.APPLICATION_JSON)
		.entity(JSON.toJSON("{\"errorCode\":" + e.getErrorCode() + ",\"message\":\"" + e.getMessage() + "\"}"))
		.build();
	}
}