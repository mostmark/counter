package com.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebServlet("/counter")
public class CounterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieve or create a new session
        HttpSession session = request.getSession(true);

        if(session.isNew()){
            System.out.println("--- NEW SESSION! ---");
        }

        // Retrieve the counter attribute from the session
        Integer counter = (Integer) session.getAttribute("counter");
        if (counter == null) {
            counter = 0; // Initialize if not present
        }
        counter++; // Increment the counter
        session.setAttribute("counter", counter);

        // Get the session ID (JSESSIONID)
        String sessionId = session.getId();

        // Get the server hostname
        String hostname = "Unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Set the content type and output the counter and session ID
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Session Counter</title></head>");
            out.println("<body>");
            out.println("<h2>Counter: " + counter + "</h2>");
            out.println("<p>JSESSIONID: " + sessionId + "</p>");
            out.println("<p>Server Hostname: " + hostname + "</p>");
            out.println("</body>");
            out.println("</html>");
        }

    }

}

