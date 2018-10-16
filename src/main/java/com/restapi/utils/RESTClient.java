package com.restapi.utils;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.restapi.exception.GenericException;

@Stateless
public class RESTClient {

  private WebTarget target(String url) throws Exception {
    Client client = ClientBuilder.newClient();
    WebTarget resourceTarget = client.target(url);
    return resourceTarget;
  }

  private String getResponse(Invocation invocation) throws Exception {
    String response = null;
    try {
      response = invocation.invoke(String.class); 
    } catch (Exception e) {
      throw new GenericException(e.getMessage());
    }
    return response;
  }

  public String get(String url) throws Exception {
    Invocation invocation = target(url)
    .request("application/json")
    .buildGet();

    return getResponse(invocation);
  }

  public String put(String url, String payload) throws Exception {
    Invocation invocation = target(url)
    .request("application/json")
    .buildPut(Entity.json(payload));

    return getResponse(invocation);
  }

  public String post(String url, String payload) throws Exception {
    Invocation invocation = target(url)
    .request("application/json")
    .buildPost(Entity.json(payload));

    return getResponse(invocation);
  }

  public String delete(String url) throws Exception {
    Invocation invocation = target(url)
    .request("application/json")
    .buildDelete();

    return getResponse(invocation);
  }
}
