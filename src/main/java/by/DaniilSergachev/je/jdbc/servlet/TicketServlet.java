package by.DaniilSergachev.je.jdbc.servlet;

import by.DaniilSergachev.je.jdbc.service.TicketService;
import by.DaniilSergachev.je.jdbc.utils.jspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet  extends HttpServlet {
    private final TicketService ticketService = TicketService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        int flightId = Integer.parseInt(req.getParameter("flightId"));
        req.setAttribute("tickets",ticketService.findAllByFlightId(flightId));
        req.getRequestDispatcher(jspHelper.getPath("tickets")).forward(req,resp);

//        try(var writer = resp.getWriter()){
//            writer.write("<h1>Купленные билеты</h1>");
//            writer.write("<ul>");
//            ticketService.findAllByFlightId(flightId).forEach(ticketDto -> writer.write("""
//                    <li>%s</li>
//                    """.formatted(ticketDto.seatNo())));
//            writer.write("</ul>");
//
//        }
    }
}
