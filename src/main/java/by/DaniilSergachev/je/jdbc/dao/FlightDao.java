package by.DaniilSergachev.je.jdbc.dao;

import by.DaniilSergachev.je.jdbc.entity.Flight;
import by.DaniilSergachev.je.jdbc.entity.FlightStatus;
import by.DaniilSergachev.je.jdbc.exception.DaoException;
import by.DaniilSergachev.je.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Integer, Flight> {
    private final static FlightDao INSTANCE = new FlightDao();


    private final static String FIND_ALL_SQL = """
            SELECT id, flight_no, departure_date,
             departure_airport_code, arrival_date,
             arrival_airport_code, aircraft_id, status
            from flight
            """;

    private final static String FIND_ALL_ByID_SQL = FIND_ALL_SQL + """
            where id = ?
            """;

    private final static String Save_SQL = """
                INSERT INTO flight(flight_no, departure_date, departure_airport_code,
                 arrival_date, arrival_airport_code, aircraft_id, status)
            values(?,?,?,?,?,?,?)
            """;
    private final static String Update_SQL = """
            UPDATE flight
             set flight_no = ?,
                  departure_date = ?,
                  departure_airport_code = ?,
                  arrival_date = ?,
                  arrival_airport_code = ?,
                  aircraft_id = ?,
                  status = ?
             where id = ?
            """;

    private final static String Delete_SQL = """
            DELETE from flight where id = ?
            """;

    @Override
    public boolean update(Flight flight) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(Update_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, flight.getFlight_no());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setInt(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setInt(5, flight.getArrivalAirportCode());
            statement.setLong(6, flight.getAircraft_id());
            statement.setString(7, flight.getStatus().name());
            statement.setInt(8, flight.getId());


            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(Save_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, flight.getFlight_no());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setLong(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setInt(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraft_id());
            statement.setString(7, flight.getStatus().name());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                flight.setId(keys.getInt("id"));

            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(Delete_SQL)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Integer id) {
        try(var connection = ConnectionManager.get()){
            return findById(id,connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Flight> findById(Integer id, Connection connection) {
        try (
             var statement = connection.prepareStatement(FIND_ALL_ByID_SQL)) {
            statement.setInt(1, id);
            var res = statement.executeQuery();
            Flight flight = null;
            if (res.next())
                flight = flightBuilder(res);

            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Flight> flights = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next())
                flights.add(
                        flightBuilder(resultSet)
                );

            return flights;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private Flight flightBuilder(ResultSet resultSet) throws SQLException {
        return new Flight(resultSet.getInt("id"),
                resultSet.getString("flight_no"),
                resultSet.getTimestamp("departure_date").toLocalDateTime(),
                resultSet.getInt("departure_airport_code"),
                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                resultSet.getInt("arrival_airport_code"),
                resultSet.getInt("aircraft_id"),
                FlightStatus.valueOf(resultSet.getString("status"))


        );


    }


    public static FlightDao getINSTANCE() {
        return INSTANCE;
    }

    private FlightDao() {

    }
}
