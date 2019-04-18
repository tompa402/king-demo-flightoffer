package hr.kingict.novak.flightoffer.model;

import hr.kingict.novak.flightoffer.util.ValidStartEndDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ValidStartEndDate(startDate = "flightDepart", endDate = "flightReturn", message = "Return date can't be before depart date.")
public class FlightOffer {

    @Valid
    private IATACode from;
    @Valid
    private IATACode to;

    @NotNull(message = "Chose date of depart")
    @DateTimeFormat(pattern = "dd.MM.yyyy.")
    @FutureOrPresent(message = "Depart date can't be in the past")
    private LocalDate flightDepart;

    @DateTimeFormat(pattern = "dd.MM.yyyy.")
    @FutureOrPresent(message = "Return date can't be in the past")
    private LocalDate flightReturn;

    @Min(value = 1, message = "Passenger number can't be negative")
    @Max(value = 9, message = "Passenger number can't be greater than 9")
    private Integer passenger;

    private Currency currency;

}
