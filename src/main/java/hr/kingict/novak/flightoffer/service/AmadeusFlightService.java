package hr.kingict.novak.flightoffer.service;

import hr.kingict.novak.flightoffer.configuration.AmadeusServiceConfig;
import hr.kingict.novak.flightoffer.model.Currency;
import hr.kingict.novak.flightoffer.model.FlightOffer;
import hr.kingict.novak.flightoffer.model.amadeus.AmadeusFlightOffer;
import hr.kingict.novak.flightoffer.model.amadeus.AmadeusResponse;
import hr.kingict.novak.flightoffer.model.dto.FlightOfferDto;
import hr.kingict.novak.flightoffer.repository.FlightOfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AmadeusFlightService {

    private final AmadeusAuthService authService;
    private final AmadeusServiceConfig amadeusServiceConfig;
    private final RestTemplate rt;
    private final FlightOfferRepository repository;

    @Autowired
    public AmadeusFlightService(AmadeusAuthService authService,
                                AmadeusServiceConfig config,
                                RestTemplate rt,
                                FlightOfferRepository repository) {
        this.authService = authService;
        this.amadeusServiceConfig = config;
        this.rt = rt;
        this.repository = repository;
    }

    public List<FlightOfferDto> getLowFareFlights(FlightOffer flightOffer) {
        populateDefaultValues(flightOffer);

        FlightOfferDto query = new FlightOfferDto(flightOffer);
        List<FlightOfferDto> offers = repository.findAll(Example.of(query));
        if (!offers.isEmpty()) {
            return offers;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.amadeus+json");
        headers.set("Authorization", "Bearer " + authService.getAccessToken());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AmadeusResponse> response = rt.exchange(createUri(flightOffer), HttpMethod.GET, entity, AmadeusResponse.class);

        offers = response.getBody().getData().stream()
                .map(offer -> mapToDto(offer, response.getBody().getMeta().getCurrency().toString(), flightOffer.getPassenger()))
                .collect(Collectors.toList());

        repository.saveAll(offers);
        return offers;
    }

    private String createUri(FlightOffer flightOffer) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(amadeusServiceConfig.getUrl())
                .path("/shopping/flight-offers")
                .queryParam("origin", flightOffer.getFrom().getCode())
                .queryParam("destination", flightOffer.getTo().getCode())
                .queryParam("departureDate", flightOffer.getFlightDepart())
                .queryParam("max", 250) // maximum number of flight offers to return
                .queryParam("adults", flightOffer.getPassenger())
                .queryParam("currency", flightOffer.getCurrency().toString());
        if (flightOffer.getFlightReturn() != null) {
            builder.queryParam("returnDate", flightOffer.getFlightReturn());
        }

        log.info("Rest URI: {}", builder.toUriString());
        return builder.toUriString();
    }

    private void populateDefaultValues(FlightOffer flightOffer) {
        if (flightOffer.getPassenger() == null) {
            flightOffer.setPassenger(1);
        }
        if (flightOffer.getCurrency() == null) {
            flightOffer.setCurrency(Currency.EUR);
        }
    }

    private FlightOfferDto mapToDto(AmadeusFlightOffer flightOffer, String currency, Integer noOfPassengers) {
        FlightOfferDto fo = new FlightOfferDto();
        int serviceSize = flightOffer.getOfferItems().get(0).getServices().size(); // 1 - one way flight, 2 - round-trip ticket
        int transferDepart = flightOffer.getOfferItems().get(0).getServices().get(0).getSegments().size();
        int transferReturn = flightOffer.getOfferItems().get(0).getServices().get(serviceSize - 1).getSegments().size();

        fo.setFromAirport(flightOffer.getOfferItems().get(0).getServices().get(0).getSegments().get(0).getFlightSegment().getDeparture().getIataCode());
        fo.setReturnAirport(flightOffer.getOfferItems().get(0).getServices().get(0).getSegments().get(transferDepart - 1).getFlightSegment().getArrival().getIataCode());
        fo.setDepartDate(flightOffer.getOfferItems().get(0).getServices().get(0).getSegments().get(0).getFlightSegment().getDeparture().getAt());
        fo.setNumberOfTransfersDepart(transferDepart);

        if (serviceSize > 1) {
            fo.setArrivalDate(flightOffer.getOfferItems().get(0).getServices().get(serviceSize - 1).getSegments().get(transferReturn - 1).getFlightSegment().getDeparture().getAt());
            fo.setNumberOfTransfersArrival(transferReturn);
        }
        fo.setTotalAmount(flightOffer.getOfferItems().get(0).getPrice().getTotal());
        fo.setCurrency(currency);
        fo.setNumberOfPassengers(noOfPassengers);
        return fo;
    }

    @Scheduled(cron = "0 5 1 * * ?", zone = "CET")
    private void deleteOldData() {
        log.info("Creating new task that will clean old values from database.");
        FlightOfferDto offer = new FlightOfferDto();
        offer.setDepartDate(LocalDate.now().minusDays(1));
        List<FlightOfferDto> oldOffers = repository.findAll(Example.of(offer));
        repository.deleteInBatch(oldOffers);
        log.info("Job done.");
    }
}
