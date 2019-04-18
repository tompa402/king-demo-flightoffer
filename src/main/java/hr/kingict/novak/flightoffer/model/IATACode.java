package hr.kingict.novak.flightoffer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IATACode {

    @JsonAlias({"code", "iataCode"})
    @NotEmpty(message = "Choose depart airport")
    private String code;

    @JsonAlias({"name"})
    @NotEmpty(message = "Choose arrival airport")
    private String name;
}
