package hr.kingict.novak.flightoffer.model.amadeus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {
    private FlightSegment flightSegment;
}
