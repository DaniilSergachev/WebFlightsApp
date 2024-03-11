package by.DaniilSergachev.je.jdbc.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {
    Integer id;
    String flight_no;
    private LocalDateTime departureDate;
    private Integer departureAirportCode;
    private LocalDateTime arrivalDate;
    private Integer arrivalAirportCode;
    private Integer aircraft_id;
    private FlightStatus status;


    public Flight(Integer id, String flight_no, LocalDateTime departureDate, Integer departureAirportCode, LocalDateTime arrivalDate, Integer arrivalAirportCode, Integer aircraft_id, FlightStatus status) {
        this.id = id;
        this.flight_no = flight_no;
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportCode = arrivalAirportCode;
        this.aircraft_id = aircraft_id;
        this.status = status;
    }

    public Flight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(Integer departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(Integer arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public Integer getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(Integer aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Flight{" +
               "id=" + id +
               ", flight_no='" + flight_no + '\'' +
               ", departureDate=" + departureDate +
               ", departureAirportCode=" + departureAirportCode +
               ", arrivalDate=" + arrivalDate +
               ", arrivalAirportCode=" + arrivalAirportCode +
               ", aircraft_id=" + aircraft_id +
               ", status=" + status +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id) && Objects.equals(flight_no, flight.flight_no) && Objects.equals(departureDate, flight.departureDate) && Objects.equals(departureAirportCode, flight.departureAirportCode) && Objects.equals(arrivalDate, flight.arrivalDate) && Objects.equals(arrivalAirportCode, flight.arrivalAirportCode) && Objects.equals(aircraft_id, flight.aircraft_id) && status == flight.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flight_no, departureDate, departureAirportCode, arrivalDate, arrivalAirportCode, aircraft_id, status);
    }
}
