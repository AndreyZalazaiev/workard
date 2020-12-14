package andrew.projects.workard.Service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class CustomDateInternatsionalizator extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DateTimeFormatter pattern;
        String lang = LocaleContextHolder.getLocale().getLanguage();

        if (lang.equalsIgnoreCase("UK")) {
            pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.UK)
                    .withZone(ZoneId.of("Europe/Kiev"));

        } else {
            pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                    .withZone(ZoneId.of("Europe/London"));
        }
        jsonGenerator.writeObject((localDateTime.format(pattern)));
    }
}