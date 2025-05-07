package com.dasher.dashboard.adapter.rest.exception;


public class TaskExistingException extends RuntimeException {
  public TaskExistingException(String message) {
    super(message);
  }
}
