package com.jy.study.spring.security.filter;

import javax.servlet.*;
import java.io.IOException;

public class ThirdPartyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("third party init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("third party filter start");
        chain.doFilter(request, response);
        System.out.println("third party filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("third party destroy");
    }
}
