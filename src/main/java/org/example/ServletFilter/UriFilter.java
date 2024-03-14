package org.example.ServletFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class UriFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UriFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Start URI checking");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Request URI: {}", req.getRequestURL());

        chain.doFilter(request, response);

        logger.info("Return URI: {}", req.getRequestURL());
    }

    @Override
    public void destroy() {
        logger.info("End URI checking");
        Filter.super.destroy();
    }
}
