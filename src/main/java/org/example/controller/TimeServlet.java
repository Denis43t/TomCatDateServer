package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value="/time")
public class TimeServlet extends HttpServlet {
    ZonedDateTime date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezone=req.getParameter("timezone");
        if(timezone==null){
            date=ZonedDateTime.now();
        } else if (timezone.contains("-")) {
            timezone=timezone.replaceAll("UTC","");
            ZoneOffset zoneOffSet= ZoneOffset.of(timezone);
            date = OffsetDateTime.now(zoneOffSet).toZonedDateTime();
        } else {
            timezone=timezone.replaceAll("UTC ","");
            timezone="+".concat(timezone);
            ZoneOffset zoneOffSet= ZoneOffset.of(timezone);
            date = OffsetDateTime.now(zoneOffSet).toZonedDateTime();
        }
        resp.setContentType("text/html");
        resp.getWriter().write(date.format(formatter)+" UTC"+timezone);
        resp.getWriter().close();

    }
}
