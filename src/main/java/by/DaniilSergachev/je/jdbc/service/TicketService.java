package by.DaniilSergachev.je.jdbc.service;

import by.DaniilSergachev.je.jdbc.dao.TicketDao;
import by.DaniilSergachev.je.jdbc.dto.TicketDto;
import lombok.Getter;

import java.util.List;

public class TicketService {
    @Getter
    private static final TicketService INSTANCE = new TicketService();

    public final TicketDao ticketDao = TicketDao.getINSTANCE();

    public List<TicketDto> findAllByFlightId(int flightId){
        return ticketDao.findAllByFlightId(flightId).stream().map(
                ticket -> new TicketDto(ticket.getId(),ticket.getFlight().getId(),ticket.getSeatNo())).toList();
    }


    private TicketService(){

    }

}
