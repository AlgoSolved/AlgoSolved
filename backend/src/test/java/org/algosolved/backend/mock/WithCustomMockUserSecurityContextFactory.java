package org.algosolved.backend.mock;

import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory implements
        WithSecurityContextFactory<WithCustomJwtMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomJwtMockUser annotation) {

        String email = annotation.username();

        Authentication auth = new UsernamePasswordAuthenticationToken(email, "",
                List.of(new SimpleGrantedAuthority("USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);

        return context;
    }
}
