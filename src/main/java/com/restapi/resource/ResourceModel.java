package com.restapi.resource;

public class ResourceModel {

  private String id;
  private String name;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ResourceModel{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
}
