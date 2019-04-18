package hr.kingict.novak.flightoffer.service;

import hr.kingict.novak.flightoffer.configuration.AmadeusServiceConfig;
import hr.kingict.novak.flightoffer.model.amadeus.AmadeusAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AmadeusAuthService {

    private final AmadeusServiceConfig amadeusServiceConfig;

    private final RestTemplate rt;
    private AmadeusAuth auth;

    @Autowired
    public AmadeusAuthService(@Qualifier("sslRestTemplate") RestTemplate rt, AmadeusServiceConfig amadeusServiceConfig) {
        this.rt = rt;
        this.amadeusServiceConfig = amadeusServiceConfig;
    }

    private void getAccessTokenRest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", amadeusServiceConfig.getApiKey());
        map.add("client_secret", amadeusServiceConfig.getApiSecret());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(amadeusServiceConfig.getUrl())
                .path("/security/oauth2/token");

        ResponseEntity<AmadeusAuth> response = rt.postForEntity(builder.toUriString(), entity, AmadeusAuth.class);
        this.auth = response.getBody();
        this.auth.setCreated(LocalDateTime.now());
    }

    public String getAccessToken(){
        if (auth == null || ChronoUnit.MINUTES.between(this.auth.getCreated(), LocalDateTime.now()) > 29) {
            getAccessTokenRest();
        }

        return this.auth.getAccessToken();
    }
}
