package by.DaniilSergachev.je.jdbc.servlet;

import by.DaniilSergachev.je.jdbc.service.FlightService;
import by.DaniilSergachev.je.jdbc.utils.jspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/flights")
public class FlightServlet  extends HttpServlet {
    private final FlightService flightService = FlightService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.setAttribute("flights", flightService.findAll());
        req.getRequestDispatcher(jspHelper.getPath("flights")).forward(req,resp);

//        try(var writer = resp.getWriter()){
//            writer.write("<h1>Список перелетов</h1>");
//            writer.write("<ul>");
//            flightService.findAll().stream().forEach(flightDto -> writer.write("""
//                <li>
//                    <a href='/tickets?flightId=%d'>%s</a>
//                </li>
//                """.formatted(flightDto.id(),flightDto.description())));
//            writer.write("</ul>");
//
//        };




    }
}
