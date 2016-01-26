package com.twilio.chat;

import com.google.gson.Gson;
import com.twilio.sdk.auth.AccessToken;
import com.twilio.sdk.auth.IpMessagingGrant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/token")
public class TokenServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String identity = request.getParameter("identity");
        String device = request.getParameter("device");

        // Create an endpoint ID which uniquely identifies the user on their current device
        String appName = "TwilioChatDemo";
        String endpointId = appName + ":" + identity + ":" + device;

        // Create IP messaging grant
        IpMessagingGrant grant = new IpMessagingGrant();
        grant.setEndpointId(endpointId);
        grant.setServiceSid(System.getenv("TWILIO_IPM_SERVICE_SID"));

        // Create access token
        AccessToken token = new AccessToken.Builder(
                System.getenv("TWILIO_ACCOUNT_SID"),
                System.getenv("TWILIO_API_KEY"),
                System.getenv("TWILIO_API_SECRET")
        ).identity(identity).grant(grant).build();

        response.setContentType("appliction/json");

        Map<String, String> json = new HashMap<>();
        json.put("identity", identity);
        json.put("token", token.toJWT());

        // Render JSON response
        Gson gson = new Gson();
        gson.toJson(json);

        try (BufferedWriter responseWriter = new BufferedWriter(response.getWriter())){
            responseWriter.write(gson.toJson(json));
            responseWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
