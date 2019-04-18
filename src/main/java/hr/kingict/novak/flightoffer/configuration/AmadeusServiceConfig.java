package hr.kingict.novak.flightoffer.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("services.flight-offer")
public class AmadeusServiceConfig {
    @NotBlank
    private String url;
    @NotBlank
    private String apiKey;
    @NotBlank
    private String apiSecret;
}
