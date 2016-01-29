package com.twilio.chat;

import com.google.gson.Gson;
import com.google.inject.Singleton;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

@Singleton
public class TokenServlet extends HttpServlet {

    private final TwilioTokenCreator tokenCreator;

    @Inject
    public TokenServlet(TwilioTokenCreator tokenCreator) {
        this.tokenCreator = tokenCreator;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String identity = request.getParameter("identity");
        String device = request.getParameter("device");

        if (identity != null && device != null) {
            // Create an endpoint ID which uniquely identifies the user on their current device
            String appName = "TwilioChatDemo";
            String endpointId = appName + ":" + identity + ":" + device;

            // Create IP messaging grant
            Map<String, String> json = tokenCreator.generateToken(identity, endpointId);

            renderJson(response, json);
        }

    }

    private void renderJson(HttpServletResponse response, Map<String, String> json) {
        Gson gson = new Gson();
        response.setContentType("application/json");
        try (BufferedWriter responseWriter = new BufferedWriter(response.getWriter())){
            responseWriter.write(gson.toJson(json));
            responseWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
