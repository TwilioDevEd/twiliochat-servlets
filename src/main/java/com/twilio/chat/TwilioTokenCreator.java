package com.twilio.chat;

import com.twilio.sdk.auth.AccessToken;
import com.twilio.sdk.auth.IpMessagingGrant;

import java.util.HashMap;
import java.util.Map;

public class TwilioTokenCreator {

    private final AppConfig appConfig;

    public TwilioTokenCreator(AppConfig appConfig) {
        this.appConfig = appConfig;
        if (appConfig.isIncomplete()) {
            throw new IncompleteConfigException(appConfig);
        }
    }

    Map<String, String> generateToken(String identity, String endpointId) {
        IpMessagingGrant grant = new IpMessagingGrant();
        grant.setEndpointId(endpointId);
        grant.setServiceSid(appConfig.getTwilioIPMServiceSID());

        AccessToken token = new AccessToken.Builder(
                appConfig.getTwilioAccountSID(),
                appConfig.getTwilioAPIKey(),
                appConfig.getTwilioAPISecret()
        ).identity(identity).grant(grant).build();

        Map<String, String> json = new HashMap<>();
        json.put("identity", identity);
        json.put("token", token.toJWT());
        return json;
    }
}
