package com.traffic.police;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class RestConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Authorization, Content-Type, Accept,Produces");
        response.setHeader("Cache-Control", "private,no-store,no-cache,must-revalidate");
        response.setHeader("If-Modified-Since", "0");
        response.setHeader("Expires", "-1");
        response.setHeader("Pragma", "no-cache");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        filterConfig.getServletContext();

    }

    @Override
    public void destroy() {
    }

}