package hr.kingict.novak.flightoffer.service;

import hr.kingict.novak.flightoffer.model.IATACode;

import java.util.List;

public interface IATACodeService {
    List<IATACode> getIataCodesByName(String keyword);
}
