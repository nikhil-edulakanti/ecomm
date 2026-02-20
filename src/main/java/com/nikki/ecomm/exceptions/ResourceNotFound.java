package com.nikki.ecomm.exceptions;

public class ResourceNotFound extends RuntimeException {

    String resourceName;
    String fieldName;
    String fieldValue;
    Long fieldId;

  public ResourceNotFound(String resourceName, String fieldName,  Long fieldId) {
    super(String.format("%s in %s with %d is not found", fieldName,resourceName , fieldId));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldId = fieldId;
  }
}
