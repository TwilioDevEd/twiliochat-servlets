package com.twilio.chat;

public class IncompleteConfigException extends RuntimeException {
  public IncompleteConfigException(AppConfig appConfig) {
    super("Incomplete config: " + appConfig);
  }
}
