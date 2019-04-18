package hr.kingict.novak.flightoffer.service;

import hr.kingict.novak.flightoffer.configuration.AmadeusServiceConfig;
import hr.kingict.novak.flightoffer.model.IATACode;
import hr.kingict.novak.flightoffer.model.IATACodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Service
public class IATACodeAmadeusService implements IATACodeService {

    private final RestTemplate rt;
    private final AmadeusAuthService authService;
    private final AmadeusServiceConfig amadeusServiceConfig;

    @Autowired
    public IATACodeAmadeusService(@Qualifier("sslRestTemplate") RestTemplate rt, AmadeusAuthService amadeusAuthService, AmadeusServiceConfig amadeusServiceConfig) {
        this.rt = rt;
        this.authService = amadeusAuthService;
        this.amadeusServiceConfig = amadeusServiceConfig;
    }

    @Override
    public List<IATACode> getIataCodesByName(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.amadeus+json");
        headers.set("Authorization", "Bearer " + authService.getAccessToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(amadeusServiceConfig.getUrl())
                .path("/reference-data/locations")
                .queryParam("subType", "AIRPORT")
                .queryParam("keyword", keyword)
                .queryParam("view", "LIGHT");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<IATACodeResponse> response = rt.exchange(builder.toUriString(), HttpMethod.GET, entity, IATACodeResponse.class);

        return response.getBody().getData();
    }
}
