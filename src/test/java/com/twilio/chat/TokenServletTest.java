package com.twilio.chat;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TokenServletTest {

    @Test
    public void shouldNotRequestATokenWhenIdentityAndDeviceAreNotProvided() {
        HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
        when(mockedServletRequest.getParameter("device")).thenReturn(null);
        when(mockedServletRequest.getParameter("identity")).thenReturn(null);

        HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

        TwilioTokenCreator mockedTokenCreator = mock(TwilioTokenCreator.class);

        TokenServlet tokenServlet = new TokenServlet(mockedTokenCreator);

        tokenServlet.doPost(mockedServletRequest, mockedServletResponse);

        verify(mockedTokenCreator, never()).generateToken(anyString(), anyString());
    }

    @Test
    public void shouldGenerateATokenWhenIdentityAndDeviceAreProvided() throws IOException {
        HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
        when(mockedServletRequest.getParameter("device")).thenReturn("my_device");
        when(mockedServletRequest.getParameter("identity")).thenReturn("me");

        HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);
        when(mockedServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));

        TwilioTokenCreator mockedTokenCreator = mock(TwilioTokenCreator.class);

        TokenServlet tokenServlet = new TokenServlet(mockedTokenCreator);

        tokenServlet.doPost(mockedServletRequest, mockedServletResponse);

        verify(mockedTokenCreator, times(1)).generateToken(anyString(), anyString());

    }
}
