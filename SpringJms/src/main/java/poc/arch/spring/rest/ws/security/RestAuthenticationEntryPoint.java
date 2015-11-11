package poc.arch.spring.rest.ws.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

        @Override
        public void commence( HttpServletRequest request, HttpServletResponse response,
                              AuthenticationException authException ) throws IOException {
            //401 is sent without the WWW-Authenticate header, as required by the HTTP Spec
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
        }
}
