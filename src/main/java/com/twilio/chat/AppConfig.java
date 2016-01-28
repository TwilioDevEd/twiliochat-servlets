package com.twilio.chat;

public class AppConfig {
    String getTwilioIPMServiceSID() {
        return System.getenv("TWILIO_IPM_SERVICE_SID");
    }

    String getTwilioAPISecret() {
        return System.getenv("TWILIO_API_SECRET");
    }

    String getTwilioAPIKey() {
        return System.getenv("TWILIO_API_KEY");
    }

    String getTwilioAccountSID() {
        return System.getenv("TWILIO_ACCOUNT_SID");
    }
}
