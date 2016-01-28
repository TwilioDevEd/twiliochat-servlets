package com.twilio.chat;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenServletTest {

    @Test(expected = IncompleteConfigException.class)
    public void shouldNotInitializeTokenServletWhenAppConfigIsIncomplete() {
        AppConfig mockedAppConfig = mock(AppConfig.class);
        when(mockedAppConfig.isIncomplete()).thenReturn(true);
        TokenServlet tokenServlet = new TokenServlet(mockedAppConfig);
    }
}
