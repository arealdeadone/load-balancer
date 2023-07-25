package tech.arvindrachuri.lb.core;

import com.google.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/*")
public class ForwardingServlet extends HttpServlet {

    private final BackendConfiguration configuration;

    @Inject
    public ForwardingServlet(BackendConfiguration config) {
        this.configuration = config;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        Backend backend = configuration.getBackend(request);

        try {
            backend.forward(request, response);
        } catch (Exception e) {
            log.error("Unable to service the request {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
