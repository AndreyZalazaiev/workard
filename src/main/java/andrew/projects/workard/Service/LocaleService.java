package andrew.projects.workard.Service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Service
@NoArgsConstructor
public class LocaleService {

    public Locale obtainLocaleFromString(String locale) {
        if (locale == "uk") {
            return new Locale("uk", "ua");
        }
        return Locale.ENGLISH;
    }


}

