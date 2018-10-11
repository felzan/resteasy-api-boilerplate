package com.felzan.filter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.alibaba.fastjson.JSON;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter, MessageBodyReader<String>{

	private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

	@Context
	private HttpServletRequest servletRequest;

	@Override
	public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}
	@Override
	public String readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
				String json = JSON.parseObject(entityStream, String.class);
				log.info("reqBody: " + json);
		return json;
	}
	
	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
		final String ip = req.getHeaderString("X-Forwarded-For");
    // Basic
		String info = "[from: " + ip + "] [" + req.getMethod() + "] " + req.getUriInfo().getPath();

    // QueryParam
		if (servletRequest.getQueryString() != null) {
			info = info.concat('?' + servletRequest.getQueryString());
		}

		log.info(info);
    // Response Payload
		if (res.getEntity() != null) {
			String json = res.getEntity().toString();
			log.info(" resBody:" + json);
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	}

}