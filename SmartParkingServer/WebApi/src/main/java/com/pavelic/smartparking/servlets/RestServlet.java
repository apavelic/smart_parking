package main.java.com.pavelic.smartparking.servlets;

import main.java.com.pavelic.smartparking.models.ParkingSettings;
import main.java.com.pavelic.smartparking.server.Server;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by Andrej on 15.07.2017..
 */
@WebServlet(name = "RestServlet", urlPatterns = "/")
public class RestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Runnable task = () -> Server.start();

            ExecutorService es = Executors.newFixedThreadPool(4);
            es.execute(new Thread(task));

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Parking RESTFUL service</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>REST SERVER IS STARTED</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
