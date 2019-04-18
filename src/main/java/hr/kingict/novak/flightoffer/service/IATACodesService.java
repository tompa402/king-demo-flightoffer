package hr.kingict.novak.flightoffer.service;

import hr.kingict.novak.flightoffer.configuration.IATACodesServiceConfig;
import hr.kingict.novak.flightoffer.model.IATACode;
import hr.kingict.novak.flightoffer.model.IATACodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class IATACodesService implements IATACodeService {

    private final RestTemplate rt;
    private final IATACodesServiceConfig serviceConfig;

    private List<IATACode> iataCodes;

    @Autowired
    public IATACodesService(@Qualifier("sslRestTemplate") RestTemplate rt, IATACodesServiceConfig serviceConfig) {
        this.rt = rt;
        this.serviceConfig = serviceConfig;
        loadInitialData();
    }

    @Override
    public List<IATACode> getIataCodesByName(String input) {
        String lowerCaseInput = input.toLowerCase();
        return iataCodes.stream()
                .filter(c -> c.getName().toLowerCase().contains(lowerCaseInput) ||
                        c.getCode().toLowerCase().contains(lowerCaseInput))
                .collect(Collectors.toList());
    }

    public List<IATACode> getAll(){
        return this.iataCodes;
    }

    private void loadInitialData() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceConfig.getUrl())
                .queryParam("api_key", serviceConfig.getApiKey());
        ResponseEntity<IATACodeResponse> response = rt.getForEntity(builder.toUriString(), IATACodeResponse.class);
        iataCodes = response.getBody().getData();
    }
}
