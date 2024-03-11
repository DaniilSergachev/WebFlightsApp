package by.DaniilSergachev.je.jdbc.servlet;

import by.DaniilSergachev.je.jdbc.service.FlightService;
import by.DaniilSergachev.je.jdbc.utils.jspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flights = flightService.findAll();
        req.setAttribute("flights",flights);

        req.getRequestDispatcher(jspHelper.getPath("content")).forward(req,resp);

    }
}
