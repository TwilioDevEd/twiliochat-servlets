package com.twilio.chat;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class TokenServletTest {

  @Test
  public void shouldNotRequestATokenWhenIdentityIsNotProvided() {
    HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
    when(mockedServletRequest.getParameter("identity")).thenReturn(null);

    HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

    TwilioTokenCreator mockedTokenCreator = mock(TwilioTokenCreator.class);

    TokenServlet tokenServlet = new TokenServlet(mockedTokenCreator);

    tokenServlet.doPost(mockedServletRequest, mockedServletResponse);

    verify(mockedTokenCreator, never()).generateToken(anyString());
  }

  @Test
  public void shouldGenerateATokenWhenIdentityISProvided() throws IOException {
    HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
    when(mockedServletRequest.getParameter("identity")).thenReturn("me");

    HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);
    when(mockedServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));

    TwilioTokenCreator mockedTokenCreator = mock(TwilioTokenCreator.class);

    TokenServlet tokenServlet = new TokenServlet(mockedTokenCreator);

    tokenServlet.doPost(mockedServletRequest, mockedServletResponse);

    verify(mockedTokenCreator, times(1)).generateToken(anyString());

  }
}
