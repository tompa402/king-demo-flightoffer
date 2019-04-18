package hr.kingict.novak.flightoffer.model.amadeus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    private BigDecimal total;
    private BigDecimal totalTaxes;
}
