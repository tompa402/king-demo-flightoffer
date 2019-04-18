package hr.kingict.novak.flightoffer.model.amadeus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSegment {

    private FlightEndPoint departure;
    private FlightEndPoint arrival;
    private String carrierCode;
    private String number;
//    private AircraftEquipment aircraft;
//    private OperatingFlight operating;
    private String duration;
    private List<FlightStop> stops;
}
