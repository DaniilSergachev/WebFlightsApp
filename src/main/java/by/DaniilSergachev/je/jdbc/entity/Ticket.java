package by.DaniilSergachev.je.jdbc.entity;
import java.math.BigDecimal;

public class Ticket {
    private Integer id;
    private String passportNo;
    private String passengerName;
    private Flight flight;
    private String seatNo;
    private BigDecimal cost;

    public Ticket() {
    }

    public Ticket(Integer id, String passportNo, String passengerName, Flight flightId, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passportNo = passportNo;
        this.passengerName = passengerName;
        this.flight = flightId;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", passportNo='" + passportNo + '\'' +
               ", passengerName='" + passengerName + '\'' +
               ", flightId=" + flight +
               ", seatNo='" + seatNo + '\'' +
               ", cost=" + cost +
               '}';
    }
}
