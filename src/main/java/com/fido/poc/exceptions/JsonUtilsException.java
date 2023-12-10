package com.fido.poc.exceptions;

public class JsonUtilsException extends RuntimeException {

  public JsonUtilsException(String message, Throwable e) {
    super(message, e);
  }
}
