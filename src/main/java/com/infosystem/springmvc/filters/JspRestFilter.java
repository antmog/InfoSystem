package com.infosystem.springmvc.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class JspRestFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getMethod();
        if (method.equalsIgnoreCase("delete")||method.equalsIgnoreCase("put")) {
            chain.doFilter(wrapRequest(req), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }

    private static HttpServletRequestWrapper wrapRequest(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getMethod() {
                return "POST";
            }
        };
    }
}