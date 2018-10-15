package com.restapi.utils;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

@Stateless
public class RESTClient {

  public String get(String url) throws Exception {
    Client client = ClientBuilder.newClient();
    WebTarget resourceTarget = client.target(url);
    
    Invocation invocation = resourceTarget
    .request("application/json")
    .buildGet();
    
    return invocation.invoke(String.class);
  }

  public String put(String url, String payload) throws Exception {
    Client client = ClientBuilder.newClient();
    WebTarget resourceTarget = client.target(url);
    
    Invocation invocation = resourceTarget
    .request("application/json")
    .buildPut(Entity.json(payload));
    
    String response = invocation.invoke(String.class);
    System.out.println(response);
    return response;
  }
}
