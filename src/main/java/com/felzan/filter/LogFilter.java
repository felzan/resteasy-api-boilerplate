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

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

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
				String entity = new Gson().toJson(IOUtils.toString(entityStream, "UTF-8"), String.class);
				entity = entity.replace("\\\"", "\"");
				entity = entity.replaceAll("\\\\n", "");
				entity = entity.replaceAll("\\\\t", "");
				entity = entity.replace("\"{", "{");
				entity = entity.replace("}\"", "}");
				log.info("reqBody: " + entity);
		return entity;
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
			String gsonRes = new Gson().toJson(res.getEntity(), String.class);
			gsonRes = gsonRes.subSequence(1, gsonRes.length()).toString();
			gsonRes = gsonRes.replace("\\\"", "\"");
			gsonRes = gsonRes.replaceAll("\\\\n", "");
			gsonRes = gsonRes.replaceAll("\\\\t", "");
			gsonRes = gsonRes.replace("\"{", "{");
			gsonRes = gsonRes.replace("}\"", "}");
			log.info(" resBody:" + gsonRes);
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	}

}