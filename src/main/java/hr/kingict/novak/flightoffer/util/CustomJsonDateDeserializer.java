package hr.kingict.novak.flightoffer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomJsonDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext) throws IOException {

        String date = jsonParser.getText();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
        return offsetDateTime.toLocalDate();
    }
}