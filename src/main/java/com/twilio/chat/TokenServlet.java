package com.twilio.chat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.inject.Singleton;

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

    if (identity != null) {

      String generatedToken = tokenCreator.generateToken(identity);

      Map<String, String> json = new HashMap<>();
      json.put("identity", identity);
      json.put("token", generatedToken);
      renderJson(response, json);
    }

  }

  private void renderJson(HttpServletResponse response, Map<String, String> json) {
    Gson gson = new Gson();
    response.setContentType("application/json");
    try (BufferedWriter responseWriter = new BufferedWriter(response.getWriter())) {
      responseWriter.write(gson.toJson(json));
      responseWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
