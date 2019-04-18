package hr.kingict.novak.flightoffer.controller;

import hr.kingict.novak.flightoffer.model.Currency;
import hr.kingict.novak.flightoffer.model.FlightOffer;
import hr.kingict.novak.flightoffer.model.dto.FlightOfferDto;
import hr.kingict.novak.flightoffer.service.AmadeusFlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/flight-offer")
public class FlightController {

    private final AmadeusFlightService flightService;

    @Autowired
    public FlightController(AmadeusFlightService flightService) {
        this.flightService = flightService;
    }

    @ModelAttribute("currencies")
    public Currency[] setExpenseType() {
        return Currency.values();
    }

    @GetMapping
    public String flightOffer(Model model) {
        model.addAttribute("flightOffer", new FlightOffer());
        model.addAttribute("currencies", Currency.values());
        return "flights/flightOffer";
    }

    @PostMapping
    public ModelAndView processFlightOffer(@ModelAttribute("currencies") Currency currencies,
                                           @Validated FlightOffer flightOffer,
                                           Errors errors) {
        log.info("Validating input data");
        if (errors.hasErrors()) {
            log.info("Input data has an error");
            ModelAndView mav = new ModelAndView("flights/flightOffer");
            mav.addObject(flightOffer);
            mav.addObject(currencies);
            return mav;
        }

        List<FlightOfferDto> flights = flightService.getLowFareFlights(flightOffer);
        ModelAndView mav = new ModelAndView("flights/suggestedFlights");
        mav.addObject("flights", flights);
        mav.addObject("flightOffer", flightOffer);

        return mav;
    }

}
