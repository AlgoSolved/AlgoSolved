package org.algosolved.backend.core.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${client.base.url}")
    private String FRONTEND_ENDPOINT;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String username = oAuth2User.getAttribute("login");
        request.getSession().setAttribute("oauthUserName", username);

        response.sendRedirect(FRONTEND_ENDPOINT);
    }
}
