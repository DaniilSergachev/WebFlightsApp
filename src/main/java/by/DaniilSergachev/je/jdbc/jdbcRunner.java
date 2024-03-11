package by.DaniilSergachev.je.jdbc;

import by.DaniilSergachev.je.jdbc.dao.FlightDao;
import by.DaniilSergachev.je.jdbc.dao.TicketDao;
import by.DaniilSergachev.je.jdbc.dto.TicketFilter;
import by.DaniilSergachev.je.jdbc.entity.Flight;
import by.DaniilSergachev.je.jdbc.entity.FlightStatus;
import by.DaniilSergachev.je.jdbc.entity.Ticket;
import by.DaniilSergachev.je.jdbc.utils.ConnectionManager;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class jdbcRunner {
    public static void main(String[] args) throws SQLException {
        var ticketDao = TicketDao.getINSTANCE();
        var flightDao = FlightDao.getINSTANCE();
        System.out.println(ticketDao.findAllByFlightId(2));
       /* Ticket ticket = new Ticket();
        ticket.setPassportNo("dfghzgzxg");
        ticket.setPassengerName("sergay");
        ticket.setFlightId(2);
        ticket.setSeatNo("345235reter");
        ticket.setCost(BigDecimal.TEN);*/

        //ticketDao.delete(5L);
       // var filter = new TicketFilter(null,null,2,0);

       // System.out.println(ticketDao.findAll(filter));
        //System.out.println(ticketDao.save(ticket));
        //System.out.println(ticketDao.delete(7L));
        //System.out.println(ticketDao.findAll());
        //System.out.println(ticketDao.findById(5L).get());
        //Ticket ticket1 = ticketDao.findById(3L).get();
        //ticket1.setSeatNo("5Z");
        //System.out.println(ticketDao.update(ticket1));
        //ticket.setSeatNo("5Z");
        //System.out.println(ticketDao.update(ticket));

      /*  var flightDao = FlightDao.getINSTANCE();
        Flight flight1 = new Flight();
        flight1.setFlight_no("1458");
        flight1.setDepartureDate(LocalDateTime.of(2024,3,1,12,30,15));
        flight1.setDepartureAirportCode(3);
        flight1.setArrivalDate(LocalDateTime.of(2024,4,2,14,25,45));
        flight1.setArrivalAirportCode(2);
        flight1.setAircraft_id(2);
        flight1.setStatus(FlightStatus.ARRIVED);
        System.out.println(flight1);
        System.out.println(flightDao.save(flight1));
        flight1.setStatus(FlightStatus.DEPARTED);
        System.out.println(flightDao.update(flight1));*/

//        String sql = """
//                SELECT * FROM ticket;
//                """;
//
//        try (var connection = ConnectionManager.get();
//             var statement = connection.createStatement()) {
//            var result = statement.executeQuery(sql);
//            while (result.next()) {
//                System.out.println(result.getString("passenger_name"));
//                System.out.println(result.getLong("flight_id"));
//                System.out.println(result.getBigDecimal("cost"));
//                System.out.println("-----------------");
//            }
//        }
//        System.out.println(getTicketByFlightId(2));
//        System.out.println(getFlightsBetween(LocalDate.of(2023, 2, 16).atStartOfDay(),
//                LocalDate.of(2023, 3, 1).atStartOfDay()));
//
//        checkMetaData();



    }

    public static List<Long> getTicketByFlightId(int flightId) {
        List<Long> tickets = new ArrayList<>();
        String sql = """
                SELECT * FROM ticket
                where flight_id = ?
                """;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            System.out.println(statement);

            statement.setInt(1, flightId);
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(result.getLong("int"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
        List<Long> flights = new ArrayList<>();
        String sql = """
                SELECT * from flight
                where departure_date between ? and ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            System.out.println(statement);
            statement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(statement);
            statement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(statement);


            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(result.getLong("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flights;
    }

    public static void checkMetaData() throws SQLException {
        try (Connection connection =ConnectionManager.get()){
            var metaDate = connection.getMetaData();
            var catalogs  = metaDate.getCatalogs();
            while(catalogs.next())
                System.out.println(catalogs.getString(1));
        }
    }
}