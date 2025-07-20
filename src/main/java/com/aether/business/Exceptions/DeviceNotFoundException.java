package com.aether.business.Exceptions;

public class DeviceNotFoundException extends RuntimeException {
  public DeviceNotFoundException(String message) {
    super(message);
  }
}
