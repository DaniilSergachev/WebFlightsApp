package by.DaniilSergachev.je.jdbc.service;

import by.DaniilSergachev.je.jdbc.dao.FlightDao;
import by.DaniilSergachev.je.jdbc.dto.FlightDto;

import java.util.List;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getINSTANCE();
    public List<FlightDto> findAll(){
        return flightDao.findAll().stream().map(
                flight -> new FlightDto(flight.getId(),"%s - %s - %s".formatted(
                        flight.getArrivalAirportCode(),
                        flight.getDepartureAirportCode(),
                        flight.getStatus()))).toList();
    }

    public static FlightService getINSTANCE() {
        return INSTANCE;
    }

    private FlightService(){}
}
