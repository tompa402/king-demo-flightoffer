package hr.kingict.novak.flightoffer.model.amadeus;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hr.kingict.novak.flightoffer.util.CustomJsonDateDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FlightEndPoint {

    private String iataCode;
    private String terminal;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate at;
}
