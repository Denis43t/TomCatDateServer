package org.example.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String timezoneParam = req.getParameter("timezone");
        if (!timezoneParam.contains("-")){
            timezoneParam=timezoneParam.replaceAll("UTC ","");
            timezoneParam ="+".concat(timezoneParam);
        }
        System.out.println(timezoneParam    );

        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                ZoneId.of(timezoneParam);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid timezone");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
