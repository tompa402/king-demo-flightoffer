package hr.kingict.novak.flightoffer.model.amadeus;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightStop {
    private String iataCode;
//    private AircraftEquipment newAircraft;
    private String duration;
    //private LocalDateTime arrivalAt;
    //private LocalDateTime departureAt;
}
