package hr.kingict.novak.flightoffer.model.amadeus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmadeusResponse {

    private List<AmadeusFlightOffer> data;
    private AmadeusMeta meta;


}
