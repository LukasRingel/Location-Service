package locationservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

@Slf4j
@Component
@AllArgsConstructor
public class LocationServiceFilter extends GenericFilterBean {

    private static final String remoteAddressHeaderName = "X-FORWARDED-FOR";

    private final Properties properties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!(request instanceof HttpServletRequest httpServletRequest)) {
            return;
        }
        var httpServletResponse = (HttpServletResponse) response;
        if (isMonitoringAddress(httpServletRequest)) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You address is not in our system.");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isMonitoringAddress(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().contains("actuator") &&
              !getMonitoringAddress().contains(httpServletRequest.getHeader(remoteAddressHeaderName));
    }

    private Collection<String> getMonitoringAddress() {
        return Arrays.asList(properties.getProperty("locationservice.monitoringaddress").split(","));
    }
}