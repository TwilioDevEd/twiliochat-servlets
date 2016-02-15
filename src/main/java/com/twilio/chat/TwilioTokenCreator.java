package com.twilio.chat;

import com.twilio.sdk.auth.AccessToken;
import com.twilio.sdk.auth.IpMessagingGrant;

import javax.inject.Inject;

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
        IpMessagingGrant grant = new IpMessagingGrant();
        grant.setEndpointId(endpointId);
        grant.setServiceSid(appConfig.getTwilioIPMServiceSID());

        AccessToken token = new AccessToken.Builder(
                appConfig.getTwilioAccountSID(),
                appConfig.getTwilioAPIKey(),
                appConfig.getTwilioAPISecret()
        ).identity(identity).grant(grant).build();

        return token.toJWT();
    }
}
