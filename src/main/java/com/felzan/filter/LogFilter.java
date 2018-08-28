package com.felzan.filter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

@Provider
public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	@Context
	private HttpServletRequest servletRequest;

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
		final String ip = req.getHeaderString("X-Forwarded-For");
    // Basic
    String info = "[from: " + ip + "] [" + req.getMethod() + "] " + req.getUriInfo().getPath();
    // QueryParam
		if (servletRequest.getQueryString() != null) {
			info = info.concat('?' + servletRequest.getQueryString());
		}
    // Request Payload
		BufferedInputStream reqStream = new BufferedInputStream(req.getEntityStream());
    String payload = IOUtils.toString(reqStream, "UTF-8");
		String gsonReq = new Gson().toJson(payload, String.class);
    gsonReq = gsonReq.replace("\\\"", "\"");
    gsonReq = gsonReq.replaceAll("\\\\n", "");
    gsonReq = gsonReq.replaceAll("\\\\t", "");
    gsonReq = gsonReq.replace("\"{", "{");
    gsonReq = gsonReq.replace("}\"", "}");
		info = req.getEntityStream() != null ? info.concat(" $" + gsonReq) : info;
    // Response Payload
    String gsonRes = new Gson().toJson(res.getEntity());
    info = res.getEntity() != null ? info.concat(" #" + gsonRes) : info;

		log.info(info);
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	}

}