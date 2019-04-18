package hr.kingict.novak.flightoffer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class IATACodeResponse {

    @JsonAlias({"response", "data"})
    private List<IATACode> data;
}
