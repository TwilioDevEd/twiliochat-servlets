package com.twilio.chat;

import javax.inject.Inject;

import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;

public class TwilioTokenCreator {

  private final AppConfig appConfig;

  @Inject
  public TwilioTokenCreator(AppConfig appConfig) {
    this.appConfig = appConfig;
    if (appConfig.isIncomplete()) {
      throw new IncompleteConfigException(appConfig);
    }
  }

  String generateToken(String identity, String endpointId) {
    ChatGrant grant = new ChatGrant();
    grant.setEndpointId(endpointId);
    grant.setServiceSid(appConfig.getTwilioChatServiceSID());

    AccessToken token = new AccessToken.Builder(
      appConfig.getTwilioAccountSID(),
      appConfig.getTwilioAPIKey(),
      appConfig.getTwilioAPISecret()
    ).identity(identity).grant(grant).build();

    return token.toJwt();
  }
}
