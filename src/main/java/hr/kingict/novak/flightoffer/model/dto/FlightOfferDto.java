package hr.kingict.novak.flightoffer.model.dto;

import hr.kingict.novak.flightoffer.model.FlightOffer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Cacheable
@Table(name = "QUERY")
public class FlightOfferDto {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "FROM_AIRPORT")
    private String fromAirport;

    @Column(name = "TO_AIRPORT")
    private String returnAirport;

    @Column(name = "DEPART_DATE")
    private LocalDate departDate;

    @Column(name = "ARRIVAL_AIRPORT")
    private LocalDate arrivalDate;

    @Column(name = "DEPART_TRANSFER")
    private Integer numberOfTransfersDepart;

    @Column(name = "ARRIVAL_TRANSFER")
    private Integer numberOfTransfersArrival;

    @Column(name = "AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "PASSENGER")
    private Integer numberOfPassengers;

    public FlightOfferDto(FlightOffer offer) {
        this.fromAirport = offer.getFrom().getCode();
        this.returnAirport = offer.getTo().getCode();
        this.departDate = offer.getFlightDepart();
        this.arrivalDate = offer.getFlightReturn();
        this.currency = offer.getCurrency().toString();
        this.numberOfPassengers = offer.getPassenger();
    }
}
