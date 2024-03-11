package by.DaniilSergachev.je.jdbc.dao;


import by.DaniilSergachev.je.jdbc.dto.TicketFilter;
import by.DaniilSergachev.je.jdbc.entity.Flight;
import by.DaniilSergachev.je.jdbc.entity.FlightStatus;
import by.DaniilSergachev.je.jdbc.entity.Ticket;
import by.DaniilSergachev.je.jdbc.exception.DaoException;
import by.DaniilSergachev.je.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Integer,Ticket> {
    private final static TicketDao INSTANCE = new TicketDao();
    private final static   FlightDao flightDao = FlightDao.getINSTANCE();
    private  final static   String Save_SQL = """
            INSERT INTO ticket(passport_no, passenger_name, flight_id, seat_no, cost)
            values (?,?,?,?,?)
            """;
    private final static String Delete_SQL = """
            DELETE from ticket where id = ?
            """;
    private final static String findAll_SQL = """
            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no, t.cost,
            f.flight_no, f.departure_date,
            f.departure_airport_code, f.arrival_date,
            f.arrival_airport_code, f.aircraft_id, f.status
            FROM ticket t
            JOIN public.flight f on f.id = t.flight_id
            """;

    private final static String findById_SQL = findAll_SQL + """
                where t.id = ?
            """;

    private final static String Update_SQL = """
            UPDATE ticket
             set passport_no = ?,
                  passenger_name = ?,
                  flight_id = ?,
                  seat_no = ?,
                  cost = ?
             where id = ?
            """;

    private final static String findAllByFlightId_SQL = findAll_SQL + """
                where t.flight_id = ?
            """;


    public List<Ticket> findAllByFlightId(int id){
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(findAllByFlightId_SQL)) {
            List<Ticket> tickets = new ArrayList<>();
            statement.setLong(1,id);
            var res = statement.executeQuery();
            while (res.next())
                tickets.add(buildTicket(res));
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Ticket> findById(Integer id){
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(findById_SQL))
        {
            statement.setInt(1,id);
            var res = statement.executeQuery();
            Ticket ticket = null;
            if(res.next())
                ticket = buildTicket(res);

            return  Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }


    }
    public Ticket save(Ticket ticket){
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(Save_SQL, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1,ticket.getPassportNo());
            statement.setString(2,ticket.getPassengerName());
            statement.setLong(3,ticket.getFlight().getId());
            statement.setString(4,ticket.getSeatNo());
            statement.setBigDecimal(5,ticket.getCost());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next())
                ticket.setId(keys.getInt("id"));

            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public boolean delete(Integer id){
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(Delete_SQL))
        {   statement.setLong(1,id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean update(Ticket ticket){
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(Update_SQL,Statement.RETURN_GENERATED_KEYS))
        {   statement.setString(1,ticket.getPassportNo());
            statement.setString(2,ticket.getPassengerName());
            statement.setInt(3,ticket.getFlight().getId());
            statement.setString(4,ticket.getSeatNo());
            statement.setBigDecimal(5,ticket.getCost());
            statement.setLong(6,ticket.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ticket> findAll(TicketFilter filter){
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.passengerName() != null){
            parameters.add(filter.passengerName());
            whereSql.add("passenger_name = ?");
        }
        if(filter.seatNo() != null){
            parameters.add("%" + filter.seatNo() + "%");
            whereSql.add("seat_no like ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        String where = whereSql.stream().collect(Collectors.joining(
                " AND",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ?"
        ));
        String sql = findAll_SQL + where ;



        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(sql))
        {   List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1,parameters.get(i));
                
            }
            System.out.println(statement);

            var resultSet = statement.executeQuery();
            while (resultSet.next())
                tickets.add(
                        buildTicket(resultSet)
                );

            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Ticket> findAll(){
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(findAll_SQL))
        {   List<Ticket> tickets = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next())
                tickets.add(
                        buildTicket(resultSet)
                );

            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static Ticket buildTicket(ResultSet resultSet) throws SQLException {
//        var flight = new Flight(resultSet.getInt("flight_id"),
//                resultSet.getString("flight_no"),
//                resultSet.getTimestamp("departure_date").toLocalDateTime(),
//                resultSet.getInt("departure_airport_code"),
//                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
//                resultSet.getInt("arrival_airport_code"),
//                resultSet.getInt("aircraft_id"),
//                FlightStatus.valueOf(resultSet.getString("status"))
//
//
//        );
        return new Ticket(resultSet.getInt("id"),
                resultSet.getString("passport_no"),
                resultSet.getString("passenger_name"),
                    flightDao.findById(
                            resultSet.getInt("flight_id"),
                            resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost"));
    }

    public static TicketDao getINSTANCE() {
        return INSTANCE;}

    private TicketDao(){

    }
}
